package FCA.Sistema.Web.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import FCA.Sistema.Web.DTO.UnidadPosgradoRequest;
import FCA.Sistema.Web.DTO.UnidadPosgradoResponse;
import FCA.Sistema.Web.Entity.User;
import FCA.Sistema.Web.Repository.UserRepository;
import FCA.Sistema.Web.Service.UnidadPosgradoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/unidades")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200"})
public class UnidadPosgradoController {

	private final UnidadPosgradoService unidadPosgradoService;
	private final UserRepository userRepository;

	@PostMapping("/crear")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERADMIN')")
	public ResponseEntity<String> crearUnidad(@RequestBody UnidadPosgradoRequest request, Principal principal) {
		User usuarioLogueado = userRepository.findByUsername(principal.getName())
				.orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

		return unidadPosgradoService.crearUnidadPosgrado(request, usuarioLogueado);
	}

	@GetMapping("/listar")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERADMIN')")
	public ResponseEntity<List<UnidadPosgradoResponse>> listarUnidades(Principal principal) {
		User usuarioLogueado = userRepository.findByUsername(principal.getName())
				.orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

		return unidadPosgradoService.listarUnidades(usuarioLogueado);
	}

	@GetMapping("/listar/{id}")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERADMIN')")
	public ResponseEntity<UnidadPosgradoResponse> obtenerUnidadPorId(@PathVariable Integer id, Principal principal) {
		User usuarioLogueado = userRepository.findByUsername(principal.getName())
				.orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

		return unidadPosgradoService.obtenerUnidadPorId(id, usuarioLogueado);
	}
}
