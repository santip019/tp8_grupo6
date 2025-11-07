package ar.edu.unju.escmi.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "productos")
public class Producto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "prod_descripcion", nullable = false, length = 100)
    private String descripcion;

    @Column(name = "prod_precio_unitario", nullable = false)
    private double precioUnitario;

    @Column(name="prod_estado", nullable = false)
    private boolean estado;

    /* Constructor por defecto */
    public Producto() {
    }

    /* Constructor parametrizado */
    public Producto(Long id, String descripcion, double precioUnitario, boolean estado) {
        this.id = id;
        this.descripcion = descripcion;
        this.precioUnitario = precioUnitario;
        this.estado = estado;
    }

    /* Getters y Setters */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    /* Metodos */
    @Override
    public String toString() {
        return "Producto [id=" + id + ", descripcion=" + descripcion + ", precioUnitario=" + precioUnitario
                + ", estado=" + estado + "]";
    }

}
