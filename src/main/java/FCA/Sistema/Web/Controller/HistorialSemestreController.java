package FCA.Sistema.Web.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import FCA.Sistema.Web.DTO.HistorialSemestreResponse;
import FCA.Sistema.Web.Entity.User;
import FCA.Sistema.Web.Repository.UserRepository;
import FCA.Sistema.Web.Service.HistorialSemestreService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/historialSemestres")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200"})
public class HistorialSemestreController {

    private final HistorialSemestreService historialSemestreService;
    private final UserRepository userRepository;

    // Listar todos los historiales de semestre (ADMIN solo los de su unidad, SUPERADMIN todos)
    @GetMapping("/listar")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<List<HistorialSemestreResponse>> listarTodosHistoriales(Principal principal) {
        // Obtener el usuario logueado
        User usuarioLogueado = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

        // Llamar al servicio para listar los historiales
        return historialSemestreService.listarTodosHistoriales(usuarioLogueado);
    }

    // Listar historial de semestres de un estudiante específico (ADMIN solo los de su unidad, SUPERADMIN todos)
    @GetMapping("/listar/{estudianteId}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<List<HistorialSemestreResponse>> listarHistorialPorEstudiante(@PathVariable Integer estudianteId, Principal principal) {
        // Obtener el usuario logueado
        User usuarioLogueado = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

        // Llamar al servicio para listar el historial por estudiante
        return historialSemestreService.listarHistorialPorEstudiante(estudianteId, usuarioLogueado);
    }

    // Eliminar un historial de semestre (Solo ADMIN y SUPERADMIN)
    @DeleteMapping("/eliminar/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<String> eliminarHistorialSemestre(@PathVariable Integer id, Principal principal) {
        // Obtener el usuario logueado
        User usuarioLogueado = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

        // Llamar al servicio para eliminar el historial
        return historialSemestreService.eliminarHistorialSemestre(id, usuarioLogueado);
    }
}

