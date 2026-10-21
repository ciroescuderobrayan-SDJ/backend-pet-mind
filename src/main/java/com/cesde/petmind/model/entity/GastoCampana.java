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

import java.math.BigDecimal;
import com.cesde.petmind.model.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "gastos_campana")
public class GastoCampana extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "campana_id", nullable = false)
    private CampanaDonacion campana;
    @Column(name = "concepto", nullable = false, length = 250)
    private String concepto;
    @Column(name = "monto", nullable = false, precision = 12, scale = 2)
    private BigDecimal monto;
    @Column(name = "orden")
    private Integer orden;
    public static abstract class GastoCampanaBuilder<C extends GastoCampana, B extends GastoCampana.GastoCampanaBuilder<C, B>> extends BaseEntity.BaseEntityBuilder<C, B> {
        private CampanaDonacion campana;
        private String concepto;
        private BigDecimal monto;
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
        public B concepto(final String concepto) {
            this.concepto = concepto;
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
            return "GastoCampana.GastoCampanaBuilder(super=" + super.toString() + ", campana=" + this.campana + ", concepto=" + this.concepto + ", monto=" + this.monto + ", orden=" + this.orden + ")";
        }
    }
    private static final class GastoCampanaBuilderImpl extends GastoCampana.GastoCampanaBuilder<GastoCampana, GastoCampana.GastoCampanaBuilderImpl> {
        private GastoCampanaBuilderImpl() {
        }

        @java.lang.Override
        protected GastoCampana.GastoCampanaBuilderImpl self() {
            return this;
        }

        @java.lang.Override
        public GastoCampana build() {
            return new GastoCampana(this);
        }
    }
    protected GastoCampana(final GastoCampana.GastoCampanaBuilder<?, ?> b) {
        super(b);
        this.campana = b.campana;
        this.concepto = b.concepto;
        this.monto = b.monto;
        this.orden = b.orden;
    }
    public static GastoCampana.GastoCampanaBuilder<?, ?> builder() {
        return new GastoCampana.GastoCampanaBuilderImpl();
    }
    public CampanaDonacion getCampana() {
        return this.campana;
    }
    public String getConcepto() {
        return this.concepto;
    }
    public BigDecimal getMonto() {
        return this.monto;
    }
    public Integer getOrden() {
        return this.orden;
    }
    public void setCampana(final CampanaDonacion campana) {
        this.campana = campana;
    }
    public void setConcepto(final String concepto) {
        this.concepto = concepto;
    }
    public void setMonto(final BigDecimal monto) {
        this.monto = monto;
    }
    public void setOrden(final Integer orden) {
        this.orden = orden;
    }
    public GastoCampana() {
    }
    public GastoCampana(final CampanaDonacion campana, final String concepto, final BigDecimal monto, final Integer orden) {
        this.campana = campana;
        this.concepto = concepto;
        this.monto = monto;
        this.orden = orden;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "GastoCampana(super=" + super.toString() + ", concepto=" + this.getConcepto() + ", monto=" + this.getMonto() + ", orden=" + this.getOrden() + ")";
    }
}
