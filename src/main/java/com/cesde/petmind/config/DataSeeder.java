package com.cesde.petmind.config;

// Lombok deshabilitado temporalmente; implementacion manual abajo.
// import lombok.RequiredArgsConstructor;
// @RequiredArgsConstructor

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.cesde.petmind.model.embeddable.Afinidad;
import com.cesde.petmind.model.embeddable.Cita;
import com.cesde.petmind.model.embeddable.CualidadesFisicas;
import com.cesde.petmind.model.embeddable.DatosHogar;
import com.cesde.petmind.model.embeddable.Direccion;
import com.cesde.petmind.model.embeddable.InformacionContacto;
import com.cesde.petmind.model.entity.Archivo;
import com.cesde.petmind.model.entity.CampanaDonacion;
import com.cesde.petmind.model.entity.Donacion;
import com.cesde.petmind.model.entity.Favorito;
import com.cesde.petmind.model.entity.Fundacion;
import com.cesde.petmind.model.entity.Mascota;
import com.cesde.petmind.model.entity.Notificacion;
import com.cesde.petmind.model.entity.SolicitudAdopcion;
import com.cesde.petmind.model.entity.Usuario;
import com.cesde.petmind.model.enums.CategoriaArchivo;
import com.cesde.petmind.model.enums.CategoriaCampana;
import com.cesde.petmind.model.enums.Especie;
import com.cesde.petmind.model.enums.EstadoAdopcion;
import com.cesde.petmind.model.enums.EstadoCampana;
import com.cesde.petmind.model.enums.EstadoDonacion;
import com.cesde.petmind.model.enums.EstadoPublicacion;
import com.cesde.petmind.model.enums.EstadoSolicitud;
import com.cesde.petmind.model.enums.EstadoVerificacion;
import com.cesde.petmind.model.enums.Frecuencia;
import com.cesde.petmind.model.enums.MetodoPago;
import com.cesde.petmind.model.enums.Modalidad;
import com.cesde.petmind.model.enums.RolUsuario;
import com.cesde.petmind.model.enums.SexoMascota;
import com.cesde.petmind.model.enums.TamanoMascota;
import com.cesde.petmind.model.enums.TipoMedio;
import com.cesde.petmind.model.enums.TipoNotificacion;
import com.cesde.petmind.repository.ArchivoRepository;
import com.cesde.petmind.repository.CampanaDonacionRepository;
import com.cesde.petmind.repository.DonacionRepository;
import com.cesde.petmind.repository.FavoritoRepository;
import com.cesde.petmind.repository.FundacionRepository;
import com.cesde.petmind.repository.MascotaRepository;
import com.cesde.petmind.repository.NotificacionRepository;
import com.cesde.petmind.repository.SolicitudAdopcionRepository;
import com.cesde.petmind.repository.UsuarioRepository;

@Configuration
public class DataSeeder {
    private final ArchivoRepository archivoRepository;
    private final CampanaDonacionRepository campanaDonacionRepository;
    private final DonacionRepository donacionRepository;
    private final FavoritoRepository favoritoRepository;
    private final FundacionRepository fundacionRepository;
    private final MascotaRepository mascotaRepository;
    private final NotificacionRepository notificacionRepository;
    private final SolicitudAdopcionRepository solicitudAdopcionRepository;
    private final UsuarioRepository usuarioRepository;

    @Bean
    public CommandLineRunner sembrarDatos() {
        return args -> sembrar();
    }

