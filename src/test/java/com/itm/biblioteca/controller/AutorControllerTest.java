package com.itm.biblioteca.controller;

import com.itm.biblioteca.model.Autor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itm.biblioteca.service.impl.AutorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.junit.jupiter.api.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AutorController.class)
//@ImportAutoConfiguration(JacksonAutoConfiguration.class)
class AutorControllerTest {
    @Autowired
    private MockMvc mockMvc; // Simula las peticiones http

    @MockitoBean
    private AutorServiceImpl autorService; //Simula el servicio

    @TestConfiguration
    static class TestConfig {
        @Bean
        ObjectMapper objectMapper() {
            return new ObjectMapper();
        }
    }

    @Autowired
    private ObjectMapper objectMapper; //Convertir objetos JAVA a JSON

    @Test
    void crearAutor_DebeRetornarStatusCreated() throws Exception {
        //GIVEN: crear autor
        Autor autor = Autor.builder()
                .idAutor("A000")
                .nombre("Test")
                .apellido("Prueba")
                .nacionalidad("Testland")
                .build();

        when(autorService.crear(any(Autor.class))).thenReturn(autor);

        //WHEN AND THEN
        mockMvc.perform(post("/api/autores")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(autor)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Test"));
    }

}
