
/*package ar.edu.unju.escmi.dao.imp;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import ar.edu.unju.escmi.config.EmfSingleton;
import ar.edu.unju.escmi.dao.IClienteDao;
import ar.edu.unju.escmi.entities.Cliente;

public class ClienteDaoImp implements IClienteDao {

	private static ClienteDaoImp instancia = new ClienteDaoImp();
	private EntityManagerFactory emf;

	private ClienteDaoImp() {
		this.emf = EmfSingleton.getInstance().getEmf();
	}

	public static ClienteDaoImp getInstance() {
		return instancia;
	}

	@Override
	public void save(Cliente cliente) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			if (cliente.getId() == null) {
				em.persist(cliente);
			} else {
				em.merge(cliente);
			}
			tx.commit();
		} catch (RuntimeException e) {
			if (tx.isActive()) tx.rollback();
			throw e;
		} finally {
			em.close();
		}
	}

	@Override
	public Cliente findById(Long id) {
		EntityManager em = emf.createEntityManager();
		try {
			return em.find(Cliente.class, id);
		} finally {
			em.close();
		}
	}

	@Override
	public List<Cliente> findAll() {
		EntityManager em = emf.createEntityManager();
		try {
			TypedQuery<Cliente> query = em.createQuery("SELECT c FROM Cliente c", Cliente.class);
			return query.getResultList();
		} finally {
			em.close();
		}
	}

	@Override
	public void delete(Long id) {
		EntityManager em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			Cliente c = em.find(Cliente.class, id);
			if (c != null) {
				em.remove(c);
			}
			tx.commit();
		} catch (RuntimeException e) {
			if (tx.isActive()) tx.rollback();
			throw e;
		} finally {
			em.close();
		}
	}

}*/
