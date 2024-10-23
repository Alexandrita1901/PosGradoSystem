package FCA.Sistema.Web.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProgramaEstudioRequest {
	private String nombre;
	private Integer creditos;
	private Integer duracionMeses;
	private Integer inversion;
	private String modalidad;
	private Boolean activo;
	private String descripcion;
	private String urlPlanEstudio;
	private Integer tipoProgramaId; // ID del tipo de programa asociado
	private Integer unidadPosgradoId; // Opcional, se usará solo por el SUPERADMIN
}
