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
    private Estudiantes estudiante; 

    @ManyToOne
    @JoinColumn(name = "semestre_id", nullable = false)
    private Semestre semestre; 
    @ManyToOne
    @JoinColumn(name = "tipo_pago_id", nullable = false)
    private TipoPago tipoPago; 

    @ManyToOne
    @JoinColumn(name = "tipo_documento_id", nullable = false)
    private TipoDocumento tipoDocumento;

    @Column(nullable = false)
    private Double monto;

    @Column(nullable = false)
    private String estado; 

    @Column(nullable = true, columnDefinition = "TEXT")
    private String observacion;

    @Column(nullable = true)
    private String urlDocumento;

    @Column(nullable = false)
    private LocalDate fechaPago;
}


