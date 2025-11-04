package ar.edu.unju.escmi.dao;

import java.util.List;
import ar.edu.unju.escmi.entities.Cliente;

public interface IClienteDao {

	void save(Cliente cliente);

	Cliente findById(Long id);

	List<Cliente> findAll();

	void delete(Long id);

}
