package FCA.Sistema.Web.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import FCA.Sistema.Web.DTO.SemestreRequest;
import FCA.Sistema.Web.Entity.Semestre;
import FCA.Sistema.Web.Entity.User;
import FCA.Sistema.Web.Repository.UserRepository;
import FCA.Sistema.Web.Service.PermisoService;
import FCA.Sistema.Web.Service.SemestreService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/semestres")
@RequiredArgsConstructor
public class SemestreController {

    private final SemestreService semestreService;
    private final UserRepository userRepository;
	private final PermisoService permisoService;

    @PostMapping("/crear")
    @PreAuthorize("hasAuthority('SUPERADMIN')")
    public ResponseEntity<String> crearSemestre(@RequestBody SemestreRequest request, Principal principal) {
    	User usuarioLogueado = userRepository.findByUsername(principal.getName())
				.orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

		if (!permisoService.tienePermisoCrear(usuarioLogueado)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para crear.");
		}
        return semestreService.crearSemestre(request);
    }

    @GetMapping("/listar")
    @PreAuthorize("hasAuthority('SUPERADMIN')")
    public ResponseEntity<List<Semestre>> listarSemestres(Principal principal) {
    	User usuarioLogueado = userRepository.findByUsername(principal.getName())
				.orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

		if (!permisoService.tienePermisoListar(usuarioLogueado)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
		}
        return semestreService.listarSemestres();
    }

    @GetMapping("/listar/{id}")
    @PreAuthorize("hasAuthority('SUPERADMIN')")
    public ResponseEntity<Semestre> obtenerSemestrePorId(@PathVariable Integer id, Principal principal) {
    	User usuarioLogueado = userRepository.findByUsername(principal.getName())
				.orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

		if (!permisoService.tienePermisoListar(usuarioLogueado)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
		}
        return semestreService.obtenerSemestrePorId(id);
    }

    @PutMapping("/actualizar/{id}")
    @PreAuthorize("hasAuthority('SUPERADMIN')")
    public ResponseEntity<String> actualizarSemestre(@PathVariable Integer id, @RequestBody SemestreRequest request, Principal principal) {
    	User usuarioLogueado = userRepository.findByUsername(principal.getName())
				.orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

		if (!permisoService.tienePermisoEditar(usuarioLogueado)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para editar.");
		}
    	return semestreService.actualizarSemestre(id, request);
    }

    @DeleteMapping("/eliminar/{id}")
    @PreAuthorize("hasAuthority('SUPERADMIN')")
    public ResponseEntity<String> eliminarSemestre(@PathVariable Integer id, Principal principal) {
    	User usuarioLogueado = userRepository.findByUsername(principal.getName())
				.orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

		if (!permisoService.tienePermisoEliminar(usuarioLogueado)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para eliminar.");
		}
    	return semestreService.eliminarSemestre(id);
    }
}
