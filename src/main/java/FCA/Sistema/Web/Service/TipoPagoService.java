package FCA.Sistema.Web.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import FCA.Sistema.Web.DTO.TipoPagoRequest;
import FCA.Sistema.Web.DTO.TipoPagoResponse;
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
    public ResponseEntity<List<TipoPagoResponse>> listarTiposPago() {
        List<TipoPago> tiposPago = tipoPagoRepository.findAll();

        // Convertir la lista de entidades a DTOs
        List<TipoPagoResponse> tipoPagoResponses = tiposPago.stream()
            .map(tipoPago -> TipoPagoResponse.builder()
                .id(tipoPago.getId())
                .nombre(tipoPago.getNombre())
                .esRecurrentePorSemestre(tipoPago.getEsRecurrentePorSemestre())
                .build())
            .collect(Collectors.toList());

        return ResponseEntity.ok(tipoPagoResponses);
    }

    // Obtener un Tipo de Pago por ID
    public ResponseEntity<TipoPagoResponse> obtenerTipoPagoPorId(Integer id) {
        Optional<TipoPago> tipoPagoOpt = tipoPagoRepository.findById(id);
        if (tipoPagoOpt.isPresent()) {
            TipoPago tipoPago = tipoPagoOpt.get();

            // Convertir la entidad a DTO
            TipoPagoResponse tipoPagoResponse = TipoPagoResponse.builder()
                .id(tipoPago.getId())
                .nombre(tipoPago.getNombre())
                .esRecurrentePorSemestre(tipoPago.getEsRecurrentePorSemestre())
                .build();

            return ResponseEntity.ok(tipoPagoResponse);
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

