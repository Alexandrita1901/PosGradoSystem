package FCA.Sistema.Web.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import FCA.Sistema.Web.Entity.Estudiantes;
import FCA.Sistema.Web.Entity.HistorialSemestre;
import FCA.Sistema.Web.Entity.Semestre;

public interface HistorialSemestreRepository extends JpaRepository<HistorialSemestre, Integer> {

    HistorialSemestre findByEstudianteAndSemestre(Estudiantes estudiante, Semestre semestreActual);

    List<HistorialSemestre> findByEstudianteId(Integer estudianteId);

    List<HistorialSemestre> findByEstudianteProgramaEstudioUnidadPosgradoId(Integer unidadPosgradoId);

    List<HistorialSemestre> findByEstudianteProgramaEstudioUnidadPosgradoIdAndEstudianteId(Integer unidadPosgradoId, Integer estudianteId);
}
