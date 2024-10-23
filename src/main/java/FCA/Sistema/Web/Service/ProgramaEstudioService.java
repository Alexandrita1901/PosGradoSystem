package FCA.Sistema.Web.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import FCA.Sistema.Web.DTO.ProgramaEstudioRequest;
import FCA.Sistema.Web.Entity.ProgramaEstudio;
import FCA.Sistema.Web.Entity.TipoPrograma;
import FCA.Sistema.Web.Entity.UnidadPosgrado;
import FCA.Sistema.Web.Entity.User;
import FCA.Sistema.Web.Repository.ProgramaEstudioRepository;
import FCA.Sistema.Web.Repository.TipoProgramaRepository;
import FCA.Sistema.Web.Repository.UnidadPosgradoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProgramaEstudioService {

    private final ProgramaEstudioRepository programaEstudioRepository;
    private final TipoProgramaRepository tipoProgramaRepository;
    private final UnidadPosgradoRepository unidadPosgradoRepository;

    // Crear un nuevo programa de estudio
    public ResponseEntity<String> crearProgramaEstudio(ProgramaEstudioRequest request, User usuarioLogueado) {
        // Buscar el tipo de programa asociado
        Optional<TipoPrograma> tipoProgramaOpt = tipoProgramaRepository.findById(request.getTipoProgramaId());
        if (!tipoProgramaOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tipo de programa no encontrado.");
        }

        UnidadPosgrado unidadPosgrado;

        // Verificar si el usuario es Admin o SuperAdmin
        if (usuarioLogueado.getRole().getName().equals("SUPERADMIN")) {
            // Si es SuperAdmin, se requiere unidad de posgrado manual
            if (request.getUnidadPosgradoId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La unidad de posgrado es requerida para el SUPERADMIN.");
            }

            unidadPosgrado = unidadPosgradoRepository.findById(request.getUnidadPosgradoId())
                    .orElseThrow(() -> new RuntimeException("Unidad de Posgrado no encontrada."));
        } else {
            // El Admin solo puede crear programas en su propia unidad
            unidadPosgrado = usuarioLogueado.getUnidadPosgrado();
        }

        // Crear el programa de estudio
        ProgramaEstudio programaEstudio = ProgramaEstudio.builder()
                .nombre(request.getNombre())
                .creditos(request.getCreditos())
                .duracionMeses(request.getDuracionMeses())
                .inversion(request.getInversion())
                .modalidad(request.getModalidad())
                .activo(request.getActivo())
                .descripcion(request.getDescripcion())
                .urlPlanEstudio(request.getUrlPlanEstudio())
                .unidadPosgrado(unidadPosgrado)
                .tipoPrograma(tipoProgramaOpt.get())
                .build();

        programaEstudioRepository.save(programaEstudio);

        return ResponseEntity.ok("Programa de estudio creado exitosamente.");
    }

    // Obtener todos los programas de estudio
    public ResponseEntity<List<ProgramaEstudio>> listarProgramasEstudio() {
        List<ProgramaEstudio> programas = programaEstudioRepository.findAll();
        return ResponseEntity.ok(programas);
    }

    // Obtener un programa de estudio por ID
    public ResponseEntity<ProgramaEstudio> obtenerProgramaEstudioPorId(Integer id) {
        Optional<ProgramaEstudio> programaOpt = programaEstudioRepository.findById(id);
        if (programaOpt.isPresent()) {
            return ResponseEntity.ok(programaOpt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Actualizar un programa de estudio
    public ResponseEntity<String> actualizarProgramaEstudio(Integer id, ProgramaEstudioRequest request, User usuarioLogueado) {
        Optional<ProgramaEstudio> programaOpt = programaEstudioRepository.findById(id);
        if (programaOpt.isPresent()) {
            ProgramaEstudio programaExistente = programaOpt.get();

            // Verificar si el usuario es SuperAdmin o Admin de la unidad
            UnidadPosgrado unidadPosgrado;
            if (usuarioLogueado.getRole().getName().equals("SUPERADMIN")) {
                if (request.getUnidadPosgradoId() == null) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La unidad de posgrado es requerida para el SUPERADMIN.");
                }
                unidadPosgrado = unidadPosgradoRepository.findById(request.getUnidadPosgradoId())
                        .orElseThrow(() -> new RuntimeException("Unidad de Posgrado no encontrada."));
            } else {
                unidadPosgrado = usuarioLogueado.getUnidadPosgrado();
            }

            programaExistente.setNombre(request.getNombre());
            programaExistente.setCreditos(request.getCreditos());
            programaExistente.setDuracionMeses(request.getDuracionMeses());
            programaExistente.setInversion(request.getInversion());
            programaExistente.setModalidad(request.getModalidad());
            programaExistente.setActivo(request.getActivo());
            programaExistente.setDescripcion(request.getDescripcion());
            programaExistente.setUrlPlanEstudio(request.getUrlPlanEstudio());
            programaExistente.setUnidadPosgrado(unidadPosgrado);

            programaEstudioRepository.save(programaExistente);

            return ResponseEntity.ok("Programa de estudio actualizado exitosamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Programa de estudio no encontrado.");
        }
    }

    // Eliminar un programa de estudio
    public ResponseEntity<String> eliminarProgramaEstudio(Integer id) {
        if (programaEstudioRepository.existsById(id)) {
            programaEstudioRepository.deleteById(id);
            return ResponseEntity.ok("Programa de estudio eliminado exitosamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Programa de estudio no encontrado.");
        }
    }
}

