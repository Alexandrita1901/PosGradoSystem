package FCA.Sistema.Web.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import FCA.Sistema.Web.DTO.TipoProgramaRequest;
import FCA.Sistema.Web.Entity.TipoPrograma;
import FCA.Sistema.Web.Repository.TipoProgramaRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TipoProgramaService {

    private final TipoProgramaRepository tipoProgramaRepository;

    // Crear un nuevo Tipo de Programa
    public ResponseEntity<String> crearTipoPrograma(TipoProgramaRequest request) {
        TipoPrograma tipoPrograma = TipoPrograma.builder()
                .nombreprograma(request.getNombreprograma())
                .build();
        tipoProgramaRepository.save(tipoPrograma);
        return ResponseEntity.ok("Tipo de Programa creado exitosamente.");
    }

    // Listar todos los Tipos de Programa
    public ResponseEntity<List<TipoPrograma>> listarTiposPrograma() {
        List<TipoPrograma> tiposPrograma = tipoProgramaRepository.findAll();
        return ResponseEntity.ok(tiposPrograma);
    }

    // Obtener un Tipo de Programa por ID
    public ResponseEntity<TipoPrograma> obtenerTipoProgramaPorId(Integer id) {
        Optional<TipoPrograma> tipoProgramaOpt = tipoProgramaRepository.findById(id);
        if (tipoProgramaOpt.isPresent()) {
            return ResponseEntity.ok(tipoProgramaOpt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Actualizar un Tipo de Programa
    public ResponseEntity<String> actualizarTipoPrograma(Integer id, TipoProgramaRequest request) {
        Optional<TipoPrograma> tipoProgramaOpt = tipoProgramaRepository.findById(id);
        if (tipoProgramaOpt.isPresent()) {
            TipoPrograma tipoProgramaExistente = tipoProgramaOpt.get();
            tipoProgramaExistente.setNombreprograma(request.getNombreprograma());
            tipoProgramaRepository.save(tipoProgramaExistente);
            return ResponseEntity.ok("Tipo de Programa actualizado exitosamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tipo de Programa no encontrado.");
        }
    }

    // Eliminar un Tipo de Programa
    public ResponseEntity<String> eliminarTipoPrograma(Integer id) {
        if (tipoProgramaRepository.existsById(id)) {
            tipoProgramaRepository.deleteById(id);
            return ResponseEntity.ok("Tipo de Programa eliminado exitosamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tipo de Programa no encontrado.");
        }
    }
}