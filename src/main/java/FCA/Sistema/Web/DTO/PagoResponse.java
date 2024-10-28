package FCA.Sistema.Web.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagoResponse {
	
    private Integer id;
    private String nombreEstudiante; 
    private String semestre; 
    private Double monto;  
    private String tipoDocumento; 
    private String estado;  
    private String tipoPago; 
    private String observacion; 
    private String urlDocumento; 
    private String fechaPago; 
}
