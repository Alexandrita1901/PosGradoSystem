package FCA.Sistema.Web.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoPagoResponse {
    private Integer id;
    private String nombre;
    private Boolean esRecurrentePorSemestre;
}