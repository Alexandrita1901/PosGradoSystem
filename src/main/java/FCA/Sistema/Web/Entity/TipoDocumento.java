package FCA.Sistema.Web.Entity;
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
@Table(name = "tipo_documento")
public class TipoDocumento {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String tipoDocumento;  // Ej. "Título Profesional", "Certificado de Estudios", "DNI", "Recibo de Inscripción", etc.

    @Column(nullable = true)
    private Boolean validado;  // Estado de validación (aprobado, pendiente, etc.)

    @Column(nullable = true, columnDefinition = "TEXT")
    private String descripcion;  // Para cualquier descripcion sobre el documento

    // Relación ManyToOne con Estudiantes
    @ManyToOne
    @JoinColumn(name = "estudiante_id", nullable = false)
    private Estudiantes estudiante; // Cada documento pertenece a un estudiante
}

