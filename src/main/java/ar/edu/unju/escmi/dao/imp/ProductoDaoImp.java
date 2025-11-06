package ar.edu.unju.escmi.dao.imp;

import ar.edu.unju.escmi.config.EmfSingleton;
import ar.edu.unju.escmi.dao.IProductoDao;
import ar.edu.unju.escmi.entities.Producto;
import jakarta.persistence.EntityManager;

public class ProductoDaoImp implements IProductoDao {

    private static EntityManager manager = EmfSingleton.getInstance().getEmf().createEntityManager();

    @Override
    public void agregarProducto(Producto producto) {
        try {
            manager.getTransaction().begin();
            manager.persist(producto);
            manager.getTransaction().commit();
        } catch (Exception e) {
            if (manager.getTransaction() != null) {
                manager.getTransaction().rollback();
            }
            System.out.println("No se pudo agregar el producto");
        } finally {
            manager.clear();
        }

    }

    @Override
    public void modificarProducto(Producto producto) {
        try {
            manager.getTransaction().begin();
            manager.merge(producto);
            manager.getTransaction().commit();
        } catch (Exception e) {
            // Verificar si la transacción está activa antes de intentar hacer rollback
            if (manager.getTransaction() != null && manager.getTransaction().isActive()) {
                manager.getTransaction().rollback(); // Solo se ejecuta si aún está abierta
            }

            System.out.println("No se pudo modificar los datos del producto");
            throw new RuntimeException("Fallo en la modificación de persistencia del producto.", e);

        } finally {
            manager.close();
        }
    }

    @Override
    public Producto obtenerProductoPorId(Long id) {
        // Inicializamos el EntityManager y la variable de retorno.
        // Usamos 'null' como valor predeterminado si no se encuentra o hay error.
        EntityManager manager = null;
        Producto producto = null;

        try {
            // Obtener y crear el EntityManager
            manager = EmfSingleton.getInstance().getEmf().createEntityManager();

            // Ejecutar la búsqueda. 'find()' retorna null si no encuentra la entidad.
            producto = manager.find(Producto.class, id);

            if (producto == null) {
                System.out.println(" ProductoDaoImp: No se encontró producto con ID " + id);
            }

        } catch (Exception e) {
            System.err.println(" ERROR al buscar producto por ID: " + id + ". Detalle: " + e.getMessage());
            producto = null; // Aseguramos que retorne null ante cualquier fallo

        } finally {
            // Asegurar el cierre del EntityManager
            if (manager != null) {
                manager.close();
            }
        }

        return producto;
    }
}