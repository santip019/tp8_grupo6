package ar.edu.unju.escmi.main;

import java.util.Scanner;

/* import ar.edu.unju.escmi.dao.IProductoDao;
import ar.edu.unju.escmi.dao.imp.ProductoDaoImp;
import ar.edu.unju.escmi.entities.Producto; */
import ar.edu.unju.escmi.dao.IClienteDao;
import ar.edu.unju.escmi.dao.imp.ClienteDaoImp;
import ar.edu.unju.escmi.entities.Cliente;
import ar.edu.unju.escmi.dao.imp.FacturaDaoImp;
import ar.edu.unju.escmi.dao.IFacturaDao;
import ar.edu.unju.escmi.entities.Factura;

public class Main {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        int op = 0;

        do {
            System.out.println("\n====== Menu Principal =====");
            System.out.println("1- Alta de cliente.");
            System.out.println("2- Alta de producto.");
            System.out.println("3- Realizar la venta de productos (Alta de una nueva factura).");
            System.out.println("4- Buscar una factura ingresando su número de factura y mostrar todos sus datos.");
            System.out.println("5- Eliminar una factura (eliminación lógica).");
            System.out.println("6- Eliminar un producto (eliminación lógica).");
            System.out.println("7- Modificar datos de cliente.");
            System.out.println("8- Modificar precio de producto.");
            System.out.println("9- Eliminar producto (eliminación lógica, se usa el atributo estado).");
            System.out.println("10- Mostrar todas las facturas.");
            System.out.println("11- Mostrar todos los clientes.");
            System.out.println("12- Mostrar las facturas que superen el total de $500.000.");
            System.out.println("13- Salir.");
            System.out.print("Seleccione una opción: ");
            op = scanner.nextInt();

            switch (op) {
                case 1:
                    // Lógica para alta de cliente
                    altaCliente();
                    break;
                case 2:
                    // Lógica para alta de producto
                    break;
                case 3:
                    // Lógica para realizar la venta de productos
                    break;
                case 4:
                    // Lógica para buscar una factura por número
                    break;
                case 5:
                    // Lógica para eliminar una factura
                    break;
                case 6:
                    // Lógica para eliminar un cliente
                    break;
                case 7:
                    // Lógica para modificar datos de cliente

                    modificarCliente(scanner);
                    break;
                case 8:
                    // Lógica para modificar precio de producto
                    break;
                case 9:
                    // Lógica para eliminar producto (eliminación lógica)
                    /*
                     * System.out.println("Ingrese id de producto a eliminar");
                     * Long idProductoAEliminar = scanner.nextLong();
                     * IProductoDao productoDao = new ProductoDaoImp();
                     * Producto p = productoDao.obtenerProducto(idProductoAEliminar); // obtener
                     * producto
                     * if (p != null) {
                     * p.setEstado(false); // false = marca como eliminado
                     * productoDao.modificarProducto(p); // actualizar en la BD
                     * }
                     */
                    break;
                case 10:
                    // Lógica para mostrar todas las facturas
                    mostrarFacturas();
                    break;
                case 11:
                    // Lógica para mostrar todos los clientes
                    break;
                case 12:
                    // Lógica para mostrar facturas que superen $500.000
                    break;
                case 13:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción inválida. Por favor, intente de nuevo.");
                    break;
            }
        } while (op != 13);
        scanner.close();
    }

    private static void altaCliente() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("****** Alta de Cliente ******");

        System.out.println("Ingrese el nombre del cliente:");
        String nombre = scanner.nextLine();

        System.out.println("Ingrese el apellido del cliente:");
        String apellido = scanner.nextLine();

        System.out.println("Ingrese el dni del cliente:");
        Integer dni = scanner.nextInt();

        System.out.println("Ingrese el domicilio del cliente:");
        String domicilio = scanner.nextLine();

        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setDni(dni);
        cliente.setDomicilio(domicilio);

        IClienteDao clienteDao = new ClienteDaoImp();
        clienteDao.guardarCliente(cliente);

        scanner.close();
    }

    private static void modificarCliente(Scanner scanner) {

        System.out.println("****** Modificación de Cliente ******");

        System.out.print("Ingrese el ID del cliente a modificar: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consumir el salto de línea

        IClienteDao clienteDao = new ClienteDaoImp();
        Cliente cliente = clienteDao.obtenerClientePorId(id);

        // Verificar si el cliente existe
        if (cliente != null) {

            System.out.println("\nCliente encontrado: " + cliente.getNombre() + " " + cliente.getApellido());

            System.out.println("--- Ingrese los nuevos valores ---");

            System.out.println("Ingrese el nuevo nombre del cliente:");
            String nuevonombre = scanner.nextLine();

            System.out.println("Ingrese el nuevo apellido del cliente:");
            String nuevoapellido = scanner.nextLine();

            System.out.println("Ingrese el nuevo domicilio del cliente:");
            String nuevodomicilio = scanner.nextLine();

            System.out.println("Ingrese el nuevo dni del cliente:");
            Integer nuevodni = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            System.out.println("Ingrese el nuevo estado del cliente (true para activo, false para inactivo):");
            boolean nuevoestado = scanner.nextBoolean();
            scanner.nextLine(); // Consumir el salto de línea

            cliente.setNombre(nuevonombre);
            cliente.setApellido(nuevoapellido);
            cliente.setDni(nuevodni);
            cliente.setDomicilio(nuevodomicilio);
            cliente.setEstado(nuevoestado);

            try {
                clienteDao.modificarCliente(cliente);
                System.out.println("Cliente con ID " + id + " modificado exitosamente.");
            } catch (Exception e) {
                System.out.println("ERROR al persistir los cambios: " + e.getMessage());
            }

        } else {
            System.out.println("No se encontró ningún cliente con el ID: " + id + ". No se pudo modificar.");
        }

    }

    private static void mostrarFacturas() {
        // Metodo Mostrar Facturas
        System.out.println("Mostrar Facturas");
        IFacturaDao facturaDao = new FacturaDaoImp();
        if (facturaDao.obtenerFacturas() != null && !facturaDao.obtenerFacturas().isEmpty()) {
            for (Factura factura : facturaDao.obtenerFacturas()) {
                System.out.println(factura);
                System.out.println("-------------------------");
            }
        } else {
            System.out.println("No hay facturas disponibles para mostrar.");
        }

    }
}
