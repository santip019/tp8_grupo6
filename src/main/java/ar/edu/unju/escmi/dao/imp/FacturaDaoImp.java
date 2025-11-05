package ar.edu.unju.escmi.dao.imp;

import java.util.List;

import ar.edu.unju.escmi.config.EmfSingleton;
import ar.edu.unju.escmi.dao.IFacturaDao;
import ar.edu.unju.escmi.entities.Factura;
import jakarta.persistence.EntityManager;

public class FacturaDaoImp implements IFacturaDao {

    private static EntityManager manager = EmfSingleton.getInstance().getEmf().createEntityManager();

    @Override
    public void agregarFactura(Factura factura) {
        try{
            manager.getTransaction().begin();
            manager.persist(factura);
            manager.getTransaction().commit();
        }catch(Exception e){
            if (manager.getTransaction() != null){
                manager.getTransaction().rollback();
            }
            System.out.println("No se pudo agregar la factura");
        }finally{
            manager.close();
        }
    }

    @Override
    public void modificarFactura(Factura factura) {
        try{
            manager.getTransaction().begin();
            manager.merge(factura);
            manager.getTransaction().commit();
        }catch(Exception e){
            if(manager.getTransaction()!=null){
                manager.getTransaction().rollback();
            }
            System.out.println("No se pudo modificar los datos de la factura");
        }finally{
            manager.close();
        }
    }

    @Override
    public List<Factura> obtenerFacturas() {
        try {
        return manager.createQuery("SELECT f FROM Factura f", Factura.class).getResultList();
		}catch(Exception e){
			System.out.println("Error al obtener la lista de facturas: " + e.getMessage());
			return null;
		}finally{
			manager.close();
		}
    }

    @Override
    public Factura buscarFacturaPorId(long id) {
        try {
            return manager.find(Factura.class, id);
        } catch (Exception e) {
            System.out.println("Error al buscar factura: " + e.getMessage());
            return null;
        } finally {
            manager.close();
        }
    }

}
