package FCA.Sistema.Web.DTO;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstudianteRequest {

    private String nombres;
    private String apellidos;
    private String email;
    private String documentoIdentidad;
    private String telefono;
    private String direccion;
    private String ultimoGradoAcademico;
    private String universidadProcedencia;
    private String entidadLaboralDependiente;
    private String cargoLaboralDependiente;
    private String direccionLaboralDependiente;
    private String telefonoLaboralDependiente;
    private String sectorLaboralIndependiente;
    private String cargoLaboralIndependiente;
    private LocalDate fechaInscripcion;
    private String urlFotografia;
    @Builder.Default
    private Boolean activo = true;
    private Integer programaEstudioId;  
    private Integer semestreIngresoId;  
    private Integer semestreActualId; 
    private Integer totalSemestres;    
}

