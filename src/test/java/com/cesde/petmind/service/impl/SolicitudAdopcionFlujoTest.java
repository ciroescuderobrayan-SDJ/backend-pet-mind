package com.cesde.petmind.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.cesde.petmind.dto.CambioEstadoSolicitudRequest;
import com.cesde.petmind.dto.RespuestaSolicitudRequest;
import com.cesde.petmind.dto.SolicitudAdopcionRequest;
import com.cesde.petmind.dto.SolicitudAdopcionResponse;
import com.cesde.petmind.exceptions.AccesoDenegadoException;
import com.cesde.petmind.exceptions.RecursoDuplicadoException;
import com.cesde.petmind.exceptions.ReglaDeNegocioException;
import com.cesde.petmind.model.embeddable.DatosHogar;
import com.cesde.petmind.model.embeddable.InformacionContacto;
import com.cesde.petmind.model.entity.Fundacion;
import com.cesde.petmind.model.entity.Mascota;
import com.cesde.petmind.model.entity.SolicitudAdopcion;
import com.cesde.petmind.model.entity.Usuario;
import com.cesde.petmind.model.enums.EstadoAdopcion;
import com.cesde.petmind.model.enums.EstadoSolicitud;
import com.cesde.petmind.model.enums.RolUsuario;
import com.cesde.petmind.repository.MascotaRepository;
import com.cesde.petmind.repository.SolicitudAdopcionRepository;
import com.cesde.petmind.repository.UsuarioRepository;

