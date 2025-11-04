package ar.edu.unju.escmi.main;

import java.util.List;

import ar.edu.unju.escmi.dao.imp.ClienteDaoImp;
import ar.edu.unju.escmi.entities.Cliente;

public class Main {
    public static void main(String[] args) {
        ClienteDaoImp dao = ClienteDaoImp.getInstance();

        // Crear un cliente de prueba
        Cliente cliente = new Cliente("Juan", "Pérez", "Calle Falsa 123", 12345678, true);

        System.out.println("Guardando cliente de prueba...");
        dao.save(cliente);

        // Listar todos los clientes y mostrarlos
        List<Cliente> clientes = dao.findAll();
        System.out.println("Clientes en la base de datos:");
        for (Cliente c : clientes) {
            System.out.println(c);
        }

        System.out.println("Fin de la prueba.");
    }
}
