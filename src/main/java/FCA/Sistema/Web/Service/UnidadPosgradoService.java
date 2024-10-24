package FCA.Sistema.Web.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import FCA.Sistema.Web.DTO.UnidadPosgradoRequest;
import FCA.Sistema.Web.DTO.UnidadPosgradoResponse;
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

	public ResponseEntity<List<UnidadPosgradoResponse>> listarUnidades(User usuarioLogueado) {
		List<UnidadPosgrado> unidades = unidadPosgradoRepository.findAll();

		// Convertir la lista de entidades a DTOs
		List<UnidadPosgradoResponse> unidadResponses = unidades.stream().map(unidad -> UnidadPosgradoResponse.builder()
			.id(unidad.getId())
			.nombre(unidad.getNombre())
			.build())
		.collect(Collectors.toList());

		return ResponseEntity.ok(unidadResponses);
	}

	public ResponseEntity<UnidadPosgradoResponse> obtenerUnidadPorId(Integer id, User usuarioLogueado) {
		Optional<UnidadPosgrado> unidadOpt = unidadPosgradoRepository.findById(id);
		if (unidadOpt.isPresent()) {
			UnidadPosgrado unidad = unidadOpt.get();

			// Convertir la entidad a DTO
			UnidadPosgradoResponse unidadResponse = UnidadPosgradoResponse.builder()
				.id(unidad.getId())
				.nombre(unidad.getNombre())
				.build();

			return ResponseEntity.ok(unidadResponse);
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

