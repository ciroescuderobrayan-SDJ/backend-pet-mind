package com.cesde.petmind.model.entity;

// Lombok deshabilitado temporalmente; implementacion manual abajo.
// import lombok.Builder;
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
// @Builder.Default
// @Builder.Default
// @Builder.Default
// @Builder.Default
// @Builder.Default
// @Builder.Default

import java.time.LocalDateTime;
import com.cesde.petmind.model.base.BaseEntity;
import com.cesde.petmind.model.embeddable.Afinidad;
import com.cesde.petmind.model.embeddable.CualidadesFisicas;
import com.cesde.petmind.model.enums.Especie;
import com.cesde.petmind.model.enums.EstadoAdopcion;
import com.cesde.petmind.model.enums.EstadoPublicacion;
import com.cesde.petmind.model.enums.SexoMascota;
import com.cesde.petmind.model.enums.TamanoMascota;
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
@Table(name = "mascotas")
public class Mascota extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fundacion_id", nullable = false)
    private Fundacion fundacion;
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    @Enumerated(EnumType.STRING)
    @Column(name = "especie", nullable = false)
    private Especie especie;
    @Column(name = "raza", length = 100)
    private String raza;
    @Column(name = "edad_meses")
    private Integer edadMeses;
    @Enumerated(EnumType.STRING)
    @Column(name = "tamano")
    private TamanoMascota tamano;
    @Enumerated(EnumType.STRING)
    @Column(name = "sexo")
    private SexoMascota sexo;
    @Embedded
    private CualidadesFisicas cualidadesFisicas;
    @Column(name = "ciudad", length = 100)
    private String ciudad;
    @Column(name = "descripcion", length = 1000)
    private String descripcion;
    @Column(name = "historia", length = 2000)
    private String historia;
    @Column(name = "etiquetas", length = 500)
    private String etiquetas;
    @Embedded
    private Afinidad afinidad;
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_adopcion", nullable = false)
    private EstadoAdopcion estadoAdopcion;
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_publicacion", nullable = false)
    private EstadoPublicacion estadoPublicacion;
    @Column(name = "esterilizado", nullable = false)
    private Boolean esterilizado;
    @Column(name = "vacunas_al_dia", nullable = false)
    private Boolean vacunasAlDia;
    @Column(name = "desparasitado", nullable = false)
    private Boolean desparasitado;
    @Column(name = "tiene_microchip", nullable = false)
    private Boolean tieneMicrochip;
    @Column(name = "apto_apadrinamiento", nullable = false)
    private Boolean aptoApadrinamiento;
    @Column(name = "fecha_publicacion")
    private LocalDateTime fechaPublicacion;
    @Column(name = "contador_visitas", nullable = false)
    private Integer contadorVisitas;
    private static Boolean $default$esterilizado() {
        return false;
    }
    private static Boolean $default$vacunasAlDia() {
        return false;
    }
    private static Boolean $default$desparasitado() {
        return false;
    }
    private static Boolean $default$tieneMicrochip() {
        return false;
    }
    private static Boolean $default$aptoApadrinamiento() {
        return false;
    }
    private static Integer $default$contadorVisitas() {
        return 0;
    }
    public static abstract class MascotaBuilder<C extends Mascota, B extends Mascota.MascotaBuilder<C, B>> extends BaseEntity.BaseEntityBuilder<C, B> {
        private Fundacion fundacion;
        private String nombre;
        private Especie especie;
        private String raza;
        private Integer edadMeses;
        private TamanoMascota tamano;
        private SexoMascota sexo;
        private CualidadesFisicas cualidadesFisicas;
        private String ciudad;
        private String descripcion;
        private String historia;
        private String etiquetas;
        private Afinidad afinidad;
        private EstadoAdopcion estadoAdopcion;
        private EstadoPublicacion estadoPublicacion;
        private boolean esterilizado$set;
        private Boolean esterilizado$value;
        private boolean vacunasAlDia$set;
        private Boolean vacunasAlDia$value;
        private boolean desparasitado$set;
        private Boolean desparasitado$value;
        private boolean tieneMicrochip$set;
        private Boolean tieneMicrochip$value;
        private boolean aptoApadrinamiento$set;
        private Boolean aptoApadrinamiento$value;
        private LocalDateTime fechaPublicacion;
        private boolean contadorVisitas$set;
        private Integer contadorVisitas$value;

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
        public B nombre(final String nombre) {
            this.nombre = nombre;
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
        public B raza(final String raza) {
            this.raza = raza;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B edadMeses(final Integer edadMeses) {
            this.edadMeses = edadMeses;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B tamano(final TamanoMascota tamano) {
            this.tamano = tamano;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B sexo(final SexoMascota sexo) {
            this.sexo = sexo;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B cualidadesFisicas(final CualidadesFisicas cualidadesFisicas) {
            this.cualidadesFisicas = cualidadesFisicas;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B ciudad(final String ciudad) {
            this.ciudad = ciudad;
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
        public B historia(final String historia) {
            this.historia = historia;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B etiquetas(final String etiquetas) {
            this.etiquetas = etiquetas;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B afinidad(final Afinidad afinidad) {
            this.afinidad = afinidad;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B estadoAdopcion(final EstadoAdopcion estadoAdopcion) {
            this.estadoAdopcion = estadoAdopcion;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B estadoPublicacion(final EstadoPublicacion estadoPublicacion) {
            this.estadoPublicacion = estadoPublicacion;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B esterilizado(final Boolean esterilizado) {
            this.esterilizado$value = esterilizado;
            esterilizado$set = true;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B vacunasAlDia(final Boolean vacunasAlDia) {
            this.vacunasAlDia$value = vacunasAlDia;
            vacunasAlDia$set = true;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B desparasitado(final Boolean desparasitado) {
            this.desparasitado$value = desparasitado;
            desparasitado$set = true;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B tieneMicrochip(final Boolean tieneMicrochip) {
            this.tieneMicrochip$value = tieneMicrochip;
            tieneMicrochip$set = true;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B aptoApadrinamiento(final Boolean aptoApadrinamiento) {
            this.aptoApadrinamiento$value = aptoApadrinamiento;
            aptoApadrinamiento$set = true;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B fechaPublicacion(final LocalDateTime fechaPublicacion) {
            this.fechaPublicacion = fechaPublicacion;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B contadorVisitas(final Integer contadorVisitas) {
            this.contadorVisitas$value = contadorVisitas;
            contadorVisitas$set = true;
            return self();
        }

        @java.lang.Override
        protected abstract B self();

        @java.lang.Override
        public abstract C build();

        @java.lang.Override
        public java.lang.String toString() {
            return "Mascota.MascotaBuilder(super=" + super.toString() + ", fundacion=" + this.fundacion + ", nombre=" + this.nombre + ", especie=" + this.especie + ", raza=" + this.raza + ", edadMeses=" + this.edadMeses + ", tamano=" + this.tamano + ", sexo=" + this.sexo + ", cualidadesFisicas=" + this.cualidadesFisicas + ", ciudad=" + this.ciudad + ", descripcion=" + this.descripcion + ", historia=" + this.historia + ", etiquetas=" + this.etiquetas + ", afinidad=" + this.afinidad + ", estadoAdopcion=" + this.estadoAdopcion + ", estadoPublicacion=" + this.estadoPublicacion + ", esterilizado$value=" + this.esterilizado$value + ", vacunasAlDia$value=" + this.vacunasAlDia$value + ", desparasitado$value=" + this.desparasitado$value + ", tieneMicrochip$value=" + this.tieneMicrochip$value + ", aptoApadrinamiento$value=" + this.aptoApadrinamiento$value + ", fechaPublicacion=" + this.fechaPublicacion + ", contadorVisitas$value=" + this.contadorVisitas$value + ")";
        }
    }
    private static final class MascotaBuilderImpl extends Mascota.MascotaBuilder<Mascota, Mascota.MascotaBuilderImpl> {
        private MascotaBuilderImpl() {
        }

        @java.lang.Override
        protected Mascota.MascotaBuilderImpl self() {
            return this;
        }

        @java.lang.Override
        public Mascota build() {
            return new Mascota(this);
        }
    }
    protected Mascota(final Mascota.MascotaBuilder<?, ?> b) {
        super(b);
        this.fundacion = b.fundacion;
        this.nombre = b.nombre;
        this.especie = b.especie;
        this.raza = b.raza;
        this.edadMeses = b.edadMeses;
        this.tamano = b.tamano;
        this.sexo = b.sexo;
        this.cualidadesFisicas = b.cualidadesFisicas;
        this.ciudad = b.ciudad;
        this.descripcion = b.descripcion;
        this.historia = b.historia;
        this.etiquetas = b.etiquetas;
        this.afinidad = b.afinidad;
        this.estadoAdopcion = b.estadoAdopcion;
        this.estadoPublicacion = b.estadoPublicacion;
        if (b.esterilizado$set) this.esterilizado = b.esterilizado$value;
         else this.esterilizado = Mascota.$default$esterilizado();
        if (b.vacunasAlDia$set) this.vacunasAlDia = b.vacunasAlDia$value;
         else this.vacunasAlDia = Mascota.$default$vacunasAlDia();
        if (b.desparasitado$set) this.desparasitado = b.desparasitado$value;
         else this.desparasitado = Mascota.$default$desparasitado();
        if (b.tieneMicrochip$set) this.tieneMicrochip = b.tieneMicrochip$value;
         else this.tieneMicrochip = Mascota.$default$tieneMicrochip();
        if (b.aptoApadrinamiento$set) this.aptoApadrinamiento = b.aptoApadrinamiento$value;
         else this.aptoApadrinamiento = Mascota.$default$aptoApadrinamiento();
        this.fechaPublicacion = b.fechaPublicacion;
        if (b.contadorVisitas$set) this.contadorVisitas = b.contadorVisitas$value;
         else this.contadorVisitas = Mascota.$default$contadorVisitas();
    }
    public static Mascota.MascotaBuilder<?, ?> builder() {
        return new Mascota.MascotaBuilderImpl();
    }
    public Fundacion getFundacion() {
        return this.fundacion;
    }
    public String getNombre() {
        return this.nombre;
    }
    public Especie getEspecie() {
        return this.especie;
    }
    public String getRaza() {
        return this.raza;
    }
    public Integer getEdadMeses() {
        return this.edadMeses;
    }
    public TamanoMascota getTamano() {
        return this.tamano;
    }
    public SexoMascota getSexo() {
        return this.sexo;
    }
    public CualidadesFisicas getCualidadesFisicas() {
        return this.cualidadesFisicas;
    }
    public String getCiudad() {
        return this.ciudad;
    }
    public String getDescripcion() {
        return this.descripcion;
    }
    public String getHistoria() {
        return this.historia;
    }
    public String getEtiquetas() {
        return this.etiquetas;
    }
    public Afinidad getAfinidad() {
        return this.afinidad;
    }
    public EstadoAdopcion getEstadoAdopcion() {
        return this.estadoAdopcion;
    }
    public EstadoPublicacion getEstadoPublicacion() {
        return this.estadoPublicacion;
    }
    public Boolean getEsterilizado() {
        return this.esterilizado;
    }
    public Boolean getVacunasAlDia() {
        return this.vacunasAlDia;
    }
    public Boolean getDesparasitado() {
        return this.desparasitado;
    }
    public Boolean getTieneMicrochip() {
        return this.tieneMicrochip;
    }
    public Boolean getAptoApadrinamiento() {
        return this.aptoApadrinamiento;
    }
    public LocalDateTime getFechaPublicacion() {
        return this.fechaPublicacion;
    }
    public Integer getContadorVisitas() {
        return this.contadorVisitas;
    }
    public void setFundacion(final Fundacion fundacion) {
        this.fundacion = fundacion;
    }
    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }
    public void setEspecie(final Especie especie) {
        this.especie = especie;
    }
    public void setRaza(final String raza) {
        this.raza = raza;
    }
    public void setEdadMeses(final Integer edadMeses) {
        this.edadMeses = edadMeses;
    }
    public void setTamano(final TamanoMascota tamano) {
        this.tamano = tamano;
    }
    public void setSexo(final SexoMascota sexo) {
        this.sexo = sexo;
    }
    public void setCualidadesFisicas(final CualidadesFisicas cualidadesFisicas) {
        this.cualidadesFisicas = cualidadesFisicas;
    }
    public void setCiudad(final String ciudad) {
        this.ciudad = ciudad;
    }
    public void setDescripcion(final String descripcion) {
        this.descripcion = descripcion;
    }
    public void setHistoria(final String historia) {
        this.historia = historia;
    }
    public void setEtiquetas(final String etiquetas) {
        this.etiquetas = etiquetas;
    }
    public void setAfinidad(final Afinidad afinidad) {
        this.afinidad = afinidad;
    }
    public void setEstadoAdopcion(final EstadoAdopcion estadoAdopcion) {
        this.estadoAdopcion = estadoAdopcion;
    }
    public void setEstadoPublicacion(final EstadoPublicacion estadoPublicacion) {
        this.estadoPublicacion = estadoPublicacion;
    }
    public void setEsterilizado(final Boolean esterilizado) {
        this.esterilizado = esterilizado;
    }
    public void setVacunasAlDia(final Boolean vacunasAlDia) {
        this.vacunasAlDia = vacunasAlDia;
    }
    public void setDesparasitado(final Boolean desparasitado) {
        this.desparasitado = desparasitado;
    }
    public void setTieneMicrochip(final Boolean tieneMicrochip) {
        this.tieneMicrochip = tieneMicrochip;
    }
    public void setAptoApadrinamiento(final Boolean aptoApadrinamiento) {
        this.aptoApadrinamiento = aptoApadrinamiento;
    }
    public void setFechaPublicacion(final LocalDateTime fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }
    public void setContadorVisitas(final Integer contadorVisitas) {
        this.contadorVisitas = contadorVisitas;
    }
    public Mascota() {
        this.esterilizado = Mascota.$default$esterilizado();
        this.vacunasAlDia = Mascota.$default$vacunasAlDia();
        this.desparasitado = Mascota.$default$desparasitado();
        this.tieneMicrochip = Mascota.$default$tieneMicrochip();
        this.aptoApadrinamiento = Mascota.$default$aptoApadrinamiento();
        this.contadorVisitas = Mascota.$default$contadorVisitas();
    }
    public Mascota(final Fundacion fundacion, final String nombre, final Especie especie, final String raza, final Integer edadMeses, final TamanoMascota tamano, final SexoMascota sexo, final CualidadesFisicas cualidadesFisicas, final String ciudad, final String descripcion, final String historia, final String etiquetas, final Afinidad afinidad, final EstadoAdopcion estadoAdopcion, final EstadoPublicacion estadoPublicacion, final Boolean esterilizado, final Boolean vacunasAlDia, final Boolean desparasitado, final Boolean tieneMicrochip, final Boolean aptoApadrinamiento, final LocalDateTime fechaPublicacion, final Integer contadorVisitas) {
        this.fundacion = fundacion;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.edadMeses = edadMeses;
        this.tamano = tamano;
        this.sexo = sexo;
        this.cualidadesFisicas = cualidadesFisicas;
        this.ciudad = ciudad;
        this.descripcion = descripcion;
        this.historia = historia;
        this.etiquetas = etiquetas;
        this.afinidad = afinidad;
        this.estadoAdopcion = estadoAdopcion;
        this.estadoPublicacion = estadoPublicacion;
        this.esterilizado = esterilizado;
        this.vacunasAlDia = vacunasAlDia;
        this.desparasitado = desparasitado;
        this.tieneMicrochip = tieneMicrochip;
        this.aptoApadrinamiento = aptoApadrinamiento;
        this.fechaPublicacion = fechaPublicacion;
        this.contadorVisitas = contadorVisitas;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Mascota(super=" + super.toString() + ", nombre=" + this.getNombre() + ", especie=" + this.getEspecie() + ", raza=" + this.getRaza() + ", edadMeses=" + this.getEdadMeses() + ", tamano=" + this.getTamano() + ", sexo=" + this.getSexo() + ", cualidadesFisicas=" + this.getCualidadesFisicas() + ", ciudad=" + this.getCiudad() + ", descripcion=" + this.getDescripcion() + ", historia=" + this.getHistoria() + ", etiquetas=" + this.getEtiquetas() + ", afinidad=" + this.getAfinidad() + ", estadoAdopcion=" + this.getEstadoAdopcion() + ", estadoPublicacion=" + this.getEstadoPublicacion() + ", esterilizado=" + this.getEsterilizado() + ", vacunasAlDia=" + this.getVacunasAlDia() + ", desparasitado=" + this.getDesparasitado() + ", tieneMicrochip=" + this.getTieneMicrochip() + ", aptoApadrinamiento=" + this.getAptoApadrinamiento() + ", fechaPublicacion=" + this.getFechaPublicacion() + ", contadorVisitas=" + this.getContadorVisitas() + ")";
    }
}
