package FCA.Sistema.Web.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "programa_estudio")
public class ProgramaEstudio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nombre;
    
    @Column(nullable = false)
    private Integer creditos;
    
    @Column(nullable = false)
    private Integer duracionMeses;
    
    @Column(nullable = false)
    private Integer inversion;
    
    @Column(nullable = false)
    private String modalidad;
    
    @Column(nullable = false)
    private Boolean activo;
    
    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(nullable = true)
    private String urlPlanEstudio;
    
    @ManyToOne
    @JoinColumn(name = "id_unidad_progrado", nullable = false)
    private UnidadPosgrado unidadPosgrado;

    @ManyToOne
    @JoinColumn(name = "id_tipo_programa", nullable = false)
    private TipoPrograma tipoPrograma;
}
