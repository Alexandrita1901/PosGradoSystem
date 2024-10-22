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
    String username;
    String nombres;
    String apellidos;
    String password;
    String dni;
    String contacto;
    String correo;
    @Builder.Default
    boolean permisoCrear = true;
    @Builder.Default
    boolean permisoEditar = true;
    @Builder.Default
    boolean permisoEliminar = true;
    @Builder.Default
    boolean permisoListar = true;
    
}