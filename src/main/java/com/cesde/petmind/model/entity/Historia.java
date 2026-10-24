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

import java.time.LocalDateTime;
import com.cesde.petmind.model.base.BaseEntity;
import com.cesde.petmind.model.enums.TipoHistoria;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "historias")
public class Historia extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fundacion_id", nullable = false)
    private Fundacion fundacion;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mascota_id")
    private Mascota mascota;
    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo;
    @Column(name = "resumen", nullable = false, length = 500)
    private String resumen;
    @Column(name = "contenido", nullable = false, length = 5000)
    private String contenido;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoHistoria tipo;
    @Column(name = "contador_likes", nullable = false)
    private Integer contadorLikes;
    @Column(name = "es_destacada", nullable = false)
    private Boolean esDestacada;
    @Column(name = "fecha_publicacion")
    private LocalDateTime fechaPublicacion;
    private static Integer $default$contadorLikes() {
        return 0;
    }
    private static Boolean $default$esDestacada() {
        return false;
    }
    public static abstract class HistoriaBuilder<C extends Historia, B extends Historia.HistoriaBuilder<C, B>> extends BaseEntity.BaseEntityBuilder<C, B> {
        private Fundacion fundacion;
        private Mascota mascota;
        private String titulo;
        private String resumen;
        private String contenido;
        private TipoHistoria tipo;
        private boolean contadorLikes$set;
        private Integer contadorLikes$value;
        private boolean esDestacada$set;
        private Boolean esDestacada$value;
        private LocalDateTime fechaPublicacion;

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
        public B resumen(final String resumen) {
            this.resumen = resumen;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B contenido(final String contenido) {
            this.contenido = contenido;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B tipo(final TipoHistoria tipo) {
            this.tipo = tipo;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B contadorLikes(final Integer contadorLikes) {
            this.contadorLikes$value = contadorLikes;
            contadorLikes$set = true;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B esDestacada(final Boolean esDestacada) {
            this.esDestacada$value = esDestacada;
            esDestacada$set = true;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B fechaPublicacion(final LocalDateTime fechaPublicacion) {
            this.fechaPublicacion = fechaPublicacion;
            return self();
        }

        @java.lang.Override
        protected abstract B self();

        @java.lang.Override
        public abstract C build();

        @java.lang.Override
        public java.lang.String toString() {
            return "Historia.HistoriaBuilder(super=" + super.toString() + ", fundacion=" + this.fundacion + ", mascota=" + this.mascota + ", titulo=" + this.titulo + ", resumen=" + this.resumen + ", contenido=" + this.contenido + ", tipo=" + this.tipo + ", contadorLikes$value=" + this.contadorLikes$value + ", esDestacada$value=" + this.esDestacada$value + ", fechaPublicacion=" + this.fechaPublicacion + ")";
        }
    }
    private static final class HistoriaBuilderImpl extends Historia.HistoriaBuilder<Historia, Historia.HistoriaBuilderImpl> {
        private HistoriaBuilderImpl() {
        }

        @java.lang.Override
        protected Historia.HistoriaBuilderImpl self() {
            return this;
        }

        @java.lang.Override
        public Historia build() {
            return new Historia(this);
        }
    }
    protected Historia(final Historia.HistoriaBuilder<?, ?> b) {
        super(b);
        this.fundacion = b.fundacion;
        this.mascota = b.mascota;
        this.titulo = b.titulo;
        this.resumen = b.resumen;
        this.contenido = b.contenido;
        this.tipo = b.tipo;
        if (b.contadorLikes$set) this.contadorLikes = b.contadorLikes$value;
         else this.contadorLikes = Historia.$default$contadorLikes();
        if (b.esDestacada$set) this.esDestacada = b.esDestacada$value;
         else this.esDestacada = Historia.$default$esDestacada();
        this.fechaPublicacion = b.fechaPublicacion;
    }
    public static Historia.HistoriaBuilder<?, ?> builder() {
        return new Historia.HistoriaBuilderImpl();
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
    public String getResumen() {
        return this.resumen;
    }
    public String getContenido() {
        return this.contenido;
    }
    public TipoHistoria getTipo() {
        return this.tipo;
    }
    public Integer getContadorLikes() {
        return this.contadorLikes;
    }
    public Boolean getEsDestacada() {
        return this.esDestacada;
    }
    public LocalDateTime getFechaPublicacion() {
        return this.fechaPublicacion;
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
    public void setResumen(final String resumen) {
        this.resumen = resumen;
    }
    public void setContenido(final String contenido) {
        this.contenido = contenido;
    }
    public void setTipo(final TipoHistoria tipo) {
        this.tipo = tipo;
    }
    public void setContadorLikes(final Integer contadorLikes) {
        this.contadorLikes = contadorLikes;
    }
    public void setEsDestacada(final Boolean esDestacada) {
        this.esDestacada = esDestacada;
    }
    public void setFechaPublicacion(final LocalDateTime fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }
    public Historia() {
        this.contadorLikes = Historia.$default$contadorLikes();
        this.esDestacada = Historia.$default$esDestacada();
    }
    public Historia(final Fundacion fundacion, final Mascota mascota, final String titulo, final String resumen, final String contenido, final TipoHistoria tipo, final Integer contadorLikes, final Boolean esDestacada, final LocalDateTime fechaPublicacion) {
        this.fundacion = fundacion;
        this.mascota = mascota;
        this.titulo = titulo;
        this.resumen = resumen;
        this.contenido = contenido;
        this.tipo = tipo;
        this.contadorLikes = contadorLikes;
        this.esDestacada = esDestacada;
        this.fechaPublicacion = fechaPublicacion;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Historia(super=" + super.toString() + ", titulo=" + this.getTitulo() + ", resumen=" + this.getResumen() + ", contenido=" + this.getContenido() + ", tipo=" + this.getTipo() + ", contadorLikes=" + this.getContadorLikes() + ", esDestacada=" + this.getEsDestacada() + ", fechaPublicacion=" + this.getFechaPublicacion() + ")";
    }
}
