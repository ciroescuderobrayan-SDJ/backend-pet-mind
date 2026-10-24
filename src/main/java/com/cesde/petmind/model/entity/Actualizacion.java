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

import java.time.LocalDateTime;
import com.cesde.petmind.model.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "actualizaciones")
public class Actualizacion extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campana_id")
    private CampanaDonacion campana;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "historia_id")
    private Historia historia;
    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo;
    @Column(name = "contenido", nullable = false, length = 5000)
    private String contenido;
    @Column(name = "fecha_evento", nullable = false)
    private LocalDateTime fechaEvento;
    @Column(name = "orden")
    private Integer orden;
    public static abstract class ActualizacionBuilder<C extends Actualizacion, B extends Actualizacion.ActualizacionBuilder<C, B>> extends BaseEntity.BaseEntityBuilder<C, B> {
        private CampanaDonacion campana;
        private Historia historia;
        private String titulo;
        private String contenido;
        private LocalDateTime fechaEvento;
        private Integer orden;

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
        public B titulo(final String titulo) {
            this.titulo = titulo;
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
        public B fechaEvento(final LocalDateTime fechaEvento) {
            this.fechaEvento = fechaEvento;
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
            return "Actualizacion.ActualizacionBuilder(super=" + super.toString() + ", campana=" + this.campana + ", historia=" + this.historia + ", titulo=" + this.titulo + ", contenido=" + this.contenido + ", fechaEvento=" + this.fechaEvento + ", orden=" + this.orden + ")";
        }
    }
    private static final class ActualizacionBuilderImpl extends Actualizacion.ActualizacionBuilder<Actualizacion, Actualizacion.ActualizacionBuilderImpl> {
        private ActualizacionBuilderImpl() {
        }

        @java.lang.Override
        protected Actualizacion.ActualizacionBuilderImpl self() {
            return this;
        }

        @java.lang.Override
        public Actualizacion build() {
            return new Actualizacion(this);
        }
    }
    protected Actualizacion(final Actualizacion.ActualizacionBuilder<?, ?> b) {
        super(b);
        this.campana = b.campana;
        this.historia = b.historia;
        this.titulo = b.titulo;
        this.contenido = b.contenido;
        this.fechaEvento = b.fechaEvento;
        this.orden = b.orden;
    }
    public static Actualizacion.ActualizacionBuilder<?, ?> builder() {
        return new Actualizacion.ActualizacionBuilderImpl();
    }
    public CampanaDonacion getCampana() {
        return this.campana;
    }
    public Historia getHistoria() {
        return this.historia;
    }
    public String getTitulo() {
        return this.titulo;
    }
    public String getContenido() {
        return this.contenido;
    }
    public LocalDateTime getFechaEvento() {
        return this.fechaEvento;
    }
    public Integer getOrden() {
        return this.orden;
    }
    public void setCampana(final CampanaDonacion campana) {
        this.campana = campana;
    }
    public void setHistoria(final Historia historia) {
        this.historia = historia;
    }
    public void setTitulo(final String titulo) {
        this.titulo = titulo;
    }
    public void setContenido(final String contenido) {
        this.contenido = contenido;
    }
    public void setFechaEvento(final LocalDateTime fechaEvento) {
        this.fechaEvento = fechaEvento;
    }
    public void setOrden(final Integer orden) {
        this.orden = orden;
    }
    public Actualizacion() {
    }
    public Actualizacion(final CampanaDonacion campana, final Historia historia, final String titulo, final String contenido, final LocalDateTime fechaEvento, final Integer orden) {
        this.campana = campana;
        this.historia = historia;
        this.titulo = titulo;
        this.contenido = contenido;
        this.fechaEvento = fechaEvento;
        this.orden = orden;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Actualizacion(super=" + super.toString() + ", titulo=" + this.getTitulo() + ", contenido=" + this.getContenido() + ", fechaEvento=" + this.getFechaEvento() + ", orden=" + this.getOrden() + ")";
    }
}
