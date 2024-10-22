package FCA.Sistema.Web.Service;

import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import FCA.Sistema.Web.DTO.UnidadPosgradoRequest;
import FCA.Sistema.Web.Entity.UnidadPosgrado;
import FCA.Sistema.Web.Entity.User;
import FCA.Sistema.Web.Repository.UnidadPosgradoRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UnidadPosgradoService {

	private final UnidadPosgradoRepository unidadPosgradoRepository;

	public ResponseEntity<String> crearUnidadPosgrado(UnidadPosgradoRequest request, User usuarioLogueado) {
		UnidadPosgrado nuevaUnidad = UnidadPosgrado.builder().nombre(request.getNombre()).build();

		unidadPosgradoRepository.save(nuevaUnidad);
		return ResponseEntity.ok("Unidad de Posgrado creada exitosamente.");
	}

	public ResponseEntity<List<UnidadPosgrado>> listarUnidades(User usuarioLogueado) {
		List<UnidadPosgrado> unidades = unidadPosgradoRepository.findAll();
		return ResponseEntity.ok(unidades);
	}

	public ResponseEntity<UnidadPosgrado> obtenerUnidadPorId(Integer id, User usuarioLogueado) {
		Optional<UnidadPosgrado> unidadOpt = unidadPosgradoRepository.findById(id);
		if (unidadOpt.isPresent()) {
			return ResponseEntity.ok(unidadOpt.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	public ResponseEntity<String> actualizarUnidad(Integer id, UnidadPosgradoRequest request, User usuarioLogueado) {
		Optional<UnidadPosgrado> unidadOpt = unidadPosgradoRepository.findById(id);
		if (unidadOpt.isPresent()) {
			UnidadPosgrado unidadExistente = unidadOpt.get();
			unidadExistente.setNombre(request.getNombre());
			unidadPosgradoRepository.save(unidadExistente);
			return ResponseEntity.ok("Unidad de Posgrado actualizada exitosamente.");
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	public ResponseEntity<String> eliminarUnidad(Integer id, User usuarioLogueado) {
		if (unidadPosgradoRepository.existsById(id)) {
			unidadPosgradoRepository.deleteById(id);
			return ResponseEntity.ok("Unidad de Posgrado eliminada exitosamente.");
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