    private void sembrar() {
        if (hayDatos()) {
            return;
        }
        List<Fundacion> fundaciones = fundacionRepository.saveAll(List.of(fundacion("Huellas de Esperanza", "900100201-1", "Laura Martínez", "Rescate y adopción responsable en Antioquia.", "huellas@petmind.org", "6045550101"), fundacion("Refugio Patitas", "900100202-2", "Andrés Gómez", "Refugio temporal para perros y gatos.", "patitas@petmind.org", "6045550102"), fundacion("Amigos de Bigotes", "900100203-3", "Camila Ríos", "Atención integral para gatos abandonados.", "bigotes@petmind.org", "6045550103"), fundacion("Colitas Felices", "900100204-4", "Santiago Pérez", "Programa comunitario de esterilización y adopción.", "colitas@petmind.org", "6045550104")));
        List<Usuario> usuarios = usuarioRepository.saveAll(List.of(usuario("Ana", "Torres", "ana.torres@petmind.org", RolUsuario.ADOPTANTE, null), usuario("Diego", "López", "diego.lopez@petmind.org", RolUsuario.ADOPTANTE, null), usuario("Valentina", "Ruiz", "valentina.ruiz@petmind.org", RolUsuario.ADMIN, null), usuario("Laura", "Martínez", "laura.martinez@petmind.org", RolUsuario.REPRESENTANTE_FUNDACION, fundaciones.get(0))));
        List<Mascota> mascotas = mascotaRepository.saveAll(List.of(mascota("Luna", Especie.PERRO, "Criollo", SexoMascota.HEMBRA, TamanoMascota.MEDIANO, fundaciones.get(0), "Luna es tranquila, cariñosa y disfruta los paseos.", EstadoAdopcion.ADOPTADO), mascota("Max", Especie.PERRO, "Labrador", SexoMascota.MACHO, TamanoMascota.GRANDE, fundaciones.get(1), "Max es juguetón y busca una familia activa.", EstadoAdopcion.DISPONIBLE), mascota("Nala", Especie.GATO, "Criollo", SexoMascota.HEMBRA, TamanoMascota.PEQUENO, fundaciones.get(2), "Nala es sociable y se adapta bien a apartamentos.", EstadoAdopcion.DISPONIBLE), mascota("Coco", Especie.CONEJO, "Mini lop", SexoMascota.MACHO, TamanoMascota.PEQUENO, fundaciones.get(3), "Coco es dócil y necesita un hogar tranquilo.", EstadoAdopcion.DISPONIBLE)));
        List<CampanaDonacion> campanas = campanaDonacionRepository.saveAll(List.of(campana("Tratamiento de Luna", "Apoya la recuperación de Luna después de su rescate.", CategoriaCampana.SALUD, 1200000, fundaciones.get(0), mascotas.get(0)), campana("Alimento para el refugio", "Compra de alimento para los animales rescatados.", CategoriaCampana.ALIMENTACION, 2500000, fundaciones.get(1), null), campana("Cirugía de Nala", "Cirugía y recuperación veterinaria para Nala.", CategoriaCampana.CIRUGIA, 1800000, fundaciones.get(2), mascotas.get(2)), campana("Adecuación del refugio", "Mejoras para los espacios de descanso del refugio.", CategoriaCampana.REFUGIO, 3200000, fundaciones.get(3), null)));
        donacionRepository.saveAll(List.of(donacion(usuarios.get(0), campanas.get(0), 150000, "PM-SEED-DON-001", MetodoPago.PSE), donacion(usuarios.get(1), campanas.get(1), 200000, "PM-SEED-DON-002", MetodoPago.TRANSFERENCIA), donacion(usuarios.get(2), campanas.get(2), 300000, "PM-SEED-DON-003", MetodoPago.TARJETA_CREDITO), donacion(null, campanas.get(3), 100000, "PM-SEED-DON-004", MetodoPago.EFECTIVO)));
        favoritoRepository.saveAll(List.of(Favorito.builder().usuario(usuarios.get(0)).mascota(mascotas.get(0)).build(), Favorito.builder().usuario(usuarios.get(1)).mascota(mascotas.get(1)).build(), Favorito.builder().usuario(usuarios.get(0)).campana(campanas.get(2)).build(), Favorito.builder().usuario(usuarios.get(1)).fundacion(fundaciones.get(2)).build()));
        notificacionRepository.saveAll(List.of(Notificacion.builder().usuario(usuarios.get(0)).tipo(TipoNotificacion.SOLICITUD).titulo("Solicitud recibida").mensaje("Tu solicitud de adopción fue recibida.").urlDestino("/solicitudes").leida(false).build(), Notificacion.builder().usuario(usuarios.get(1)).tipo(TipoNotificacion.CAMPANA).titulo("Nueva campaña").mensaje("Conoce la campaña de alimento para el refugio.").urlDestino("/campanas").leida(false).build(), Notificacion.builder().usuario(usuarios.get(2)).tipo(TipoNotificacion.DONACION).titulo("Donación completada").mensaje("La donación fue registrada correctamente.").urlDestino("/donaciones").leida(true).build(), Notificacion.builder().usuario(usuarios.get(3)).tipo(TipoNotificacion.ADOPCION).titulo("Nueva solicitud").mensaje("Hay una nueva solicitud para revisar.").urlDestino("/solicitudes").leida(false).build()));
        List<SolicitudAdopcion> solicitudes = solicitudAdopcionRepository.saveAll(List.of(solicitud(usuarios.get(0), mascotas.get(0), EstadoSolicitud.PENDIENTE, 92), solicitud(usuarios.get(1), mascotas.get(1), EstadoSolicitud.APROBADA, 87), solicitud(usuarios.get(0), mascotas.get(2), EstadoSolicitud.RECHAZADA, 64), solicitud(usuarios.get(1), mascotas.get(3), EstadoSolicitud.PENDIENTE, 78)));
        archivoRepository.saveAll(List.of(Archivo.builder().mascota(mascotas.get(0)).url("https://images.petmind.org/mascotas/luna.jpg").tipoMedio(TipoMedio.IMAGEN).categoria(CategoriaArchivo.PORTADA).esPortada(true).orden(1).build(), Archivo.builder().solicitud(solicitudes.get(0)).url("https://files.petmind.org/solicitudes/ana-documento.pdf").tipoMedio(TipoMedio.DOCUMENTO).categoria(CategoriaArchivo.EVIDENCIA).esPortada(false).orden(1).build(), Archivo.builder().campana(campanas.get(0)).url("https://images.petmind.org/campanas/luna-cirugia.jpg").tipoMedio(TipoMedio.IMAGEN).categoria(CategoriaArchivo.GALERIA).esPortada(true).orden(1).build(), Archivo.builder().mascota(mascotas.get(2)).url("https://images.petmind.org/mascotas/nala.jpg").tipoMedio(TipoMedio.IMAGEN).categoria(CategoriaArchivo.GALERIA).esPortada(true).orden(1).build()));
    }

