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
// @ToString.Exclude
// @ToString.Exclude
// @ToString.Exclude
// @Builder.Default

import com.cesde.petmind.model.base.BaseEntity;
import com.cesde.petmind.model.enums.CategoriaArchivo;
import com.cesde.petmind.model.enums.TipoMedio;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "archivos")
public class Archivo extends BaseEntity {
    // Tabla transversal de medios: las cinco FK son nulas y solo una se llena.
    // La validacion de "solo una llena" va en el servicio, no aca.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mascota_id")
    private Mascota mascota;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solicitud_id")
    private SolicitudAdopcion solicitud;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campana_id")
    private CampanaDonacion campana;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "historia_id")
    private Historia historia;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporte_id")
    private ReporteAnimal reporte;
    @Column(name = "url", nullable = false, length = 500)
    private String url;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_medio", nullable = false)
    private TipoMedio tipoMedio;
    @Enumerated(EnumType.STRING)
    @Column(name = "categoria", nullable = false)
    private CategoriaArchivo categoria;
    @Column(name = "es_portada", nullable = false)
    private Boolean esPortada;
    // Posicion dentro de la galeria
    @Column(name = "orden")
    private Integer orden;
    private static Boolean $default$esPortada() {
        return false;
    }
    public static abstract class ArchivoBuilder<C extends Archivo, B extends Archivo.ArchivoBuilder<C, B>> extends BaseEntity.BaseEntityBuilder<C, B> {
        private Mascota mascota;
        private SolicitudAdopcion solicitud;
        private CampanaDonacion campana;
        private Historia historia;
        private ReporteAnimal reporte;
        private String url;
        private TipoMedio tipoMedio;
        private CategoriaArchivo categoria;
        private boolean esPortada$set;
        private Boolean esPortada$value;
        private Integer orden;

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
        public B solicitud(final SolicitudAdopcion solicitud) {
            this.solicitud = solicitud;
            return self();
        }

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
        public B historia(final Historia historia) {
            this.historia = historia;
            return self();
        }

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
        public B url(final String url) {
            this.url = url;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B tipoMedio(final TipoMedio tipoMedio) {
            this.tipoMedio = tipoMedio;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B categoria(final CategoriaArchivo categoria) {
            this.categoria = categoria;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B esPortada(final Boolean esPortada) {
            this.esPortada$value = esPortada;
            esPortada$set = true;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B orden(final Integer orden) {
            this.orden = orden;
            return self();
        }

        @java.lang.Override
        protected abstract B self();

        @java.lang.Override
        public abstract C build();

        @java.lang.Override
        public java.lang.String toString() {
            return "Archivo.ArchivoBuilder(super=" + super.toString() + ", mascota=" + this.mascota + ", solicitud=" + this.solicitud + ", campana=" + this.campana + ", historia=" + this.historia + ", reporte=" + this.reporte + ", url=" + this.url + ", tipoMedio=" + this.tipoMedio + ", categoria=" + this.categoria + ", esPortada$value=" + this.esPortada$value + ", orden=" + this.orden + ")";
        }
    }
    private static final class ArchivoBuilderImpl extends Archivo.ArchivoBuilder<Archivo, Archivo.ArchivoBuilderImpl> {
        private ArchivoBuilderImpl() {
        }

        @java.lang.Override
        protected Archivo.ArchivoBuilderImpl self() {
            return this;
        }

        @java.lang.Override
        public Archivo build() {
            return new Archivo(this);
        }
    }
    protected Archivo(final Archivo.ArchivoBuilder<?, ?> b) {
        super(b);
        this.mascota = b.mascota;
        this.solicitud = b.solicitud;
        this.campana = b.campana;
        this.historia = b.historia;
        this.reporte = b.reporte;
        this.url = b.url;
        this.tipoMedio = b.tipoMedio;
        this.categoria = b.categoria;
        if (b.esPortada$set) this.esPortada = b.esPortada$value;
         else this.esPortada = Archivo.$default$esPortada();
        this.orden = b.orden;
    }
    public static Archivo.ArchivoBuilder<?, ?> builder() {
        return new Archivo.ArchivoBuilderImpl();
    }
    public Mascota getMascota() {
        return this.mascota;
    }
    public SolicitudAdopcion getSolicitud() {
        return this.solicitud;
    }
    public CampanaDonacion getCampana() {
        return this.campana;
    }
    public Historia getHistoria() {
        return this.historia;
    }
    public ReporteAnimal getReporte() {
        return this.reporte;
    }
    public String getUrl() {
        return this.url;
    }
    public TipoMedio getTipoMedio() {
        return this.tipoMedio;
    }
    public CategoriaArchivo getCategoria() {
        return this.categoria;
    }
    public Boolean getEsPortada() {
        return this.esPortada;
    }
    public Integer getOrden() {
        return this.orden;
    }
    public void setMascota(final Mascota mascota) {
        this.mascota = mascota;
    }
    public void setSolicitud(final SolicitudAdopcion solicitud) {
        this.solicitud = solicitud;
    }
    public void setCampana(final CampanaDonacion campana) {
        this.campana = campana;
    }
    public void setHistoria(final Historia historia) {
        this.historia = historia;
    }
    public void setReporte(final ReporteAnimal reporte) {
        this.reporte = reporte;
    }
    public void setUrl(final String url) {
        this.url = url;
    }
    public void setTipoMedio(final TipoMedio tipoMedio) {
        this.tipoMedio = tipoMedio;
    }
    public void setCategoria(final CategoriaArchivo categoria) {
        this.categoria = categoria;
    }
    public void setEsPortada(final Boolean esPortada) {
        this.esPortada = esPortada;
    }
    public void setOrden(final Integer orden) {
        this.orden = orden;
    }
    public Archivo() {
        this.esPortada = Archivo.$default$esPortada();
    }
    public Archivo(final Mascota mascota, final SolicitudAdopcion solicitud, final CampanaDonacion campana, final Historia historia, final ReporteAnimal reporte, final String url, final TipoMedio tipoMedio, final CategoriaArchivo categoria, final Boolean esPortada, final Integer orden) {
        this.mascota = mascota;
        this.solicitud = solicitud;
        this.campana = campana;
        this.historia = historia;
        this.reporte = reporte;
        this.url = url;
        this.tipoMedio = tipoMedio;
        this.categoria = categoria;
        this.esPortada = esPortada;
        this.orden = orden;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Archivo(super=" + super.toString() + ", url=" + this.getUrl() + ", tipoMedio=" + this.getTipoMedio() + ", categoria=" + this.getCategoria() + ", esPortada=" + this.getEsPortada() + ", orden=" + this.getOrden() + ")";
    }
}
