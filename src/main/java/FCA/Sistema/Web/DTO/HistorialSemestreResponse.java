package FCA.Sistema.Web.DTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HistorialSemestreResponse {
    private Integer id;
    private String semestreNombre;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
}
