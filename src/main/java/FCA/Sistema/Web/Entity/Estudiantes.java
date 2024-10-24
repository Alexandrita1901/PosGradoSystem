package FCA.Sistema.Web.Entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "estudiantes")
public class Estudiantes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombres;

    @Column(nullable = false)
    private String apellidos;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String documentoIdentidad;

    @Column(nullable = false)
    private String telefono;

    @Column(nullable = true)
    private String direccion;

    @Column(nullable = false)
    private String ultimoGradoAcademico;

    @Column(nullable = true)
    private String universidadProcedencia;

    @Column(nullable = true)
    private String entidadLaboralDependiente;

    @Column(nullable = true)
    private String cargoLaboralDependiente;

    @Column(nullable = true)
    private String direccionLaboralDependiente;

    @Column(nullable = true)
    private String telefonoLaboralDependiente;

    @Column(nullable = true)
    private String sectorLaboralIndependiente;

    @Column(nullable = true)
    private String cargoLaboralIndependiente;

    @Column(nullable = false)
    private LocalDate fechaInscripcion;

    @Column(nullable = true)
    private String urlFotografia;

    @ManyToOne
    @JoinColumn(name = "id_programa_estudio", nullable = false)
    private ProgramaEstudio programaEstudio;

    @ManyToOne
    @JoinColumn(name = "semestre_ingreso_id", nullable = false)
    private Semestre semestreIngreso;  // Semestre en el que el estudiante ingresó

    @ManyToOne
    @JoinColumn(name = "semestre_actual_id", nullable = false)
    private Semestre semestreActual;  // Semestre en el que está actualmente el estudiante

    @OneToMany(mappedBy = "estudiante")
    private List<Pago> pagos;  // Lista de pagos del estudiante

    @OneToMany(mappedBy = "estudiante")
    private List<HistorialSemestre> historialSemestres;  // Historial de semestres del estudiante

    @Builder.Default
    private Boolean activo = true;

    @Column(nullable = false)
    private Integer totalSemestres;  // Duración del programa en semestres

    // Método para verificar si el estudiante está al día con sus pagos
    public boolean estaAlDia() {
        return pagos.stream().allMatch(pago -> "Pagado".equalsIgnoreCase(pago.getEstado()));
    }
}
