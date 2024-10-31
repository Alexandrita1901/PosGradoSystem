package FCA.Sistema.Web.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestablecerContrasenaRequest {
    private String token;
    private String nuevaContrasena;
}
