package FCA.Sistema.Web.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import FCA.Sistema.Web.Entity.ProgramaEstudio;
import FCA.Sistema.Web.Entity.UnidadPosgrado;

public interface ProgramaEstudioRepository extends JpaRepository<ProgramaEstudio, Integer> {
	List<ProgramaEstudio> findByUnidadPosgradoId(Integer unidadPosgradoId);
	 List<ProgramaEstudio> findByTipoProgramaId(Integer tipoProgramaId);
	List<ProgramaEstudio> findByUnidadPosgrado(UnidadPosgrado unidadPosgrado);
	

}
