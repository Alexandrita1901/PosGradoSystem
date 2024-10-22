package FCA.Sistema.Web.Controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import FCA.Sistema.Web.DTO.TipoProgramaRequest;
import FCA.Sistema.Web.Entity.TipoPrograma;
import FCA.Sistema.Web.Service.TipoProgramaService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tipoprograma")
@RequiredArgsConstructor
public class TipoProgramaController {

    private final TipoProgramaService tipoProgramaService;

    @PostMapping("/crear")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<String> crearTipoPrograma(@RequestBody TipoProgramaRequest request) {
        return tipoProgramaService.crearTipoPrograma(request);
    }

    @GetMapping("/listar")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<List<TipoPrograma>> listarTiposPrograma() {
        return tipoProgramaService.listarTiposPrograma();
    }

    @GetMapping("/listar/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<TipoPrograma> obtenerTipoProgramaPorId(@PathVariable Integer id) {
        return tipoProgramaService.obtenerTipoProgramaPorId(id);
    }

 
    @PutMapping("/actualizar/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<String> actualizarTipoPrograma(@PathVariable Integer id, @RequestBody TipoProgramaRequest request) {
        return tipoProgramaService.actualizarTipoPrograma(id, request);
    }

    @DeleteMapping("/eliminar/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'SUPERADMIN')")
    public ResponseEntity<String> eliminarTipoPrograma(@PathVariable Integer id) {
        return tipoProgramaService.eliminarTipoPrograma(id);
    }
}


