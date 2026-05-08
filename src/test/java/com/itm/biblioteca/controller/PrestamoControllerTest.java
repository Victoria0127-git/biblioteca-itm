package com.itm.biblioteca.controller;

import com.itm.biblioteca.model.Bibliotecario;
import com.itm.biblioteca.model.Ejemplar;
import com.itm.biblioteca.model.Miembro;
import com.itm.biblioteca.model.Prestamo;
import com.itm.biblioteca.service.IPrestamoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import java.util.List;
import java.time.LocalDate;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PrestamoController.class)
@ActiveProfiles("test")
public class PrestamoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IPrestamoService prestamoService;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());

    @Test
    void listarPrestamos_RetornaStatusIsOk() throws Exception {
        when(prestamoService.listarTodos()).thenReturn(List.of());

        mockMvc.perform(get("/api/prestamos")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void buscarPrestamoPorId_RetornaStatusIsOk() throws Exception {
        String idBuscar = "P000";
        Prestamo prestamo = Prestamo.builder()
                .idPrestamo(idBuscar)
                .fechaPrestamo(LocalDate.of(2026, 5, 4))
                .fechaDevolucion(LocalDate.of(2026, 5, 8))
                .ejemplar(Ejemplar.builder().id("EJ00").build())
                .miembro(Miembro.builder().idMiembro("M000").build())
                .bibliotecario(Bibliotecario.builder().idBibliotecario("B000").build())
                .build();

        when(prestamoService.buscarPorId(idBuscar)).thenReturn(Optional.of(prestamo));

        mockMvc.perform(get("/api/prestamos/" + idBuscar)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPrestamo").value(prestamo.getIdPrestamo()));
    }

    @Test
    void crearPrestamo_RetornaStatusIsCreated() throws Exception {
        Prestamo prestamonuevo = Prestamo.builder()
                .idPrestamo("P010")
                .fechaPrestamo(LocalDate.of(2026, 5, 4))
                .fechaDevolucion(LocalDate.of(2026, 5, 8))
                .ejemplar(Ejemplar.builder().id("EJ00").build())
                .miembro(Miembro.builder().idMiembro("M000").build())
                .bibliotecario(Bibliotecario.builder().idBibliotecario("B000").build())
                .build();

        when(prestamoService.crear(prestamonuevo)).thenReturn(prestamonuevo);

        mockMvc.perform(post("/api/prestamos")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(prestamonuevo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idPrestamo").value(prestamonuevo.getIdPrestamo()));
    }

    @Test
    void actualizarPrestamo_RetornaStatusIsOk() throws Exception {
        String idExistente = "P010";
        Prestamo prestamoActualizado = Prestamo.builder()
                .idPrestamo(idExistente)
                .fechaPrestamo(LocalDate.of(2026, 5, 4))
                .fechaDevolucion(LocalDate.of(2026, 5, 20)) // Cambiamos la fecha
                .ejemplar(Ejemplar.builder().id("EJ00").build())
                .miembro(Miembro.builder().idMiembro("M000").build())
                .bibliotecario(Bibliotecario.builder().idBibliotecario("B000").build())
                .build();

        when(prestamoService.actualizar(eq(idExistente), any(Prestamo.class)))
                .thenReturn(prestamoActualizado);

        mockMvc.perform(put("/api/prestamos/{id}", idExistente)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(prestamoActualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idPrestamo").value(idExistente))
                .andExpect(jsonPath("$.fechaDevolucion").value("2026-05-20"));
    }

    @Test
    void eliminarPrestamo_RetornaStatusIsNoContent() throws Exception {
        String idEliminar = "P010";

        doNothing().when(prestamoService).eliminar(idEliminar);

        mockMvc.perform(delete("/api/prestamos/{id}", idEliminar)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        verify(prestamoService, times(1)).eliminar(idEliminar);
    }
}
