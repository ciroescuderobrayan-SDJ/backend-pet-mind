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

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import com.cesde.petmind.model.base.BaseEntity;
import com.cesde.petmind.model.embeddable.Direccion;
import com.cesde.petmind.model.embeddable.InformacionContacto;
import com.cesde.petmind.model.enums.RolUsuario;
import com.cesde.petmind.model.enums.TipoToken;

@Entity
@Table(name = "usuarios")
public class Usuario extends BaseEntity {
    // Solo la llenan los usuarios con rol REPRESENTANTE_FUNDACION
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fundacion_id")
    private Fundacion fundacion;
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    @Column(name = "apellido", nullable = false, length = 100)
    private String apellido;
    // Se recibe en el JSON de entrada pero nunca se devuelve en las respuestas
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "contrasena", nullable = false, length = 100)
    private String contrasena;
    @Embedded
    private InformacionContacto contacto;
    @Embedded
    private Direccion direccion;
    @Column(name = "url_avatar", length = 500)
    private String urlAvatar;
    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    private RolUsuario rol;
    @Column(name = "correo_verificado", nullable = false)
    private Boolean correoVerificado;
    // Token de un solo uso para verificar correo o recuperar contrasena.
    // Ni el codigo ni su tipo ni su vencimiento se devuelven en el JSON: son datos internos.
    @JsonIgnore
    @Column(name = "token_codigo", length = 100)
    private String tokenCodigo;
    @JsonIgnore
    @Enumerated(EnumType.STRING)
    @Column(name = "token_tipo")
    private TipoToken tokenTipo;
    @JsonIgnore
    @Column(name = "token_expira")
    private LocalDateTime tokenExpira;
    private static Boolean $default$correoVerificado() {
        return false;
    }
    public static abstract class UsuarioBuilder<C extends Usuario, B extends Usuario.UsuarioBuilder<C, B>> extends BaseEntity.BaseEntityBuilder<C, B> {
        private Fundacion fundacion;
        private String nombre;
        private String apellido;
        private String contrasena;
        private InformacionContacto contacto;
        private Direccion direccion;
        private String urlAvatar;
        private RolUsuario rol;
        private boolean correoVerificado$set;
        private Boolean correoVerificado$value;
        private String tokenCodigo;
        private TipoToken tokenTipo;
        private LocalDateTime tokenExpira;

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
        public B apellido(final String apellido) {
            this.apellido = apellido;
            return self();
        }

        /**
         * @return {@code this}.
         */
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        public B contrasena(final String contrasena) {
            this.contrasena = contrasena;
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
        public B direccion(final Direccion direccion) {
            this.direccion = direccion;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B urlAvatar(final String urlAvatar) {
            this.urlAvatar = urlAvatar;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B rol(final RolUsuario rol) {
            this.rol = rol;
            return self();
        }

        /**
         * @return {@code this}.
         */
        public B correoVerificado(final Boolean correoVerificado) {
            this.correoVerificado$value = correoVerificado;
            correoVerificado$set = true;
            return self();
        }

        /**
         * @return {@code this}.
         */
        @JsonIgnore
        public B tokenCodigo(final String tokenCodigo) {
            this.tokenCodigo = tokenCodigo;
            return self();
        }

        /**
         * @return {@code this}.
         */
        @JsonIgnore
        public B tokenTipo(final TipoToken tokenTipo) {
            this.tokenTipo = tokenTipo;
            return self();
        }

        /**
         * @return {@code this}.
         */
        @JsonIgnore
        public B tokenExpira(final LocalDateTime tokenExpira) {
            this.tokenExpira = tokenExpira;
            return self();
        }

        @java.lang.Override
        protected abstract B self();

        @java.lang.Override
        public abstract C build();

        @java.lang.Override
        public java.lang.String toString() {
            return "Usuario.UsuarioBuilder(super=" + super.toString() + ", fundacion=" + this.fundacion + ", nombre=" + this.nombre + ", apellido=" + this.apellido + ", contrasena=" + this.contrasena + ", contacto=" + this.contacto + ", direccion=" + this.direccion + ", urlAvatar=" + this.urlAvatar + ", rol=" + this.rol + ", correoVerificado$value=" + this.correoVerificado$value + ", tokenCodigo=" + this.tokenCodigo + ", tokenTipo=" + this.tokenTipo + ", tokenExpira=" + this.tokenExpira + ")";
        }
    }
    private static final class UsuarioBuilderImpl extends Usuario.UsuarioBuilder<Usuario, Usuario.UsuarioBuilderImpl> {
        private UsuarioBuilderImpl() {
        }

        @java.lang.Override
        protected Usuario.UsuarioBuilderImpl self() {
            return this;
        }

        @java.lang.Override
        public Usuario build() {
            return new Usuario(this);
        }
    }
    protected Usuario(final Usuario.UsuarioBuilder<?, ?> b) {
        super(b);
        this.fundacion = b.fundacion;
        this.nombre = b.nombre;
        this.apellido = b.apellido;
        this.contrasena = b.contrasena;
        this.contacto = b.contacto;
        this.direccion = b.direccion;
        this.urlAvatar = b.urlAvatar;
        this.rol = b.rol;
        if (b.correoVerificado$set) this.correoVerificado = b.correoVerificado$value;
         else this.correoVerificado = Usuario.$default$correoVerificado();
        this.tokenCodigo = b.tokenCodigo;
        this.tokenTipo = b.tokenTipo;
        this.tokenExpira = b.tokenExpira;
    }
    public static Usuario.UsuarioBuilder<?, ?> builder() {
        return new Usuario.UsuarioBuilderImpl();
    }
    public Fundacion getFundacion() {
        return this.fundacion;
    }
    public String getNombre() {
        return this.nombre;
    }
    public String getApellido() {
        return this.apellido;
    }
    public String getContrasena() {
        return this.contrasena;
    }
    public InformacionContacto getContacto() {
        return this.contacto;
    }
    public Direccion getDireccion() {
        return this.direccion;
    }
    public String getUrlAvatar() {
        return this.urlAvatar;
    }
    public RolUsuario getRol() {
        return this.rol;
    }
    public Boolean getCorreoVerificado() {
        return this.correoVerificado;
    }
    public String getTokenCodigo() {
        return this.tokenCodigo;
    }
    public TipoToken getTokenTipo() {
        return this.tokenTipo;
    }
    public LocalDateTime getTokenExpira() {
        return this.tokenExpira;
    }
    public void setFundacion(final Fundacion fundacion) {
        this.fundacion = fundacion;
    }
    public void setNombre(final String nombre) {
        this.nombre = nombre;
    }
    public void setApellido(final String apellido) {
        this.apellido = apellido;
    }
    public void setContrasena(final String contrasena) {
        this.contrasena = contrasena;
    }
    public void setContacto(final InformacionContacto contacto) {
        this.contacto = contacto;
    }
    public void setDireccion(final Direccion direccion) {
        this.direccion = direccion;
    }
    public void setUrlAvatar(final String urlAvatar) {
        this.urlAvatar = urlAvatar;
    }
    public void setRol(final RolUsuario rol) {
        this.rol = rol;
    }
    public void setCorreoVerificado(final Boolean correoVerificado) {
        this.correoVerificado = correoVerificado;
    }
    public void setTokenCodigo(final String tokenCodigo) {
        this.tokenCodigo = tokenCodigo;
    }
    public void setTokenTipo(final TipoToken tokenTipo) {
        this.tokenTipo = tokenTipo;
    }
    public void setTokenExpira(final LocalDateTime tokenExpira) {
        this.tokenExpira = tokenExpira;
    }
    public Usuario() {
        this.correoVerificado = Usuario.$default$correoVerificado();
    }
    public Usuario(final Fundacion fundacion, final String nombre, final String apellido, final String contrasena, final InformacionContacto contacto, final Direccion direccion, final String urlAvatar, final RolUsuario rol, final Boolean correoVerificado, final String tokenCodigo, final TipoToken tokenTipo, final LocalDateTime tokenExpira) {
        this.fundacion = fundacion;
        this.nombre = nombre;
        this.apellido = apellido;
        this.contrasena = contrasena;
        this.contacto = contacto;
        this.direccion = direccion;
        this.urlAvatar = urlAvatar;
        this.rol = rol;
        this.correoVerificado = correoVerificado;
        this.tokenCodigo = tokenCodigo;
        this.tokenTipo = tokenTipo;
        this.tokenExpira = tokenExpira;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Usuario(super=" + super.toString() + ", nombre=" + this.getNombre() + ", apellido=" + this.getApellido() + ", contrasena=" + this.getContrasena() + ", contacto=" + this.getContacto() + ", direccion=" + this.getDireccion() + ", urlAvatar=" + this.getUrlAvatar() + ", rol=" + this.getRol() + ", correoVerificado=" + this.getCorreoVerificado() + ", tokenCodigo=" + this.getTokenCodigo() + ", tokenTipo=" + this.getTokenTipo() + ", tokenExpira=" + this.getTokenExpira() + ")";
    }
}
