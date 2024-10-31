package FCA.Sistema.Web.Controller;

import java.security.Principal;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import FCA.Sistema.Web.DTO.TipoDocumentoRequest;
import FCA.Sistema.Web.DTO.TipoDocumentoResponse;
import FCA.Sistema.Web.Entity.User;
import FCA.Sistema.Web.Repository.UserRepository;
import FCA.Sistema.Web.Service.PermisoService;
import FCA.Sistema.Web.Service.TipoDocumentoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tipodocumento")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:4200"})
public class TipoDocumentoController {

    private final TipoDocumentoService tipoDocumentoService;
    private final UserRepository userRepository;
    private final PermisoService permisoService;

    @PostMapping("/crear")
    @PreAuthorize("hasAnyAuthority('SUPERADMIN')")
    public ResponseEntity<String> crearTipoDocumento(@RequestBody TipoDocumentoRequest request, Principal principal) {
        User usuarioLogueado = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

        if (!permisoService.tienePermisoCrear(usuarioLogueado)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para crear.");
        }

        return tipoDocumentoService.crearTipoDocumento(request);
    }

    @GetMapping("/listar")
    @PreAuthorize("hasAnyAuthority('SUPERADMIN')")
    public ResponseEntity<List<TipoDocumentoResponse>> listarTiposDocumento(Principal principal) {
        User usuarioLogueado = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

        if (!permisoService.tienePermisoListar(usuarioLogueado)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        return tipoDocumentoService.listarTiposDocumento();
    }

    @GetMapping("/listar/{id}")
    @PreAuthorize("hasAnyAuthority('SUPERADMIN')")
    public ResponseEntity<TipoDocumentoResponse> obtenerTipoDocumentoPorId(@PathVariable Integer id, Principal principal) {
        User usuarioLogueado = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

        if (!permisoService.tienePermisoListar(usuarioLogueado)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        return tipoDocumentoService.obtenerTipoDocumentoPorId(id);
    }

    @PutMapping("/actualizar/{id}")
    @PreAuthorize("hasAnyAuthority('SUPERADMIN')")
    public ResponseEntity<String> actualizarTipoDocumento(@PathVariable Integer id, @RequestBody TipoDocumentoRequest request, Principal principal) {
        User usuarioLogueado = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

        if (!permisoService.tienePermisoEditar(usuarioLogueado)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para editar.");
        }

        return tipoDocumentoService.actualizarTipoDocumento(id, request);
    }

    @DeleteMapping("/eliminar/{id}")
    @PreAuthorize("hasAnyAuthority('SUPERADMIN')")
    public ResponseEntity<String> eliminarTipoDocumento(@PathVariable Integer id, Principal principal) {
        User usuarioLogueado = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

        if (!permisoService.tienePermisoEliminar(usuarioLogueado)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para eliminar.");
        }

        return tipoDocumentoService.eliminarTipoDocumento(id);
    }
}
