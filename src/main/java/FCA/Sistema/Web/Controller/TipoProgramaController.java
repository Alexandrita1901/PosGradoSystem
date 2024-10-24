package FCA.Sistema.Web.Controller;

import java.security.Principal;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import FCA.Sistema.Web.DTO.TipoProgramaRequest;
import FCA.Sistema.Web.DTO.TipoProgramaResponse;
import FCA.Sistema.Web.Entity.User;
import FCA.Sistema.Web.Repository.UserRepository;
import FCA.Sistema.Web.Service.PermisoService;
import FCA.Sistema.Web.Service.TipoProgramaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tipoprograma")
@RequiredArgsConstructor
public class TipoProgramaController {

    private final TipoProgramaService tipoProgramaService;
	private final UserRepository userRepository;
	private final PermisoService permisoService;

    @PostMapping("/crear")
    @PreAuthorize("hasAnyAuthority('SUPERADMIN')")
    public ResponseEntity<String> crearTipoPrograma(@RequestBody TipoProgramaRequest request, Principal principal) {
    	User usuarioLogueado = userRepository.findByUsername(principal.getName())
				.orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

		if (!permisoService.tienePermisoCrear(usuarioLogueado)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para crear.");
		}

        return tipoProgramaService.crearTipoPrograma(request);
    }

    @GetMapping("/listar")
    @PreAuthorize("hasAnyAuthority('SUPERADMIN')")
    public ResponseEntity<List<TipoProgramaResponse>> listarTiposPrograma(Principal principal) {
    	User usuarioLogueado = userRepository.findByUsername(principal.getName())
				.orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

		if (!permisoService.tienePermisoListar(usuarioLogueado)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
		}
        return tipoProgramaService.listarTiposPrograma();
    }

    @GetMapping("/listar/{id}")
    @PreAuthorize("hasAnyAuthority('SUPERADMIN')")
    public ResponseEntity<TipoProgramaResponse> obtenerTipoProgramaPorId(@PathVariable Integer id,  Principal principal) {
    	User usuarioLogueado = userRepository.findByUsername(principal.getName())
				.orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

		if (!permisoService.tienePermisoListar(usuarioLogueado)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
		}
        return tipoProgramaService.obtenerTipoProgramaPorId(id);
    }

    @PutMapping("/actualizar/{id}")
    @PreAuthorize("hasAnyAuthority('SUPERADMIN')")
    public ResponseEntity<String> actualizarTipoPrograma(@PathVariable Integer id, @RequestBody TipoProgramaRequest request,  Principal principal) {
    	User usuarioLogueado = userRepository.findByUsername(principal.getName())
				.orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

		if (!permisoService.tienePermisoEditar(usuarioLogueado)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para editar.");
		}

    	return tipoProgramaService.actualizarTipoPrograma(id, request);
    }

    @DeleteMapping("/eliminar/{id}")
    @PreAuthorize("hasAnyAuthority('SUPERADMIN')")
    public ResponseEntity<String> eliminarTipoPrograma(@PathVariable Integer id,  Principal principal) {
    	User usuarioLogueado = userRepository.findByUsername(principal.getName())
				.orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

		if (!permisoService.tienePermisoEliminar(usuarioLogueado)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para eliminar.");
		}
        return tipoProgramaService.eliminarTipoPrograma(id);
    }
}
