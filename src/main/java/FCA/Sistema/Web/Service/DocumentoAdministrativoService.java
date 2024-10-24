package FCA.Sistema.Web.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import FCA.Sistema.Web.DTO.DocumentoAdministrativoRequest;
import FCA.Sistema.Web.DTO.DocumentoAdministrativoResponse;
import FCA.Sistema.Web.Entity.DocumentoAdministrativos;
import FCA.Sistema.Web.Entity.TipoDocumento;
import FCA.Sistema.Web.Entity.UnidadPosgrado;
import FCA.Sistema.Web.Entity.User;
import FCA.Sistema.Web.Repository.DocumentoAdministrativoRepository;
import FCA.Sistema.Web.Repository.TipoDocumentoRepository;
import FCA.Sistema.Web.Repository.UnidadPosgradoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DocumentoAdministrativoService {

    private final DocumentoAdministrativoRepository documentoAdministrativoRepository;
    private final TipoDocumentoRepository tipoDocumentoRepository;
    private final UnidadPosgradoRepository unidadPosgradoRepository;

    // Crear un nuevo documento administrativo
    public ResponseEntity<String> crearDocumentoAdministrativo(DocumentoAdministrativoRequest request, User usuarioLogueado) {
        // Obtener el tipo de documento
        Optional<TipoDocumento> tipoDocumentoOpt = tipoDocumentoRepository.findById(request.getTipoDocumentoId());
        if (!tipoDocumentoOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tipo de documento no encontrado.");
        }

        UnidadPosgrado unidadPosgrado;
        // Verificar si es SUPERADMIN o ADMIN/USER
        if ("SUPERADMIN".equals(usuarioLogueado.getRole().getName())) {
            // SUPERADMIN puede especificar manualmente la unidad
            if (request.getUnidadPosgradoId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unidad de posgrado requerida para el SUPERADMIN.");
            }
            unidadPosgrado = unidadPosgradoRepository.findById(request.getUnidadPosgradoId())
                    .orElseThrow(() -> new RuntimeException("Unidad de Posgrado no encontrada."));
        } else {
            // ADMIN/USER solo pueden crear documentos en su propia unidad
            unidadPosgrado = usuarioLogueado.getUnidadPosgrado();
        }

        // Crear el documento administrativo
        DocumentoAdministrativos documento = DocumentoAdministrativos.builder()
                .tipoDocumento(tipoDocumentoOpt.get())
                .unidadPosgrado(unidadPosgrado)
                .titulo(request.getTitulo())
                .urlDocumento(request.getUrlDocumento())
                .validado(request.getValidado())
                .build();

        documentoAdministrativoRepository.save(documento);

        return ResponseEntity.ok("Documento administrativo creado exitosamente.");
    }

    // Listar documentos administrativos (con restricción por unidad para USER/ADMIN)
    public ResponseEntity<List<DocumentoAdministrativoResponse>> listarDocumentosAdministrativos(User usuarioLogueado) {
        List<DocumentoAdministrativos> documentos;

        if ("SUPERADMIN".equals(usuarioLogueado.getRole().getName())) {
            // SUPERADMIN puede listar todos los documentos
            documentos = documentoAdministrativoRepository.findAll();
        } else {
            // USER/ADMIN solo pueden listar documentos de su unidad
            documentos = documentoAdministrativoRepository.findByUnidadPosgrado(usuarioLogueado.getUnidadPosgrado());
        }

        List<DocumentoAdministrativoResponse> response = documentos.stream()
                .map(doc -> DocumentoAdministrativoResponse.builder()
                        .id(doc.getId())
                        .tipoDocumento(doc.getTipoDocumento().getTipoDocumento())
                        .unidadPosgrado(doc.getUnidadPosgrado().getNombre())
                        .titulo(doc.getTitulo())
                        .urlDocumento(doc.getUrlDocumento())
                        .validado(doc.getValidado())
                        .build())
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    // Obtener un documento administrativo por ID
    public ResponseEntity<DocumentoAdministrativoResponse> obtenerDocumentoPorId(Integer id, User usuarioLogueado) {
        Optional<DocumentoAdministrativos> documentoOpt = documentoAdministrativoRepository.findById(id);
        if (documentoOpt.isPresent()) {
            DocumentoAdministrativos documento = documentoOpt.get();

            // Verificar que el usuario tiene acceso a este documento
            if (!"SUPERADMIN".equals(usuarioLogueado.getRole().getName()) &&
                !documento.getUnidadPosgrado().equals(usuarioLogueado.getUnidadPosgrado())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
            }

            DocumentoAdministrativoResponse response = DocumentoAdministrativoResponse.builder()
                    .id(documento.getId())
                    .tipoDocumento(documento.getTipoDocumento().getTipoDocumento())
                    .unidadPosgrado(documento.getUnidadPosgrado().getNombre())
                    .titulo(documento.getTitulo())
                    .urlDocumento(documento.getUrlDocumento())
                    .validado(documento.getValidado())
                    .build();

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Actualizar un documento administrativo
    public ResponseEntity<String> actualizarDocumentoAdministrativo(Integer id, DocumentoAdministrativoRequest request, User usuarioLogueado) {
        Optional<DocumentoAdministrativos> documentoOpt = documentoAdministrativoRepository.findById(id);
        if (documentoOpt.isPresent()) {
            DocumentoAdministrativos documentoExistente = documentoOpt.get();

            // Verificar que el usuario tiene acceso a este documento
            if (!"SUPERADMIN".equals(usuarioLogueado.getRole().getName()) &&
                !documentoExistente.getUnidadPosgrado().equals(usuarioLogueado.getUnidadPosgrado())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para editar este documento.");
            }

            documentoExistente.setTitulo(request.getTitulo());
            documentoExistente.setUrlDocumento(request.getUrlDocumento());
            documentoExistente.setValidado(request.getValidado());

            documentoAdministrativoRepository.save(documentoExistente);
            return ResponseEntity.ok("Documento administrativo actualizado exitosamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Documento administrativo no encontrado.");
        }
    }

    // Eliminar un documento administrativo (solo permitido para ADMIN y SUPERADMIN)
    public ResponseEntity<String> eliminarDocumentoAdministrativo(Integer id, User usuarioLogueado) {
        Optional<DocumentoAdministrativos> documentoOpt = documentoAdministrativoRepository.findById(id);

        if (!documentoOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Documento administrativo no encontrado.");
        }

        DocumentoAdministrativos documento = documentoOpt.get();

        // Verificar que el usuario tiene acceso a este documento
        if (!"SUPERADMIN".equals(usuarioLogueado.getRole().getName()) &&
            !documento.getUnidadPosgrado().equals(usuarioLogueado.getUnidadPosgrado())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para eliminar este documento.");
        }

        documentoAdministrativoRepository.delete(documento);
        return ResponseEntity.ok("Documento administrativo eliminado exitosamente.");
    }
}
