package FCA.Sistema.Web.Auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import FCA.Sistema.Web.DTO.LoginRequest;
import FCA.Sistema.Web.DTO.PasswordResetRequest;
import FCA.Sistema.Web.DTO.RegisterRequest;
import FCA.Sistema.Web.DTO.RestablecerContrasenaRequest;
import FCA.Sistema.Web.Entity.User;
import FCA.Sistema.Web.Service.AuthService;
import FCA.Sistema.Web.Service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin("*")
public class AuthController {

    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerSuperAdmin(@RequestBody RegisterRequest request) {
        AuthResponse response = authService.registerSuperAdmin(request);
        return ResponseEntity.ok(response);
    }

    // Nuevo endpoint para solicitar restablecimiento de contraseña
    @PostMapping("/solicitar-restablecimiento")
    public ResponseEntity<String> solicitarRestablecimiento(@RequestBody PasswordResetRequest request) {
        return userService.solicitarRestablecimiento(request.getCorreo());
    }


    // Nuevo endpoint para restablecer la contraseña con el token
    @PostMapping("/restablecer")
    public ResponseEntity<String> restablecerConToken(@RequestBody RestablecerContrasenaRequest request) {
        return userService.restablecerConToken(request.getToken(), request.getNuevaContrasena());
    }
    
    @GetMapping("/current-user")
    public ResponseEntity<User> getCurrentUser() {
        User currentUser = authService.getCurrentUser();
        return ResponseEntity.ok(currentUser);
    }


}
