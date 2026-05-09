package com.itm.biblioteca.controller;

import com.itm.biblioteca.model.Libro;
import com.itm.biblioteca.service.ILibroService;
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
@RequestMapping("/api/libros")
@RequiredArgsConstructor
@Tag(name = "Libros", description = "Endpoints para la gestión de libros")
public class LibroController {
    private final ILibroService libroService; // Inyectamos la interfaz del SERVICE

    @GetMapping
    @Operation(summary = "Listar libros", description = "Retorna una lista de todos los libros registrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorno de libros exitoso"),
            @ApiResponse(responseCode = "400", description = "Ha ocurrido un error")
    })
    public List<Libro> obtenerTodas() {
        return libroService.listarTodos();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar libro", description = "Obtiene el registro de un libro mediante su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Libro encontrado"),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado")
    })
    public ResponseEntity<Libro> obtenerPorId(@PathVariable String id) {
        return libroService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Registrar libro", description = "Registra un nuevo libro en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Libro registrado"),
            @ApiResponse(responseCode = "400", description = "Ha ocurrido un error")
    })
    public ResponseEntity<?> crearLibro(@RequestBody Libro libro) {
        try {
            Libro guardada = libroService.crear(libro);
            return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
        } catch (RuntimeException e) {
            // Captura errores de validación definidos en la capa de servicio
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{isbn}")
    @Operation(summary = "Actualizar libro", description = "Modifica los datos de un libro registrado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Libro actualizado"),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado")
    })
    public ResponseEntity<?> actualizarLibro(@PathVariable String isbn, @RequestBody Libro libro) {
        try {
            Libro actualizado = libroService.actualizar(isbn, libro);
            return ResponseEntity.ok(actualizado);
        }
        catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar libro", description = "Elimina el registro de un libro en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Libro no encontrado"),
            @ApiResponse(responseCode = "404", description = "Libro no encontrado")
    })
    public ResponseEntity<?> eliminarLibro(@PathVariable String id) {
        try {
            libroService.eliminar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}