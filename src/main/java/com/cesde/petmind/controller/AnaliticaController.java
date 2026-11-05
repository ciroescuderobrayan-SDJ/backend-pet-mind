package com.cesde.petmind.controller;

import com.cesde.petmind.service.AnaliticaService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analitica")
public class AnaliticaController {

    private final AnaliticaService analiticaService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> obtenerAnalitica() {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(analiticaService.obtenerAnalitica());
    }

    public AnaliticaController(AnaliticaService analiticaService) {
        this.analiticaService = analiticaService;
    }
}
