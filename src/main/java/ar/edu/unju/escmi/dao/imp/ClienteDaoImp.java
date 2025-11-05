package ar.edu.unju.escmi.dao.imp;

import java.util.List;

import ar.edu.unju.escmi.config.EmfSingleton;
import ar.edu.unju.escmi.dao.IClienteDao;
import ar.edu.unju.escmi.entities.Cliente;
import jakarta.persistence.EntityManager;

public class ClienteDaoImp implements IClienteDao {

    private static EntityManager manager = EmfSingleton.getInstance().getEmf().createEntityManager();

    @Override
    public void guardarCliente(Cliente cliente) {
        try{
            manager.getTransaction().begin();
            manager.persist(cliente);
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
    public void modificarCliente(Cliente cliente) {
        try{
            manager.getTransaction().begin();
            manager.merge(cliente);
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

	@Override
	public List<Cliente> obtenerClientes() {
		try {
        return manager.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
		}catch(Exception e){
			System.out.println("Error al obtener la lista de clientes: " + e.getMessage());
			return null;
		}finally{
			manager.close();
		}
	}

}
