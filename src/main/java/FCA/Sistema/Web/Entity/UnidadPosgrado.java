package FCA.Sistema.Web.Entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "unidad_posgrado")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnidadPosgrado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String nombre;
    
    @OneToMany(mappedBy = "unidadPosgrado")
    private List<ProgramaEstudio> programasEstudio;
}

