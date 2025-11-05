package ar.edu.unju.escmi.dao.imp;

import ar.edu.unju.escmi.config.EmfSingleton;
import ar.edu.unju.escmi.dao.IProductoDao;
import ar.edu.unju.escmi.entities.Producto;
import jakarta.persistence.EntityManager;

public class ProductoDaoImp implements IProductoDao {

    private static EntityManager manager = EmfSingleton.getInstance().getEmf().createEntityManager();

    @Override
    public void agregarProducto(Producto producto) {
        try{
            manager.getTransaction().begin();
            manager.persist(producto);
            manager.getTransaction().commit();
        }catch(Exception e){
            if (manager.getTransaction() != null){
                manager.getTransaction().rollback();
            }
            System.out.println("No se pudo agregar el producto");
        }finally{
            manager.close();
        }
        
    }

    @Override
    public void modificarProducto(Producto producto) {
        try{
            manager.getTransaction().begin();
            manager.merge(producto);
            manager.getTransaction().commit();
        }catch(Exception e){
            if(manager.getTransaction()!=null){
                manager.getTransaction().rollback();
            }
            System.out.println("No se pudo modificar los datos del producto");
        }finally{
            manager.close();
        }
    }
    /*
    @Override
    public Producto obteneProducto(Long id){
        try {
            return manager.find(Producto.class, id);
        } catch (Exception e) {
            System.out.println("Error al buscar producto: " + e.getMessage());
            return null;
        } finally {
            manager.close();
        }
    }
    @Override
    public Producto obtenerProducto(Long id) {
        EntityManager manager = EmfSingleton.getInstance().getEmf().createEntityManager();
        try {
            return manager.find(Producto.class, id);
        } catch (Exception e) {
            System.out.println("Error al buscar producto: " + e.getMessage());
            return null;
        } finally {
            manager.close();
        }
    }
        */
}
