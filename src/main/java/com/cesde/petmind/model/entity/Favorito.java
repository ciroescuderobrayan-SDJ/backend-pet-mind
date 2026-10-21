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
// @ToString.Exclude
// @ToString.Exclude

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import com.cesde.petmind.model.base.BaseEntity;

@Entity
@Table(name = "favoritos")
public class Favorito extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
    // Las tres FK siguientes son nulas: un favorito apunta a UNA sola de ellas.
    // La validacion de "solo una llena" va en el servicio, no aca.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mascota_id")
    private Mascota mascota;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campana_id")
    private CampanaDonacion campana;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fundacion_id")
    private Fundacion fundacion;
    public static abstract class FavoritoBuilder<C extends Favorito, B extends Favorito.FavoritoBuilder<C, B>> extends BaseEntity.BaseEntityBuilder<C, B> {
        private Usuario usuario;
        private Mascota mascota;
        private CampanaDonacion campana;
        private Fundacion fundacion;

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
        public B mascota(final Mascota mascota) {
            this.mascota = mascota;
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
        public B fundacion(final Fundacion fundacion) {
            this.fundacion = fundacion;
            return self();
        }

        @java.lang.Override
        protected abstract B self();

        @java.lang.Override
        public abstract C build();

        @java.lang.Override
        public java.lang.String toString() {
            return "Favorito.FavoritoBuilder(super=" + super.toString() + ", usuario=" + this.usuario + ", mascota=" + this.mascota + ", campana=" + this.campana + ", fundacion=" + this.fundacion + ")";
        }
    }
    private static final class FavoritoBuilderImpl extends Favorito.FavoritoBuilder<Favorito, Favorito.FavoritoBuilderImpl> {
        private FavoritoBuilderImpl() {
        }

        @java.lang.Override
        protected Favorito.FavoritoBuilderImpl self() {
            return this;
        }

        @java.lang.Override
        public Favorito build() {
            return new Favorito(this);
        }
    }
    protected Favorito(final Favorito.FavoritoBuilder<?, ?> b) {
        super(b);
        this.usuario = b.usuario;
        this.mascota = b.mascota;
        this.campana = b.campana;
        this.fundacion = b.fundacion;
    }
    public static Favorito.FavoritoBuilder<?, ?> builder() {
        return new Favorito.FavoritoBuilderImpl();
    }
    public Usuario getUsuario() {
        return this.usuario;
    }
    public Mascota getMascota() {
        return this.mascota;
    }
    public CampanaDonacion getCampana() {
        return this.campana;
    }
    public Fundacion getFundacion() {
        return this.fundacion;
    }
    public void setUsuario(final Usuario usuario) {
        this.usuario = usuario;
    }
    public void setMascota(final Mascota mascota) {
        this.mascota = mascota;
    }
    public void setCampana(final CampanaDonacion campana) {
        this.campana = campana;
    }
    public void setFundacion(final Fundacion fundacion) {
        this.fundacion = fundacion;
    }
    public Favorito() {
    }
    public Favorito(final Usuario usuario, final Mascota mascota, final CampanaDonacion campana, final Fundacion fundacion) {
        this.usuario = usuario;
        this.mascota = mascota;
        this.campana = campana;
        this.fundacion = fundacion;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Favorito(super=" + super.toString() + ")";
    }
}
