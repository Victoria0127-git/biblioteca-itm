package com.itm.biblioteca.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itm.biblioteca.model.Bibliotecario;
import com.itm.biblioteca.service.IBibliotecarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@WebMvcTest(BibliotecarioController.class)
public class BibliotecarioControllerTest {
    @Autowired
    private MockMvc mockMvc; //Simula las peticiones http

    @MockitoBean
    private IBibliotecarioService bibliotecarioService; //Simula el servicio

    ObjectMapper objectMapper = new ObjectMapper(); //Traduce JAVA a JSON

    @Test
    void crearBibliotecario_RetornaStatusCreated() throws Exception{
        //GIVEN
        Bibliotecario bibliotecarioNuevo = Bibliotecario.builder()
                .idBibliotecario("B000")
                .nombre("Test")
                .apellido("Prueba")
                .build();
        //WHEN
        when(bibliotecarioService.crear(any(Bibliotecario.class))).thenReturn(bibliotecarioNuevo);

        //THEN
        mockMvc.perform(post("/api/bibliotecarios")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(bibliotecarioNuevo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre").value("Test"));
    }

    @Test
    void actualizaBibliotecario_RetornaStatusIsOk() throws Exception{
        String IdBibliotecario = "B000";
        Bibliotecario datosNuevos = Bibliotecario.builder()
                .apellido("Uran")
                .build();

        Bibliotecario biblioPrueba = Bibliotecario.builder()
                .idBibliotecario(IdBibliotecario)
                .nombre("Carlitos")
                .apellido("Uran")
                .build();

        when(bibliotecarioService.actualizar(eq(IdBibliotecario), any(Bibliotecario.class))).thenReturn(biblioPrueba);

        mockMvc.perform(put("/api/bibliotecarios/"+IdBibliotecario)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(datosNuevos)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idBibliotecario").value(IdBibliotecario))
                .andExpect(jsonPath("$.nombre").value("Carlitos"))
                .andExpect(jsonPath("$.apellido").value("Uran"));
    }

    @Test
    void eliminarBibliotecario_RetornaNoContent() throws Exception{
        doNothing().when(bibliotecarioService).eliminar("B999");

        mockMvc.perform(delete("/api/bibliotecarios/B999"))
                .andExpect(status().isNoContent());

        verify(bibliotecarioService, times(1)).eliminar("B999");
    }

    @Test
    void buscarBiblioPorId_RetornaBibliotecario() throws Exception{
        String idBuscado = "B222";
        Bibliotecario biblioBuscado = Bibliotecario.builder()
                .idBibliotecario(idBuscado)
                .nombre("Maria")
                .apellido("Bernal")
                .build();

        when(bibliotecarioService.buscarPorId(idBuscado)).thenReturn(Optional.of(biblioBuscado));

        mockMvc.perform(get("/api/bibliotecarios/"+idBuscado)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Maria"));
    }

    @Test
    void listarTodos_RetornaTodosLosBibliotecarios()  throws Exception{
        List<Bibliotecario> bibliotecarioList = List.of(
                Bibliotecario.builder()
                        .idBibliotecario("B111")
                        .nombre("Rosa")
                        .apellido("Gonzalez")
                        .build(),
                Bibliotecario.builder()
                        .idBibliotecario("B222")
                        .nombre("Luis")
                        .apellido("Joven")
                        .build()
        );

        when(bibliotecarioService.listarTodos()).thenReturn(bibliotecarioList);

        mockMvc.perform(get("/api/bibliotecarios")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].nombre").value("Rosa"))
                .andExpect(jsonPath("$[1].apellido").value("Joven"));
    }

}
