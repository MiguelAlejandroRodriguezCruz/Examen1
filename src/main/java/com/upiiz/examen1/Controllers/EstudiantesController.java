package com.upiiz.examen1.Controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.upiiz.examen1.Models.Estudiantes;
import com.upiiz.examen1.Services.EstudiantesService;

@RestController
@RequestMapping("/api/v1/Estudiantes")
public class EstudiantesController {
    @Autowired
    private EstudiantesService EstudiantesService;

    @GetMapping()
    public ResponseEntity<List<Estudiantes>> getEstudiantes() {
        return ResponseEntity.ok(EstudiantesService.getAllEstudiantes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Estudiantes> getEstudiante(@PathVariable Long id) {
        return ResponseEntity.ok(EstudiantesService.getEstudianteById(id));
    }

    @PostMapping()
    public ResponseEntity<Estudiantes> addEstudiante(@RequestBody Estudiantes Estudiante) {
        return ResponseEntity.ok(EstudiantesService.createEstudiante(Estudiante));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Estudiantes> updateEstudiante(@RequestBody Estudiantes Estudiante, @PathVariable Long id) {
        Estudiante.setId(id);
        return ResponseEntity.ok(EstudiantesService.updateEstudiante(Estudiante));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEstudiante(@PathVariable Long id) {
        EstudiantesService.deleteEstudiante(id);
        return ResponseEntity.noContent().build();
    }

}
