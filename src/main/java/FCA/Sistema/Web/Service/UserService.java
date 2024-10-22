package FCA.Sistema.Web.Service;

import FCA.Sistema.Web.DTO.UserRequest;
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

import java.util.List;
import java.util.Optional;

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


	public ResponseEntity<List<User>> listarUsuarios(User usuarioLogueado) {
		List<User> usuarios;

		if (usuarioLogueado.getRole().getName().equals("SUPERADMIN")) {
			usuarios = userRepository.findAll();
		} else {
			usuarios = userRepository.findByUnidadPosgrado(usuarioLogueado.getUnidadPosgrado());
		}

		return ResponseEntity.ok(usuarios);
	}


	public ResponseEntity<User> obtenerUsuarioPorId(Integer id, User usuarioLogueado) {
		Optional<User> usuarioOpt = userRepository.findById(id);
		if (usuarioOpt.isPresent()) {
			User usuario = usuarioOpt.get();
			if (usuarioLogueado.getRole().getName().equals("ADMIN")
					&& !usuarioLogueado.getUnidadPosgrado().equals(usuario.getUnidadPosgrado())) {
				return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
			}
			return ResponseEntity.ok(usuario);
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
}
