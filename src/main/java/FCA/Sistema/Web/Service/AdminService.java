package FCA.Sistema.Web.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import FCA.Sistema.Web.DTO.AdminRequest;
import FCA.Sistema.Web.Entity.Permiso;
import FCA.Sistema.Web.Entity.Role;
import FCA.Sistema.Web.Entity.UnidadPosgrado;
import FCA.Sistema.Web.Entity.User;
import FCA.Sistema.Web.Repository.PermisoRepository;
import FCA.Sistema.Web.Repository.RoleRepository;
import FCA.Sistema.Web.Repository.UnidadPosgradoRepository;
import FCA.Sistema.Web.Repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PermisoRepository permisoRepository;
	private final UnidadPosgradoRepository unidadPosgradoRepository;
	private final PasswordEncoder passwordEncoder;

	public ResponseEntity<String> crearAdminParaUnidad(AdminRequest request, User usuarioLogueado) {
		UnidadPosgrado unidad = unidadPosgradoRepository.findById(request.getUnidadPosgradoId())
				.orElseThrow(() -> new RuntimeException("Unidad de Posgrado no encontrada"));

		Role adminRole = roleRepository.findByName("ADMIN")
				.orElseThrow(() -> new RuntimeException("Rol ADMIN no encontrado"));

		User admin = User.builder().username(request.getUsername())
				.password(passwordEncoder.encode(request.getPassword())).nombres(request.getNombres())
				.apellidos(request.getApellidos()).correo(request.getCorreo()).contacto(request.getContacto())
				.dni(request.getDni()).unidadPosgrado(unidad).role(adminRole).build();

		userRepository.save(admin);

		Permiso permisos = Permiso.builder().user(admin).permisoCrear(request.isPermisoCrear())
				.permisoEditar(request.isPermisoEditar()).permisoEliminar(request.isPermisoEliminar())
				.permisoListar(request.isPermisoListar()).build();

		permisoRepository.save(permisos);

		return ResponseEntity.ok("Administrador asignado a la unidad exitosamente.");
	}

	public ResponseEntity<List<User>> listarAdminsPorUnidad(Integer unidadId, User usuarioLogueado) {
		List<User> admins = userRepository.findByUnidadPosgradoIdAndRoleName(unidadId, "ADMIN");
		return ResponseEntity.ok(admins);
	}

	public ResponseEntity<List<User>> listarAdmins(User usuarioLogueado) {
		List<User> admins = userRepository.findByRoleName("ADMIN");
		return ResponseEntity.ok(admins);
	}

	public ResponseEntity<User> listarAdminPorId(Integer adminId, User usuarioLogueado) {
		Optional<User> adminOpt = userRepository.findByIdAndRoleName(adminId, "ADMIN");

		if (adminOpt.isPresent()) {
			return ResponseEntity.ok(adminOpt.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	public ResponseEntity<String> actualizarAdmin(Integer adminId, AdminRequest request, User usuarioLogueado) {
		User admin = userRepository.findById(adminId)
				.orElseThrow(() -> new RuntimeException("Administrador no encontrado"));

		admin.setNombres(request.getNombres());
		admin.setApellidos(request.getApellidos());
		admin.setCorreo(request.getCorreo());
		admin.setContacto(request.getContacto());
		admin.setDni(request.getDni());

		UnidadPosgrado unidad = unidadPosgradoRepository.findById(request.getUnidadPosgradoId())
				.orElseThrow(() -> new RuntimeException("Unidad de Posgrado no encontrada"));

		admin.setUnidadPosgrado(unidad);

		userRepository.save(admin);
		return ResponseEntity.ok("Administrador actualizado exitosamente.");
	}

	public ResponseEntity<String> eliminarAdmin(Integer adminId, User usuarioLogueado) {
		User admin = userRepository.findById(adminId)
				.orElseThrow(() -> new RuntimeException("Administrador no encontrado"));

		userRepository.delete(admin);
		return ResponseEntity.ok("Administrador eliminado exitosamente.");
	}
}
