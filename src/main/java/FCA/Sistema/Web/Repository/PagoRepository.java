package FCA.Sistema.Web.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import FCA.Sistema.Web.Entity.Pago;

public interface PagoRepository extends JpaRepository<Pago, Integer> {

	List<Pago> findByEstudianteProgramaEstudioUnidadPosgradoId(Integer id);

}
