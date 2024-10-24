package FCA.Sistema.Web.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import FCA.Sistema.Web.DTO.SemestreRequest;
import FCA.Sistema.Web.DTO.SemestreResponse;
import FCA.Sistema.Web.Entity.Semestre;
import FCA.Sistema.Web.Repository.SemestreRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SemestreService {

    private final SemestreRepository semestreRepository;

    // Crear un nuevo Semestre
    public ResponseEntity<String> crearSemestre(SemestreRequest request) {
        Semestre semestre = Semestre.builder()
                .nombre(request.getNombre())
                .numero(request.getNumero())
                .fechaInicio(request.getFechaInicio())
                .fechaFin(request.getFechaFin())
                .build();

        semestreRepository.save(semestre);
        return ResponseEntity.ok("Semestre creado exitosamente.");
    }

    // Listar todos los semestres
    public ResponseEntity<List<SemestreResponse>> listarSemestres() {
        List<Semestre> semestres = semestreRepository.findAll();

        // Convertir la lista de entidades a DTOs
        List<SemestreResponse> semestreResponses = semestres.stream()
            .map(semestre -> SemestreResponse.builder()
                .id(semestre.getId())
                .nombre(semestre.getNombre())
                .numero(semestre.getNumero())
                .fechaInicio(semestre.getFechaInicio())
                .fechaFin(semestre.getFechaFin())
                .build())
            .collect(Collectors.toList());

        return ResponseEntity.ok(semestreResponses);
    }

    // Obtener un semestre por ID
    public ResponseEntity<SemestreResponse> obtenerSemestrePorId(Integer id) {
        Optional<Semestre> semestreOpt = semestreRepository.findById(id);
        if (semestreOpt.isPresent()) {
            Semestre semestre = semestreOpt.get();

            // Convertir la entidad a DTO
            SemestreResponse semestreResponse = SemestreResponse.builder()
                .id(semestre.getId())
                .nombre(semestre.getNombre())
                .numero(semestre.getNumero())
                .fechaInicio(semestre.getFechaInicio())
                .fechaFin(semestre.getFechaFin())
                .build();

            return ResponseEntity.ok(semestreResponse);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Actualizar un semestre
    public ResponseEntity<String> actualizarSemestre(Integer id, SemestreRequest request) {
        Optional<Semestre> semestreOpt = semestreRepository.findById(id);
        if (semestreOpt.isPresent()) {
            Semestre semestreExistente = semestreOpt.get();
            semestreExistente.setNombre(request.getNombre());
            semestreExistente.setNumero(request.getNumero());
            semestreExistente.setFechaInicio(request.getFechaInicio());
            semestreExistente.setFechaFin(request.getFechaFin());

            semestreRepository.save(semestreExistente);
            return ResponseEntity.ok("Semestre actualizado exitosamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Semestre no encontrado.");
        }
    }

    // Eliminar un semestre
    public ResponseEntity<String> eliminarSemestre(Integer id) {
        if (semestreRepository.existsById(id)) {
            semestreRepository.deleteById(id);
            return ResponseEntity.ok("Semestre eliminado exitosamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Semestre no encontrado.");
        }
    }
}
