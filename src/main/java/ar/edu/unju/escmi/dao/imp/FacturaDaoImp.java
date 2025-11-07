package ar.edu.unju.escmi.dao.imp;

import java.util.List;

import ar.edu.unju.escmi.config.EmfSingleton;
import ar.edu.unju.escmi.dao.IFacturaDao;
import ar.edu.unju.escmi.entities.Factura;
import jakarta.persistence.EntityManager;

public class FacturaDaoImp implements IFacturaDao {

    @Override
    public void agregarFactura(Factura factura) {
        EntityManager manager = EmfSingleton.getInstance().getEmf().createEntityManager();
        try {
            manager.getTransaction().begin();
            manager.persist(factura);
            manager.getTransaction().commit();
        } catch (Exception e) {
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            System.out.println("No se pudo agregar la factura");
        } finally {
            manager.close();
        }
    }

    @Override
    public void modificarFactura(Factura factura) {
        EntityManager manager = EmfSingleton.getInstance().getEmf().createEntityManager();
        try {
            manager.getTransaction().begin();
            manager.merge(factura);
            manager.getTransaction().commit();
        } catch (Exception e) {
            if (manager.getTransaction().isActive()) {
                manager.getTransaction().rollback();
            }
            System.out.println("No se pudo modificar los datos de la factura");
        } finally {
            manager.close();
        }
    }

    @Override
    public List<Factura> obtenerFacturas() {
        EntityManager manager = EmfSingleton.getInstance().getEmf().createEntityManager();
        try {
            return manager.createQuery("SELECT f FROM Factura f", Factura.class).getResultList();
        } catch (Exception e) {
            System.out.println("Error al obtener la lista de facturas: " + e.getMessage());
            return null;
        } finally {
            manager.close();
        }
    }

    @Override
    public Factura buscarFacturaPorId(long id) {
        EntityManager manager = EmfSingleton.getInstance().getEmf().createEntityManager();
        try {
            return manager.find(Factura.class, id);
        } catch (Exception e) {
            System.out.println("Error al buscar factura: " + e.getMessage());
            return null;
        } finally {
            manager.close();
        }
    }

    @Override
    public List<Factura> obtenerFacturasMayoresAMedioMillon() {
        EntityManager manager = EmfSingleton.getInstance().getEmf().createEntityManager();
        try {
            double umbral = 500000.0;
            return manager.createQuery(
                    "SELECT f FROM Factura f WHERE f.total > :umbral",
                    Factura.class).setParameter("umbral", umbral).getResultList();
        } catch (Exception e) {
            System.out.println("Error al obtener facturas mayores a medio millón: " + e.getMessage());
            return java.util.Collections.emptyList();
        } finally {
            manager.close();
        }
    }
}