// Flujo de una solicitud: la persona la envia, la fundacion pide documentos, aprueba o rechaza,
// y la persona responde cuando le piden documentos. Usa Mockito, sin base de datos.
@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class SolicitudAdopcionFlujoTest {

    @Mock
    private SolicitudAdopcionRepository solicitudes;
    @Mock
    private UsuarioRepository usuarios;
    @Mock
    private MascotaRepository mascotas;

    private SolicitudAdopcionServiceImpl servicio;
    private Fundacion fundacion;
    private Mascota pancho;
    private Usuario adoptante;
    private Usuario representante;

    @BeforeEach
    void preparar() {
        servicio = new SolicitudAdopcionServiceImpl(solicitudes, usuarios, mascotas);
        fundacion = new Fundacion();
        fundacion.setId(19L);
        pancho = new Mascota();
        pancho.setId(75L);
        pancho.setNombre("Pancho");
        pancho.setFundacion(fundacion);
        pancho.setEstadoAdopcion(EstadoAdopcion.DISPONIBLE);
        adoptante = usuario(30L, RolUsuario.ADOPTANTE, null);
        representante = usuario(40L, RolUsuario.REPRESENTANTE_FUNDACION, fundacion);
        when(mascotas.findById(75L)).thenReturn(Optional.of(pancho));
        when(usuarios.findById(30L)).thenReturn(Optional.of(adoptante));
        when(usuarios.findById(40L)).thenReturn(Optional.of(representante));
        when(solicitudes.save(any(SolicitudAdopcion.class))).thenAnswer(invocacion -> invocacion.getArgument(0));
    }

    private static Usuario usuario(Long id, RolUsuario rol, Fundacion fundacion) {
        InformacionContacto contacto = new InformacionContacto();
        contacto.setEmailContacto("u" + id + "@correo.com");
        Usuario usuario = Usuario.builder().nombre("Ana").apellido("Gómez").rol(rol).contacto(contacto).fundacion(fundacion).build();
        usuario.setId(id);
        return usuario;
    }

    private SolicitudAdopcion solicitudEn(EstadoSolicitud estado) {
        SolicitudAdopcion solicitud = new SolicitudAdopcion();
        solicitud.setId(5L);
        solicitud.setUsuario(adoptante);
        solicitud.setMascota(pancho);
        solicitud.setEstado(estado);
        when(solicitudes.findById(5L)).thenReturn(Optional.of(solicitud));
        return solicitud;
    }

    @Test
    void crearQuedaPendienteYDevuelveDatosParaLaFundacion() {
        SolicitudAdopcionResponse creada = servicio.crear(new SolicitudAdopcionRequest(30L, 75L, new DatosHogar()));

        assertEquals(EstadoSolicitud.PENDIENTE, creada.estado());
        assertEquals(19L, creada.fundacionId());
        assertEquals("Pancho", creada.mascotaNombre());
        assertEquals("u30@correo.com", creada.solicitante().correo());
    }

    @Test
    void crearRechazaCuentasQueNoSonDePersonas() {
        assertThrows(ReglaDeNegocioException.class,
                () -> servicio.crear(new SolicitudAdopcionRequest(40L, 75L, new DatosHogar())));
        verify(solicitudes, never()).save(any());
    }

    @Test
    void crearRechazaUnaSegundaSolicitudAbiertaParaLaMismaMascota() {
        when(solicitudes.existsByUsuarioIdAndMascotaIdAndEstadoInAndEstadoActivoTrue(anyLong(), anyLong(), anyCollection()))
                .thenReturn(true);

        assertThrows(RecursoDuplicadoException.class,
                () -> servicio.crear(new SolicitudAdopcionRequest(30L, 75L, new DatosHogar())));
    }

    @Test
    void pedirDocumentosExigeDecirCuales() {
        solicitudEn(EstadoSolicitud.PENDIENTE);

        assertThrows(ReglaDeNegocioException.class, () -> servicio.cambiarEstado(5L,
                new CambioEstadoSolicitudRequest(40L, EstadoSolicitud.DOCUMENTOS_PENDIENTES, "  ")));
    }

    @Test
    void pedirDocumentosGuardaElMensajeYMarcaLaRevision() {
        solicitudEn(EstadoSolicitud.PENDIENTE);

        SolicitudAdopcionResponse respuesta = servicio.cambiarEstado(5L, new CambioEstadoSolicitudRequest(
                40L, EstadoSolicitud.DOCUMENTOS_PENDIENTES, " Copia de la cédula y un recibo público "));

        assertEquals(EstadoSolicitud.DOCUMENTOS_PENDIENTES, respuesta.estado());
        assertEquals("Copia de la cédula y un recibo público", respuesta.mensajeFundacion());
        assertNotNull(respuesta.fechaEnRevision());
        assertNull(respuesta.fechaDecision());
    }

    @Test
    void rechazarExigeMotivoYCierraLaSolicitud() {
        solicitudEn(EstadoSolicitud.EN_REVISION);
        assertThrows(ReglaDeNegocioException.class, () -> servicio.cambiarEstado(5L,
                new CambioEstadoSolicitudRequest(40L, EstadoSolicitud.RECHAZADA, null)));

        SolicitudAdopcionResponse respuesta = servicio.cambiarEstado(5L,
                new CambioEstadoSolicitudRequest(40L, EstadoSolicitud.RECHAZADA, "Vive muy lejos de la fundación"));

        assertEquals(EstadoSolicitud.RECHAZADA, respuesta.estado());
        assertNotNull(respuesta.fechaDecision());
    }

    @Test
    void unRepresentanteDeOtraFundacionNoPuedeDecidir() {
        solicitudEn(EstadoSolicitud.PENDIENTE);
        Fundacion otra = new Fundacion();
        otra.setId(99L);
        when(usuarios.findById(41L)).thenReturn(Optional.of(usuario(41L, RolUsuario.REPRESENTANTE_FUNDACION, otra)));

        assertThrows(AccesoDenegadoException.class, () -> servicio.cambiarEstado(5L,
                new CambioEstadoSolicitudRequest(41L, EstadoSolicitud.APROBADA, null)));
        assertThrows(AccesoDenegadoException.class, () -> servicio.cambiarEstado(5L,
                new CambioEstadoSolicitudRequest(30L, EstadoSolicitud.APROBADA, null)));
    }

    @Test
    void aprobarDejaLaMascotaAdoptadaYCierraLasDemasSolicitudes() {
        SolicitudAdopcion aprobada = solicitudEn(EstadoSolicitud.EN_REVISION);
        SolicitudAdopcion otra = new SolicitudAdopcion();
        otra.setId(6L);
        otra.setEstado(EstadoSolicitud.PENDIENTE);
        when(solicitudes.findByMascotaIdAndEstadoInAndEstadoActivoTrue(anyLong(), anyCollection()))
                .thenReturn(List.of(aprobada, otra));

        SolicitudAdopcionResponse respuesta = servicio.cambiarEstado(5L,
                new CambioEstadoSolicitudRequest(40L, EstadoSolicitud.APROBADA, null));

        assertEquals(EstadoSolicitud.APROBADA, respuesta.estado());
        assertEquals(EstadoAdopcion.ADOPTADO, pancho.getEstadoAdopcion());
        assertEquals(EstadoSolicitud.RECHAZADA, otra.getEstado());
        assertNotNull(otra.getMensajeFundacion());
        verify(mascotas).save(pancho);
    }

    @Test
    void unaSolicitudCerradaNoCambiaDeEstado() {
        solicitudEn(EstadoSolicitud.APROBADA);

        assertThrows(ReglaDeNegocioException.class, () -> servicio.cambiarEstado(5L,
                new CambioEstadoSolicitudRequest(40L, EstadoSolicitud.EN_REVISION, null)));
    }

    @Test
    void laFundacionNoPuedeDevolverLaSolicitudAPendiente() {
        solicitudEn(EstadoSolicitud.EN_REVISION);

        assertThrows(ReglaDeNegocioException.class, () -> servicio.cambiarEstado(5L,
                new CambioEstadoSolicitudRequest(40L, EstadoSolicitud.PENDIENTE, null)));
    }

    @Test
    void responderVuelveARevisionYSoloLoHaceLaPersonaQueSolicito() {
        solicitudEn(EstadoSolicitud.DOCUMENTOS_PENDIENTES);

        assertThrows(AccesoDenegadoException.class,
                () -> servicio.responder(5L, new RespuestaSolicitudRequest(40L, "Adjunto la cédula")));

        SolicitudAdopcionResponse respuesta =
                servicio.responder(5L, new RespuestaSolicitudRequest(30L, " Adjunto la cédula "));

        assertEquals(EstadoSolicitud.EN_REVISION, respuesta.estado());
        assertEquals("Adjunto la cédula", respuesta.respuestaAdoptante());
    }

    @Test
    void responderSinQueLaFundacionHayaPedidoDocumentosNoSePermite() {
        solicitudEn(EstadoSolicitud.PENDIENTE);

        assertThrows(ReglaDeNegocioException.class,
                () -> servicio.responder(5L, new RespuestaSolicitudRequest(30L, "Hola")));
    }
}
