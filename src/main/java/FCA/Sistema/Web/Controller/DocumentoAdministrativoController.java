package FCA.Sistema.Web.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import FCA.Sistema.Web.DTO.DocumentoAdministrativoRequest;
import FCA.Sistema.Web.DTO.DocumentoAdministrativoResponse;
import FCA.Sistema.Web.Entity.User;
import FCA.Sistema.Web.Repository.UserRepository;
import FCA.Sistema.Web.Service.DocumentoAdministrativoService;
import FCA.Sistema.Web.Service.PermisoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/documentos")
@RequiredArgsConstructor
public class DocumentoAdministrativoController {

    private final DocumentoAdministrativoService documentoAdministrativoService;
    private final UserRepository userRepository;
    private final PermisoService permisoService;

    @PostMapping("/crear")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'SUPERADMIN')")
    public ResponseEntity<String> crearDocumentoAdministrativo(@RequestBody DocumentoAdministrativoRequest request, Principal principal) {
        User usuarioLogueado = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

        if (!permisoService.tienePermisoCrear(usuarioLogueado)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para crear.");
        }

        return documentoAdministrativoService.crearDocumentoAdministrativo(request, usuarioLogueado);
    }

    @GetMapping("/listar")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'SUPERADMIN')")
    public ResponseEntity<List<DocumentoAdministrativoResponse>> listarDocumentosAdministrativos(Principal principal) {
        User usuarioLogueado = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

        if (!permisoService.tienePermisoListar(usuarioLogueado)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        return documentoAdministrativoService.listarDocumentosAdministrativos(usuarioLogueado);
    }

    @GetMapping("/listar/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'SUPERADMIN')")
    public ResponseEntity<DocumentoAdministrativoResponse> obtenerDocumentoAdministrativoPorId(@PathVariable Integer id, Principal principal) {
        User usuarioLogueado = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

        if (!permisoService.tienePermisoListar(usuarioLogueado)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }

        return documentoAdministrativoService.obtenerDocumentoPorId(id, usuarioLogueado);
    }

    @PutMapping("/actualizar/{id}")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN', 'SUPERADMIN')")
    public ResponseEntity<String> actualizarDocumentoAdministrativo(@PathVariable Integer id,
                                                                    @RequestBody DocumentoAdministrativoRequest request, Principal principal) {
        User usuarioLogueado = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

        if (!permisoService.tienePermisoEditar(usuarioLogueado)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para editar.");
        }

        return documentoAdministrativoService.actualizarDocumentoAdministrativo(id, request, usuarioLogueado);
    }

    @DeleteMapping("/eliminar/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<String> eliminarDocumentoAdministrativo(@PathVariable Integer id, Principal principal) {
        User usuarioLogueado = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));

        if (!permisoService.tienePermisoEliminar(usuarioLogueado)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("No tienes permiso para eliminar.");
        }

        return documentoAdministrativoService.eliminarDocumentoAdministrativo(id, usuarioLogueado);
    }
}

