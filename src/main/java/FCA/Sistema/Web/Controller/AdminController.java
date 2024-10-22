package FCA.Sistema.Web.Controller;

import java.security.Principal;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import FCA.Sistema.Web.DTO.AdminRequest;
import FCA.Sistema.Web.Entity.User;
import FCA.Sistema.Web.Repository.UserRepository;
import FCA.Sistema.Web.Service.AdminService;
import FCA.Sistema.Web.Service.PermisoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admins")
@RequiredArgsConstructor
public class AdminController {

	private final UserRepository userRepository;
	private final PermisoService permisoService;
	private final AdminService adminService;

	@PostMapping("/crear")
	@PreAuthorize("hasAuthority('SUPERADMIN')")
	public ResponseEntity<String> crearAdminParaUnidad(@RequestBody AdminRequest request, Principal principal) {

		User usuarioLogueado = userRepository.findByUsername(principal.getName())
				.orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

		if (!permisoService.tienePermisoCrear(usuarioLogueado)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para crear.");
		}

		return adminService.crearAdminParaUnidad(request, usuarioLogueado);
	}

	@GetMapping("/listar-Unidad/{unidadId}")
	@PreAuthorize("hasAuthority('SUPERADMIN')")
	public ResponseEntity<List<User>> listarAdminsPorUnidad(@PathVariable Integer unidadId, Principal principal) {

		User usuarioLogueado = userRepository.findByUsername(principal.getName())
				.orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

		if (!permisoService.tienePermisoListar(usuarioLogueado)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
		}

		return adminService.listarAdminsPorUnidad(unidadId, usuarioLogueado);
	}

	@GetMapping("/listar")
	@PreAuthorize("hasAuthority('SUPERADMIN')")
	public ResponseEntity<List<User>> listarAdmins(Principal principal) {

		User usuarioLogueado = userRepository.findByUsername(principal.getName())
				.orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));
		if (!permisoService.tienePermisoListar(usuarioLogueado)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
		}

		return adminService.listarAdmins(usuarioLogueado);
	}

	@GetMapping("/listar-Admin/{adminId}")
	@PreAuthorize("hasAuthority('SUPERADMIN')")
	public ResponseEntity<User> listarAdminPorId(@PathVariable Integer adminId, Principal principal) {

		User usuarioLogueado = userRepository.findByUsername(principal.getName())
				.orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

		if (!permisoService.tienePermisoListar(usuarioLogueado)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
		}

		return adminService.listarAdminPorId(adminId, usuarioLogueado);
	}

	@PutMapping("/actualizar/{adminId}")
	@PreAuthorize("hasAuthority('SUPERADMIN')")
	public ResponseEntity<String> actualizarAdmin(@PathVariable Integer adminId, @RequestBody AdminRequest request,
			Principal principal) {

		User usuarioLogueado = userRepository.findByUsername(principal.getName())
				.orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

		if (!permisoService.tienePermisoEditar(usuarioLogueado)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para editar.");
		}

		return adminService.actualizarAdmin(adminId, request, usuarioLogueado);
	}

	@DeleteMapping("/eliminar/{adminId}")
	@PreAuthorize("hasAuthority('SUPERADMIN')")
	public ResponseEntity<String> eliminarAdmin(@PathVariable Integer adminId, Principal principal) {
		User usuarioLogueado = userRepository.findByUsername(principal.getName())
				.orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

		if (!permisoService.tienePermisoEliminar(usuarioLogueado)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para eliminar.");
		}

		return adminService.eliminarAdmin(adminId, usuarioLogueado);
	}
}
