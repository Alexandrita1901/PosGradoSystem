package FCA.Sistema.Web.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import FCA.Sistema.Web.Entity.DocumentoEstudiante;
import FCA.Sistema.Web.Entity.UnidadPosgrado;

public interface DocumentoEstudianteRepository extends JpaRepository<DocumentoEstudiante, Integer> {
    List<DocumentoEstudiante> findByEstudianteProgramaEstudioUnidadPosgrado(UnidadPosgrado unidadPosgrado);
}
