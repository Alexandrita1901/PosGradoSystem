package FCA.Sistema.Web.Service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import FCA.Sistema.Web.DTO.HistorialSemestreRequest;
import FCA.Sistema.Web.DTO.HistorialSemestreResponse;
import FCA.Sistema.Web.Entity.HistorialSemestre;
import FCA.Sistema.Web.Entity.User;
import FCA.Sistema.Web.Repository.HistorialSemestreRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HistorialSemestreService {

    private final HistorialSemestreRepository historialSemestreRepository;

    // Listar todos los historiales (ADMIN solo los de su unidad, SUPERADMIN todos)
    public ResponseEntity<List<HistorialSemestreResponse>> listarTodosHistoriales(User usuarioLogueado) {
        List<HistorialSemestre> historialSemestres;

        if ("SUPERADMIN".equals(usuarioLogueado.getRole().getName())) {
            // SUPERADMIN puede listar todos
            historialSemestres = historialSemestreRepository.findAll();
        } else if ("ADMIN".equals(usuarioLogueado.getRole().getName())) {
            // ADMIN solo puede listar los historiales de su unidad
            historialSemestres = historialSemestreRepository.findByEstudianteProgramaEstudioUnidadPosgradoId(usuarioLogueado.getUnidadPosgrado().getId());
        } else {
            // Otros roles no tienen acceso
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        List<HistorialSemestreResponse> response = historialSemestres.stream()
            .map(h -> new HistorialSemestreResponse(h.getId(), h.getSemestre().getNombre(), h.getFechaInicio(), h.getFechaFin()))
            .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    // Listar historial de semestres de un estudiante específico
    public ResponseEntity<List<HistorialSemestreResponse>> listarHistorialPorEstudiante(Integer estudianteId, User usuarioLogueado) {
        List<HistorialSemestre> historialSemestres;

        if ("SUPERADMIN".equals(usuarioLogueado.getRole().getName())) {
            historialSemestres = historialSemestreRepository.findByEstudianteId(estudianteId);
        } else if ("ADMIN".equals(usuarioLogueado.getRole().getName())) {
            historialSemestres = historialSemestreRepository.findByEstudianteProgramaEstudioUnidadPosgradoIdAndEstudianteId(usuarioLogueado.getUnidadPosgrado().getId(), estudianteId);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        List<HistorialSemestreResponse> response = historialSemestres.stream()
            .map(h -> new HistorialSemestreResponse(h.getId(), h.getSemestre().getNombre(), h.getFechaInicio(), h.getFechaFin()))
            .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    // Crear una entrada en el historial de semestres (usando DTO)
    public ResponseEntity<String> crearHistorialSemestre(HistorialSemestreRequest request) {
        HistorialSemestre historialSemestre = HistorialSemestre.builder()
                .estudiante(request.getEstudiante()) // Deberías obtener el estudiante desde el servicio o base de datos
                .semestre(request.getSemestre())     // Deberías obtener el semestre desde el servicio o base de datos
                .fechaInicio(request.getFechaInicio())
                .fechaFin(request.getFechaFin())
                .build();

        historialSemestreRepository.save(historialSemestre);

        return ResponseEntity.ok("Historial de semestre creado exitosamente.");
    }

    // Eliminar un historial de semestre (Solo ADMIN y SUPERADMIN)
    public ResponseEntity<String> eliminarHistorialSemestre(Integer id, User usuarioLogueado) {
        // Validar que solo ADMIN o SUPERADMIN puedan eliminar
        if (!"ADMIN".equals(usuarioLogueado.getRole().getName()) && !"SUPERADMIN".equals(usuarioLogueado.getRole().getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para eliminar.");
        }

        // Verificar si el historial existe
        if (historialSemestreRepository.existsById(id)) {
            historialSemestreRepository.deleteById(id);
            return ResponseEntity.ok("Historial de semestre eliminado exitosamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Historial de semestre no encontrado.");
        }
    }
}


