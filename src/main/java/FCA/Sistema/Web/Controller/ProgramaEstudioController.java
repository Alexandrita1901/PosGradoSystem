package FCA.Sistema.Web.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import FCA.Sistema.Web.DTO.ProgramaEstudioRequest;
import FCA.Sistema.Web.DTO.ProgramaEstudioResponse;
import FCA.Sistema.Web.Entity.User;
import FCA.Sistema.Web.Repository.UserRepository;
import FCA.Sistema.Web.Service.ProgramaEstudioService;
import FCA.Sistema.Web.Service.PermisoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/programas")
@RequiredArgsConstructor
public class ProgramaEstudioController {

    private final ProgramaEstudioService programaEstudioService;
    private final UserRepository userRepository;
    private final PermisoService permisoService;

    // Crear un nuevo programa de estudio
    @PostMapping("/crear")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<String> crearProgramaEstudio(@RequestBody ProgramaEstudioRequest request, Principal principal) {
        User usuarioLogueado = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

        if (!permisoService.tienePermisoCrear(usuarioLogueado)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para crear.");
        }

        return programaEstudioService.crearProgramaEstudio(request, usuarioLogueado);
    }

    // Listar todos los programas de estudio
    @GetMapping("/listar")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<List<ProgramaEstudioResponse>> listarProgramasEstudio(Principal principal) {
        User usuarioLogueado = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

        if (!permisoService.tienePermisoListar(usuarioLogueado)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        return programaEstudioService.listarProgramasEstudio(usuarioLogueado);
    }

    // Obtener un programa de estudio por ID
    @GetMapping("/listar/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<ProgramaEstudioResponse> obtenerProgramaEstudioPorId(@PathVariable Integer id, Principal principal) {
        User usuarioLogueado = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

        if (!permisoService.tienePermisoListar(usuarioLogueado)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        return programaEstudioService.obtenerProgramaEstudioPorId(id);
    }

    // Actualizar un programa de estudio.
    @PutMapping("/actualizar/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<String> actualizarProgramaEstudio(@PathVariable Integer id,
                                                            @RequestBody ProgramaEstudioRequest request, Principal principal) {
        User usuarioLogueado = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

        if (!permisoService.tienePermisoEditar(usuarioLogueado)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para editar.");
        }

        return programaEstudioService.actualizarProgramaEstudio(id, request, usuarioLogueado);
    }

    // Eliminar un programa de estudio
    @DeleteMapping("/eliminar/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<String> eliminarProgramaEstudio(@PathVariable Integer id, Principal principal) {
        User usuarioLogueado = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

        if (!permisoService.tienePermisoEliminar(usuarioLogueado)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para eliminar.");
        }

        return programaEstudioService.eliminarProgramaEstudio(id);
    }
}
