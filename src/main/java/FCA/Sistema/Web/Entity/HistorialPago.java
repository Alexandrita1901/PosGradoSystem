package FCA.Sistema.Web.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "historial_pagos")
public class HistorialPago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "estudiante_id", nullable = false)
    private Estudiantes estudiante;  // Estudiante que realiza el pago

    @ManyToOne
    @JoinColumn(name = "semestre_id", nullable = false)
    private Semestre semestre;  // El semestre asociado al pago

    @Column(nullable = false)
    private Double monto;  // Monto del pago realizado

    @Column(nullable = false)
    private String estadoPago;  // Estado del pago (pagado o pendiente)

    @Column(nullable = false)
    private LocalDate fechaPago;  // Fecha de realización del pago

    @Column(nullable = false)
    private String urlReciboPago;  // URL del recibo de pago (voucher, documento)

    @Column(nullable = true, columnDefinition = "TEXT")
    private String observacion;  // Cualquier observación relacionada con el pago
}
