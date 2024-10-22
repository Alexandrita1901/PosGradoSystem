package FCA.Sistema.Web.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    private String username;
    private String password;
    private String nombres;
    private String apellidos;
    private String correo;
    private String contacto;
    private String dni;
    private Integer unidadPosgradoId;
    @Builder.Default
    private String roleName = "USER";
    private boolean permisoCrear;
    private boolean permisoEditar;
    private boolean permisoEliminar;
    private boolean permisoListar;
}
