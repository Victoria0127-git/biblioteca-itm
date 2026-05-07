package com.itm.biblioteca.controller;

import com.itm.biblioteca.model.Autor;
import com.itm.biblioteca.service.IAutorService;
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
@RequestMapping("/api/autores")
@RequiredArgsConstructor
// 1. Agregamos un Tag para agrupar en Swagger
@Tag(name = "Autores", description = "Endpoints para la gestión de autores de libros")
public class AutorController {

    private final IAutorService autorService;

    @GetMapping
    @Operation(summary = "Listar autores", description = "Obtiene una lista de todos los autores registrados")
    @ApiResponse(responseCode = "200", description = "Retorno de lista de autores exitoso")
    public List<Autor> obtenerTodos() {
        return autorService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar autor", description = "Obtiene un autor específico mediante su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autor encontrado"),
            @ApiResponse(responseCode = "404", description = "Autor no encontrado")
    })
    public ResponseEntity<Autor> obtenerPorId(@PathVariable String id) {
        return autorService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear autor", description = "Registra un nuevo autor en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Autor creado"),
            @ApiResponse(responseCode = "400", description = "Ha ocurrido un error")
    })
    public ResponseEntity<?> crearAutor(@RequestBody Autor autor) {
        try {
            Autor guardado = autorService.crear(autor);
            return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar autor", description = "Modifica los datos de un autor existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Autor actualizado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Autor no encontrado")
    })
    public ResponseEntity<?> actualizarAutor(@PathVariable String id, @RequestBody Autor autor) {
        try {
            Autor actualizado = autorService.actualizar(id, autor);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar autor", description = "Borra un autor del sistema permanentemente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "El autor se ha eliminado"),
            @ApiResponse(responseCode = "404", description = "Autor no encontrado")
    })
    public ResponseEntity<?> eliminarAutor(@PathVariable String id) {
        try {
            autorService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}