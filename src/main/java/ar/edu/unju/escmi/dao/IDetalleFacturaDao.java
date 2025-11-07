package ar.edu.unju.escmi.dao;

import java.util.List;
import ar.edu.unju.escmi.entities.DetalleFactura;

public interface IDetalleFacturaDao {

    public List<DetalleFactura> obtenerDetallesFactura (long id);//recibe el id de factura
}
