package FCA.Sistema.Web.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import FCA.Sistema.Web.DTO.PagoRequest;
import FCA.Sistema.Web.DTO.PagoResponse;
import FCA.Sistema.Web.Entity.User;
import FCA.Sistema.Web.Repository.UserRepository;
import FCA.Sistema.Web.Service.PagoService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService pagoService;
    private final UserRepository userRepository;

    @PostMapping("/crear")
    @PreAuthorize("hasAnyAuthority('SUPERADMIN', 'ADMIN', 'USER')")
    public ResponseEntity<String> registrarPago(@RequestBody PagoRequest request, Principal principal) {
        User usuarioLogueado = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));
        return pagoService.crearPago(request, usuarioLogueado);
    }

    //@GetMapping("/listar")
   // @PreAuthorize("hasAnyAuthority('SUPERADMIN', 'ADMIN', 'USER')")
    //public ResponseEntity<List<PagoResponse>> listarPagos(Principal principal) {
      //  User usuarioLogueado = userRepository.findByUsername(principal.getName())
                //.orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));
        //return pagoService.listarPagos(usuarioLogueado);
    //}


    @DeleteMapping("/eliminar/{id}")
    @PreAuthorize("hasAnyAuthority('SUPERADMIN', 'ADMIN')")
    public ResponseEntity<String> eliminarPago(@PathVariable Integer id, Principal principal) {
        User usuarioLogueado = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("Usuario logueado no encontrado"));
        return pagoService.eliminarPago(id, usuarioLogueado);
    }
}
