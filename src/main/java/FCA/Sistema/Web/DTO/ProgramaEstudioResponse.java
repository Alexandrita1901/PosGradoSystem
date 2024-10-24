package FCA.Sistema.Web.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramaEstudioResponse {
    private Integer id;
    private String nombre;
    private Integer creditos;
    private Integer duracionMeses;
    private Integer inversion;
    private String modalidad;
    private Boolean activo;
    private String descripcion;
    private String urlPlanEstudio;

    // Solo el nombre de la unidad y del tipo de programa para simplificar la respuesta
    private String unidadPosgradoNombre;
    private String tipoProgramaNombre;
}
