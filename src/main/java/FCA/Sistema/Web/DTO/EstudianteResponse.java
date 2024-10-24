package FCA.Sistema.Web.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstudianteResponse {

    private Integer id;
    private String nombres;
    private String apellidos;
    private String email;
    private String documentoIdentidad;
    private String telefono;
    private String estadoEstudiante;
    private String programaEstudioNombre;
}
