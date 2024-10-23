package FCA.Sistema.Web.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import FCA.Sistema.Web.DTO.TipoPagoRequest;
import FCA.Sistema.Web.Entity.TipoPago;
import FCA.Sistema.Web.Repository.TipoPagoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TipoPagoService {

    private final TipoPagoRepository tipoPagoRepository;

    // Crear un nuevo Tipo de Pago
    public ResponseEntity<String> crearTipoPago(TipoPagoRequest request) {
        TipoPago tipoPago = TipoPago.builder()
                .nombre(request.getNombre())
                .esRecurrentePorSemestre(request.getEsRecurrentePorSemestre())
                .build();
        tipoPagoRepository.save(tipoPago);
        return ResponseEntity.ok("Tipo de Pago creado exitosamente.");
    }

    // Listar todos los Tipos de Pago
    public ResponseEntity<List<TipoPago>> listarTiposPago() {
        List<TipoPago> tiposPago = tipoPagoRepository.findAll();
        return ResponseEntity.ok(tiposPago);
    }

    // Obtener un Tipo de Pago por ID
    public ResponseEntity<TipoPago> obtenerTipoPagoPorId(Integer id) {
        Optional<TipoPago> tipoPagoOpt = tipoPagoRepository.findById(id);
        if (tipoPagoOpt.isPresent()) {
            return ResponseEntity.ok(tipoPagoOpt.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // Actualizar un Tipo de Pago
    public ResponseEntity<String> actualizarTipoPago(Integer id, TipoPagoRequest request) {
        Optional<TipoPago> tipoPagoOpt = tipoPagoRepository.findById(id);
        if (tipoPagoOpt.isPresent()) {
            TipoPago tipoPagoExistente = tipoPagoOpt.get();
            tipoPagoExistente.setNombre(request.getNombre());
            tipoPagoExistente.setEsRecurrentePorSemestre(request.getEsRecurrentePorSemestre());
            tipoPagoRepository.save(tipoPagoExistente);
            return ResponseEntity.ok("Tipo de Pago actualizado exitosamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tipo de Pago no encontrado.");
        }
    }

    // Eliminar un Tipo de Pago
    public ResponseEntity<String> eliminarTipoPago(Integer id) {
        if (tipoPagoRepository.existsById(id)) {
            tipoPagoRepository.deleteById(id);
            return ResponseEntity.ok("Tipo de Pago eliminado exitosamente.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tipo de Pago no encontrado.");
        }
    }
}

