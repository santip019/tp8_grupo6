package ar.edu.unju.escmi.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "clientes")
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name="cli_nombre", nullable = false, length = 100)
	private String nombre;

	@Column(name="cli_apellido", nullable = false, length = 100)
	private String apellido;

	@Column(name="cli_domicilio", nullable = false, length = 100)
	private String domicilio;

	@Column(name="cli_dni", nullable = false, unique = true)
	private int dni;

	@Column(name="cli_estado", nullable = false)
	private boolean estado;

	/* Constructor por defecto */
	public Cliente() {
	}

	/* Constructor parametrizado */
	public Cliente(String nombre, String apellido, String domicilio, int dni, boolean estado) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.domicilio = domicilio;
		this.dni = dni;
		this.estado = estado;
	}

	/* Getters y Setters */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public int getDni() {
		return dni;
	}

	public void setDni(int dni) {
		this.dni = dni;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	/* Relacion entre Cliente y Factura */
	@OneToMany(mappedBy = "cliente")
	private List<Factura> facturas;

	/* Metodos */
	@Override
	public String toString() {
		return "Cliente{" +
				"id=" + id +
				", nombre='" + nombre + '\'' +
				", apellido='" + apellido + '\'' +
				", domicilio='" + domicilio + '\'' +
				", dni=" + dni +
				", estado=" + estado +
				'}';
	}

}