    private boolean hayDatos() {
        return archivoRepository.count() > 0 || campanaDonacionRepository.count() > 0 || donacionRepository.count() > 0 || favoritoRepository.count() > 0 || fundacionRepository.count() > 0 || mascotaRepository.count() > 0 || notificacionRepository.count() > 0 || solicitudAdopcionRepository.count() > 0 || usuarioRepository.count() > 0;
    }

    private Fundacion fundacion(String nombre, String nit, String representante, String descripcion, String email, String telefono) {
        Direccion direccion = new Direccion();
        direccion.setCalle("Carrera 50 # 10-20");
        direccion.setCiudad("Medellín");
        direccion.setPais("Colombia");
        direccion.setCodigoPostal("050001");
        return Fundacion.builder().nombre(nombre).nit(nit).representanteLegal(representante).descripcion(descripcion).direccion(direccion).contacto(contacto(email, telefono)).estadoVerificacion(EstadoVerificacion.VERIFICADA).fechaVerificacion(LocalDateTime.now().minusDays(15)).latitud(new BigDecimal("6.2442000")).longitud(new BigDecimal("-75.5812000")).build();
    }

    private Usuario usuario(String nombre, String apellido, String email, RolUsuario rol, Fundacion fundacion) {
        return Usuario.builder().nombre(nombre).apellido(apellido).contrasena("$2a$10$7EqJtq98hPqEX7fNZaFWoO7wKfXhJ7P9Q1uVJYJ5zZq4XjK8Xv4Qe").contacto(contacto(email, "3005550100")).rol(rol).fundacion(fundacion).correoVerificado(true).build();
    }

    private Mascota mascota(String nombre, Especie especie, String raza, SexoMascota sexo, TamanoMascota tamano, Fundacion fundacion, String descripcion, EstadoAdopcion estadoAdopcion) {
        CualidadesFisicas cualidades = new CualidadesFisicas();
        cualidades.setPesoKg(12.5);
        cualidades.setAlturaCm(42.0);
        cualidades.setColorPelaje("Marrón y blanco");
        cualidades.setCondicionesMedicas("Ninguna conocida");
        cualidades.setCuidadosEspeciales("Controles veterinarios anuales");
        Afinidad afinidad = new Afinidad();
        afinidad.setNivelEnergia(4);
        afinidad.setAfinidadPerros(5);
        afinidad.setAfinidadGatos(4);
        afinidad.setAfinidadNinos(5);
        return Mascota.builder().fundacion(fundacion).nombre(nombre).especie(especie).raza(raza).edadMeses(24).tamano(tamano).sexo(sexo).cualidadesFisicas(cualidades).ciudad("Medellín").descripcion(descripcion).historia("Fue rescatado y se encuentra listo para comenzar una nueva vida.").etiquetas("rescatado, cariñoso, familia").afinidad(afinidad).estadoAdopcion(estadoAdopcion).estadoPublicacion(EstadoPublicacion.PUBLICADA).esterilizado(true).vacunasAlDia(true).desparasitado(true).tieneMicrochip(false).aptoApadrinamiento(true).fechaPublicacion(LocalDateTime.now().minusDays(10)).contadorVisitas(12).build();
    }

