package ar.edu.unju.escmi.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "detalles_de_facturas")
public class DetalleFactura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "det_fa_cantidad", nullable = false)
    private int cantidad;

    @Column(name = "det_fa_subtotal", nullable = false)
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
    @JoinColumn(name = "factura_id")
    private Factura factura;

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    /* Relacion entre DetalleFactura y Producto */
    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    /* Metodos */
    @Override
    public String toString() {
        return "DetalleFactura #" + id +
                "\nCantidad: " + cantidad +
                "\nSubtotal: $" + subtotal +
                "\nProducto: " + (producto != null ? producto.getDescripcion() : "Sin producto") +
                "\nFactura ID: " + (factura != null ? factura.getId() : "Sin factura");
    }

    public void calcularSubtotal() {
        if (producto != null)
            this.subtotal = this.cantidad * producto.getPrecioUnitario();
        else
            this.subtotal = 0;
    }

}
