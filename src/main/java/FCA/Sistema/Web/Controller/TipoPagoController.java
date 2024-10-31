package FCA.Sistema.Web.Controller;

import java.security.Principal;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import FCA.Sistema.Web.DTO.TipoPagoRequest;
import FCA.Sistema.Web.DTO.TipoPagoResponse;
import FCA.Sistema.Web.Entity.User;
import FCA.Sistema.Web.Repository.UserRepository;
import FCA.Sistema.Web.Service.PermisoService;
import FCA.Sistema.Web.Service.TipoPagoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tipopago")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200"})
public class TipoPagoController {

    private final TipoPagoService tipoPagoService;
    private final UserRepository userRepository;
	private final PermisoService permisoService;

    @PostMapping("/crear")
    @PreAuthorize("hasAnyAuthority('SUPERADMIN')")
    public ResponseEntity<String> crearTipoPago(@RequestBody TipoPagoRequest request, Principal principal) {
    	User usuarioLogueado = userRepository.findByUsername(principal.getName())
				.orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

		if (!permisoService.tienePermisoCrear(usuarioLogueado)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para crear.");
		}
        return tipoPagoService.crearTipoPago(request);
    }

    @GetMapping("/listar")
    @PreAuthorize("hasAnyAuthority('SUPERADMIN')")
    public ResponseEntity<List<TipoPagoResponse>> listarTiposPago(Principal principal) {
    	User usuarioLogueado = userRepository.findByUsername(principal.getName())
				.orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

		if (!permisoService.tienePermisoListar(usuarioLogueado)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
		}
        return tipoPagoService.listarTiposPago();
    }

    @GetMapping("/listar/{id}")
    @PreAuthorize("hasAnyAuthority('SUPERADMIN')")
    public ResponseEntity<TipoPagoResponse> obtenerTipoPagoPorId(@PathVariable Integer id, Principal principal) {
    	User usuarioLogueado = userRepository.findByUsername(principal.getName())
				.orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

		if (!permisoService.tienePermisoListar(usuarioLogueado)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
		}
        return tipoPagoService.obtenerTipoPagoPorId(id);
    }

    @PutMapping("/actualizar/{id}")
    @PreAuthorize("hasAnyAuthority('SUPERADMIN')")
    public ResponseEntity<String> actualizarTipoPago(@PathVariable Integer id, @RequestBody TipoPagoRequest request, Principal principal) {
    	User usuarioLogueado = userRepository.findByUsername(principal.getName())
				.orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

		if (!permisoService.tienePermisoEditar(usuarioLogueado)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para editar.");
		}
    	return tipoPagoService.actualizarTipoPago(id, request);
    }

    @DeleteMapping("/eliminar/{id}")
    @PreAuthorize("hasAnyAuthority('SUPERADMIN')")
    public ResponseEntity<String> eliminarTipoPago(@PathVariable Integer id, Principal principal) {
    	User usuarioLogueado = userRepository.findByUsername(principal.getName())
				.orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

		if (!permisoService.tienePermisoEliminar(usuarioLogueado)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para eliminar.");
		}
        return tipoPagoService.eliminarTipoPago(id);
    }
}
