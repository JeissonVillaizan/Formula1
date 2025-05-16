package edu.ucompensar.ClasesMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.ucompensar.datamanagers.CircuitosDataManager;
import edu.ucompensar.model.Circuito;

/**
 * Clase que permite seleccionar una carrera de la temporada 2024 de F1.
 * Muestra la lista de circuitos y permite al usuario elegir uno.
 */
public class SeleccionarCarrera {
    
    
    private List<String> nombreCircuitos;
    private List<String> idCircuitos;
    private List<String> paisesCircuitos;
    private static String carreraSeleccionada;
    
    /**
     * Constructor que carga los datos de los circuitos desde el CircuitosDataManager.
     */
    public SeleccionarCarrera() {
        cargarCircuitos();
    }
    
    /**
     * Carga los datos de los circuitos desde el CircuitosDataManager.
     */
    private void cargarCircuitos() {
        nombreCircuitos = new ArrayList<>();
        idCircuitos = new ArrayList<>();
        paisesCircuitos = new ArrayList<>();
        
        // Obtener la instancia del gestor de datos de circuitos
        CircuitosDataManager circuitosManager = CircuitosDataManager.getInstance();
        List<Circuito> circuitos = circuitosManager.getCircuitos();
        
        // Recorrer todos los circuitos y guardar sus datos
        for (Circuito circuito : circuitos) {
            nombreCircuitos.add(circuito.getCircuitName());
            idCircuitos.add(circuito.getCircuitId());
            paisesCircuitos.add(circuito.getCountry());
        }
    }
    
    /**
     * Muestra la lista de circuitos y permite al usuario seleccionar uno.
     * 
     * @return El ID del circuito seleccionado o null si hubo un error
     */
    public String seleccionarCarrera() {
        mostrarCircuitos();
        
        Scanner scanner = new Scanner(System.in);
        int seleccion = -1;
        
        while (seleccion < 1 || seleccion > nombreCircuitos.size()) {
            System.out.print("\nSeleccione un circuito (1-" + nombreCircuitos.size() + "): ");
            try {
                seleccion = scanner.nextInt();
                if (seleccion < 1 || seleccion > nombreCircuitos.size()) {
                    System.out.println("Número inválido. Intente nuevamente.");
                }
            } catch (Exception e) {
                System.out.println("Entrada inválida. Ingrese un número.");
                scanner.nextLine(); // Limpiar buffer
            }
        }
        
        // Obtener la carrera seleccionada (índice base 0)
        int index = seleccion - 1;
        carreraSeleccionada = nombreCircuitos.get(index);
        String circuitoId = idCircuitos.get(index);
        
        System.out.println("\nHa seleccionado: " + carreraSeleccionada + 
                          " (" + paisesCircuitos.get(index) + ")");
        
        return circuitoId;
    }
    
    /**
     * Muestra la lista de circuitos con su número correspondiente.
     */
    private void mostrarCircuitos() {
        System.out.println("\n===== CIRCUITOS FORMULA 1 - TEMPORADA 2024 =====\n");
        
        if (nombreCircuitos.isEmpty()) {
            System.out.println("No se encontraron circuitos para mostrar.");
            return;
        }
        
        System.out.printf("%-3s %-45s %-25s\n", "N°", "NOMBRE DEL CIRCUITO", "UBICACIÓN");
        System.out.println("-------------------------------------------------------------------------------------");
        
        for (int i = 0; i < nombreCircuitos.size(); i++) {
            System.out.printf("%-3d %-45s %-25s\n", 
                (i + 1), 
                nombreCircuitos.get(i), 
                paisesCircuitos.get(i));
        }
        
        System.out.println("\n===================================================\n");
    }
    
    /**
     * Obtiene el ID del circuito para la carrera seleccionada actualmente.
     * 
     * @return El ID del circuito o null si no hay carrera seleccionada
     */
    public String getIdCarreraSeleccionada() {
        if (carreraSeleccionada == null) {
            return null;
        }
        
        int index = nombreCircuitos.indexOf(carreraSeleccionada);
        if (index >= 0) {
            return idCircuitos.get(index);
        }
        
        return null;
    }
    
    /**
     * Devuelve el nombre de la carrera seleccionada actualmente.
     * 
     * @return El nombre de la carrera o null si no hay carrera seleccionada
     */
    public static String getCarreraSeleccionada() {
        return carreraSeleccionada;
    }
    
    /**
     * Método estático para obtener una instancia y seleccionar una carrera.
     * 
     * @return El ID del circuito seleccionado
     */
    public static String seleccionarCarreraStatic() {
        SeleccionarCarrera selector = new SeleccionarCarrera();
        return selector.seleccionarCarrera();
    }

    /**
     * Método estático para obtener el ID de la carrera seleccionada.
     * 
     * @return El ID de la carrera seleccionada o null si no hay selección
     */
    public static String getIdCarreraSeleccionadaStatic() {
        SeleccionarCarrera selector = new SeleccionarCarrera();
        
        // Si ya hay una carrera seleccionada
        if (carreraSeleccionada != null) {
            int index = selector.nombreCircuitos.indexOf(carreraSeleccionada);
            if (index >= 0) {
                return selector.idCircuitos.get(index);
            }
        }
        
        return null;
    }
}