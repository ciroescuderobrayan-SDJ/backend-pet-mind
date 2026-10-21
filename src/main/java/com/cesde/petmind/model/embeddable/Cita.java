package com.cesde.petmind.model.embeddable;

// Lombok deshabilitado temporalmente; implementacion manual abajo.
// import lombok.Getter;
// import lombok.Setter;
// @Getter
// @Setter

import com.cesde.petmind.model.enums.EstadoCita;
import com.cesde.petmind.model.enums.Modalidad;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.LocalDateTime;

@Embeddable
public class Cita {
    @Column(name = "cita_fecha")
    private LocalDateTime fecha;
    @Enumerated(EnumType.STRING)
    @Column(name = "cita_modalidad")
    private Modalidad modalidad;
    @Column(name = "cita_url_reunion", length = 500)
    private String urlReunion;
    @Enumerated(EnumType.STRING)
    @Column(name = "cita_estado")
    private EstadoCita estado;
    public LocalDateTime getFecha() {
        return this.fecha;
    }
    public Modalidad getModalidad() {
        return this.modalidad;
    }
    public String getUrlReunion() {
        return this.urlReunion;
    }
    public EstadoCita getEstado() {
        return this.estado;
    }
    public void setFecha(final LocalDateTime fecha) {
        this.fecha = fecha;
    }
    public void setModalidad(final Modalidad modalidad) {
        this.modalidad = modalidad;
    }
    public void setUrlReunion(final String urlReunion) {
        this.urlReunion = urlReunion;
    }
    public void setEstado(final EstadoCita estado) {
        this.estado = estado;
    }
}
