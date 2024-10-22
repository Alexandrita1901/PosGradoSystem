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
@Table(name = "tipo_programa")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TipoPrograma {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false, unique = true)
	private String nombreprograma;
	
    @OneToMany(mappedBy = "tipoPrograma")
    private List<ProgramaEstudio> programasEstudio;
}
