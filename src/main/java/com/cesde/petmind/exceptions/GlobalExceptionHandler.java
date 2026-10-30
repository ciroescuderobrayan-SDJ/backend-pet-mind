package com.cesde.petmind.exceptions;

import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

// Convierte las excepciones en respuestas JSON con la forma { "mensaje": "..." }.
// Antes solo se manejaban las tres excepciones propias; el resto (JSON mal escrito, un enum
// que no existe, un campo obligatorio vacio) respondia con el error por defecto de Spring,
// que en desarrollo incluye el stack trace completo del servidor.
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // Codigo SQL estandar de "valor duplicado" (restriccion UNIQUE)
    private static final String SQL_VALOR_DUPLICADO = "23505";

    // Mantiene la forma {"mensaje": "..."} y enumera todos los campos invalidos.
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> manejarValidacion(MethodArgumentNotValidException ex) {
        String mensaje = ex.getBindingResult().getAllErrors().stream()
                .map(error -> error instanceof org.springframework.validation.FieldError campo
                        ? campo.getField() + ": " + error.getDefaultMessage()
                        : error.getObjectName() + ": " + error.getDefaultMessage())
                .sorted()
                .collect(Collectors.joining("; "));
        return responder(HttpStatus.BAD_REQUEST, mensaje.isEmpty() ? "La petición tiene datos inválidos" : mensaje);
    }

    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<Map<String, String>> manejarNoEncontrado(RecursoNoEncontradoException ex) {
        return responder(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ReglaDeNegocioException.class)
    public ResponseEntity<Map<String, String>> manejarReglaDeNegocio(ReglaDeNegocioException ex) {
        return responder(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(RecursoDuplicadoException.class)
    public ResponseEntity<Map<String, String>> manejarRecursoDuplicado(RecursoDuplicadoException ex) {
        return responder(HttpStatus.CONFLICT, ex.getMessage());
    }

    // JSON mal escrito o con un valor que no existe en un enum (p. ej. "metodoPago": "TARJETA")
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> manejarJsonInvalido(HttpMessageNotReadableException ex) {
        return responder(HttpStatus.BAD_REQUEST, "El cuerpo de la peticion no es un JSON valido o tiene valores no permitidos");
    }

    // Parametro de la URL con el tipo equivocado: /api/mascotas/abc o ?estado=XYZ
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> manejarTipoInvalido(MethodArgumentTypeMismatchException ex) {
        return responder(HttpStatus.BAD_REQUEST, "El valor '" + ex.getValue() + "' no es valido para " + ex.getName());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<Map<String, String>> manejarParametroFaltante(MissingServletRequestParameterException ex) {
        return responder(HttpStatus.BAD_REQUEST, "Falta el parametro " + ex.getParameterName());
    }

    // Un id nulo que llega hasta el repositorio
    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public ResponseEntity<Map<String, String>> manejarUsoInvalido(InvalidDataAccessApiUsageException ex) {
        return responder(HttpStatus.BAD_REQUEST, "La peticion tiene datos incompletos");
    }

    // La base rechazo los datos: un campo obligatorio vacio (400) o un valor repetido (409)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> manejarIntegridad(DataIntegrityViolationException ex) {
        if (esValorDuplicado(ex)) {
            return responder(HttpStatus.CONFLICT, "Ya existe un registro con esos datos");
        }
        return responder(HttpStatus.BAD_REQUEST, "Faltan datos obligatorios o tienen un formato no permitido");
    }

    // Cualquier otro error: se registra en el log y al cliente no se le muestran detalles internos
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> manejarErrorInesperado(Exception ex) {
        // Errores de Spring que ya traen su codigo (ruta inexistente 404, metodo no permitido 405...):
        // se respeta ese codigo en lugar de convertirlos en 500
        if (ex instanceof ErrorResponse errorSpring) {
            String detalle = errorSpring.getBody().getDetail();
            return responder(HttpStatus.valueOf(errorSpring.getStatusCode().value()),
                    detalle != null ? detalle : "No se pudo procesar la peticion");
        }
        log.error("Error inesperado en la API", ex);
        return responder(HttpStatus.INTERNAL_SERVER_ERROR, "Ocurrio un error inesperado. Intenta de nuevo mas tarde");
    }

    private ResponseEntity<Map<String, String>> responder(HttpStatus estado, String mensaje) {
        return ResponseEntity.status(estado).body(Map.of("mensaje", mensaje));
    }

    private boolean esValorDuplicado(Throwable error) {
        for (Throwable causa = error; causa != null; causa = causa.getCause()) {
            if (causa instanceof SQLException sql && SQL_VALOR_DUPLICADO.equals(sql.getSQLState())) {
                return true;
            }
        }
        return false;
    }
}
