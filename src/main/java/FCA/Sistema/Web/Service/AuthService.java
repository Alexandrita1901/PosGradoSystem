package FCA.Sistema.Web.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

import FCA.Sistema.Web.Auth.AuthResponse;
import FCA.Sistema.Web.DTO.LoginRequest;
import FCA.Sistema.Web.DTO.RegisterRequest;
import FCA.Sistema.Web.Entity.Permiso;
import FCA.Sistema.Web.Entity.Role;
import FCA.Sistema.Web.Entity.User;
import FCA.Sistema.Web.Jwt.JwtService;
import FCA.Sistema.Web.Repository.PermisoRepository;
import FCA.Sistema.Web.Repository.RoleRepository;
import FCA.Sistema.Web.Repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PermisoRepository permisoRepository;
	private final JwtService jwtService;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;

	private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

	public AuthResponse login(LoginRequest request) {
		logger.info("Iniciando login para el usuario: {}", request.getUsername());

		var user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> {
			logger.error("Usuario no encontrado: {}", request.getUsername());
			return new UsernameNotFoundException("User not found");
		});

		logger.info("Usuario encontrado: {}", user.getUsername());

		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

		String accessToken = jwtService.getToken(user);
		logger.debug("Token de acceso generado: {}", accessToken);

		String refreshToken = user.getRefreshToken() != null ? user.getRefreshToken()
				: jwtService.generateRefreshToken(user);
		logger.debug("Token de refresco generado: {}", refreshToken);

		user.setRefreshToken(refreshToken);
		userRepository.save(user);
		logger.info("Refresh token guardado para el usuario: {}", user.getUsername());

		return AuthResponse.builder().token(accessToken).refreshToken(refreshToken).build();
	}

	public AuthResponse registerSuperAdmin(RegisterRequest request) {
	    logger.info("Registrando SuperAdmin con username predeterminado: SuperAdmin");

	    Role superadminRole = roleRepository.findByName("SUPERADMIN").orElseThrow(() -> {
	        logger.error("Rol SUPERADMIN no encontrado");
	        return new RuntimeException("Role SUPERADMIN not found");
	    });

	    // Usamos "SuperAdmin" como nombre de usuario fijo
	    User superadminUser = User.builder()
	            .username("SuperAdmin")  // Aquí se asigna directamente el username fijo
	            .password(passwordEncoder.encode(request.getPassword()))
	            .apellidos(request.getApellidos())
	            .dni(request.getDni())
	            .correo(request.getCorreo())
	            .nombres(request.getNombres())
	            .contacto(request.getContacto())
	            .role(superadminRole)
	            .build();

	    userRepository.save(superadminUser);
	    roleRepository.save(superadminRole);

	    Permiso permisosSuperAdmin = Permiso.builder()
	            .user(superadminUser)
	            .permisoCrear(true)
	            .permisoEditar(true)
	            .permisoEliminar(true)
	            .permisoListar(true)
	            .build();

	    permisoRepository.save(permisosSuperAdmin);

	    String accessToken = jwtService.getToken(superadminUser);
	    String refreshToken = jwtService.generateRefreshToken(superadminUser);
	    superadminUser.setRefreshToken(refreshToken);
	    userRepository.save(superadminUser);

	    logger.info("SuperAdmin registrado con éxito, username: {}", superadminUser.getUsername());

	    return AuthResponse.builder()
	            .token(accessToken)
	            .refreshToken(refreshToken)
	            .build();
	}

	public void logout(String username) {
		logger.info("Cerrando sesión para el usuario: {}", username);

		var user = userRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));

		user.setLoggedOut(true);
		user.setRefreshToken(null);
		userRepository.save(user);

		logger.info("Usuario {} ha cerrado sesión correctamente", user.getUsername());
	}

	public AuthResponse refreshToken(String refreshToken) {
		logger.info("Generando nuevo token de acceso para el token de refresco proporcionado");

		var user = userRepository.findByRefreshToken(refreshToken).orElseThrow(() -> {
			logger.error("Token de refresco inválido");
			return new RuntimeException("Invalid refresh token");
		});

		if (user.isLoggedOut()) {
			logger.error("El usuario ha cerrado sesión, debe volver a registrarse o iniciar sesión");
			throw new RuntimeException("User has logged out, please re-register or login again.");
		}

		String newAccessToken = jwtService.getToken(user);
		logger.debug("Nuevo token de acceso generado: {}", newAccessToken);

		return AuthResponse.builder().token(newAccessToken).refreshToken(refreshToken).build();
	}
	
	public User getCurrentUser() {
	    // Obtener el objeto principal de autenticación
	    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

	    String username;
	    if (principal instanceof UserDetails) {
	        username = ((UserDetails) principal).getUsername();
	    } else if (principal instanceof String) {
	        username = (String) principal;
	    } else {
	        throw new UsernameNotFoundException("User not found");
	    }

	    return userRepository.findByUsername(username)
	            .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
	}
}
