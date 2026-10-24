package com.cesde.petmind.model.entity;

// Lombok deshabilitado temporalmente; implementacion manual abajo.
// import lombok.AllArgsConstructor;
// import lombok.Builder;
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
// @Builder.Default
// @Builder.Default

import java.math.BigDecimal;
import java.time.LocalDate;
import com.cesde.petmind.model.base.BaseEntity;
import com.cesde.petmind.model.enums.CategoriaCampana;
import com.cesde.petmind.model.enums.EstadoCampana;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "campanas_donacion")
public class CampanaDonacion extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "fundacion_id", nullable = false)
    private Fundacion fundacion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mascota_id")
    private Mascota mascota;
    @Column(name = "titulo", nullable = false, length = 150)
    private String titulo;
    @Column(name = "descripcion", nullable = false, length = 1000)
    private String descripcion;
    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false)
    private CategoriaCampana categoria;
    @Column(name = "meta_monto", nullable = false, precision = 12, scale = 2)
    private BigDecimal metaMonto;
    @Column(name = "monto_recaudado", nullable = false, precision = 12, scale = 2)
    private BigDecimal montoRecaudado;
    @Column(name = "fecha_inicio", nullable = false)
    private LocalDate fechaInicio;
    @Column(name = "fecha_fin")
    private LocalDate fechaFin;
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoCampana estado;
    @Column(name = "es_urgente", nullable = false)
    private Boolean esUrgente;
    private static BigDecimal $default$montoRecaudado() {
        return BigDecimal.ZERO;
    }
    private static Boolean $default$esUrgente() {
        return false;
    }
    public static abstract class CampanaDonacionBuilder<C extends CampanaDonacion, B extends CampanaDonacion.CampanaDonacionBuilder<C, B>> extends BaseEntity.BaseEntityBuilder<C, B> {
        private Fundacion fundacion;
        private Mascota mascota;
        private String titulo;
        private String descripcion;
        private CategoriaCampana categoria;
        private BigDecimal metaMonto;
        private boolean montoRecaudado$set;
        private BigDecimal montoRecaudado$value;
        private LocalDate fechaInicio;
        private LocalDate fechaFin;
        private EstadoCampana estado;
        private boolean esUrgente$set;
        private Boolean esUrgente$value;

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
        public B mascota(final Mascota mascota) {
            this.mascota = mascota;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B titulo(final String titulo) {
            this.titulo = titulo;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B descripcion(final String descripcion) {
            this.descripcion = descripcion;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B categoria(final CategoriaCampana categoria) {
            this.categoria = categoria;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B metaMonto(final BigDecimal metaMonto) {
            this.metaMonto = metaMonto;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B montoRecaudado(final BigDecimal montoRecaudado) {
            this.montoRecaudado$value = montoRecaudado;
            montoRecaudado$set = true;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B fechaInicio(final LocalDate fechaInicio) {
            this.fechaInicio = fechaInicio;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B fechaFin(final LocalDate fechaFin) {
            this.fechaFin = fechaFin;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B estado(final EstadoCampana estado) {
            this.estado = estado;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B esUrgente(final Boolean esUrgente) {
            this.esUrgente$value = esUrgente;
            esUrgente$set = true;
            return self();
        }

        @java.lang.Override
        protected abstract B self();

        @java.lang.Override
        public abstract C build();

        @java.lang.Override
        public java.lang.String toString() {
            return "CampanaDonacion.CampanaDonacionBuilder(super=" + super.toString() + ", fundacion=" + this.fundacion + ", mascota=" + this.mascota + ", titulo=" + this.titulo + ", descripcion=" + this.descripcion + ", categoria=" + this.categoria + ", metaMonto=" + this.metaMonto + ", montoRecaudado$value=" + this.montoRecaudado$value + ", fechaInicio=" + this.fechaInicio + ", fechaFin=" + this.fechaFin + ", estado=" + this.estado + ", esUrgente$value=" + this.esUrgente$value + ")";
        }
    }
    private static final class CampanaDonacionBuilderImpl extends CampanaDonacion.CampanaDonacionBuilder<CampanaDonacion, CampanaDonacion.CampanaDonacionBuilderImpl> {
        private CampanaDonacionBuilderImpl() {
        }

        @java.lang.Override
        protected CampanaDonacion.CampanaDonacionBuilderImpl self() {
            return this;
        }

        @java.lang.Override
        public CampanaDonacion build() {
            return new CampanaDonacion(this);
        }
    }
    protected CampanaDonacion(final CampanaDonacion.CampanaDonacionBuilder<?, ?> b) {
        super(b);
        this.fundacion = b.fundacion;
        this.mascota = b.mascota;
        this.titulo = b.titulo;
        this.descripcion = b.descripcion;
        this.categoria = b.categoria;
        this.metaMonto = b.metaMonto;
        if (b.montoRecaudado$set) this.montoRecaudado = b.montoRecaudado$value;
         else this.montoRecaudado = CampanaDonacion.$default$montoRecaudado();
        this.fechaInicio = b.fechaInicio;
        this.fechaFin = b.fechaFin;
        this.estado = b.estado;
        if (b.esUrgente$set) this.esUrgente = b.esUrgente$value;
         else this.esUrgente = CampanaDonacion.$default$esUrgente();
    }
    public static CampanaDonacion.CampanaDonacionBuilder<?, ?> builder() {
        return new CampanaDonacion.CampanaDonacionBuilderImpl();
    }
    public Fundacion getFundacion() {
        return this.fundacion;
    }
    public Mascota getMascota() {
        return this.mascota;
    }
    public String getTitulo() {
        return this.titulo;
    }
    public String getDescripcion() {
        return this.descripcion;
    }
    public CategoriaCampana getCategoria() {
        return this.categoria;
    }
    public BigDecimal getMetaMonto() {
        return this.metaMonto;
    }
    public BigDecimal getMontoRecaudado() {
        return this.montoRecaudado;
    }
    public LocalDate getFechaInicio() {
        return this.fechaInicio;
    }
    public LocalDate getFechaFin() {
        return this.fechaFin;
    }
    public EstadoCampana getEstado() {
        return this.estado;
    }
    public Boolean getEsUrgente() {
        return this.esUrgente;
    }
    public void setFundacion(final Fundacion fundacion) {
        this.fundacion = fundacion;
    }
    public void setMascota(final Mascota mascota) {
        this.mascota = mascota;
    }
    public void setTitulo(final String titulo) {
        this.titulo = titulo;
    }
    public void setDescripcion(final String descripcion) {
        this.descripcion = descripcion;
    }
    public void setCategoria(final CategoriaCampana categoria) {
        this.categoria = categoria;
    }
    public void setMetaMonto(final BigDecimal metaMonto) {
        this.metaMonto = metaMonto;
    }
    public void setMontoRecaudado(final BigDecimal montoRecaudado) {
        this.montoRecaudado = montoRecaudado;
    }
    public void setFechaInicio(final LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }
    public void setFechaFin(final LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }
    public void setEstado(final EstadoCampana estado) {
        this.estado = estado;
    }
    public void setEsUrgente(final Boolean esUrgente) {
        this.esUrgente = esUrgente;
    }
    public CampanaDonacion() {
        this.montoRecaudado = CampanaDonacion.$default$montoRecaudado();
        this.esUrgente = CampanaDonacion.$default$esUrgente();
    }
    public CampanaDonacion(final Fundacion fundacion, final Mascota mascota, final String titulo, final String descripcion, final CategoriaCampana categoria, final BigDecimal metaMonto, final BigDecimal montoRecaudado, final LocalDate fechaInicio, final LocalDate fechaFin, final EstadoCampana estado, final Boolean esUrgente) {
        this.fundacion = fundacion;
        this.mascota = mascota;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.categoria = categoria;
        this.metaMonto = metaMonto;
        this.montoRecaudado = montoRecaudado;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.esUrgente = esUrgente;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "CampanaDonacion(super=" + super.toString() + ", titulo=" + this.getTitulo() + ", descripcion=" + this.getDescripcion() + ", categoria=" + this.getCategoria() + ", metaMonto=" + this.getMetaMonto() + ", montoRecaudado=" + this.getMontoRecaudado() + ", fechaInicio=" + this.getFechaInicio() + ", fechaFin=" + this.getFechaFin() + ", estado=" + this.getEstado() + ", esUrgente=" + this.getEsUrgente() + ")";
    }
}
