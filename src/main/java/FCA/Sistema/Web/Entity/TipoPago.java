package FCA.Sistema.Web.Entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "tipo_pago")
public class TipoPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;  // Ej. "Matrícula", "Mensualidad", "Derecho de Grado", etc.

    @Column(nullable = false)
    private Boolean esRecurrentePorSemestre;  // Indica si este pago se repite cada semestre

    @OneToMany(mappedBy = "tipoPago")
    private List<Pago> pagos;  // Lista de pagos que están asociados a este tipo
}
