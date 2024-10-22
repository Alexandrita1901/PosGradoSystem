package FCA.Sistema.Web.Entity;

import java.time.LocalDate;
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

    @Column(nullable = false)
    private String titulo;  // Título o nombre del documento (ej. "Resolución 123/2024")

    @Column(nullable = false, columnDefinition = "TEXT")
    private String descripcion;  // Descripción del documento

    @Column(nullable = false)
    private String urlDocumento;  // URL o ubicación del archivo

    @Column(nullable = false)
    private LocalDate fechaCreacion;  // Fecha de creación del documento

    @ManyToOne
    @JoinColumn(name = "unidad_id", nullable = false)
    private UnidadPosgrado unidadPosgrado;  // Unidad de Posgrado a la que pertenece el documento
}

