package ar.edu.unju.escmi.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import ar.edu.unju.escmi.dao.IClienteDao;
import ar.edu.unju.escmi.dao.imp.ClienteDaoImp;
import ar.edu.unju.escmi.entities.Cliente;
import ar.edu.unju.escmi.entities.DetalleFactura;
import ar.edu.unju.escmi.dao.imp.FacturaDaoImp;
import ar.edu.unju.escmi.dao.imp.ProductoDaoImp;
import ar.edu.unju.escmi.dao.IFacturaDao;
import ar.edu.unju.escmi.dao.IProductoDao;
import ar.edu.unju.escmi.entities.Factura;
import ar.edu.unju.escmi.entities.Producto;

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
            System.out.println("6- Eliminar un cliente (eliminación lógica).");
            System.out.println("7- Modificar datos de cliente.");
            System.out.println("8- Modificar precio de producto.");
            System.out.println("9- Eliminar producto (eliminación lógica, se usa el atributo estado).");
            System.out.println("10- Mostrar todas las facturas.");
            System.out.println("11- Mostrar todos los clientes.");
            System.out.println("12- Mostrar las facturas que superen el total de $500.000.");
            System.out.println("13- Salir.");
            System.out.print("Seleccione una opción: ");
            op = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (op) {
                case 1:
                    // Lógica para alta de cliente
                    altaCliente(scanner);
                    break;
                case 2:
                    // Lógica para alta de producto
                    altaProducto(scanner);
                    break;
                case 3:
                    // Lógica para realizar la venta de productos
                    realizarVenta(scanner);
                    break;
                case 4:
                    // Lógica para buscar una factura por número
                    buscarFactura(scanner);
                    break;
                case 5:
                    // Lógica para eliminar una factura
                    eliminarFactura(scanner);
                    break;
                case 6:
                    // Lógica para eliminar un cliente
                    eliminarCliente(scanner);
                    break;
                case 7:
                    // Lógica para modificar datos de cliente
                    modificarCliente(scanner);
                    break;
                case 8:
                    // Lógica para modificar precio de producto
                    modificarProd(scanner);
                    break;
                case 9:
                    // Lógica para eliminar producto (eliminación lógica)
                    eliminarProducto(scanner);
                    break;
                case 10:
                    // Lógica para mostrar todas las facturas
                    mostrarFacturas();
                    break;
                case 11:
                    // Lógica para mostrar todos los clientes
                    mostrarClientes(scanner);
                    break;
                case 12:
                    // Lógica para mostrar facturas que superen $500.000
                    mostrarFacturasAltas();
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

    private static void realizarVenta(Scanner scanner) {
        // Lógica para realizar la venta de productos (alta de una nueva factura)
        IClienteDao clienteDao = new ClienteDaoImp();
        IProductoDao productoDao = new ProductoDaoImp();
        IFacturaDao facturaDao = new FacturaDaoImp();

        List<Producto> productosDisponibles = productoDao.obtenerProductos();
        List<Cliente> clientesDisponibles = clienteDao.obtenerClientes();

        // Verificar si hay clientes y productos disponibles
        if (clientesDisponibles == null || clientesDisponibles.stream().filter(Cliente::isEstado).count() == 0) {
            System.out.println(" No hay clientes disponibles para realizar la venta.");
            return;
        }
        if (productosDisponibles == null || productosDisponibles.stream().filter(Producto::isEstado).count() == 0) {
            System.out.println(" No hay productos disponibles para realizar la venta.");
            return;
        }

        System.out.println("\n==== Nueva Venta ====");
        // Paso 1: seleccionar el cliente que va a comprar
        System.out.print("Ingrese el ID del cliente: ");
        Long clienteId = scanner.nextLong();
        Cliente cliente = clienteDao.obtenerClientePorId(clienteId);

        if (cliente == null) {
            System.out.println(" No se encontró ningún cliente con el ID: " + clienteId);
            return;
        }

        // paso 2: si el cliente se encontró, se procede con la creacion de la nueva
        // factura
        Factura factura = new Factura();
        factura.setCliente(cliente);
        factura.setFecha(LocalDate.now());
        factura.setDomicilio(cliente.getDomicilio());
        factura.setEstado(true);
        factura.setTotal(0.0);

        List<DetalleFactura> detalles = new ArrayList<>();
        factura.setDetallesFactura(detalles);

        // Paso 3: agregar productos a la factura
        boolean continuarAgregando = true;
        while (continuarAgregando) {
            System.out.print("\nIngrese ID del producto (0 para finalizar): ");
            Long productoId = scanner.nextLong();

            if (productoId == 0) {
                System.out.println("Se han agregado todos los productos a la factura.\n Calculando total...");
                continuarAgregando = false;
                continue;
            }

            Producto producto = productoDao.obtenerProductoPorId(productoId);
            if (producto == null) {
                System.out.println("Producto no encontrado.");
                continue;
            }

            System.out.print("Ingrese cantidad: ");
            int cantidad = scanner.nextInt();

            // Se crea el detalle de la factura
            DetalleFactura detalle = new DetalleFactura();
            detalle.setProducto(producto);
            detalle.setCantidad(cantidad);
            detalle.setFactura(factura);
            detalle.calcularSubtotal();

            detalles.add(detalle);

            System.out.println("Producto agregado: " + producto.getDescripcion() + "\n" +
                    "Cantidad: " + cantidad + "\n" + "Subtotal: " + detalle.getSubtotal());
        }

        // Paso 4: calcular el total de la factura
        factura.calcularTotal();
        System.out.println("Total de la factura: $" + factura.getTotal());

        // Paso 5: Confirmar la venta y guardar la factura
        System.out.print("\n¿Confirmar venta? (S/N): ");
        scanner.nextLine(); // Limpiar buffer
        String confirmacion = scanner.nextLine();

        if (confirmacion.equalsIgnoreCase("S")) {
            try {
                facturaDao.agregarFactura(factura);
                System.out.println("Venta realizada con éxito!");
                System.out.println("Número de factura: " + factura.getId());
            } catch (Exception e) {
                System.out.println("Error al guardar la venta: " + e.getMessage());
            }
        } else {
            System.out.println("Venta cancelada.");
        }

    }

    private static void altaCliente(Scanner scanner) {

        System.out.println("****** Alta de Cliente ******");

        System.out.println("Ingrese el nombre del cliente:");
        String nombre = scanner.nextLine();

        System.out.println("Ingrese el apellido del cliente:");
        String apellido = scanner.nextLine();

        // VALIDACIÓN PARA DNI
        Integer dni = null;

        do {
            System.out.println("Ingrese el dni del cliente (solo números):");

            try {

                dni = scanner.nextInt();
                scanner.nextLine(); // Consumir el '\n'

            } catch (InputMismatchException e) {

                System.out.println(" Entrada no válida. Por favor, ingrese solo números para el DNI.");
                scanner.nextLine(); // Consumir la entrada no válida

            }

        } while (dni == null);

        System.out.println("Ingrese el domicilio del cliente:");
        String domicilio = scanner.nextLine();

        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setDni(dni);
        cliente.setDomicilio(domicilio);
        cliente.setEstado(true);

        IClienteDao clienteDao = new ClienteDaoImp();
        clienteDao.agregarCliente(cliente);

        System.out.println("Cliente agregado con éxito.");
        System.out.println("ID asignado: " + cliente.getId());
        System.out.println("Presione ENTER para volver al menú principal...");

        scanner.nextLine();
    }

    private static void modificarCliente(Scanner scanner) {

        System.out.println("****** Modificación de Cliente ******");

        // VALIDACIÓN PARA ID
        Long id = null;
        do {

            System.out.print("Ingrese el ID del cliente a modificar (solo números): ");

            try {
                id = scanner.nextLong();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Entrada no válida. Por favor, ingrese el ID como un número.");
                scanner.nextLine();
            }

        } while (id == null);

        IClienteDao clienteDao = new ClienteDaoImp();
        Cliente cliente = clienteDao.obtenerClientePorId(id);

        if (cliente != null) {
            System.out.println("\nCliente encontrado: " + cliente.getNombre() + " " + cliente.getApellido());
            System.out.println("*** Ingrese los nuevos valores ***");

            System.out.println("Ingrese el nuevo nombre del cliente (Actual: " + cliente.getNombre() + "):");
            String nuevonombre = scanner.nextLine();

            System.out.println("Ingrese el nuevo apellido del cliente (Actual: " + cliente.getApellido() + "):");
            String nuevoapellido = scanner.nextLine();

            System.out.println("Ingrese el nuevo domicilio del cliente (Actual: " + cliente.getDomicilio() + "):");
            String nuevodomicilio = scanner.nextLine();

            // VALIDACIÓN PARA NUEVO DNI
            Integer nuevodni = null;
            do {

                System.out
                        .println("Ingrese el nuevo dni del cliente (Actual: " + cliente.getDni() + ", solo números):");
                try {

                    nuevodni = scanner.nextInt();
                    scanner.nextLine();

                } catch (InputMismatchException e) {

                    System.out.println("Entrada no válida. Por favor, ingrese solo números para el DNI.");
                    scanner.nextLine();
                }

            } while (nuevodni == null);

            // VALIDACIÓN PARA ESTADO
            Boolean nuevoestado = null;
            do {
                System.out.println(
                        "Ingrese el nuevo estado del cliente (true/false, Actual: " + cliente.isEstado() + "):");
                try {
                    nuevoestado = scanner.nextBoolean();
                    scanner.nextLine();
                } catch (InputMismatchException e) {
                    System.out.println(" Entrada no válida. Por favor, ingrese 'true' o 'false'.");
                    scanner.nextLine(); // Consumir la entrada no válida
                }
            } while (nuevoestado == null);

            cliente.setNombre(nuevonombre);
            cliente.setApellido(nuevoapellido);
            cliente.setDni(nuevodni);
            cliente.setDomicilio(nuevodomicilio);
            cliente.setEstado(nuevoestado);

            try {
                clienteDao.modificarCliente(cliente);
                System.out.println(" Cliente con ID " + id + " fue modificado.");
                System.out.println("Presione ENTER para volver al menú principal...");
                scanner.nextLine();
            } catch (Exception e) {

                System.out.println(" ERROR al persistir los cambios:");
                e.printStackTrace();

            }

        } else {

            System.out.println("No se encontró ningún cliente con el ID: " + id + ". No se pudo modificar.");
            System.out.println("Presione ENTER para volver al menú principal...");
            scanner.nextLine();
        }
    }

    private static void mostrarFacturas() {
        System.out.println("--- Mostrar Facturas ---");
        IFacturaDao facturaDao = new FacturaDaoImp();
        List<Factura> facturas = facturaDao.obtenerFacturas();
        System.out.println("Cantidad de facturas encontradas: " + facturas.size());
        if (facturas != null && !facturas.isEmpty()) {
            for (Factura factura : facturas) {
                System.out.println(factura);
                System.out.println("-------------------------");
            }
        } else {
            System.out.println("No hay facturas disponibles para mostrar.");
        }
    }

    private static void altaProducto(Scanner scanner) {
        System.out.println("\n==== Alta de Producto ====");
        System.out.print("Ingrese la descripción del producto: ");
        String descripcion = scanner.nextLine();

        System.out.print("Ingrese el precio unitario: ");
        double precio = scanner.nextDouble();
        scanner.nextLine(); // limpiar buffer

        Producto producto = new Producto();
        producto.setDescripcion(descripcion);
        producto.setPrecioUnitario(precio);
        producto.setEstado(true);

        IProductoDao productoDao = new ProductoDaoImp();
        productoDao.agregarProducto(producto);
        System.out.println("Producto agregado correctamente con el id: " + producto.getId());
    }

    private static void buscarFactura(Scanner scanner) {
        System.out.println("\n==== Buscar Factura ====");
        System.out.print("Ingrese el número de la factura: ");
        long id = scanner.nextLong();

        IFacturaDao facturaDao = new FacturaDaoImp();
        Factura factura = facturaDao.buscarFacturaPorId(id);
        if (factura != null) {
            System.out.println("\n ==== Factura encontrada ====");
            System.out.println(factura); // Usa el toString() de Factura
        } else {
            System.out.println("No se encontró ninguna factura con el ID: " + id);
        }

    }

    public static void modificarProd(Scanner scanner) {
        System.out.println("\n==== Modificar Precio de Producto ====");
        System.out.print("Ingrese el ID del producto: ");
        Long idProducto = scanner.nextLong();

        IProductoDao productoDao = new ProductoDaoImp();
        Producto producto = productoDao.obtenerProductoPorId(idProducto);
        if (producto == null) {
            System.out.println(" No se encontró ningún producto con el ID: " + idProducto);
        } else {
            System.out.println("Producto encontrado:");
            System.out.println(producto);
            System.out.print("Ingrese el nuevo precio: ");
            double nuevoPrecio = scanner.nextDouble();
            producto.setPrecioUnitario(nuevoPrecio);
            productoDao.modificarProducto(producto);
            System.out.println(" Precio actualizado correctamente.");
        }
    }

    public static void eliminarCliente(Scanner scanner) {
        System.out.println("Ingrese id de cliente a eliminar");
        long idClienteAEliminar = scanner.nextLong();
        IClienteDao clienteDao = new ClienteDaoImp();
        Cliente c = clienteDao.obtenerClientePorId(idClienteAEliminar); // obtener
        // cliente
        try {
            if (c != null) {
                c.setEstado(false); // false = marca como eliminado
                clienteDao.modificarCliente(c); // actualizar en la BD
            } else {
                System.out.println("No se encontró un cliente con el ID: " + idClienteAEliminar);
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar cliente: " + e.getMessage());
        }
    }

    public static void eliminarProducto(Scanner scanner) {
        System.out.println("Ingrese id de producto a eliminar");
        long idProductoAEliminar = scanner.nextLong();
        IProductoDao productoDao = new ProductoDaoImp();
        Producto p = productoDao.obtenerProductoPorId(idProductoAEliminar); // obtener
        // producto
        try {
            if (p != null) {
                p.setEstado(false); // false = marca como eliminado
                productoDao.modificarProducto(p); // actualizar en la BD
            } else {
                System.out.println("No se encontró un producto con el ID: " + idProductoAEliminar);
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar producto: " + e.getMessage());
        }
    }

    public static void mostrarFacturasAltas() {
        System.out.println("==== Facturas que superan los $500.000 ====");
        IFacturaDao facturaDao = new FacturaDaoImp();
        try {
            var facturasAltas = facturaDao.obtenerFacturasMayoresAMedioMillon();// usamos var para que java detecte
                                                                                // automaticamente el tipo por la
                                                                                // inferencia de tipos, sino seria
                                                                                // List<Factura>
            if (facturasAltas != null && !facturasAltas.isEmpty()) {// Si lo que se obtiene es distinto de null y no
                                                                    // esta vacio
                for (Factura factura : facturasAltas) {
                    System.out.println(factura);
                    System.out.println("-------------------------");
                }
            } else {
                System.out.println("No hay facturas que superen los $500.000.");
            }
        } catch (Exception e) {
            System.out.println("Error al obtener facturas altas: " + e.getMessage());
        }
    }

    public static void mostrarClientes(Scanner scanner) {
        System.out.println("---- Mostrar Clientes ----");
        IClienteDao clienteDao = new ClienteDaoImp();
        List<Cliente> clientes = clienteDao.obtenerClientes();
        System.out.println("Cantidad de clientes encontrados: " + clientes.size());
        if (clientes != null && !clientes.isEmpty()) {
            for (Cliente cliente : clientes) {
                System.out.println(cliente);
                System.out.println("-------------------------");
            }
        } else {
            System.out.println("No hay clientes disponibles para mostrar.");
        }

        System.out.println("Presione ENTER para volver al menú principal...");
        scanner.nextLine(); // Esperar a que el usuario presione ENTER
    }

    public static void eliminarFactura(Scanner scanner) {
        long idFacturaAEliminar;
        System.out.println("Ingrese id de factura a eliminar:");

        try {
            if (scanner.hasNextLong()) {
                idFacturaAEliminar = scanner.nextLong();
            } else {
                System.out.println("Error: La entrada no es un número de ID válido.");
                scanner.next(); // Consumir la entrada no válida
                return;
            }

            IFacturaDao facturaDao = new FacturaDaoImp();
            Factura f = facturaDao.buscarFacturaPorId(idFacturaAEliminar);

            if (f != null) {
                f.setEstado(false);
                facturaDao.modificarFactura(f);
                System.out.println(" Factura con ID " + idFacturaAEliminar + " eliminada lógicamente.");
            } else {
                System.out.println("No se encontró una factura con el ID: " + idFacturaAEliminar);
            }

        } catch (Exception e) {
            System.err.println("ERROR al intentar eliminar la factura:");
            System.err.println("Mensaje: " + e.getMessage());
        }

        System.out.println("Presione ENTER para volver al menú principal...");
        scanner.nextLine(); // Esperar a que el usuario presione ENTER
    }
}
