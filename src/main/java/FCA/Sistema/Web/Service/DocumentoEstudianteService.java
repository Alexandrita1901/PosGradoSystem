package FCA.Sistema.Web.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import FCA.Sistema.Web.DTO.DocumentoEstudianteRequest;
import FCA.Sistema.Web.DTO.DocumentoEstudianteResponse;
import FCA.Sistema.Web.Entity.DocumentoEstudiante;
import FCA.Sistema.Web.Entity.Estudiantes;
import FCA.Sistema.Web.Entity.TipoDocumento;
import FCA.Sistema.Web.Entity.User;
import FCA.Sistema.Web.Repository.DocumentoEstudianteRepository;
import FCA.Sistema.Web.Repository.EstudiantesRepository;
import FCA.Sistema.Web.Repository.TipoDocumentoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentoEstudianteService {

    private final DocumentoEstudianteRepository documentoEstudianteRepository;
    private final TipoDocumentoRepository tipoDocumentoRepository;
    private final EstudiantesRepository estudianteRepository;

    // Crear un nuevo documento para el estudiante
    public ResponseEntity<String> crearDocumentoEstudiante(DocumentoEstudianteRequest request, User usuarioLogueado) {
        // Obtener el tipo de documento
        Optional<TipoDocumento> tipoDocumentoOpt = tipoDocumentoRepository.findById(request.getTipoDocumentoId());
        if (!tipoDocumentoOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tipo de documento no encontrado.");
        }

        // Obtener el estudiante
        Optional<Estudiantes> estudianteOpt = estudianteRepository.findById(request.getEstudianteId());
        if (!estudianteOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Estudiante no encontrado.");
        }

        DocumentoEstudiante documento = DocumentoEstudiante.builder()
                .tipoDocumento(tipoDocumentoOpt.get())
                .estudiante(estudianteOpt.get())
                .urlDocumento(request.getUrlDocumento())
                .validado(request.getValidado())
                .build();

        documentoEstudianteRepository.save(documento);
        return ResponseEntity.ok("Documento de estudiante creado exitosamente.");
    }

    // Listar documentos de estudiantes
    public ResponseEntity<List<DocumentoEstudianteResponse>> listarDocumentosEstudiantes(User usuarioLogueado) {
        List<DocumentoEstudiante> documentos;

        if ("SUPERADMIN".equals(usuarioLogueado.getRole().getName())) {
            documentos = documentoEstudianteRepository.findAll();
        } else {
            documentos = documentoEstudianteRepository.findByEstudianteProgramaEstudioUnidadPosgrado(usuarioLogueado.getUnidadPosgrado());
        }

        List<DocumentoEstudianteResponse> response = documentos.stream()
                .map(documento -> DocumentoEstudianteResponse.builder()
                        .id(documento.getId())
                        .tipoDocumentoNombre(documento.getTipoDocumento().getTipoDocumento())
                        .estudianteNombre(documento.getEstudiante().getNombres() + " " + documento.getEstudiante().getApellidos())
                        .urlDocumento(documento.getUrlDocumento())
                        .validado(documento.getValidado())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
    
 // Método para actualizar un documento de estudiante
    public ResponseEntity<String> actualizarDocumentoEstudiante(Integer id, DocumentoEstudianteRequest request, User usuarioLogueado) {
        Optional<DocumentoEstudiante> documentoOpt = documentoEstudianteRepository.findById(id);

        if (!documentoOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Documento no encontrado.");
        }

        DocumentoEstudiante documento = documentoOpt.get();

        // Solo SUPERADMIN puede devolver el estado a false
        if (!"SUPERADMIN".equals(usuarioLogueado.getRole().getName()) && request.getValidado() != null) {
            if (!documento.getValidado() && request.getValidado()) {
                // Cualquier usuario puede cambiar el estado a true
                documento.setValidado(true);
            } else if (documento.getValidado() && !request.getValidado()) {
                // Si el documento ya está validado (true), y el usuario no es SUPERADMIN, no puede cambiarlo a false
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Solo el SUPERADMIN puede devolver el estado a no validado.");
            }
        } else if ("SUPERADMIN".equals(usuarioLogueado.getRole().getName()) && request.getValidado() != null) {
            // SUPERADMIN puede establecer true o false
            documento.setValidado(request.getValidado());
        }

        // Actualizar otros campos del documento según la solicitud (si es necesario)
        documento.setUrlDocumento(request.getUrlDocumento());

        documentoEstudianteRepository.save(documento);
        return ResponseEntity.ok("Documento actualizado exitosamente.");
    }
    
    // Eliminar un documento de estudiante (solo ADMIN y SUPERADMIN)
    public ResponseEntity<String> eliminarDocumentoEstudiante(Integer id, User usuarioLogueado) {
        if (!"SUPERADMIN".equals(usuarioLogueado.getRole().getName()) && !"ADMIN".equals(usuarioLogueado.getRole().getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para eliminar.");
        }

        if (documentoEstudianteRepository.existsById(id)) {
            documentoEstudianteRepository.deleteById(id);
            return ResponseEntity.ok("Documento de estudiante eliminado exitosamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Documento de estudiante no encontrado.");
        }
    }
}
