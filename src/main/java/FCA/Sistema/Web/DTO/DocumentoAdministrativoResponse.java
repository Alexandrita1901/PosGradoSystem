package FCA.Sistema.Web.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentoAdministrativoResponse {
    private Integer id;
    private String tipoDocumento;
    private String unidadPosgrado;
    private String titulo;
    private String urlDocumento;
    private Boolean validado;
}
