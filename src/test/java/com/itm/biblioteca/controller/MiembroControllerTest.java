package com.itm.biblioteca.controller;

import com.itm.biblioteca.model.Miembro;
import com.itm.biblioteca.service.IMiembroService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MiembroController.class)
@ActiveProfiles("test")
public class MiembroControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IMiembroService miembroService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void crearMiembro_RetornaStatusIsCreated() throws Exception {
        Miembro miembro = Miembro.builder()
                .idMiembro("idTest")
                .nombre("test")
                .apellido("Prueba")
                .correo("test@correo.com")
                .direccion("Cr23A # 44B - 56")
                .telefono("12344567891")
                .build();

        when(miembroService.crear(any(Miembro.class))).thenReturn(miembro);

        mockMvc.perform(post("/api/miembro")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(miembro)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idMiembro").value(miembro.getIdMiembro()));
    }

    @Test
    void actualizarMiembro_RetornaStatusIsOk() throws Exception {
        String idMiembro = "M001";
        Miembro datosNuevos = Miembro.builder()
                .nombre("Eduardo")
                .build();
        Miembro miembroActualizado = Miembro.builder()
                .idMiembro(idMiembro)
                .nombre("Eduardo")
                .apellido("Ramos")
                .correo("ramos@correo.com")
                .direccion("Cr23A # 44B - 56")
                .telefono("12344567891")
                .build();

        when(miembroService.actualizar(eq(idMiembro), any(Miembro.class))).thenReturn(miembroActualizado);

        mockMvc.perform(put("/api/miembro/"+idMiembro)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(datosNuevos)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value(miembroActualizado.getNombre()));
    }

    @Test
    void eliminarMiembro_RetornaStatusNotFound() throws Exception {
        String idNoExiste = "M000";

        doThrow(new RuntimeException())
                .when(miembroService).eliminar(eq(idNoExiste));

        mockMvc.perform(delete("/api/miembro/"+idNoExiste))
                .andExpect(status().isNotFound());
    }

    @Test
    void buscarMiembroPorId_RetornaStatusIsOk() throws Exception {
        String idBuscado = "M333";
        Miembro miembroBuscado = Miembro.builder()
                .idMiembro(idBuscado)
                .nombre("Stefany")
                .apellido("Serna")
                .telefono("3122245678")
                .correo("stefaserna@correo.com")
                .build();

        when(miembroService.buscarPorId(eq(idBuscado))).thenReturn(Optional.of(miembroBuscado));

        mockMvc.perform(get("/api/miembro/"+idBuscado)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value(miembroBuscado.getNombre()));
    }
}
