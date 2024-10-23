package FCA.Sistema.Web.Entity;

import java.time.LocalDate;
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
@Table(name = "semestre")
public class Semestre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;  // Ej. "Semestre 1", "Semestre 2", etc.

    @Column(nullable = false)
    private Integer numero;  // Número del semestre (1, 2, 3, etc.)

    @Column(nullable = false)
    private LocalDate fechaInicio;

    @Column(nullable = true)
    private LocalDate fechaFin;

    @OneToMany(mappedBy = "semestre")
    private List<Pago> pagos;  // Lista de pagos asociados al semestre
}
