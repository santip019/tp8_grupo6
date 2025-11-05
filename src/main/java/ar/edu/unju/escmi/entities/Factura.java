package ar.edu.unju.escmi.entities;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.JoinColumn;


@Entity
@Table(name = "facturas")
public class Factura {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @Column (name="fa_facha_de_emision", nullable = false)
    private LocalDate fecha;

	@Column(name="fa_domicilio_cliente")
	private String domicilio;

	@Column(name="fa_total", nullable = false, precision = 10, scale = 2)
	private double total;

	@Transient
	private boolean estado;

    /* Constructor por defecto */
    public Factura() {
    }

    /* Constructor parametrizado */
    public Factura(Long id, LocalDate fecha, String domicilio, double total, boolean estado) {
        this.id = id;
        this.fecha = fecha;
        this.domicilio = domicilio;
        this.total = total;
        this.estado = estado;
    }

    /* Getters y Setters */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    /* Relacion entre Cliente y Factura */
    @ManyToOne
    @JoinColumn(name="cliente_id")
    private Cliente cliente;

    /* Relacion entre DetalleFactura y Factura */
    @OneToMany(mappedBy = "factura")
    private List<DetalleFactura> detallesFactura;

    /* Metodos */
    @Override
    public String toString() {
        return "Factura [id=" + id + ", fecha=" + fecha + ", domicilio=" + domicilio + ", total=" + total + ", estado="
                + estado + ", cliente=" + cliente + ", detallesFactura=" + detallesFactura + "]";
    }

    public void calcularTotal() {
        for (DetalleFactura detalle : detallesFactura) {
            if (detalle != null) {
                this.total += detalle.getSubtotal();
            }
        }
    }
    
}
