package FCA.Sistema.Web.DTO;

import java.time.LocalDate;

import FCA.Sistema.Web.Entity.Estudiantes;
import FCA.Sistema.Web.Entity.Semestre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistorialSemestreRequest {
    private Estudiantes estudiante;
    private Semestre semestre;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
}