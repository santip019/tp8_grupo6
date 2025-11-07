package ar.edu.unju.escmi.dao.imp;

import java.util.List;

import ar.edu.unju.escmi.config.EmfSingleton;
import ar.edu.unju.escmi.dao.IClienteDao;
import ar.edu.unju.escmi.entities.Cliente;
import jakarta.persistence.EntityManager;

public class ClienteDaoImp implements IClienteDao {

    @Override
    public void agregarCliente(Cliente cliente) {
        EntityManager manager = EmfSingleton.getInstance().getEmf().createEntityManager();
        try {
            manager.getTransaction().begin();
            manager.persist(cliente);
            manager.getTransaction().commit();
        } catch (Exception e) {
            if (manager.getTransaction() != null) {
                manager.getTransaction().rollback();
            }
            System.out.println("No se pudo agregar el cliente");
        } finally {
            manager.close();
        }

    }

    @Override
    public void modificarCliente(Cliente cliente) {
        EntityManager manager = EmfSingleton.getInstance().getEmf().createEntityManager();
        try {
            manager.getTransaction().begin();
            manager.merge(cliente);
            manager.getTransaction().commit();
        } catch (Exception e) {
            if (manager.getTransaction() != null) {
                manager.getTransaction().rollback();
            }
            System.out.println("No se pudo modificar los datos del cliente");
        } finally {
            manager.close();
        }
    }

    public Cliente obtenerClientePorId(Long id) {
        // Inicializamos el EntityManager y la variable de retorno.
        // Usamos 'null' como valor predeterminado si no se encuentra o hay error.
        EntityManager manager = null;
        Cliente cliente = null;

        try {
            // Obtener y crear el EntityManager
            manager = EmfSingleton.getInstance().getEmf().createEntityManager();

            // Ejecutar la búsqueda. 'find()' retorna null si no encuentra la entidad.
            cliente = manager.find(Cliente.class, id);

            if (cliente == null) {
                System.out.println(" ClienteDaoImp: No se encontró cliente con ID " + id);
            }

        } catch (Exception e) {
            System.err.println(" ERROR al buscar cliente por ID: " + id + ". Detalle: " + e.getMessage());
            cliente = null; // Aseguramos que retorne null ante cualquier fallo

        } finally {
            // Asegurar el cierre del EntityManager
            if (manager != null) {
                manager.close();
            }
        }

        return cliente;
    }

    @Override
    public List<Cliente> obtenerClientes() {
        EntityManager manager = EmfSingleton.getInstance().getEmf().createEntityManager();
        try {
            return manager.createQuery("SELECT c FROM Cliente c", Cliente.class).getResultList();
        } catch (Exception e) {
            System.out.println("Error al obtener la lista de clientes: " + e.getMessage());
            return null;
        } finally {
            manager.close();
        }
    }

}
