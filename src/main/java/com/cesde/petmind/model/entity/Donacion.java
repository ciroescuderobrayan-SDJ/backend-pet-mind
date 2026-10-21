package com.cesde.petmind.model.entity;

// Lombok deshabilitado temporalmente; implementacion manual abajo.
// import lombok.AllArgsConstructor;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;
// import lombok.ToString;
// import lombok.experimental.SuperBuilder;
// import lombok.Builder;
// @Getter
// @Setter
// @NoArgsConstructor
// @AllArgsConstructor
// @SuperBuilder
// @ToString(callSuper = true)
// @ToString.Exclude
// @ToString.Exclude
// @Builder.Default

import java.math.BigDecimal;
import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import com.cesde.petmind.model.base.BaseEntity;
import com.cesde.petmind.model.enums.EstadoDonacion;
import com.cesde.petmind.model.enums.EstadoRecurrencia;
import com.cesde.petmind.model.enums.Frecuencia;
import com.cesde.petmind.model.enums.MetodoPago;

@Entity
@Table(name = "donaciones")
public class Donacion extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campana_id")
    private CampanaDonacion campana;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
    @Column(name = "monto", nullable = false, precision = 12, scale = 2)
    private BigDecimal monto;
    @Column(name = "valor_comision", precision = 12, scale = 2)
    private BigDecimal valorComision;
    @Column(name = "monto_total", nullable = false, precision = 12, scale = 2)
    private BigDecimal montoTotal;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "donacion_origen_id")
    private Donacion donacionOrigen;
    @Enumerated(EnumType.STRING)
    @Column(name = "frecuencia", nullable = false)
    private Frecuencia frecuencia;
    @Column(name = "fecha_donacion", nullable = false)
    private LocalDate fechaDonacion;
    @Enumerated(EnumType.STRING)
    @Column(name = "metodo_pago", nullable = false)
    private MetodoPago metodoPago;
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoDonacion estado;
    @Column(name = "es_anonima", nullable = false)
    private Boolean esAnonima;
    @Column(name = "mensaje", length = 500)
    private String mensaje;
    @Column(name = "referencia_pago", unique = true, length = 100)
    private String referenciaPago;
    @Column(name = "nombre_donante", length = 150)
    private String nombreDonante;
    @Column(name = "correo_donante", length = 100)
    private String correoDonante;
    @Column(name = "documento_donante", length = 30)
    private String documentoDonante;
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_recurrencia")
    private EstadoRecurrencia estadoRecurrencia;
    @Column(name = "dia_cobro")
    private Integer diaCobro;
    @Column(name = "fecha_proximo_cobro")
    private LocalDate fechaProximoCobro;
    @Column(name = "certificado_codigo", unique = true, length = 100)
    private String certificadoCodigo;
    @Column(name = "certificado_url", length = 500)
    private String certificadoUrl;
    @Column(name = "certificado_fecha")
    private LocalDate certificadoFecha;
    private static Boolean $default$esAnonima() {
        return false;
    }
    public static abstract class DonacionBuilder<C extends Donacion, B extends Donacion.DonacionBuilder<C, B>> extends BaseEntity.BaseEntityBuilder<C, B> {
        private CampanaDonacion campana;
        private Usuario usuario;
        private BigDecimal monto;
        private BigDecimal valorComision;
        private BigDecimal montoTotal;
        private Donacion donacionOrigen;
        private Frecuencia frecuencia;
        private LocalDate fechaDonacion;
        private MetodoPago metodoPago;
        private EstadoDonacion estado;
        private boolean esAnonima$set;
        private Boolean esAnonima$value;
        private String mensaje;
        private String referenciaPago;
        private String nombreDonante;
        private String correoDonante;
        private String documentoDonante;
        private EstadoRecurrencia estadoRecurrencia;
        private Integer diaCobro;
        private LocalDate fechaProximoCobro;
        private String certificadoCodigo;
        private String certificadoUrl;
        private LocalDate certificadoFecha;

        /**
         * @return {@code this}.
         */
        public B campana(final CampanaDonacion campana) {
            this.campana = campana;
            return self();
        }

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
        public B monto(final BigDecimal monto) {
            this.monto = monto;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B valorComision(final BigDecimal valorComision) {
            this.valorComision = valorComision;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B montoTotal(final BigDecimal montoTotal) {
            this.montoTotal = montoTotal;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B donacionOrigen(final Donacion donacionOrigen) {
            this.donacionOrigen = donacionOrigen;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B frecuencia(final Frecuencia frecuencia) {
            this.frecuencia = frecuencia;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B fechaDonacion(final LocalDate fechaDonacion) {
            this.fechaDonacion = fechaDonacion;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B metodoPago(final MetodoPago metodoPago) {
            this.metodoPago = metodoPago;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B estado(final EstadoDonacion estado) {
            this.estado = estado;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B esAnonima(final Boolean esAnonima) {
            this.esAnonima$value = esAnonima;
            esAnonima$set = true;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B mensaje(final String mensaje) {
            this.mensaje = mensaje;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B referenciaPago(final String referenciaPago) {
            this.referenciaPago = referenciaPago;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B nombreDonante(final String nombreDonante) {
            this.nombreDonante = nombreDonante;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B correoDonante(final String correoDonante) {
            this.correoDonante = correoDonante;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B documentoDonante(final String documentoDonante) {
            this.documentoDonante = documentoDonante;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B estadoRecurrencia(final EstadoRecurrencia estadoRecurrencia) {
            this.estadoRecurrencia = estadoRecurrencia;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B diaCobro(final Integer diaCobro) {
            this.diaCobro = diaCobro;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B fechaProximoCobro(final LocalDate fechaProximoCobro) {
            this.fechaProximoCobro = fechaProximoCobro;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B certificadoCodigo(final String certificadoCodigo) {
            this.certificadoCodigo = certificadoCodigo;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B certificadoUrl(final String certificadoUrl) {
            this.certificadoUrl = certificadoUrl;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B certificadoFecha(final LocalDate certificadoFecha) {
            this.certificadoFecha = certificadoFecha;
            return self();
        }

        @java.lang.Override
        protected abstract B self();

        @java.lang.Override
        public abstract C build();

        @java.lang.Override
        public java.lang.String toString() {
            return "Donacion.DonacionBuilder(super=" + super.toString() + ", campana=" + this.campana + ", usuario=" + this.usuario + ", monto=" + this.monto + ", valorComision=" + this.valorComision + ", montoTotal=" + this.montoTotal + ", donacionOrigen=" + this.donacionOrigen + ", frecuencia=" + this.frecuencia + ", fechaDonacion=" + this.fechaDonacion + ", metodoPago=" + this.metodoPago + ", estado=" + this.estado + ", esAnonima$value=" + this.esAnonima$value + ", mensaje=" + this.mensaje + ", referenciaPago=" + this.referenciaPago + ", nombreDonante=" + this.nombreDonante + ", correoDonante=" + this.correoDonante + ", documentoDonante=" + this.documentoDonante + ", estadoRecurrencia=" + this.estadoRecurrencia + ", diaCobro=" + this.diaCobro + ", fechaProximoCobro=" + this.fechaProximoCobro + ", certificadoCodigo=" + this.certificadoCodigo + ", certificadoUrl=" + this.certificadoUrl + ", certificadoFecha=" + this.certificadoFecha + ")";
        }
    }
    private static final class DonacionBuilderImpl extends Donacion.DonacionBuilder<Donacion, Donacion.DonacionBuilderImpl> {
        private DonacionBuilderImpl() {
        }

        @java.lang.Override
        protected Donacion.DonacionBuilderImpl self() {
            return this;
        }

        @java.lang.Override
        public Donacion build() {
            return new Donacion(this);
        }
    }
    protected Donacion(final Donacion.DonacionBuilder<?, ?> b) {
        super(b);
        this.campana = b.campana;
        this.usuario = b.usuario;
        this.monto = b.monto;
        this.valorComision = b.valorComision;
        this.montoTotal = b.montoTotal;
        this.donacionOrigen = b.donacionOrigen;
        this.frecuencia = b.frecuencia;
        this.fechaDonacion = b.fechaDonacion;
        this.metodoPago = b.metodoPago;
        this.estado = b.estado;
        if (b.esAnonima$set) this.esAnonima = b.esAnonima$value;
         else this.esAnonima = Donacion.$default$esAnonima();
        this.mensaje = b.mensaje;
        this.referenciaPago = b.referenciaPago;
        this.nombreDonante = b.nombreDonante;
        this.correoDonante = b.correoDonante;
        this.documentoDonante = b.documentoDonante;
        this.estadoRecurrencia = b.estadoRecurrencia;
        this.diaCobro = b.diaCobro;
        this.fechaProximoCobro = b.fechaProximoCobro;
        this.certificadoCodigo = b.certificadoCodigo;
        this.certificadoUrl = b.certificadoUrl;
        this.certificadoFecha = b.certificadoFecha;
    }
    public static Donacion.DonacionBuilder<?, ?> builder() {
        return new Donacion.DonacionBuilderImpl();
    }
    public CampanaDonacion getCampana() {
        return this.campana;
    }
    public Usuario getUsuario() {
        return this.usuario;
    }
    public BigDecimal getMonto() {
        return this.monto;
    }
    public BigDecimal getValorComision() {
        return this.valorComision;
    }
    public BigDecimal getMontoTotal() {
        return this.montoTotal;
    }
    public Donacion getDonacionOrigen() {
        return this.donacionOrigen;
    }
    public Frecuencia getFrecuencia() {
        return this.frecuencia;
    }
    public LocalDate getFechaDonacion() {
        return this.fechaDonacion;
    }
    public MetodoPago getMetodoPago() {
        return this.metodoPago;
    }
    public EstadoDonacion getEstado() {
        return this.estado;
    }
    public Boolean getEsAnonima() {
        return this.esAnonima;
    }
    public String getMensaje() {
        return this.mensaje;
    }
    public String getReferenciaPago() {
        return this.referenciaPago;
    }
    public String getNombreDonante() {
        return this.nombreDonante;
    }
    public String getCorreoDonante() {
        return this.correoDonante;
    }
    public String getDocumentoDonante() {
        return this.documentoDonante;
    }
    public EstadoRecurrencia getEstadoRecurrencia() {
        return this.estadoRecurrencia;
    }
    public Integer getDiaCobro() {
        return this.diaCobro;
    }
    public LocalDate getFechaProximoCobro() {
        return this.fechaProximoCobro;
    }
    public String getCertificadoCodigo() {
        return this.certificadoCodigo;
    }
    public String getCertificadoUrl() {
        return this.certificadoUrl;
    }
    public LocalDate getCertificadoFecha() {
        return this.certificadoFecha;
    }
    public void setCampana(final CampanaDonacion campana) {
        this.campana = campana;
    }
    public void setUsuario(final Usuario usuario) {
        this.usuario = usuario;
    }
    public void setMonto(final BigDecimal monto) {
        this.monto = monto;
    }
    public void setValorComision(final BigDecimal valorComision) {
        this.valorComision = valorComision;
    }
    public void setMontoTotal(final BigDecimal montoTotal) {
        this.montoTotal = montoTotal;
    }
    public void setDonacionOrigen(final Donacion donacionOrigen) {
        this.donacionOrigen = donacionOrigen;
    }
    public void setFrecuencia(final Frecuencia frecuencia) {
        this.frecuencia = frecuencia;
    }
    public void setFechaDonacion(final LocalDate fechaDonacion) {
        this.fechaDonacion = fechaDonacion;
    }
    public void setMetodoPago(final MetodoPago metodoPago) {
        this.metodoPago = metodoPago;
    }
    public void setEstado(final EstadoDonacion estado) {
        this.estado = estado;
    }
    public void setEsAnonima(final Boolean esAnonima) {
        this.esAnonima = esAnonima;
    }
    public void setMensaje(final String mensaje) {
        this.mensaje = mensaje;
    }
    public void setReferenciaPago(final String referenciaPago) {
        this.referenciaPago = referenciaPago;
    }
    public void setNombreDonante(final String nombreDonante) {
        this.nombreDonante = nombreDonante;
    }
    public void setCorreoDonante(final String correoDonante) {
        this.correoDonante = correoDonante;
    }
    public void setDocumentoDonante(final String documentoDonante) {
        this.documentoDonante = documentoDonante;
    }
    public void setEstadoRecurrencia(final EstadoRecurrencia estadoRecurrencia) {
        this.estadoRecurrencia = estadoRecurrencia;
    }
    public void setDiaCobro(final Integer diaCobro) {
        this.diaCobro = diaCobro;
    }
    public void setFechaProximoCobro(final LocalDate fechaProximoCobro) {
        this.fechaProximoCobro = fechaProximoCobro;
    }
    public void setCertificadoCodigo(final String certificadoCodigo) {
        this.certificadoCodigo = certificadoCodigo;
    }
    public void setCertificadoUrl(final String certificadoUrl) {
        this.certificadoUrl = certificadoUrl;
    }
    public void setCertificadoFecha(final LocalDate certificadoFecha) {
        this.certificadoFecha = certificadoFecha;
    }
    public Donacion() {
        this.esAnonima = Donacion.$default$esAnonima();
    }
    public Donacion(final CampanaDonacion campana, final Usuario usuario, final BigDecimal monto, final BigDecimal valorComision, final BigDecimal montoTotal, final Donacion donacionOrigen, final Frecuencia frecuencia, final LocalDate fechaDonacion, final MetodoPago metodoPago, final EstadoDonacion estado, final Boolean esAnonima, final String mensaje, final String referenciaPago, final String nombreDonante, final String correoDonante, final String documentoDonante, final EstadoRecurrencia estadoRecurrencia, final Integer diaCobro, final LocalDate fechaProximoCobro, final String certificadoCodigo, final String certificadoUrl, final LocalDate certificadoFecha) {
        this.campana = campana;
        this.usuario = usuario;
        this.monto = monto;
        this.valorComision = valorComision;
        this.montoTotal = montoTotal;
        this.donacionOrigen = donacionOrigen;
        this.frecuencia = frecuencia;
        this.fechaDonacion = fechaDonacion;
        this.metodoPago = metodoPago;
        this.estado = estado;
        this.esAnonima = esAnonima;
        this.mensaje = mensaje;
        this.referenciaPago = referenciaPago;
        this.nombreDonante = nombreDonante;
        this.correoDonante = correoDonante;
        this.documentoDonante = documentoDonante;
        this.estadoRecurrencia = estadoRecurrencia;
        this.diaCobro = diaCobro;
        this.fechaProximoCobro = fechaProximoCobro;
        this.certificadoCodigo = certificadoCodigo;
        this.certificadoUrl = certificadoUrl;
        this.certificadoFecha = certificadoFecha;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Donacion(super=" + super.toString() + ", usuario=" + this.getUsuario() + ", monto=" + this.getMonto() + ", valorComision=" + this.getValorComision() + ", montoTotal=" + this.getMontoTotal() + ", frecuencia=" + this.getFrecuencia() + ", fechaDonacion=" + this.getFechaDonacion() + ", metodoPago=" + this.getMetodoPago() + ", estado=" + this.getEstado() + ", esAnonima=" + this.getEsAnonima() + ", mensaje=" + this.getMensaje() + ", referenciaPago=" + this.getReferenciaPago() + ", nombreDonante=" + this.getNombreDonante() + ", correoDonante=" + this.getCorreoDonante() + ", documentoDonante=" + this.getDocumentoDonante() + ", estadoRecurrencia=" + this.getEstadoRecurrencia() + ", diaCobro=" + this.getDiaCobro() + ", fechaProximoCobro=" + this.getFechaProximoCobro() + ", certificadoCodigo=" + this.getCertificadoCodigo() + ", certificadoUrl=" + this.getCertificadoUrl() + ", certificadoFecha=" + this.getCertificadoFecha() + ")";
    }
}
