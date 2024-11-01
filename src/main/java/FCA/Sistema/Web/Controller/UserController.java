package FCA.Sistema.Web.Controller;

import java.security.Principal;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import FCA.Sistema.Web.DTO.UserRequest;
import FCA.Sistema.Web.DTO.UserResponse;
import FCA.Sistema.Web.Entity.User;
import FCA.Sistema.Web.Repository.UserRepository;
import FCA.Sistema.Web.Service.PermisoService;
import FCA.Sistema.Web.Service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200"})
public class UserController {

    private final UserRepository userRepository;
    private final PermisoService permisoService;
    private final UserService userService;

    @PostMapping("/crear")
	@PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERADMIN')")
	public ResponseEntity<String> crearUsuarioParaUnidad(@RequestBody UserRequest request, Principal principal) {
		User usuarioLogueado = userRepository.findByUsername(principal.getName())
				.orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

		if (!permisoService.tienePermisoCrear(usuarioLogueado)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para crear.");
		}

		return userService.crearUsuarioParaUnidad(request, usuarioLogueado);
	}
    @GetMapping("/listar")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<List<UserResponse>> listarUsuarios(Principal principal) {
        User usuarioLogueado = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

        if (!permisoService.tienePermisoListar(usuarioLogueado)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        return userService.listarUsuarios(usuarioLogueado);
    }

    @GetMapping("/listar/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<UserResponse> obtenerUsuarioPorId(@PathVariable Integer id, Principal principal) {
        User usuarioLogueado = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

        if (!permisoService.tienePermisoListar(usuarioLogueado)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        return userService.obtenerUsuarioPorId(id, usuarioLogueado);
    }

    @PutMapping("/actualizar/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<String> actualizarUsuario(@PathVariable Integer id, @RequestBody UserRequest request,
            Principal principal) {
        User usuarioLogueado = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

        if (!permisoService.tienePermisoEditar(usuarioLogueado)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para editar.");
        }

        return userService.actualizarUsuario(id, request, usuarioLogueado);
    }

    @DeleteMapping("/eliminar/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<String> eliminarUsuario(@PathVariable Integer id, Principal principal) {
        User usuarioLogueado = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

        if (!permisoService.tienePermisoEliminar(usuarioLogueado)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para eliminar.");
        }

        return userService.eliminarUsuario(id, usuarioLogueado);
    }
}
