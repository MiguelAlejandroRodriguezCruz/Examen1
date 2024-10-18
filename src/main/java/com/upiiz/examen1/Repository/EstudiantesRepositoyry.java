package com.upiiz.examen1.Repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.upiiz.examen1.Models.Estudiantes;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class EstudiantesRepositoyry {

    // Ruta al archivo JSON
    private final String archivoJson = "db/db.json";

    private ObjectMapper objectMapper = new ObjectMapper();
    private AtomicLong id;

    // Leer el archivo JSON al iniciar para obtener la lista de estudiantes
    private List<Estudiantes> cargarDatos() {
        try {
            ClassPathResource resource = new ClassPathResource(archivoJson);
            File archivo = resource.getFile();
            if (archivo.exists()) {
                return new ArrayList<>(objectMapper.readValue(archivo, new TypeReference<List<Estudiantes>>() {
                }));
            } else {
                return new ArrayList<>(); // Si no existe el archivo, devuelve una lista vacía modificable
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>(); // En caso de error, devuelve una lista vacía modificable
        }
    }

    // Inicializar el ID con el valor más alto
    private void inicializarId(List<Estudiantes> estudiantes) {
        if (!estudiantes.isEmpty()) {
            long maxId = estudiantes.stream()
                    .mapToLong(Estudiantes::getId)
                    .max()
                    .orElse(0);
            id = new AtomicLong(maxId);
        } else {
            id = new AtomicLong(0); // Si no hay estudiantes, empieza en 0
        }
    }

    // Guardar los datos en el archivo JSON
    private void guardarDatos(List<Estudiantes> estudiantes) {
        try {
            File archivo = new ClassPathResource(archivoJson).getFile();
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(archivo, estudiantes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // GET - Todas las Estudiantes
    public List<Estudiantes> obtenerTodas() {
        List<Estudiantes> estudiantes = cargarDatos();
        inicializarId(estudiantes); // Asegura que el ID esté correctamente inicializado
        return estudiantes;
    }

    // GET - Obtener un estudiante por ID
    public Estudiantes obtnerPorId(Long id) {
        List<Estudiantes> estudiantes = cargarDatos();
        return estudiantes.stream().filter(e -> e.getId().equals(id)).findFirst().orElse(null);
    }

    // POST - Guardar un nuevo estudiante
    public Estudiantes guardar(Estudiantes estudiante) {
        List<Estudiantes> estudiantes = cargarDatos();

        // Inicializa el ID si es necesario
        inicializarId(estudiantes);

        // Generar un nuevo ID
        estudiante.setId(id.incrementAndGet());

        // Agregar el estudiante a la lista
        estudiantes.add(estudiante);

        // Guardar los cambios en el archivo JSON
        guardarDatos(estudiantes);

        return estudiante;
    }

    // PUT - Actualizar un estudiante existente
    public Estudiantes actualizar(Estudiantes estudiante) {
        List<Estudiantes> estudiantes = cargarDatos();

        // Eliminar el estudiante existente por ID
        estudiantes = estudiantes.stream()
                .filter(e -> !e.getId().equals(estudiante.getId()))
                .collect(Collectors.toList());

        // Agregar el estudiante actualizado
        estudiantes.add(estudiante);

        // Guardar los cambios en el archivo JSON
        guardarDatos(estudiantes);

        return estudiante;
    }

    // DELETE - Eliminar un estudiante por ID
    public void eliminar(Long id) {
        List<Estudiantes> estudiantes = cargarDatos();

        // Eliminar el estudiante por ID
        estudiantes = estudiantes.stream()
                .filter(e -> !e.getId().equals(id))
                .collect(Collectors.toList());

        // Guardar los cambios en el archivo JSON
        guardarDatos(estudiantes);
    }
}
