package com.itm.biblioteca.controller;

import com.itm.biblioteca.model.Prestamo;
import com.itm.biblioteca.service.IPrestamoService;
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
@RequestMapping("/api/prestamos")
@RequiredArgsConstructor
@Tag(name = "Prestamos", description = "Endpoints para la gestión de prestamos de ejemplares")
public class PrestamoController {
    private final IPrestamoService prestamoService; // Inyectamos la interfaz del SERVICE

    @GetMapping
    @Operation(summary = "Listar prestamos", description = "Obtiene una lista de todos los prestamos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorno de prestamos exitoso"),
            @ApiResponse(responseCode = "400", description = "Ha ocurrido un error")
    })
    public List<Prestamo> obtenerTodas() {
        return prestamoService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar prestamo", description = "Obtiene la info de un prestamo mediante su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prestamo encontrado"),
            @ApiResponse(responseCode = "404", description = "Prestamo no encontrado")
    })
    public ResponseEntity<Prestamo> obtenerPorId(@PathVariable String id) {
        return prestamoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Registrar prestamo", description = "Registra un nuevo prestamo en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Prestamo registrado"),
            @ApiResponse(responseCode = "400", description = "Ha ocurrido un error")
    })
    public ResponseEntity<?> crearPrestamo(@RequestBody Prestamo prestamo) {
        try {
            Prestamo guardada = prestamoService.crear(prestamo);
            return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
        } catch (RuntimeException e) {
            // Captura errores de validación definidos en la capa de servicio
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar prestamo", description = "Modifica la info de un prestamo mediante su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prestamo actualizado"),
            @ApiResponse(responseCode = "404", description = "Prestamo no encontrado")
    })
    public ResponseEntity<?> actualizarPrestamo(@PathVariable String id, @RequestBody Prestamo prestamo) {
        try {
            Prestamo actualizado = prestamoService.actualizar(id, prestamo);
            return ResponseEntity.ok(actualizado);
        }
        catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar prestamo", description = "Elimina la info de un prestamo mediante su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Prestamo eliminado"),
            @ApiResponse(responseCode = "404", description = "Prestamo no encontrado")
    })
    public ResponseEntity<?> eliminarPrestamo(@PathVariable String id) {
        try {
            prestamoService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}