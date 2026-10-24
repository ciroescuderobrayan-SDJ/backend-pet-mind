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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import com.cesde.petmind.model.base.BaseEntity;
import com.cesde.petmind.model.enums.EstadoAtencion;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reporte_fundacion")
public class ReporteFundacion extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporte_id", nullable = false)
    private ReporteAnimal reporte;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fundacion_id", nullable = false)
    private Fundacion fundacion;
    @Column(name = "distancia_km", precision = 10, scale = 2)
    private BigDecimal distanciaKm;
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_atencion", nullable = false)
    private EstadoAtencion estadoAtencion;
    @Column(name = "fecha_notificacion")
    private LocalDateTime fechaNotificacion;
    public static abstract class ReporteFundacionBuilder<C extends ReporteFundacion, B extends ReporteFundacion.ReporteFundacionBuilder<C, B>> extends BaseEntity.BaseEntityBuilder<C, B> {
        private ReporteAnimal reporte;
        private Fundacion fundacion;
        private BigDecimal distanciaKm;
        private EstadoAtencion estadoAtencion;
        private LocalDateTime fechaNotificacion;

        /**
         * @return {@code this}.
         */
        public B reporte(final ReporteAnimal reporte) {
            this.reporte = reporte;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B fundacion(final Fundacion fundacion) {
            this.fundacion = fundacion;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B distanciaKm(final BigDecimal distanciaKm) {
            this.distanciaKm = distanciaKm;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B estadoAtencion(final EstadoAtencion estadoAtencion) {
            this.estadoAtencion = estadoAtencion;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B fechaNotificacion(final LocalDateTime fechaNotificacion) {
            this.fechaNotificacion = fechaNotificacion;
            return self();
        }

        @java.lang.Override
        protected abstract B self();

        @java.lang.Override
        public abstract C build();

        @java.lang.Override
        public java.lang.String toString() {
            return "ReporteFundacion.ReporteFundacionBuilder(super=" + super.toString() + ", reporte=" + this.reporte + ", fundacion=" + this.fundacion + ", distanciaKm=" + this.distanciaKm + ", estadoAtencion=" + this.estadoAtencion + ", fechaNotificacion=" + this.fechaNotificacion + ")";
        }
    }
    private static final class ReporteFundacionBuilderImpl extends ReporteFundacion.ReporteFundacionBuilder<ReporteFundacion, ReporteFundacion.ReporteFundacionBuilderImpl> {
        private ReporteFundacionBuilderImpl() {
        }

        @java.lang.Override
        protected ReporteFundacion.ReporteFundacionBuilderImpl self() {
            return this;
        }

        @java.lang.Override
        public ReporteFundacion build() {
            return new ReporteFundacion(this);
        }
    }
    protected ReporteFundacion(final ReporteFundacion.ReporteFundacionBuilder<?, ?> b) {
        super(b);
        this.reporte = b.reporte;
        this.fundacion = b.fundacion;
        this.distanciaKm = b.distanciaKm;
        this.estadoAtencion = b.estadoAtencion;
        this.fechaNotificacion = b.fechaNotificacion;
    }
    public static ReporteFundacion.ReporteFundacionBuilder<?, ?> builder() {
        return new ReporteFundacion.ReporteFundacionBuilderImpl();
    }
    public ReporteAnimal getReporte() {
        return this.reporte;
    }
    public Fundacion getFundacion() {
        return this.fundacion;
    }
    public BigDecimal getDistanciaKm() {
        return this.distanciaKm;
    }
    public EstadoAtencion getEstadoAtencion() {
        return this.estadoAtencion;
    }
    public LocalDateTime getFechaNotificacion() {
        return this.fechaNotificacion;
    }
    public void setReporte(final ReporteAnimal reporte) {
        this.reporte = reporte;
    }
    public void setFundacion(final Fundacion fundacion) {
        this.fundacion = fundacion;
    }
    public void setDistanciaKm(final BigDecimal distanciaKm) {
        this.distanciaKm = distanciaKm;
    }
    public void setEstadoAtencion(final EstadoAtencion estadoAtencion) {
        this.estadoAtencion = estadoAtencion;
    }
    public void setFechaNotificacion(final LocalDateTime fechaNotificacion) {
        this.fechaNotificacion = fechaNotificacion;
    }
    public ReporteFundacion() {
    }
    public ReporteFundacion(final ReporteAnimal reporte, final Fundacion fundacion, final BigDecimal distanciaKm, final EstadoAtencion estadoAtencion, final LocalDateTime fechaNotificacion) {
        this.reporte = reporte;
        this.fundacion = fundacion;
        this.distanciaKm = distanciaKm;
        this.estadoAtencion = estadoAtencion;
        this.fechaNotificacion = fechaNotificacion;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "ReporteFundacion(super=" + super.toString() + ", distanciaKm=" + this.getDistanciaKm() + ", estadoAtencion=" + this.getEstadoAtencion() + ", fechaNotificacion=" + this.getFechaNotificacion() + ")";
    }
}
