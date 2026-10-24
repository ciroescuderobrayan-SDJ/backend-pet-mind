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

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import com.cesde.petmind.model.base.BaseEntity;
import com.cesde.petmind.model.embeddable.Direccion;
import com.cesde.petmind.model.embeddable.InformacionContacto;
import com.cesde.petmind.model.enums.EstadoVerificacion;

@Entity
@Table(name = "fundaciones")
public class Fundacion extends BaseEntity {
    @Column(name = "nombre", nullable = false, unique = true, length = 150)
    private String nombre;
    @Column(name = "nit", nullable = false, unique = true, length = 20)
    private String nit;
    @Column(name = "representante_legal", nullable = false, length = 150)
    private String representanteLegal;
    @Column(name = "descripcion", length = 1000)
    private String descripcion;
    @Embedded
    private Direccion direccion;
    @Embedded
    private InformacionContacto contacto;
    @Column(name = "url_logo", length = 500)
    private String urlLogo;
    @Column(name = "url_portada", length = 500)
    private String urlPortada;
    // Documentos legales que la fundacion sube para su verificacion
    @Column(name = "url_rut", length = 500)
    private String urlRut;
    @Column(name = "url_camara_comercio", length = 500)
    private String urlCamaraComercio;
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_verificacion", nullable = false)
    private EstadoVerificacion estadoVerificacion;
    @Column(name = "fecha_verificacion")
    private LocalDateTime fechaVerificacion;
    // Para ubicar la fundacion en el mapa de las pantallas
    @Column(name = "latitud", precision = 10, scale = 7)
    private BigDecimal latitud;
    @Column(name = "longitud", precision = 10, scale = 7)
    private BigDecimal longitud;
    public static abstract class FundacionBuilder<C extends Fundacion, B extends Fundacion.FundacionBuilder<C, B>> extends BaseEntity.BaseEntityBuilder<C, B> {
        private String nombre;
        private String nit;
        private String representanteLegal;
        private String descripcion;
        private Direccion direccion;
        private InformacionContacto contacto;
        private String urlLogo;
        private String urlPortada;
        private String urlRut;
        private String urlCamaraComercio;
        private EstadoVerificacion estadoVerificacion;
        private LocalDateTime fechaVerificacion;
        private BigDecimal latitud;
        private BigDecimal longitud;

