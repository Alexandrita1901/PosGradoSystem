package FCA.Sistema.Web.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import FCA.Sistema.Web.DTO.TipoDocumentoRequest;
import FCA.Sistema.Web.Entity.TipoDocumento;
import FCA.Sistema.Web.Repository.TipoDocumentoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TipoDocumentoService {

    private final TipoDocumentoRepository tipoDocumentoRepository;

    // Crear un nuevo Tipo de Documento
    public ResponseEntity<String> crearTipoDocumento(TipoDocumentoRequest request) {
        TipoDocumento tipoDocumento = TipoDocumento.builder()
                .tipoDocumento(request.getTipoDocumento())
                .descripcion(request.getDescripcion())
                .build();

        tipoDocumentoRepository.save(tipoDocumento);
        return ResponseEntity.ok("Tipo de documento creado exitosamente.");
    }

    // Listar todos los Tipos de Documento
    public ResponseEntity<List<TipoDocumento>> listarTiposDocumento() {
        List<TipoDocumento> tiposDocumento = tipoDocumentoRepository.findAll();
        return ResponseEntity.ok(tiposDocumento);
    }

    // Obtener un Tipo de Documento por ID
    public ResponseEntity<TipoDocumento> obtenerTipoDocumentoPorId(Integer id) {
        Optional<TipoDocumento> tipoDocumentoOpt = tipoDocumentoRepository.findById(id);
        if (tipoDocumentoOpt.isPresent()) {
            return ResponseEntity.ok(tipoDocumentoOpt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Actualizar un Tipo de Documento
    public ResponseEntity<String> actualizarTipoDocumento(Integer id, TipoDocumentoRequest request) {
        Optional<TipoDocumento> tipoDocumentoOpt = tipoDocumentoRepository.findById(id);
        if (tipoDocumentoOpt.isPresent()) {
            TipoDocumento tipoDocumentoExistente = tipoDocumentoOpt.get();
            tipoDocumentoExistente.setTipoDocumento(request.getTipoDocumento());
            tipoDocumentoExistente.setDescripcion(request.getDescripcion());

            tipoDocumentoRepository.save(tipoDocumentoExistente);
            return ResponseEntity.ok("Tipo de documento actualizado exitosamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tipo de documento no encontrado.");
        }
    }

    // Eliminar un Tipo de Documento
    public ResponseEntity<String> eliminarTipoDocumento(Integer id) {
        if (tipoDocumentoRepository.existsById(id)) {
            tipoDocumentoRepository.deleteById(id);
            return ResponseEntity.ok("Tipo de documento eliminado exitosamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tipo de documento no encontrado.");
        }
    }
}