    private CampanaDonacion campana(String titulo, String descripcion, CategoriaCampana categoria, int meta, Fundacion fundacion, Mascota mascota) {
        return CampanaDonacion.builder().fundacion(fundacion).mascota(mascota).titulo(titulo).descripcion(descripcion).categoria(categoria).metaMonto(BigDecimal.valueOf(meta)).montoRecaudado(BigDecimal.valueOf(meta / 3)).fechaInicio(LocalDate.now().minusDays(5)).fechaFin(LocalDate.now().plusDays(25)).estado(EstadoCampana.ACTIVA).esUrgente(categoria == CategoriaCampana.SALUD || categoria == CategoriaCampana.CIRUGIA).build();
    }

    private Donacion donacion(Usuario usuario, CampanaDonacion campana, int monto, String referencia, MetodoPago metodoPago) {
        return Donacion.builder().usuario(usuario).campana(campana).monto(BigDecimal.valueOf(monto)).valorComision(BigDecimal.valueOf(monto * 0.03)).montoTotal(BigDecimal.valueOf(monto * 1.03)).frecuencia(Frecuencia.UNICA).fechaDonacion(LocalDate.now().minusDays(2)).metodoPago(metodoPago).estado(EstadoDonacion.COMPLETADA).esAnonima(usuario == null).mensaje("Aporte de datos semilla").referenciaPago(referencia).nombreDonante(usuario == null ? "Donante anónimo" : usuario.getNombre() + " " + usuario.getApellido()).correoDonante(usuario == null ? "anonimo@petmind.org" : usuario.getContacto().getEmailContacto()).build();
    }

    private SolicitudAdopcion solicitud(Usuario usuario, Mascota mascota, EstadoSolicitud estado, int afinidad) {
        DatosHogar hogar = new DatosHogar();
        hogar.setTipoVivienda(com.cesde.petmind.model.enums.TipoVivienda.APARTAMENTO);
        hogar.setEsPropietario(true);
        hogar.setTienePatio(false);
        hogar.setNumeroConvivientes(2);
        hogar.setTieneOtraMascota(false);
        hogar.setExperienciaPrevia("Experiencia cuidando mascotas de familiares.");
        hogar.setHorasDisponiblesDia(6);
        hogar.setMotivoAdopcion("Brindar un hogar responsable y permanente.");
        hogar.setAceptaCompromisos(true);
        hogar.setAutorizaDatos(true);
        Cita cita = new Cita();
        cita.setFecha(LocalDateTime.now().plusDays(5));
        cita.setModalidad(Modalidad.VIRTUAL);
        cita.setUrlReunion("https://meet.petmind.org/semilla-" + mascota.getNombre().toLowerCase());
        cita.setEstado(com.cesde.petmind.model.enums.EstadoCita.PROGRAMADA);
        return SolicitudAdopcion.builder().usuario(usuario).mascota(mascota).estado(estado).porcentajeAfinidad(afinidad).datosHogar(hogar).cita(cita).notasInternas("Solicitud creada como dato semilla.").fechaSolicitud(LocalDate.now().minusDays(3)).fechaEnRevision(estado == EstadoSolicitud.PENDIENTE ? null : LocalDateTime.now().minusDays(2)).fechaDecision(estado == EstadoSolicitud.PENDIENTE ? null : LocalDateTime.now().minusDays(1)).build();
    }

    private InformacionContacto contacto(String email, String telefono) {
        InformacionContacto contacto = new InformacionContacto();
        contacto.setEmailContacto(email);
        contacto.setTelefonoPrincipal(telefono);
        return contacto;
    }
    public DataSeeder(final ArchivoRepository archivoRepository, final CampanaDonacionRepository campanaDonacionRepository, final DonacionRepository donacionRepository, final FavoritoRepository favoritoRepository, final FundacionRepository fundacionRepository, final MascotaRepository mascotaRepository, final NotificacionRepository notificacionRepository, final SolicitudAdopcionRepository solicitudAdopcionRepository, final UsuarioRepository usuarioRepository) {
        this.archivoRepository = archivoRepository;
        this.campanaDonacionRepository = campanaDonacionRepository;
        this.donacionRepository = donacionRepository;
        this.favoritoRepository = favoritoRepository;
        this.fundacionRepository = fundacionRepository;
        this.mascotaRepository = mascotaRepository;
        this.notificacionRepository = notificacionRepository;
        this.solicitudAdopcionRepository = solicitudAdopcionRepository;
        this.usuarioRepository = usuarioRepository;
    }
}
