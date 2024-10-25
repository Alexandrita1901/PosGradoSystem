package FCA.Sistema.Web.Entity;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pagos")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "estudiante_id", nullable = false)
    private Estudiantes estudiante;  // El estudiante asociado al pago

    @ManyToOne
    @JoinColumn(name = "semestre_id", nullable = false)
    private Semestre semestre;  // El semestre al que está asociado el pago

    @ManyToOne
    @JoinColumn(name = "tipo_pago_id", nullable = false)
    private TipoPago tipoPago;  // El tipo de pago que se realiza (mensualidad, matrícula, etc.)

    @ManyToOne
    @JoinColumn(name = "tipo_documento_id", nullable = false)
    private TipoDocumento tipoDocumento;  // Documento asociado (voucher, recibo, etc.)

    @Column(nullable = false)
    private Double monto;  // Monto del pago

    @Column(nullable = false)
    private String estado;  // Estado del pago (pagado, pendiente, etc.)

    @Column(nullable = true, columnDefinition = "TEXT")
    private String observacion;  // Observaciones adicionales del pago

    @Column(nullable = true)
    private String urlDocumento;  // URL del documento asociado (voucher, recibo, etc.)

    @Column(nullable = false)
    private LocalDate fechaPago;  // Fecha en la que se realizó el pago
}


