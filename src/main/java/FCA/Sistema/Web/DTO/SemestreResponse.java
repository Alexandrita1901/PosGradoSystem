package FCA.Sistema.Web.DTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SemestreResponse {
    private Integer id;
    private String nombre;
    private Integer numero;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
}
