package FCA.Sistema.Web.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import FCA.Sistema.Web.DTO.EstudianteRequest;
import FCA.Sistema.Web.DTO.EstudianteResponse;
import FCA.Sistema.Web.Entity.Estudiantes;
import FCA.Sistema.Web.Entity.ProgramaEstudio;
import FCA.Sistema.Web.Entity.Semestre;
import FCA.Sistema.Web.Entity.User;
import FCA.Sistema.Web.Repository.EstudiantesRepository;
import FCA.Sistema.Web.Repository.ProgramaEstudioRepository;
import FCA.Sistema.Web.Repository.SemestreRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EstudiantesService {

	    private final EstudiantesRepository estudianteRepository;
	    private final ProgramaEstudioRepository programaEstudioRepository;
	    private final SemestreRepository semestreRepository;

	    // Crear un nuevo estudiante (permiso para USER, ADMIN y SUPERADMIN)
	    public ResponseEntity<String> crearEstudiante(EstudianteRequest request, User usuarioLogueado) {
	        Optional<ProgramaEstudio> programaEstudioOpt = programaEstudioRepository.findById(request.getProgramaEstudioId());
	        if (!programaEstudioOpt.isPresent()) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Programa de estudio no encontrado.");
	        }

	        Optional<Semestre> semestreIngresoOpt = semestreRepository.findById(request.getSemestreIngresoId());
	        Optional<Semestre> semestreActualOpt = semestreRepository.findById(request.getSemestreActualId());
	        if (!semestreIngresoOpt.isPresent() || !semestreActualOpt.isPresent()) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Semestre no encontrado.");
	        }

	        Estudiantes estudiante = Estudiantes.builder()
	                .nombres(request.getNombres())
	                .apellidos(request.getApellidos())
	                .email(request.getEmail())
	                .documentoIdentidad(request.getDocumentoIdentidad())
	                .telefono(request.getTelefono())
	                .direccion(request.getDireccion())
	                .ultimoGradoAcademico(request.getUltimoGradoAcademico())
	                .universidadProcedencia(request.getUniversidadProcedencia())
	                .entidadLaboralDependiente(request.getEntidadLaboralDependiente())
	                .cargoLaboralDependiente(request.getCargoLaboralDependiente())
	                .direccionLaboralDependiente(request.getDireccionLaboralDependiente())
	                .telefonoLaboralDependiente(request.getTelefonoLaboralDependiente())
	                .sectorLaboralIndependiente(request.getSectorLaboralIndependiente())
	                .cargoLaboralIndependiente(request.getCargoLaboralIndependiente())
	                .estadoEstudiante(request.getEstadoEstudiante())
	                .fechaInscripcion(request.getFechaInscripcion())
	                .urlFotografia(request.getUrlFotografia())
	                .programaEstudio(programaEstudioOpt.get())
	                .semestreIngreso(semestreIngresoOpt.get())
	                .semestreActual(semestreActualOpt.get())
	                .activo(request.getActivo())
	                .totalSemestres(request.getTotalSemestres())
	                .build();

	        estudianteRepository.save(estudiante);
	        return ResponseEntity.ok("Estudiante creado exitosamente.");
	    }

	    // Listar estudiantes (USER y ADMIN según su unidad, SUPERADMIN todos)
	    public ResponseEntity<List<EstudianteResponse>> listarEstudiantes(User usuarioLogueado) {
	        List<Estudiantes> estudiantes;

	        if ("SUPERADMIN".equals(usuarioLogueado.getRole().getName())) {
	            estudiantes = estudianteRepository.findAll();
	        } else {
	            estudiantes = estudianteRepository.findByProgramaEstudioUnidadPosgrado(usuarioLogueado.getUnidadPosgrado());
	        }

	        List<EstudianteResponse> response = estudiantes.stream()
	                .map(estudiante -> EstudianteResponse.builder()
	                        .id(estudiante.getId())
	                        .nombres(estudiante.getNombres())
	                        .apellidos(estudiante.getApellidos())
	                        .email(estudiante.getEmail())
	                        .documentoIdentidad(estudiante.getDocumentoIdentidad())
	                        .telefono(estudiante.getTelefono())
	                        .estadoEstudiante(estudiante.getEstadoEstudiante())
	                        .programaEstudioNombre(estudiante.getProgramaEstudio().getNombre())
	                        .build())
	                .collect(Collectors.toList());

	        return ResponseEntity.ok(response);
	    }

	    // Obtener un estudiante por ID
	    public ResponseEntity<EstudianteResponse> obtenerEstudiantePorId(Integer id, User usuarioLogueado) {
	        Optional<Estudiantes> estudianteOpt = estudianteRepository.findById(id);
	        if (estudianteOpt.isPresent()) {
	            Estudiantes estudiante = estudianteOpt.get();

	            // Validar que el estudiante pertenece a la unidad del usuario (solo para USER y ADMIN)
	            if (!"SUPERADMIN".equals(usuarioLogueado.getRole().getName()) &&
	                !estudiante.getProgramaEstudio().getUnidadPosgrado().equals(usuarioLogueado.getUnidadPosgrado())) {
	                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
	            }

	            EstudianteResponse response = EstudianteResponse.builder()
	                    .id(estudiante.getId())
	                    .nombres(estudiante.getNombres())
	                    .apellidos(estudiante.getApellidos())
	                    .email(estudiante.getEmail())
	                    .documentoIdentidad(estudiante.getDocumentoIdentidad())
	                    .telefono(estudiante.getTelefono())
	                    .estadoEstudiante(estudiante.getEstadoEstudiante())
	                    .programaEstudioNombre(estudiante.getProgramaEstudio().getNombre())
	                    .build();

	            return ResponseEntity.ok(response);
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	        }
	    }

	    // Actualizar un estudiante (ADMIN y SUPERADMIN)
	    public ResponseEntity<String> actualizarEstudiante(Integer id, EstudianteRequest request, User usuarioLogueado) {
	        Optional<Estudiantes> estudianteOpt = estudianteRepository.findById(id);
	        if (estudianteOpt.isPresent()) {
	            Estudiantes estudianteExistente = estudianteOpt.get();

	            // Validar permisos para USER
	            if ("USER".equals(usuarioLogueado.getRole().getName())) {
	                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para editar.");
	            }

	            estudianteExistente.setNombres(request.getNombres());
	            estudianteExistente.setApellidos(request.getApellidos());
	            estudianteExistente.setEmail(request.getEmail());
	            estudianteExistente.setDocumentoIdentidad(request.getDocumentoIdentidad());
	            estudianteExistente.setTelefono(request.getTelefono());
	            estudianteExistente.setEstadoEstudiante(request.getEstadoEstudiante());

	            estudianteRepository.save(estudianteExistente);
	            return ResponseEntity.ok("Estudiante actualizado exitosamente.");
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Estudiante no encontrado.");
	        }
	    }

	    // Eliminar un estudiante (solo ADMIN y SUPERADMIN)
	    public ResponseEntity<String> eliminarEstudiante(Integer id, User usuarioLogueado) {
	        if (!"SUPERADMIN".equals(usuarioLogueado.getRole().getName()) && !"ADMIN".equals(usuarioLogueado.getRole().getName())) {
	            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para eliminar.");
	        }

	        if (estudianteRepository.existsById(id)) {
	            estudianteRepository.deleteById(id);
	            return ResponseEntity.ok("Estudiante eliminado exitosamente.");
	        } else {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Estudiante no encontrado.");
	        }
	    }
	}

