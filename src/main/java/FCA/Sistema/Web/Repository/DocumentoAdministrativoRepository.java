package FCA.Sistema.Web.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import FCA.Sistema.Web.Entity.DocumentoAdministrativos;
import FCA.Sistema.Web.Entity.UnidadPosgrado;

public interface DocumentoAdministrativoRepository extends JpaRepository<DocumentoAdministrativos, Integer> {

	List<DocumentoAdministrativos> findByUnidadPosgrado(UnidadPosgrado unidadPosgrado);

}
