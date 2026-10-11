package com.cesde.petmind.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cesde.petmind.model.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Borrado logico: los listados solo traen los registros activos
    List<Usuario> findByEstadoActivoTrue();

    // Recorre el embebido contacto -> emailContacto. Lo usa la regla de correo unico.
    // IgnoreCase: "Ana@correo.com" y "ana@correo.com" son el mismo correo.
    boolean existsByContactoEmailContactoIgnoreCase(String emailContacto);
}
