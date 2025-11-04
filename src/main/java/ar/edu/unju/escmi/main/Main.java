package ar.edu.unju.escmi.main;

import java.util.Scanner;

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
                    // Lógica para eliminar un producto
                    break;
                case 7:
                    // Lógica para modificar datos de cliente
                    break;
                case 8:
                    // Lógica para modificar precio de producto
                    break;
                case 9:
                    // Lógica para eliminar producto (eliminación lógica)
                    break;
                case 10:
                    // Lógica para mostrar todas las facturas
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
}
