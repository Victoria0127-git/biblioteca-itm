package com.itm.biblioteca.controller;

import com.itm.biblioteca.model.Bibliotecario;
import com.itm.biblioteca.service.IBibliotecarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bibliotecarios")
@RequiredArgsConstructor
@Tag(name = "Bibliotecarios", description = "Endpoints para la gestión de bibliotecarios")
public class BibliotecarioController {
    private final IBibliotecarioService bibliotecarioService; // Inyectamos el SERVICE, no el repo

    @GetMapping
    @Operation(summary = "Listar bibliotecarios", description = "Obtiene una lista de todos los bibliotecarios registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorno de bibliotecarios exitoso"),
            @ApiResponse(responseCode = "400", description = "Ha ocurrido un error")
    })
    public List<Bibliotecario> obtenerTodos() {
        return bibliotecarioService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar bibliotecario", description = "Obtiene un bibliotecario específico mediante su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bibliotecario encontrado"),
            @ApiResponse(responseCode = "404", description = "Bibliotecario no encontrado")
    })
    public ResponseEntity<Bibliotecario> obtenerPorId(@PathVariable String id) {
        return bibliotecarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Registrar bibliotecario", description = "Registra un nuevo bibliotecario en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Bibliotecario registrado"),
            @ApiResponse(responseCode = "400", description = "Ha ocurrido un error")
    })
    public ResponseEntity<?> crearBibliotecario(@RequestBody Bibliotecario bibliotecario) {
        try {
            Bibliotecario guardado = bibliotecarioService.crear(bibliotecario);
            return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
        } catch (RuntimeException e) {
            // Si el validador del Service lanza error, aquí lo atrapamos
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar bibliotecario", description = "Modifica los datos de un bibliotecario registrado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Bibliotecario actualizado"),
            @ApiResponse(responseCode = "404", description = "Bibliotecario no encontrado")
    })
    public ResponseEntity<?> actualizarBibliotecario(@PathVariable String id, @RequestBody Bibliotecario bibliotecario) {
        try {
            Bibliotecario actualizado = bibliotecarioService.actualizar(id, bibliotecario);
            return ResponseEntity.ok(actualizado);
        }
        catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar bibliotecarios", description = "Elimina el registro de un bibliotecario en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Retorno de bibliotecarios exitoso"),
            @ApiResponse(responseCode = "404", description = "Bibliotecario no encontrado")
    })
    public ResponseEntity<?> eliminarBibliotecario(@PathVariable String id) {
        try {
            bibliotecarioService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}