        /**
         * @return {@code this}.
         */
        public B nombre(final String nombre) {
            this.nombre = nombre;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B nit(final String nit) {
            this.nit = nit;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B representanteLegal(final String representanteLegal) {
            this.representanteLegal = representanteLegal;
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
        public B direccion(final Direccion direccion) {
            this.direccion = direccion;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B contacto(final InformacionContacto contacto) {
            this.contacto = contacto;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B urlLogo(final String urlLogo) {
            this.urlLogo = urlLogo;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B urlPortada(final String urlPortada) {
            this.urlPortada = urlPortada;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B urlRut(final String urlRut) {
            this.urlRut = urlRut;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B urlCamaraComercio(final String urlCamaraComercio) {
            this.urlCamaraComercio = urlCamaraComercio;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B estadoVerificacion(final EstadoVerificacion estadoVerificacion) {
            this.estadoVerificacion = estadoVerificacion;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B fechaVerificacion(final LocalDateTime fechaVerificacion) {
            this.fechaVerificacion = fechaVerificacion;
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

        @java.lang.Override
        protected abstract B self();

        @java.lang.Override
        public abstract C build();

        @java.lang.Override
        public java.lang.String toString() {
            return "Fundacion.FundacionBuilder(super=" + super.toString() + ", nombre=" + this.nombre + ", nit=" + this.nit + ", representanteLegal=" + this.representanteLegal + ", descripcion=" + this.descripcion + ", direccion=" + this.direccion + ", contacto=" + this.contacto + ", urlLogo=" + this.urlLogo + ", urlPortada=" + this.urlPortada + ", urlRut=" + this.urlRut + ", urlCamaraComercio=" + this.urlCamaraComercio + ", estadoVerificacion=" + this.estadoVerificacion + ", fechaVerificacion=" + this.fechaVerificacion + ", latitud=" + this.latitud + ", longitud=" + this.longitud + ")";
        }
    }
    private static final class FundacionBuilderImpl extends Fundacion.FundacionBuilder<Fundacion, Fundacion.FundacionBuilderImpl> {
        private FundacionBuilderImpl() {
        }

        @java.lang.Override
        protected Fundacion.FundacionBuilderImpl self() {
            return this;
        }

        @java.lang.Override
        public Fundacion build() {
            return new Fundacion(this);
        }
    }
    protected Fundacion(final Fundacion.FundacionBuilder<?, ?> b) {
        super(b);
        this.nombre = b.nombre;
        this.nit = b.nit;
        this.representanteLegal = b.representanteLegal;
        this.descripcion = b.descripcion;
        this.direccion = b.direccion;
        this.contacto = b.contacto;
        this.urlLogo = b.urlLogo;
        this.urlPortada = b.urlPortada;
        this.urlRut = b.urlRut;
        this.urlCamaraComercio = b.urlCamaraComercio;
        this.estadoVerificacion = b.estadoVerificacion;
        this.fechaVerificacion = b.fechaVerificacion;
        this.latitud = b.latitud;
        this.longitud = b.longitud;
    }
    public static Fundacion.FundacionBuilder<?, ?> builder() {
        return new Fundacion.FundacionBuilderImpl();
    }
    public String getNombre() {
        return this.nombre;
    }
    public String getNit() {
        return this.nit;
    }
    public String getRepresentanteLegal() {
        return this.representanteLegal;
    }
    public String getDescripcion() {
        return this.descripcion;
    }
    public Direccion getDireccion() {
        return this.direccion;
    }
    public InformacionContacto getContacto() {
        return this.contacto;
    }
    public String getUrlLogo() {
        return this.urlLogo;
    }
    public String getUrlPortada() {
        return this.urlPortada;
    }
    public String getUrlRut() {
        return this.urlRut;
    }
    public String getUrlCamaraComercio() {
        return this.urlCamaraComercio;
    }
    public EstadoVerificacion getEstadoVerificacion() {
        return this.estadoVerificacion;
    }
    public LocalDateTime getFechaVerificacion() {
        return this.fechaVerificacion;
    }
    public BigDecimal getLatitud() {
        return this.latitud;
    }
    public BigDecimal getLongitud() {
        return this.longitud;
    }
    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }
    public void setNit(final String nit) {
        this.nit = nit;
    }
    public void setRepresentanteLegal(final String representanteLegal) {
        this.representanteLegal = representanteLegal;
    }
    public void setDescripcion(final String descripcion) {
        this.descripcion = descripcion;
    }
    public void setDireccion(final Direccion direccion) {
        this.direccion = direccion;
    }
    public void setContacto(final InformacionContacto contacto) {
        this.contacto = contacto;
    }
    public void setUrlLogo(final String urlLogo) {
        this.urlLogo = urlLogo;
    }
    public void setUrlPortada(final String urlPortada) {
        this.urlPortada = urlPortada;
    }
    public void setUrlRut(final String urlRut) {
        this.urlRut = urlRut;
    }
    public void setUrlCamaraComercio(final String urlCamaraComercio) {
        this.urlCamaraComercio = urlCamaraComercio;
    }
    public void setEstadoVerificacion(final EstadoVerificacion estadoVerificacion) {
        this.estadoVerificacion = estadoVerificacion;
    }
    public void setFechaVerificacion(final LocalDateTime fechaVerificacion) {
        this.fechaVerificacion = fechaVerificacion;
    }
    public void setLatitud(final BigDecimal latitud) {
        this.latitud = latitud;
    }
    public void setLongitud(final BigDecimal longitud) {
        this.longitud = longitud;
    }
    public Fundacion() {
    }
    public Fundacion(final String nombre, final String nit, final String representanteLegal, final String descripcion, final Direccion direccion, final InformacionContacto contacto, final String urlLogo, final String urlPortada, final String urlRut, final String urlCamaraComercio, final EstadoVerificacion estadoVerificacion, final LocalDateTime fechaVerificacion, final BigDecimal latitud, final BigDecimal longitud) {
        this.nombre = nombre;
        this.nit = nit;
        this.representanteLegal = representanteLegal;
        this.descripcion = descripcion;
        this.direccion = direccion;
        this.contacto = contacto;
        this.urlLogo = urlLogo;
        this.urlPortada = urlPortada;
        this.urlRut = urlRut;
        this.urlCamaraComercio = urlCamaraComercio;
        this.estadoVerificacion = estadoVerificacion;
        this.fechaVerificacion = fechaVerificacion;
        this.latitud = latitud;
        this.longitud = longitud;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Fundacion(super=" + super.toString() + ", nombre=" + this.getNombre() + ", nit=" + this.getNit() + ", representanteLegal=" + this.getRepresentanteLegal() + ", descripcion=" + this.getDescripcion() + ", direccion=" + this.getDireccion() + ", contacto=" + this.getContacto() + ", urlLogo=" + this.getUrlLogo() + ", urlPortada=" + this.getUrlPortada() + ", urlRut=" + this.getUrlRut() + ", urlCamaraComercio=" + this.getUrlCamaraComercio() + ", estadoVerificacion=" + this.getEstadoVerificacion() + ", fechaVerificacion=" + this.getFechaVerificacion() + ", latitud=" + this.getLatitud() + ", longitud=" + this.getLongitud() + ")";
    }
}
