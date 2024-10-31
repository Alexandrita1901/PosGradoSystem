package FCA.Sistema.Web.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
	 private String nombres;
	 private String apellidos;
	 private String password;
	 private String dni;
	 private String contacto;
	 private String correo;
    @Builder.Default
    boolean permisoCrear = true;
    @Builder.Default
    boolean permisoEditar = true;
    @Builder.Default
    boolean permisoEliminar = true;
    @Builder.Default
    boolean permisoListar = true;
    
}