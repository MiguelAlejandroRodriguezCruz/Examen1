package com.upiiz.examen1.Services;

import java.util.List;
import org.springframework.stereotype.Service;

import com.upiiz.examen1.Models.Estudiantes;
import com.upiiz.examen1.Repository.EstudiantesRepositoyry;

@Service
public class EstudiantesService {
    EstudiantesRepositoyry EstudiantesRepositoyry;

    public EstudiantesService(EstudiantesRepositoyry EstudiantesRepositoyry) {
        this.EstudiantesRepositoyry = EstudiantesRepositoyry;
    }

    // @GetMapping()
    public List<Estudiantes> getAllEstudiantes() {
        return EstudiantesRepositoyry.obtenerTodas();
    }

    // @GetMapping("/{id}")
    public Estudiantes getEstudianteById(Long id) {
        return EstudiantesRepositoyry.obtnerPorId(id);
    }

    // @PostMapping()
    public Estudiantes createEstudiante(Estudiantes Estudiante) {
        return EstudiantesRepositoyry.guardar(Estudiante);
    }

    // @PutMapping("/{id}")
    public Estudiantes updateEstudiante(Estudiantes Estudiante) {
        return EstudiantesRepositoyry.actualizar(Estudiante);
    }

    // DeleteMapping("/{id}")
    public void deleteEstudiante(Long id) {
        EstudiantesRepositoyry.eliminar(id);
    }

}
