package FCA.Sistema.Web.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentoEstudianteResponse {
    private Integer id;
    private String tipoDocumentoNombre;
    private String estudianteNombre;
    private String urlDocumento;
    private Boolean validado;
}
