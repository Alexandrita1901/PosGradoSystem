package FCA.Sistema.Web.Controller;

import java.security.Principal;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import FCA.Sistema.Web.DTO.UnidadPosgradoRequest;
import FCA.Sistema.Web.Entity.UnidadPosgrado;
import FCA.Sistema.Web.Entity.User;
import FCA.Sistema.Web.Repository.UserRepository;
import FCA.Sistema.Web.Service.PermisoService;
import FCA.Sistema.Web.Service.UnidadPosgradoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/unidades")
@RequiredArgsConstructor
public class UnidadPosgradoController {

	private final UserRepository userRepository;
	private final PermisoService permisoService;
	private final UnidadPosgradoService unidadPosgradoService;

	@PostMapping("/crear")
	@PreAuthorize("hasAnyAuthority('SUPERADMIN')")
	public ResponseEntity<String> crearUnidadPosgrado(@RequestBody UnidadPosgradoRequest request, Principal principal) {
		User usuarioLogueado = userRepository.findByUsername(principal.getName())
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

		if (!permisoService.tienePermisoCrear(usuarioLogueado)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para crear.");
		}

		return unidadPosgradoService.crearUnidadPosgrado(request, usuarioLogueado);
	}

	@GetMapping("/listar")
	@PreAuthorize("hasAnyAuthority('SUPERADMIN')")
	public ResponseEntity<List<UnidadPosgrado>> listarUnidades(Principal principal) {
		User usuarioLogueado = userRepository.findByUsername(principal.getName())
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

		if (!permisoService.tienePermisoListar(usuarioLogueado)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
		}

		return unidadPosgradoService.listarUnidades(usuarioLogueado);
	}

	@GetMapping("/listar/{id}")
	@PreAuthorize("hasAnyAuthority('SUPERADMIN')")
	public ResponseEntity<UnidadPosgrado> obtenerUnidadPorId(@PathVariable Integer id, Principal principal) {
		User usuarioLogueado = userRepository.findByUsername(principal.getName())
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

		if (!permisoService.tienePermisoListar(usuarioLogueado)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
		}

		return unidadPosgradoService.obtenerUnidadPorId(id, usuarioLogueado);
	}

	@PutMapping("/actualizar/{id}")
	@PreAuthorize("hasAnyAuthority('SUPERADMIN')")
	public ResponseEntity<String> actualizarUnidad(@PathVariable Integer id, @RequestBody UnidadPosgradoRequest request,
			Principal principal) {
		User usuarioLogueado = userRepository.findByUsername(principal.getName())
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

		if (!permisoService.tienePermisoEditar(usuarioLogueado)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para editar.");
		}

		return unidadPosgradoService.actualizarUnidad(id, request, usuarioLogueado);
	}

	@DeleteMapping("/eliminar/{id}")
	@PreAuthorize("hasAnyAuthority('SUPERADMIN')")
	public ResponseEntity<String> eliminarUnidad(@PathVariable Integer id, Principal principal) {
		User usuarioLogueado = userRepository.findByUsername(principal.getName())
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

		if (!permisoService.tienePermisoEliminar(usuarioLogueado)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para eliminar.");
		}

		return unidadPosgradoService.eliminarUnidad(id, usuarioLogueado);
	}
}
