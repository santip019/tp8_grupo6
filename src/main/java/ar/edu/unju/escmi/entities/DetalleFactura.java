package ar.edu.unju.escmi.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "detalles_de_facturas")
public class DetalleFactura {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

    @Column (name="det_fa_cantidad", nullable = false)
    private int cantidad;

	@Column(name="det_fa_subtotal", nullable = false, precision = 10, scale = 2)
	private double subtotal;

    /* Constructor por defecto */
    public DetalleFactura() {
    }

    /* Constructor parametrizado */
    public DetalleFactura(Long id, int cantidad, double subtotal) {
        this.id = id;
        this.cantidad = cantidad;
        this.subtotal = subtotal;
    }

    /* Getters y Setters */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    /* Relacion entre DetalleFactura y Factura */
    @ManyToOne
    @JoinColumn(name="factura_id")
    private Factura factura;
	
    /* Relacion entre DetalleFactura y Producto */
    @OneToOne
    @JoinColumn(name="producto_id")
    private Producto producto;

    /* Metodos */
    @Override
    public String toString() {
        return "DetalleFactura [id=" + id + ", cantidad=" + cantidad 
                + ", subtotal=" + subtotal + ", factura=" + factura
                + ", producto=" + producto + "]";
    }

    public void calcularSubtotal () {
        if (producto != null)
            this.subtotal = this.cantidad * producto.getPrecioUnitario();
        else
            this.subtotal = 0;
    }
    
}
