package FCA.Sistema.Web.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import FCA.Sistema.Web.Entity.Estudiantes;
import FCA.Sistema.Web.Entity.UnidadPosgrado;

public interface EstudiantesRepository extends JpaRepository<Estudiantes, Integer>{

	List<Estudiantes> findByProgramaEstudioUnidadPosgrado(UnidadPosgrado unidadPosgrado);

}
