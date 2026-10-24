package com.cesde.petmind.model.base;

// Lombok deshabilitado temporalmente; implementacion manual abajo.
// import lombok.AllArgsConstructor;
// import lombok.Builder;
// import lombok.Getter;
// import lombok.NoArgsConstructor;
// import lombok.Setter;
// import lombok.experimental.SuperBuilder;
// @Getter
// @Setter
// @SuperBuilder
// @NoArgsConstructor
// @AllArgsConstructor
// @Builder.Default

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    @Column(name = "fecha_creacion", nullable = false, updatable = false)
    private LocalDateTime fechaCreacion;
    @UpdateTimestamp
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    @Column(name = "estado_activo", nullable = false)
    private Boolean estadoActivo;
    private static Boolean $default$estadoActivo() {
        return true;
    }
    public static abstract class BaseEntityBuilder<C extends BaseEntity, B extends BaseEntity.BaseEntityBuilder<C, B>> {
        private Long id;
        private LocalDateTime fechaCreacion;
        private LocalDateTime fechaActualizacion;
        private boolean estadoActivo$set;
        private Boolean estadoActivo$value;

        /**
         * @return {@code this}.
         */
        public B id(final Long id) {
            this.id = id;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B fechaCreacion(final LocalDateTime fechaCreacion) {
            this.fechaCreacion = fechaCreacion;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B fechaActualizacion(final LocalDateTime fechaActualizacion) {
            this.fechaActualizacion = fechaActualizacion;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B estadoActivo(final Boolean estadoActivo) {
            this.estadoActivo$value = estadoActivo;
            estadoActivo$set = true;
            return self();
        }
        protected abstract B self();
        public abstract C build();

        @java.lang.Override
        public java.lang.String toString() {
            return "BaseEntity.BaseEntityBuilder(id=" + this.id + ", fechaCreacion=" + this.fechaCreacion + ", fechaActualizacion=" + this.fechaActualizacion + ", estadoActivo$value=" + this.estadoActivo$value + ")";
        }
    }
    protected BaseEntity(final BaseEntity.BaseEntityBuilder<?, ?> b) {
        this.id = b.id;
        this.fechaCreacion = b.fechaCreacion;
        this.fechaActualizacion = b.fechaActualizacion;
        if (b.estadoActivo$set) this.estadoActivo = b.estadoActivo$value;
         else this.estadoActivo = BaseEntity.$default$estadoActivo();
    }
    public Long getId() {
        return this.id;
    }
    public LocalDateTime getFechaCreacion() {
        return this.fechaCreacion;
    }
    public LocalDateTime getFechaActualizacion() {
        return this.fechaActualizacion;
    }
    public Boolean getEstadoActivo() {
        return this.estadoActivo;
    }
    public void setId(final Long id) {
        this.id = id;
    }
    public void setFechaCreacion(final LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    public void setFechaActualizacion(final LocalDateTime fechaActualizacion) {
        this.fechaActualizacion = fechaActualizacion;
    }
    public void setEstadoActivo(final Boolean estadoActivo) {
        this.estadoActivo = estadoActivo;
    }
    public BaseEntity() {
        this.estadoActivo = BaseEntity.$default$estadoActivo();
    }
    public BaseEntity(final Long id, final LocalDateTime fechaCreacion, final LocalDateTime fechaActualizacion, final Boolean estadoActivo) {
        this.id = id;
        this.fechaCreacion = fechaCreacion;
        this.fechaActualizacion = fechaActualizacion;
        this.estadoActivo = estadoActivo;
    }
}
