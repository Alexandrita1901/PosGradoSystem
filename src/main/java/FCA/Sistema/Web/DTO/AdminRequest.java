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
	String username;
	String password;
	String nombres;
	String apellidos;
	String correo;
	String contacto;
	String dni;
	Integer unidadPosgradoId;
	@Builder.Default
	boolean permisoCrear = true;
	@Builder.Default
	boolean permisoEditar = true;
	@Builder.Default
	boolean permisoEliminar = true;
	@Builder.Default
	boolean permisoListar = true;
}
