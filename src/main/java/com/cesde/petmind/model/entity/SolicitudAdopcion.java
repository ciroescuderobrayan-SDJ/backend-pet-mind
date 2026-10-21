package com.cesde.petmind.model.entity;

// Lombok deshabilitado temporalmente; implementacion manual abajo.
// import lombok.AllArgsConstructor;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;
// import lombok.ToString;
// import lombok.experimental.SuperBuilder;
// @Getter
// @Setter
// @NoArgsConstructor
// @AllArgsConstructor
// @SuperBuilder
// @ToString(callSuper = true)
// @ToString.Exclude
// @ToString.Exclude

import java.time.LocalDate;
import java.time.LocalDateTime;
import com.cesde.petmind.model.base.BaseEntity;
import com.cesde.petmind.model.embeddable.Cita;
import com.cesde.petmind.model.embeddable.DatosHogar;
import com.cesde.petmind.model.enums.EstadoSolicitud;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "solicitudes_adopcion")
public class SolicitudAdopcion extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mascota_id", nullable = false)
    private Mascota mascota;
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoSolicitud estado;
    @Column(name = "porcentaje_afinidad")
    private Integer porcentajeAfinidad;
    @Embedded
    private DatosHogar datosHogar;
    @Embedded
    private Cita cita;
    @Column(name = "notas_internas", length = 2000)
    private String notasInternas;
    @Column(name = "fecha_solicitud", nullable = false)
    private LocalDate fechaSolicitud;
    @Column(name = "fecha_en_revision")
    private LocalDateTime fechaEnRevision;
    @Column(name = "fecha_entrevista")
    private LocalDateTime fechaEntrevista;
    @Column(name = "fecha_visita_hogar")
    private LocalDateTime fechaVisitaHogar;
    @Column(name = "fecha_decision")
    private LocalDateTime fechaDecision;
    public static abstract class SolicitudAdopcionBuilder<C extends SolicitudAdopcion, B extends SolicitudAdopcion.SolicitudAdopcionBuilder<C, B>> extends BaseEntity.BaseEntityBuilder<C, B> {
        private Usuario usuario;
        private Mascota mascota;
        private EstadoSolicitud estado;
        private Integer porcentajeAfinidad;
        private DatosHogar datosHogar;
        private Cita cita;
        private String notasInternas;
        private LocalDate fechaSolicitud;
        private LocalDateTime fechaEnRevision;
        private LocalDateTime fechaEntrevista;
        private LocalDateTime fechaVisitaHogar;
        private LocalDateTime fechaDecision;

        /**
         * @return {@code this}.
         */
        public B usuario(final Usuario usuario) {
            this.usuario = usuario;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B mascota(final Mascota mascota) {
            this.mascota = mascota;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B estado(final EstadoSolicitud estado) {
            this.estado = estado;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B porcentajeAfinidad(final Integer porcentajeAfinidad) {
            this.porcentajeAfinidad = porcentajeAfinidad;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B datosHogar(final DatosHogar datosHogar) {
            this.datosHogar = datosHogar;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B cita(final Cita cita) {
            this.cita = cita;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B notasInternas(final String notasInternas) {
            this.notasInternas = notasInternas;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B fechaSolicitud(final LocalDate fechaSolicitud) {
            this.fechaSolicitud = fechaSolicitud;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B fechaEnRevision(final LocalDateTime fechaEnRevision) {
            this.fechaEnRevision = fechaEnRevision;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B fechaEntrevista(final LocalDateTime fechaEntrevista) {
            this.fechaEntrevista = fechaEntrevista;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B fechaVisitaHogar(final LocalDateTime fechaVisitaHogar) {
            this.fechaVisitaHogar = fechaVisitaHogar;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B fechaDecision(final LocalDateTime fechaDecision) {
            this.fechaDecision = fechaDecision;
            return self();
        }

        @java.lang.Override
        protected abstract B self();

        @java.lang.Override
        public abstract C build();

        @java.lang.Override
        public java.lang.String toString() {
            return "SolicitudAdopcion.SolicitudAdopcionBuilder(super=" + super.toString() + ", usuario=" + this.usuario + ", mascota=" + this.mascota + ", estado=" + this.estado + ", porcentajeAfinidad=" + this.porcentajeAfinidad + ", datosHogar=" + this.datosHogar + ", cita=" + this.cita + ", notasInternas=" + this.notasInternas + ", fechaSolicitud=" + this.fechaSolicitud + ", fechaEnRevision=" + this.fechaEnRevision + ", fechaEntrevista=" + this.fechaEntrevista + ", fechaVisitaHogar=" + this.fechaVisitaHogar + ", fechaDecision=" + this.fechaDecision + ")";
        }
    }
    private static final class SolicitudAdopcionBuilderImpl extends SolicitudAdopcion.SolicitudAdopcionBuilder<SolicitudAdopcion, SolicitudAdopcion.SolicitudAdopcionBuilderImpl> {
        private SolicitudAdopcionBuilderImpl() {
        }

        @java.lang.Override
        protected SolicitudAdopcion.SolicitudAdopcionBuilderImpl self() {
            return this;
        }

        @java.lang.Override
        public SolicitudAdopcion build() {
            return new SolicitudAdopcion(this);
        }
    }
    protected SolicitudAdopcion(final SolicitudAdopcion.SolicitudAdopcionBuilder<?, ?> b) {
        super(b);
        this.usuario = b.usuario;
        this.mascota = b.mascota;
        this.estado = b.estado;
        this.porcentajeAfinidad = b.porcentajeAfinidad;
        this.datosHogar = b.datosHogar;
        this.cita = b.cita;
        this.notasInternas = b.notasInternas;
        this.fechaSolicitud = b.fechaSolicitud;
        this.fechaEnRevision = b.fechaEnRevision;
        this.fechaEntrevista = b.fechaEntrevista;
        this.fechaVisitaHogar = b.fechaVisitaHogar;
        this.fechaDecision = b.fechaDecision;
    }
    public static SolicitudAdopcion.SolicitudAdopcionBuilder<?, ?> builder() {
        return new SolicitudAdopcion.SolicitudAdopcionBuilderImpl();
    }
    public Usuario getUsuario() {
        return this.usuario;
    }
    public Mascota getMascota() {
        return this.mascota;
    }
    public EstadoSolicitud getEstado() {
        return this.estado;
    }
    public Integer getPorcentajeAfinidad() {
        return this.porcentajeAfinidad;
    }
    public DatosHogar getDatosHogar() {
        return this.datosHogar;
    }
    public Cita getCita() {
        return this.cita;
    }
    public String getNotasInternas() {
        return this.notasInternas;
    }
    public LocalDate getFechaSolicitud() {
        return this.fechaSolicitud;
    }
    public LocalDateTime getFechaEnRevision() {
        return this.fechaEnRevision;
    }
    public LocalDateTime getFechaEntrevista() {
        return this.fechaEntrevista;
    }
    public LocalDateTime getFechaVisitaHogar() {
        return this.fechaVisitaHogar;
    }
    public LocalDateTime getFechaDecision() {
        return this.fechaDecision;
    }
    public void setUsuario(final Usuario usuario) {
        this.usuario = usuario;
    }
    public void setMascota(final Mascota mascota) {
        this.mascota = mascota;
    }
    public void setEstado(final EstadoSolicitud estado) {
        this.estado = estado;
    }
    public void setPorcentajeAfinidad(final Integer porcentajeAfinidad) {
        this.porcentajeAfinidad = porcentajeAfinidad;
    }
    public void setDatosHogar(final DatosHogar datosHogar) {
        this.datosHogar = datosHogar;
    }
    public void setCita(final Cita cita) {
        this.cita = cita;
    }
    public void setNotasInternas(final String notasInternas) {
        this.notasInternas = notasInternas;
    }
    public void setFechaSolicitud(final LocalDate fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }
    public void setFechaEnRevision(final LocalDateTime fechaEnRevision) {
        this.fechaEnRevision = fechaEnRevision;
    }
    public void setFechaEntrevista(final LocalDateTime fechaEntrevista) {
        this.fechaEntrevista = fechaEntrevista;
    }
    public void setFechaVisitaHogar(final LocalDateTime fechaVisitaHogar) {
        this.fechaVisitaHogar = fechaVisitaHogar;
    }
    public void setFechaDecision(final LocalDateTime fechaDecision) {
        this.fechaDecision = fechaDecision;
    }
    public SolicitudAdopcion() {
    }
    public SolicitudAdopcion(final Usuario usuario, final Mascota mascota, final EstadoSolicitud estado, final Integer porcentajeAfinidad, final DatosHogar datosHogar, final Cita cita, final String notasInternas, final LocalDate fechaSolicitud, final LocalDateTime fechaEnRevision, final LocalDateTime fechaEntrevista, final LocalDateTime fechaVisitaHogar, final LocalDateTime fechaDecision) {
        this.usuario = usuario;
        this.mascota = mascota;
        this.estado = estado;
        this.porcentajeAfinidad = porcentajeAfinidad;
        this.datosHogar = datosHogar;
        this.cita = cita;
        this.notasInternas = notasInternas;
        this.fechaSolicitud = fechaSolicitud;
        this.fechaEnRevision = fechaEnRevision;
        this.fechaEntrevista = fechaEntrevista;
        this.fechaVisitaHogar = fechaVisitaHogar;
        this.fechaDecision = fechaDecision;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "SolicitudAdopcion(super=" + super.toString() + ", estado=" + this.getEstado() + ", porcentajeAfinidad=" + this.getPorcentajeAfinidad() + ", datosHogar=" + this.getDatosHogar() + ", cita=" + this.getCita() + ", notasInternas=" + this.getNotasInternas() + ", fechaSolicitud=" + this.getFechaSolicitud() + ", fechaEnRevision=" + this.getFechaEnRevision() + ", fechaEntrevista=" + this.getFechaEntrevista() + ", fechaVisitaHogar=" + this.getFechaVisitaHogar() + ", fechaDecision=" + this.getFechaDecision() + ")";
    }
}
