package com.cesde.petmind.config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.cesde.petmind.model.enums.EstadoSolicitud;

// Hibernate crea un CHECK con los valores del enum cuando crea la tabla, pero ddl-auto=update no lo
// actualiza cuando el enum gana un valor (DOCUMENTOS_PENDIENTES). Sin este paso, guardar ese estado
// fallaria en una base que ya existia. Si el CHECK ya tiene todos los valores, no hace nada.
@Component
public class RestriccionEstadoSolicitud implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(RestriccionEstadoSolicitud.class);
    private static final String TABLA = "solicitudes_adopcion";
    private static final String NOMBRE = "solicitudes_adopcion_estado_check";

    private final DataSource dataSource;

    public RestriccionEstadoSolicitud(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void run(ApplicationArguments args) {
        try (Connection conexion = dataSource.getConnection()) {
            if (!"PostgreSQL".equalsIgnoreCase(conexion.getMetaData().getDatabaseProductName())) {
                return;
            }
            List<String[]> restricciones = restriccionesDeEstado(conexion);
            boolean desactualizada = restricciones.stream()
                    .anyMatch(r -> Arrays.stream(EstadoSolicitud.values()).anyMatch(e -> !r[1].contains("'" + e.name() + "'")));
            if (!desactualizada) {
                return;
            }
            String valores = Arrays.stream(EstadoSolicitud.values())
                    .map(e -> "'" + e.name() + "'")
                    .collect(Collectors.joining(", "));
            try (Statement sql = conexion.createStatement()) {
                for (String[] r : restricciones) {
                    sql.execute("ALTER TABLE " + TABLA + " DROP CONSTRAINT \"" + r[0].replace("\"", "\"\"") + "\"");
                }
                sql.execute("ALTER TABLE " + TABLA + " ADD CONSTRAINT " + NOMBRE + " CHECK (estado IN (" + valores + "))");
            }
            log.info("Se actualizo la restriccion de estados de {} con {}", TABLA, valores);
        } catch (SQLException ex) {
            log.warn("No se pudo revisar la restriccion de estados de {}", TABLA, ex);
        }
    }

    // [nombre, definicion] de cada CHECK que usa la columna estado
    private static List<String[]> restriccionesDeEstado(Connection conexion) throws SQLException {
        String consulta = """
                select c.conname, pg_get_constraintdef(c.oid)
                from pg_constraint c
                join pg_attribute a on a.attrelid = c.conrelid and a.attnum = any(c.conkey)
                where c.conrelid = to_regclass(?) and c.contype = 'c' and a.attname = 'estado'
                """;
        List<String[]> resultado = new ArrayList<>();
        try (PreparedStatement ps = conexion.prepareStatement(consulta)) {
            ps.setString(1, TABLA);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    resultado.add(new String[] { rs.getString(1), rs.getString(2) });
                }
            }
        }
        return resultado;
    }
}
