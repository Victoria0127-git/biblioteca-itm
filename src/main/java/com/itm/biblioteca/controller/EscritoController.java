package com.itm.biblioteca.controller;

import com.itm.biblioteca.model.Escrito;
import com.itm.biblioteca.service.IEscritoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/escrito")
@RequiredArgsConstructor
@Tag(name = "Escrito", description =  "Conexion mucho a mucho entre libro y autor")
public class EscritoController {
    private final IEscritoService escritoService;

    @GetMapping
    @Operation(summary = "Listar escrito", description = "Retorna una lista de todos los escritos registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorno de ejemplares exitoso"),
            @ApiResponse(responseCode = "400", description = "Ha ocurrido un error")
    })

    public List<Escrito> obtenerTodas() {
        return escritoService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar escrito", description = "Obtiene un escrito específico mediante su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ejemplar encontrado"),
            @ApiResponse(responseCode = "404", description = "Ejemplar no encontrado")
    })
    public ResponseEntity<Escrito> obtenerPorId(@PathVariable String id) {
        return escritoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
