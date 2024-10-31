package FCA.Sistema.Web.Service;

import FCA.Sistema.Web.DTO.UserRequest;
import FCA.Sistema.Web.DTO.UserResponse;
import FCA.Sistema.Web.Entity.Permiso;
import FCA.Sistema.Web.Entity.Role;
import FCA.Sistema.Web.Entity.UnidadPosgrado;
import FCA.Sistema.Web.Entity.User;
import FCA.Sistema.Web.Repository.PermisoRepository;
import FCA.Sistema.Web.Repository.RoleRepository;
import FCA.Sistema.Web.Repository.UnidadPosgradoRepository;
import FCA.Sistema.Web.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	private final UnidadPosgradoRepository unidadPosgradoRepository;
	private final PermisoRepository permisoRepository;


	public ResponseEntity<String> crearUsuarioParaUnidad(UserRequest request, User usuarioLogueado) {
		UnidadPosgrado unidad;
		Role userRole;

		if (usuarioLogueado.getRole().getName().equals("ADMIN")) {
			if (request.getUnidadPosgradoId() != null
					&& !usuarioLogueado.getUnidadPosgrado().getId().equals(request.getUnidadPosgradoId())) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body("No puedes crear usuarios en una unidad diferente a la tuya.");
			}

			userRole = roleRepository.findByName("USER")
					.orElseThrow(() -> new RuntimeException("Rol USER no encontrado"));
			unidad = usuarioLogueado.getUnidadPosgrado();
		} else {
			unidad = unidadPosgradoRepository.findById(request.getUnidadPosgradoId())
					.orElseThrow(() -> new RuntimeException("Unidad de Posgrado no encontrada"));

			userRole = roleRepository.findByName(request.getRoleName())
					.orElseThrow(() -> new RuntimeException("Rol no encontrado"));
		}

		User nuevoUsuario = User.builder().username(request.getUsername())
				.password(passwordEncoder.encode(request.getPassword())).nombres(request.getNombres())
				.apellidos(request.getApellidos()).correo(request.getCorreo()).contacto(request.getContacto())
				.dni(request.getDni()).unidadPosgrado(unidad).role(userRole).build();

		userRepository.save(nuevoUsuario);

		Permiso permisos = Permiso.builder().user(nuevoUsuario).permisoCrear(request.isPermisoCrear())
				.permisoEditar(request.isPermisoEditar()).permisoEliminar(request.isPermisoEliminar())
				.permisoListar(request.isPermisoListar()).build();

		permisoRepository.save(permisos);

		return ResponseEntity.ok("Usuario creado exitosamente.");
	}


	public ResponseEntity<List<UserResponse>> listarUsuarios(User usuarioLogueado) {
	    List<User> usuarios;
	    if (usuarioLogueado.getRole().getName().equals("SUPERADMIN")) {
	        usuarios = userRepository.findAll();
	    } else {
	        usuarios = userRepository.findByUnidadPosgrado(usuarioLogueado.getUnidadPosgrado());
	    }

	    // Convertir la lista de User a UserResponse
	    List<UserResponse> usuariosResponse = usuarios.stream().map(usuario -> UserResponse.builder()
	        .id(usuario.getId())
	        .username(usuario.getUsername())
	        .nombres(usuario.getNombres())
	        .apellidos(usuario.getApellidos())
	        .correo(usuario.getCorreo())
	        .contacto(usuario.getContacto())
	        .roleName(usuario.getRole().getName())
	        // Aquí ajustamos para que solo incluya el nombre de la unidad
	        .unidadPosgrado(usuario.getUnidadPosgrado() != null ? usuario.getUnidadPosgrado().getNombre() : null)
	        .build()).toList();

	    return ResponseEntity.ok(usuariosResponse);
	}

	public ResponseEntity<UserResponse> obtenerUsuarioPorId(Integer id, User usuarioLogueado) {
	    Optional<User> usuarioOpt = userRepository.findById(id);
	    if (usuarioOpt.isPresent()) {
	        User usuario = usuarioOpt.get();
	        if (usuarioLogueado.getRole().getName().equals("ADMIN")
	                && !usuarioLogueado.getUnidadPosgrado().equals(usuario.getUnidadPosgrado())) {
	            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
	        }

	        // Convertir el User a UserResponse
	        UserResponse usuarioResponse = UserResponse.builder()
	            .id(usuario.getId())
	            .username(usuario.getUsername())
	            .nombres(usuario.getNombres())
	            .apellidos(usuario.getApellidos())
	            .correo(usuario.getCorreo())
	            .contacto(usuario.getContacto())
	            .roleName(usuario.getRole().getName())
	            // Solo obtenemos el nombre de la unidad
	            .unidadPosgrado(usuario.getUnidadPosgrado() != null ? usuario.getUnidadPosgrado().getNombre() : null)
	            .build();

	        return ResponseEntity.ok(usuarioResponse);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}


	public ResponseEntity<String> actualizarUsuario(Integer id, UserRequest request, User usuarioLogueado) {
		Optional<User> usuarioOpt = userRepository.findById(id);
		if (usuarioOpt.isPresent()) {
			User usuarioExistente = usuarioOpt.get();
			if (usuarioLogueado.getRole().getName().equals("ADMIN")
					&& !usuarioLogueado.getUnidadPosgrado().equals(usuarioExistente.getUnidadPosgrado())) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN)
						.body("No puedes actualizar usuarios de otra unidad.");
			}

			usuarioExistente.setNombres(request.getNombres());
			usuarioExistente.setApellidos(request.getApellidos());
			usuarioExistente.setCorreo(request.getCorreo());
			usuarioExistente.setContacto(request.getContacto());
			usuarioExistente.setDni(request.getDni());

			userRepository.save(usuarioExistente);
			return ResponseEntity.ok("Usuario actualizado exitosamente.");
		} else {
			return ResponseEntity.notFound().build();
		}
	}


	public ResponseEntity<String> eliminarUsuario(Integer id, User usuarioLogueado) {
		Optional<User> usuarioOpt = userRepository.findById(id);
		if (usuarioOpt.isPresent()) {
			User usuarioAEliminar = usuarioOpt.get();
			if (usuarioLogueado.getRole().getName().equals("ADMIN")
					&& !usuarioLogueado.getUnidadPosgrado().equals(usuarioAEliminar.getUnidadPosgrado())) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No puedes eliminar usuarios de otra unidad.");
			}

			userRepository.deleteById(id);
			return ResponseEntity.ok("Usuario eliminado exitosamente.");
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	/*public ResponseEntity<String> solicitarRestablecimiento(String correo) {
        Optional<User> usuarioOpt = userRepository.findByCorreo(correo);
        if (!usuarioOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
        }

        User usuario = usuarioOpt.get();
        String token = UUID.randomUUID().toString();
        usuario.setResetToken(token);
        usuario.setResetTokenExpiration(LocalDateTime.now().plusHours(1));
        userRepository.save(usuario);

       
        enviarEmailRestablecimiento(usuario.getCorreo(), token);

        return ResponseEntity.ok("Se ha enviado un correo con el enlace de restablecimiento.");
    }*/
	
	public ResponseEntity<String> solicitarRestablecimiento(String correo) {
	    User usuario = userRepository.findByCorreo(correo)
	        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
	    
	    // Generar el token de restablecimiento (por ejemplo, usando UUID)
	    String token = UUID.randomUUID().toString();
	    
	    // Guardar el token en la base de datos o en una entidad temporal para pruebas
	    // usuario.setResetToken(token);
	    userRepository.save(usuario);

	    // Para pruebas, devolver el token en la respuesta
	    return ResponseEntity.ok("Token de restablecimiento: " + token);
	}


    private void enviarEmailRestablecimiento(String correo, String token) {
    
        System.out.println("Enlace de restablecimiento: http://localhost:8080/usuarios/restablecer?token=" + token);
    }

    public ResponseEntity<String> restablecerConToken(String token, String nuevaContrasena) {
        Optional<User> usuarioOpt = userRepository.findByResetToken(token);
        if (!usuarioOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Token inválido.");
        }

        User usuario = usuarioOpt.get();

      
        if (usuario.getResetTokenExpiration().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("El token ha expirado.");
        }

     
        usuario.setPassword(passwordEncoder.encode(nuevaContrasena));
        usuario.setResetToken(null); 
        usuario.setResetTokenExpiration(null);
        userRepository.save(usuario);

        return ResponseEntity.ok("Contraseña restablecida exitosamente.");
    }

}
