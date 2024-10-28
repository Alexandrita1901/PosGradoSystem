package FCA.Sistema.Web.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoRequest {
	
	    private Integer estudianteId;
	    private Integer tipoPagoId;
	    private Integer tipoDocumentoId;
	    private Double monto;
	    private String observacion;
	    private String urlDocumento;
	}
 

