package FCA.Sistema.Web.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminRequest {
	 private String username;
	 private String password;
	 private String nombres;
	 private String apellidos;
	 private String correo;
	 private String contacto;
	 private String dni;
	 private Integer unidadPosgradoId;
	@Builder.Default
	boolean permisoCrear = true;
	@Builder.Default
	boolean permisoEditar = true;
	@Builder.Default
	boolean permisoEliminar = true;
	@Builder.Default
	boolean permisoListar = true;
}
