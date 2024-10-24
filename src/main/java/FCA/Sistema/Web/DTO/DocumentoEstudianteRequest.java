package FCA.Sistema.Web.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentoEstudianteRequest {
    private Integer tipoDocumentoId;
    private Integer estudianteId;
    private String urlDocumento;
    @Builder.Default
    private Boolean validado=false;
}
