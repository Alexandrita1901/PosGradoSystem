package FCA.Sistema.Web.Service;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import FCA.Sistema.Web.DTO.PagoRequest;

import FCA.Sistema.Web.Entity.Estudiantes;
import FCA.Sistema.Web.Entity.Pago;
import FCA.Sistema.Web.Entity.TipoDocumento;
import FCA.Sistema.Web.Entity.TipoPago;
import FCA.Sistema.Web.Entity.User;
import FCA.Sistema.Web.Repository.EstudiantesRepository;
import FCA.Sistema.Web.Repository.PagoRepository;
import FCA.Sistema.Web.Repository.TipoDocumentoRepository;
import FCA.Sistema.Web.Repository.TipoPagoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PagoService {

    private final PagoRepository pagoRepository;
    private final EstudiantesRepository estudiantesRepository;
    private final TipoPagoRepository tipoPagoRepository;
    private final TipoDocumentoRepository tipoDocumentoRepository;

    // Crear un nuevo pago
    public ResponseEntity<String> crearPago(PagoRequest request, User usuarioLogueado) {
        Estudiantes estudiante = estudiantesRepository.findById(request.getEstudianteId())
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        // Validación de acceso por unidad
        if (!"SUPERADMIN".equals(usuarioLogueado.getRole().getName()) &&
                !estudiante.getProgramaEstudio().getUnidadPosgrado().equals(usuarioLogueado.getUnidadPosgrado())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes acceso a este estudiante.");
        }

        TipoPago tipoPago = tipoPagoRepository.findById(request.getTipoPagoId())
                .orElseThrow(() -> new RuntimeException("Tipo de pago no encontrado"));

        TipoDocumento tipoDocumento = tipoDocumentoRepository.findById(request.getTipoDocumentoId())
                .orElseThrow(() -> new RuntimeException("Tipo de documento no encontrado"));

        Pago pago = Pago.builder()
                .estudiante(estudiante)
                .semestre(estudiante.getSemestreActual())  // Asociar el pago al semestre actual del estudiante
                .tipoPago(tipoPago)
                .tipoDocumento(tipoDocumento)  // Incluir tipoDocumento
                .monto(request.getMonto())
                .estado("Pendiente")  // Estado inicial del pago
                .observacion(request.getObservacion())
                .urlDocumento(request.getUrlDocumento())
                .fechaPago(LocalDate.now())  // Fecha de pago actual
                .build();

        pagoRepository.save(pago);

        return ResponseEntity.ok("Pago registrado exitosamente para el semestre actual.");
    }

   //LISTAR

    // Actualizar un pago
    public ResponseEntity<String> actualizarPago(Integer id, PagoRequest request, User usuarioLogueado) {
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));

        // Validación de acceso para ADMIN y USER
        if (!"SUPERADMIN".equals(usuarioLogueado.getRole().getName()) &&
                !pago.getEstudiante().getProgramaEstudio().getUnidadPosgrado().equals(usuarioLogueado.getUnidadPosgrado())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes acceso para actualizar este pago.");
        }

        // Actualizar datos del pago
        TipoPago tipoPago = tipoPagoRepository.findById(request.getTipoPagoId())
                .orElseThrow(() -> new RuntimeException("Tipo de pago no encontrado"));
        TipoDocumento tipoDocumento = tipoDocumentoRepository.findById(request.getTipoDocumentoId())
                .orElseThrow(() -> new RuntimeException("Tipo de documento no encontrado"));

        pago.setTipoPago(tipoPago);
        pago.setTipoDocumento(tipoDocumento);
        pago.setMonto(request.getMonto());
        pago.setObservacion(request.getObservacion());
        pago.setUrlDocumento(request.getUrlDocumento());

        pagoRepository.save(pago);

        return ResponseEntity.ok("Pago actualizado exitosamente.");
    }

    // Eliminar un pago
    public ResponseEntity<String> eliminarPago(Integer id, User usuarioLogueado) {
        if ("USER".equals(usuarioLogueado.getRole().getName())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para eliminar pagos.");
        }

        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));

        // Validación de acceso para ADMIN
        if (!"SUPERADMIN".equals(usuarioLogueado.getRole().getName()) &&
                !pago.getEstudiante().getProgramaEstudio().getUnidadPosgrado().equals(usuarioLogueado.getUnidadPosgrado())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes acceso para eliminar este pago.");
        }

        pagoRepository.delete(pago);
        return ResponseEntity.ok("Pago eliminado exitosamente.");
    }
}

