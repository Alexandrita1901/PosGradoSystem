package FCA.Sistema.Web.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import FCA.Sistema.Web.DTO.EstudianteRequest;
import FCA.Sistema.Web.DTO.EstudianteResponse;
import FCA.Sistema.Web.Entity.User;
import FCA.Sistema.Web.Repository.UserRepository;
import FCA.Sistema.Web.Service.EstudiantesService;
import FCA.Sistema.Web.Service.PermisoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/estudiantes")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200"})
public class EstudianteController {

    private final EstudiantesService estudianteService;
    private final UserRepository userRepository;
    private final PermisoService permisoService;

    @PostMapping("/crear")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'SUPERADMIN')")
    public ResponseEntity<String> crearEstudiante(@RequestBody EstudianteRequest request, Principal principal) {
        User usuarioLogueado = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

        if (!permisoService.tienePermisoCrear(usuarioLogueado)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para crear.");
        }

        return estudianteService.crearEstudiante(request, usuarioLogueado);
    }

    @GetMapping("/listar")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'SUPERADMIN')")
    public ResponseEntity<List<EstudianteResponse>> listarEstudiantes(Principal principal) {
        User usuarioLogueado = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

        if (!permisoService.tienePermisoListar(usuarioLogueado)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        return estudianteService.listarEstudiantes(usuarioLogueado);
    }

    @GetMapping("/listar/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'SUPERADMIN')")
    public ResponseEntity<EstudianteResponse> obtenerEstudiantePorId(@PathVariable Integer id, Principal principal) {
        User usuarioLogueado = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

        if (!permisoService.tienePermisoListar(usuarioLogueado)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        return estudianteService.obtenerEstudiantePorId(id, usuarioLogueado);
    }

    @PutMapping("/actualizar/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<String> actualizarEstudiante(@PathVariable Integer id,
                                                       @RequestBody EstudianteRequest request, Principal principal) {
        User usuarioLogueado = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

        if (!permisoService.tienePermisoEditar(usuarioLogueado)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para editar.");
        }

        return estudianteService.actualizarEstudiante(id, request, usuarioLogueado);
    }
    
    @PutMapping("/actualizar-semestre/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'SUPERADMIN')")
    public ResponseEntity<String> actualizarSemestreEstudiante(@PathVariable Integer id,
                                                               @RequestParam Integer nuevoSemestreId, 
                                                               Principal principal) {
        User usuarioLogueado = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

        // Permiso para actualizar el semestre (USER, ADMIN, SUPERADMIN)
        if (!permisoService.tienePermisoEditar(usuarioLogueado)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para editar el semestre.");
        }

        return estudianteService.actualizarSemestreEstudiante(id, nuevoSemestreId, usuarioLogueado);
    }
    

    @DeleteMapping("/eliminar/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<String> eliminarEstudiante(@PathVariable Integer id, Principal principal) {
        User usuarioLogueado = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

        if (!permisoService.tienePermisoEliminar(usuarioLogueado)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para eliminar.");
        }

        return estudianteService.eliminarEstudiante(id, usuarioLogueado);
    }
}

