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
// @Builder.Default

import java.math.BigDecimal;
import java.time.LocalDate;
import com.cesde.petmind.model.base.BaseEntity;
import com.cesde.petmind.model.embeddable.Direccion;
import com.cesde.petmind.model.enums.Especie;
import com.cesde.petmind.model.enums.EstadoReporte;
import com.cesde.petmind.model.enums.NivelUrgencia;
import com.cesde.petmind.model.enums.TipoCaso;
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
@Table(name = "reportes_animal")
public class ReporteAnimal extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    @Column(name = "codigo_seguimiento", nullable = false, unique = true, length = 50)
    private String codigoSeguimiento;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_caso", nullable = false)
    private TipoCaso tipoCaso;
    @Enumerated(EnumType.STRING)
    @Column(name = "especie", nullable = false)
    private Especie especie;
    @Enumerated(EnumType.STRING)
    @Column(name = "nivel_urgencia", nullable = false)
    private NivelUrgencia nivelUrgencia;
    @Embedded
    private Direccion direccion;
    @Column(name = "latitud", precision = 10, scale = 7)
    private BigDecimal latitud;
    @Column(name = "longitud", precision = 10, scale = 7)
    private BigDecimal longitud;
    @Column(name = "descripcion", nullable = false, length = 1000)
    private String descripcion;
    @Column(name = "nombre_contacto", length = 150)
    private String nombreContacto;
    @Column(name = "telefono_contacto", length = 20)
    private String telefonoContacto;
    @Column(name = "es_anonimo", nullable = false)
    private Boolean esAnonimo;
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoReporte estado;
    @Column(name = "fecha_reporte", nullable = false)
    private LocalDate fechaReporte;
    private static Boolean $default$esAnonimo() {
        return false;
    }
    public static abstract class ReporteAnimalBuilder<C extends ReporteAnimal, B extends ReporteAnimal.ReporteAnimalBuilder<C, B>> extends BaseEntity.BaseEntityBuilder<C, B> {
        private Usuario usuario;
        private String codigoSeguimiento;
        private TipoCaso tipoCaso;
        private Especie especie;
        private NivelUrgencia nivelUrgencia;
        private Direccion direccion;
        private BigDecimal latitud;
        private BigDecimal longitud;
        private String descripcion;
        private String nombreContacto;
        private String telefonoContacto;
        private boolean esAnonimo$set;
        private Boolean esAnonimo$value;
        private EstadoReporte estado;
        private LocalDate fechaReporte;

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
        public B codigoSeguimiento(final String codigoSeguimiento) {
            this.codigoSeguimiento = codigoSeguimiento;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B tipoCaso(final TipoCaso tipoCaso) {
            this.tipoCaso = tipoCaso;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B especie(final Especie especie) {
            this.especie = especie;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B nivelUrgencia(final NivelUrgencia nivelUrgencia) {
            this.nivelUrgencia = nivelUrgencia;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B direccion(final Direccion direccion) {
            this.direccion = direccion;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B latitud(final BigDecimal latitud) {
            this.latitud = latitud;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B longitud(final BigDecimal longitud) {
            this.longitud = longitud;
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
        public B nombreContacto(final String nombreContacto) {
            this.nombreContacto = nombreContacto;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B telefonoContacto(final String telefonoContacto) {
            this.telefonoContacto = telefonoContacto;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B esAnonimo(final Boolean esAnonimo) {
            this.esAnonimo$value = esAnonimo;
            esAnonimo$set = true;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B estado(final EstadoReporte estado) {
            this.estado = estado;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B fechaReporte(final LocalDate fechaReporte) {
            this.fechaReporte = fechaReporte;
            return self();
        }

        @java.lang.Override
        protected abstract B self();

        @java.lang.Override
        public abstract C build();

        @java.lang.Override
        public java.lang.String toString() {
            return "ReporteAnimal.ReporteAnimalBuilder(super=" + super.toString() + ", usuario=" + this.usuario + ", codigoSeguimiento=" + this.codigoSeguimiento + ", tipoCaso=" + this.tipoCaso + ", especie=" + this.especie + ", nivelUrgencia=" + this.nivelUrgencia + ", direccion=" + this.direccion + ", latitud=" + this.latitud + ", longitud=" + this.longitud + ", descripcion=" + this.descripcion + ", nombreContacto=" + this.nombreContacto + ", telefonoContacto=" + this.telefonoContacto + ", esAnonimo$value=" + this.esAnonimo$value + ", estado=" + this.estado + ", fechaReporte=" + this.fechaReporte + ")";
        }
    }
    private static final class ReporteAnimalBuilderImpl extends ReporteAnimal.ReporteAnimalBuilder<ReporteAnimal, ReporteAnimal.ReporteAnimalBuilderImpl> {
        private ReporteAnimalBuilderImpl() {
        }

        @java.lang.Override
        protected ReporteAnimal.ReporteAnimalBuilderImpl self() {
            return this;
        }

        @java.lang.Override
        public ReporteAnimal build() {
            return new ReporteAnimal(this);
        }
    }
    protected ReporteAnimal(final ReporteAnimal.ReporteAnimalBuilder<?, ?> b) {
        super(b);
        this.usuario = b.usuario;
        this.codigoSeguimiento = b.codigoSeguimiento;
        this.tipoCaso = b.tipoCaso;
        this.especie = b.especie;
        this.nivelUrgencia = b.nivelUrgencia;
        this.direccion = b.direccion;
        this.latitud = b.latitud;
        this.longitud = b.longitud;
        this.descripcion = b.descripcion;
        this.nombreContacto = b.nombreContacto;
        this.telefonoContacto = b.telefonoContacto;
        if (b.esAnonimo$set) this.esAnonimo = b.esAnonimo$value;
         else this.esAnonimo = ReporteAnimal.$default$esAnonimo();
        this.estado = b.estado;
        this.fechaReporte = b.fechaReporte;
    }
    public static ReporteAnimal.ReporteAnimalBuilder<?, ?> builder() {
        return new ReporteAnimal.ReporteAnimalBuilderImpl();
    }
    public Usuario getUsuario() {
        return this.usuario;
    }
    public String getCodigoSeguimiento() {
        return this.codigoSeguimiento;
    }
    public TipoCaso getTipoCaso() {
        return this.tipoCaso;
    }
    public Especie getEspecie() {
        return this.especie;
    }
    public NivelUrgencia getNivelUrgencia() {
        return this.nivelUrgencia;
    }
    public Direccion getDireccion() {
        return this.direccion;
    }
    public BigDecimal getLatitud() {
        return this.latitud;
    }
    public BigDecimal getLongitud() {
        return this.longitud;
    }
    public String getDescripcion() {
        return this.descripcion;
    }
    public String getNombreContacto() {
        return this.nombreContacto;
    }
    public String getTelefonoContacto() {
        return this.telefonoContacto;
    }
    public Boolean getEsAnonimo() {
        return this.esAnonimo;
    }
    public EstadoReporte getEstado() {
        return this.estado;
    }
    public LocalDate getFechaReporte() {
        return this.fechaReporte;
    }
    public void setUsuario(final Usuario usuario) {
        this.usuario = usuario;
    }
    public void setCodigoSeguimiento(final String codigoSeguimiento) {
        this.codigoSeguimiento = codigoSeguimiento;
    }
    public void setTipoCaso(final TipoCaso tipoCaso) {
        this.tipoCaso = tipoCaso;
    }
    public void setEspecie(final Especie especie) {
        this.especie = especie;
    }
    public void setNivelUrgencia(final NivelUrgencia nivelUrgencia) {
        this.nivelUrgencia = nivelUrgencia;
    }
    public void setDireccion(final Direccion direccion) {
        this.direccion = direccion;
    }
    public void setLatitud(final BigDecimal latitud) {
        this.latitud = latitud;
    }
    public void setLongitud(final BigDecimal longitud) {
        this.longitud = longitud;
    }
    public void setDescripcion(final String descripcion) {
        this.descripcion = descripcion;
    }
    public void setNombreContacto(final String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }
    public void setTelefonoContacto(final String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }
    public void setEsAnonimo(final Boolean esAnonimo) {
        this.esAnonimo = esAnonimo;
    }
    public void setEstado(final EstadoReporte estado) {
        this.estado = estado;
    }
    public void setFechaReporte(final LocalDate fechaReporte) {
        this.fechaReporte = fechaReporte;
    }
    public ReporteAnimal() {
        this.esAnonimo = ReporteAnimal.$default$esAnonimo();
    }
    public ReporteAnimal(final Usuario usuario, final String codigoSeguimiento, final TipoCaso tipoCaso, final Especie especie, final NivelUrgencia nivelUrgencia, final Direccion direccion, final BigDecimal latitud, final BigDecimal longitud, final String descripcion, final String nombreContacto, final String telefonoContacto, final Boolean esAnonimo, final EstadoReporte estado, final LocalDate fechaReporte) {
        this.usuario = usuario;
        this.codigoSeguimiento = codigoSeguimiento;
        this.tipoCaso = tipoCaso;
        this.especie = especie;
        this.nivelUrgencia = nivelUrgencia;
        this.direccion = direccion;
        this.latitud = latitud;
        this.longitud = longitud;
        this.descripcion = descripcion;
        this.nombreContacto = nombreContacto;
        this.telefonoContacto = telefonoContacto;
        this.esAnonimo = esAnonimo;
        this.estado = estado;
        this.fechaReporte = fechaReporte;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "ReporteAnimal(super=" + super.toString() + ", codigoSeguimiento=" + this.getCodigoSeguimiento() + ", tipoCaso=" + this.getTipoCaso() + ", especie=" + this.getEspecie() + ", nivelUrgencia=" + this.getNivelUrgencia() + ", direccion=" + this.getDireccion() + ", latitud=" + this.getLatitud() + ", longitud=" + this.getLongitud() + ", descripcion=" + this.getDescripcion() + ", nombreContacto=" + this.getNombreContacto() + ", telefonoContacto=" + this.getTelefonoContacto() + ", esAnonimo=" + this.getEsAnonimo() + ", estado=" + this.getEstado() + ", fechaReporte=" + this.getFechaReporte() + ")";
    }
}
