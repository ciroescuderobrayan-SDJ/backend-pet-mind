package com.cesde.petmind.service.impl;

import com.cesde.petmind.exceptions.AnaliticaException;
import com.cesde.petmind.service.AnaliticaService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

@Service
public class AnaliticaServiceImpl implements AnaliticaService {

    private final RestClient restClient;
    private final String analiticaApiUrl;

    public AnaliticaServiceImpl(
            @Value("${analitica.api.url}") String analiticaApiUrl) {
        this.restClient = RestClient.builder().build();
        this.analiticaApiUrl = analiticaApiUrl;
    }

    @Override
    public String obtenerAnalitica() {
        try {
            String respuesta = restClient.get()
                    .uri(analiticaApiUrl)
                    .retrieve()
                    .body(String.class);

            if (respuesta == null || respuesta.isBlank()) {
                throw new AnaliticaException("La API de analitica devolvio una respuesta vacia");
            }
            return respuesta;
        } catch (RestClientException ex) {
            throw new AnaliticaException("No se pudo consultar la API de analitica", ex);
        }
    }
}
