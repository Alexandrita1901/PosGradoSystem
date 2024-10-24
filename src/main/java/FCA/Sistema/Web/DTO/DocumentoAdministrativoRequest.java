package FCA.Sistema.Web.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentoAdministrativoRequest {
	private Integer tipoDocumentoId;
	private Integer unidadPosgradoId;
	private String titulo;
	private String urlDocumento;
	private Boolean validado;
}
