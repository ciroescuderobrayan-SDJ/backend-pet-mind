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

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import com.cesde.petmind.model.base.BaseEntity;
import com.cesde.petmind.model.enums.TipoNotificacion;

@Entity
@Table(name = "notificaciones")
public class Notificacion extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoNotificacion tipo;
    @Column(name = "titulo", nullable = false, length = 150)
    private String titulo;
    @Column(name = "mensaje", nullable = false, length = 500)
    private String mensaje;
    // A donde lleva la notificacion al tocarla en el navbar
    @Column(name = "url_destino", length = 500)
    private String urlDestino;
    @Column(name = "leida", nullable = false)
    private Boolean leida;
    private static Boolean $default$leida() {
        return false;
    }
    public static abstract class NotificacionBuilder<C extends Notificacion, B extends Notificacion.NotificacionBuilder<C, B>> extends BaseEntity.BaseEntityBuilder<C, B> {
        private Usuario usuario;
        private TipoNotificacion tipo;
        private String titulo;
        private String mensaje;
        private String urlDestino;
        private boolean leida$set;
        private Boolean leida$value;

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
        public B tipo(final TipoNotificacion tipo) {
            this.tipo = tipo;
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
        public B mensaje(final String mensaje) {
            this.mensaje = mensaje;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B urlDestino(final String urlDestino) {
            this.urlDestino = urlDestino;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B leida(final Boolean leida) {
            this.leida$value = leida;
            leida$set = true;
            return self();
        }

        @java.lang.Override
        protected abstract B self();

        @java.lang.Override
        public abstract C build();

        @java.lang.Override
        public java.lang.String toString() {
            return "Notificacion.NotificacionBuilder(super=" + super.toString() + ", usuario=" + this.usuario + ", tipo=" + this.tipo + ", titulo=" + this.titulo + ", mensaje=" + this.mensaje + ", urlDestino=" + this.urlDestino + ", leida$value=" + this.leida$value + ")";
        }
    }
    private static final class NotificacionBuilderImpl extends Notificacion.NotificacionBuilder<Notificacion, Notificacion.NotificacionBuilderImpl> {
        private NotificacionBuilderImpl() {
        }

        @java.lang.Override
        protected Notificacion.NotificacionBuilderImpl self() {
            return this;
        }

        @java.lang.Override
        public Notificacion build() {
            return new Notificacion(this);
        }
    }
    protected Notificacion(final Notificacion.NotificacionBuilder<?, ?> b) {
        super(b);
        this.usuario = b.usuario;
        this.tipo = b.tipo;
        this.titulo = b.titulo;
        this.mensaje = b.mensaje;
        this.urlDestino = b.urlDestino;
        if (b.leida$set) this.leida = b.leida$value;
         else this.leida = Notificacion.$default$leida();
    }
    public static Notificacion.NotificacionBuilder<?, ?> builder() {
        return new Notificacion.NotificacionBuilderImpl();
    }
    public Usuario getUsuario() {
        return this.usuario;
    }
    public TipoNotificacion getTipo() {
        return this.tipo;
    }
    public String getTitulo() {
        return this.titulo;
    }
    public String getMensaje() {
        return this.mensaje;
    }
    public String getUrlDestino() {
        return this.urlDestino;
    }
    public Boolean getLeida() {
        return this.leida;
    }
    public void setUsuario(final Usuario usuario) {
        this.usuario = usuario;
    }
    public void setTipo(final TipoNotificacion tipo) {
        this.tipo = tipo;
    }
    public void setTitulo(final String titulo) {
        this.titulo = titulo;
    }
    public void setMensaje(final String mensaje) {
        this.mensaje = mensaje;
    }
    public void setUrlDestino(final String urlDestino) {
        this.urlDestino = urlDestino;
    }
    public void setLeida(final Boolean leida) {
        this.leida = leida;
    }
    public Notificacion() {
        this.leida = Notificacion.$default$leida();
    }
    public Notificacion(final Usuario usuario, final TipoNotificacion tipo, final String titulo, final String mensaje, final String urlDestino, final Boolean leida) {
        this.usuario = usuario;
        this.tipo = tipo;
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.urlDestino = urlDestino;
        this.leida = leida;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Notificacion(super=" + super.toString() + ", tipo=" + this.getTipo() + ", titulo=" + this.getTitulo() + ", mensaje=" + this.getMensaje() + ", urlDestino=" + this.getUrlDestino() + ", leida=" + this.getLeida() + ")";
    }
}
