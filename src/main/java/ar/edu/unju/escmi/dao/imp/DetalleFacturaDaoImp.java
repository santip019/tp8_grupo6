package ar.edu.unju.escmi.dao.imp;

import java.util.List;

import ar.edu.unju.escmi.config.EmfSingleton;
import ar.edu.unju.escmi.dao.IDetalleFacturaDao;
import ar.edu.unju.escmi.entities.DetalleFactura;
import jakarta.persistence.EntityManager;

public class DetalleFacturaDaoImp implements IDetalleFacturaDao {

    @Override
    public List<DetalleFactura> obtenerDetallesFactura(long id) {
        EntityManager manager = EmfSingleton.getInstance().getEmf().createEntityManager();
        try{
            return manager.createQuery(
                "SELECT d FROM DetalleFactura d WHERE d.factura.id = :id",
                DetalleFactura.class).setParameter("id", id).getResultList();
        }catch(Exception e){
            System.out.println("Error al obtener detalles de factura: " + e.getMessage());
            return java.util.Collections.emptyList();
        }finally{
            manager.close();
        }
    }

}
