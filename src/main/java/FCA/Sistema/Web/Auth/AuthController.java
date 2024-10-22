package FCA.Sistema.Web.Auth;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import FCA.Sistema.Web.DTO.LoginRequest;
import FCA.Sistema.Web.DTO.RegisterRequest;
import FCA.Sistema.Web.Service.AuthService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

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
}
