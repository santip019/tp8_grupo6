package ar.edu.unju.escmi.dao;

import java.util.List;
import ar.edu.unju.escmi.entities.Factura;

public interface IFacturaDao {

    public void agregarFactura(Factura factura);

    public void modificarFactura(Factura factura);

    public List<Factura> obtenerFacturasMayoresAMedioMillon();

    public List<Factura> obtenerFacturas();

    public Factura buscarFacturaPorId(long id);
}
