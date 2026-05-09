package com.itm.biblioteca.controller;

import com.itm.biblioteca.model.Ejemplar;
import com.itm.biblioteca.service.IEjemplarService;
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
@RequestMapping("/api/ejemplares")
@RequiredArgsConstructor
@Tag(name = "Ejemplares", description = "Endpoints para la gestión de ejemplares de libros")
public class EjemplarController {
    private final IEjemplarService ejemplarService; // Inyectamos la interfaz del SERVICE

    @GetMapping
    @Operation(summary = "Listar ejemplares", description = "Retorna una lista de todos los ejemplares registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorno de ejemplares exitoso"),
            @ApiResponse(responseCode = "400", description = "Ha ocurrido un error")
    })
    public List<Ejemplar> obtenerTodas() {
        return ejemplarService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar ejemplar", description = "Obtiene un ejempar específico mediante su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ejemplar encontrado"),
            @ApiResponse(responseCode = "404", description = "Ejemplar no encontrado")
    })
    public ResponseEntity<Ejemplar> obtenerPorId(@PathVariable String id) {
        return ejemplarService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Registrar ejemplar", description = "Registra un nuevo ejemplar en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Ejemplar registrado"),
            @ApiResponse(responseCode = "400", description = "Ha ocurrido un error")
    })
    public ResponseEntity<?> crearEjemplar(@RequestBody Ejemplar ejemplar) {
        try {
            Ejemplar guardada = ejemplarService.crear(ejemplar);
            return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
        } catch (RuntimeException e) {
            // Captura errores de validación definidos en la capa de servicio
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar ejemplar", description = "Modifica los datos de un ejemplar registrado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorno de ejemplares exitoso"),
            @ApiResponse(responseCode = "404", description = "Ejemplar no encontrado")
    })
    public ResponseEntity<?> actualizarEjemplar(@PathVariable String id, @RequestBody Ejemplar ejemplar) {
        try {
            Ejemplar actualizado = ejemplarService.actualizar(id, ejemplar);
            return ResponseEntity.ok(actualizado);
        }
        catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar ejemplar", description = "Elimina el registro de un ejemplar en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Ejemplar eliminado"),
            @ApiResponse(responseCode = "404", description = "Ejemplar no encontrado"),
    })
    public ResponseEntity<?> eliminarEjemplar(@PathVariable String id) {
        try {
            ejemplarService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}