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
@Table(name = "documentos_administrativos")
public class DocumentoAdministrativos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "tipo_documento_id", nullable = false)
    private TipoDocumento tipoDocumento;  // Relación con TipoDocumento

    @ManyToOne
    @JoinColumn(name = "unidad_id", nullable = false)
    private UnidadPosgrado unidadPosgrado;  // Relación con la unidad administrativa

    @Column(nullable = false)
    private String titulo;  // Título del documento administrativo

    @Column(nullable = false)
    private String urlDocumento;  // URL o ubicación del documento subido

    @Column(nullable = false)
    private Boolean validado;  // Estado de validación del documento
}

