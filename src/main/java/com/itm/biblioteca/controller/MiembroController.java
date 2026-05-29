package com.itm.biblioteca.controller;

import com.itm.biblioteca.model.Miembro;
import com.itm.biblioteca.service.IMiembroService;
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
@RequestMapping("/api/miembro")
@RequiredArgsConstructor
@Tag(name = "Miembros", description = "Endpoints para la gestión de mimebros (usuarios)")
public class MiembroController {
    private final IMiembroService miembroService; // Inyectamos la interfaz del SERVICE

    @GetMapping
    @Operation(summary = "Listar miembros", description = "Retorna una lista de todos los miembros registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorno de miembros exitoso"),
            @ApiResponse(responseCode = "400", description = "Ha ocurrido un error")
    })
    public List<Miembro> obtenerTodas() {
        return miembroService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar miembro", description = "Obtiene el registro de miembro mediante su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Miembro encontrado"),
            @ApiResponse(responseCode = "404", description = "Miembro no encontrado")
    })
    public ResponseEntity<Miembro> obtenerPorId(@PathVariable String id) {
        return miembroService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Crear miembro", description = "Registra un nuevo miembro en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Miembro registrado"),
            @ApiResponse(responseCode = "400", description = "Ha ocurrido un error")
    })
    public ResponseEntity<?> crearMiembro(@RequestBody Miembro miembro) {
        try {
            Miembro guardada = miembroService.crear(miembro);
            return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
        } catch (RuntimeException e) {
            // Captura errores de validación definidos en la capa de servicio
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar miembro", description = "Modifica los datos de un miembro registrado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Miembro actualizado"),
            @ApiResponse(responseCode = "404", description = "Miembro no encontrado")
    })
    public ResponseEntity<?> actualizarMiembro(@PathVariable String id, @RequestBody Miembro miembro) {
        try {
            Miembro actualizado = miembroService.actualizar(id, miembro);
            return ResponseEntity.ok(actualizado);
        }
        catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar miembro", description = "Elimina el registro de un miembro del sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Miembro eliminado"),
            @ApiResponse(responseCode = "404", description = "Miembro no encontrado")
    })
    public ResponseEntity<?> eliminarMiembro(@PathVariable String id) {
        try {
            miembroService.eliminar(id);
            return ResponseEntity.noContent().build(); // 204 No Content (Éxito)
        } catch (RuntimeException e) {
            // Evaluamos el mensaje para saber qué código HTTP mandar
            if (e.getMessage().contains("no existe")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage()); // 404
            }

            // Si es por los préstamos activos, es un conflicto de regla de negocio
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage()); // 400
        }
    }
}