package com.itm.biblioteca.controller;

import com.itm.biblioteca.model.Editorial;
import com.itm.biblioteca.service.IEditorialService;
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
@RequestMapping("/api/editoriales")
@RequiredArgsConstructor
@Tag(name = "Editorial", description = "Endpoints para la gestión de editoriales de libros")
public class EditorialController {
    private final IEditorialService editorialService; // Inyectamos la interfaz del SERVICE

    @GetMapping
    @Operation(summary = "Listar editoriales", description = "Obtiene una lista de todas las editoriales registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorno de editoriales exitoso"),
            @ApiResponse(responseCode = "400", description = "Ha ocurrido un error")
    })
    public List<Editorial> obtenerTodas() {
        return editorialService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar editorial", description = "Obtiene el registro de una editorial mediante su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Editorial encontrada"),
            @ApiResponse(responseCode = "404", description = "Editorial no encontrada")
    })
    public ResponseEntity<Editorial> obtenerPorId(@PathVariable String id) {
        return editorialService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Registrar editorial", description = "Registra una nueva editorial en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Editorial registrada"),
            @ApiResponse(responseCode = "400", description = "Ha ocurrido un error")
    })
    public ResponseEntity<?> crearEditorial(@RequestBody Editorial editorial) {
        try {
            Editorial guardada = editorialService.crear(editorial);
            return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
        } catch (RuntimeException e) {
            // Captura errores de validación definidos en la capa de servicio
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar editorial", description = "Modifica los datos de una editorial registrada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Editorial actualizada"),
            @ApiResponse(responseCode = "404", description = "Editorial no encontrada")
    })
    public ResponseEntity<?> actualizarEditorial(@PathVariable String id, @RequestBody Editorial editorial) {
        try {
            Editorial actualizado = editorialService.actualizar(id, editorial);
            return ResponseEntity.ok(actualizado);
        }
        catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar editorial", description = "Elimina el registro de una editorial")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Editorial eliminada"),
            @ApiResponse(responseCode = "404", description = "Editorial no encontrada")
    })
    public ResponseEntity<?> eliminarEditorial(@PathVariable String id) {
        try {
            editorialService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}