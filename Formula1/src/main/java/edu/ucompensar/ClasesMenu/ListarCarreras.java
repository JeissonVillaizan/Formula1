package edu.ucompensar.ClasesMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.ucompensar.datamanagers.CircuitosDataManager;
import edu.ucompensar.model.Circuito;

public class ListarCarreras {
    
    private List<String> nombreCircuitos;
    private List<String> paisesCircuitos;
    
    /**
     * Constructor que carga los datos de los circuitos desde el CircuitosDataManager.
     */
    public ListarCarreras() {
        cargarCircuitos();
    }
    
    /**
     * Carga los datos de los circuitos desde el CircuitosDataManager.
     */
    private void cargarCircuitos() {
        nombreCircuitos = new ArrayList<>();
        paisesCircuitos = new ArrayList<>();
        
        // Obtener la instancia del gestor de datos de circuitos
        CircuitosDataManager circuitosManager = CircuitosDataManager.getInstance();
        List<Circuito> circuitos = circuitosManager.getCircuitos();
        
        // Recorrer todos los circuitos y guardar sus datos
        for (Circuito circuito : circuitos) {
            nombreCircuitos.add(circuito.getCircuitName());
            paisesCircuitos.add(circuito.getCountry());
        }
        
        System.out.println("Se han cargado " + nombreCircuitos.size() + " circuitos.");
    }
    
    /**
     * Método estático que muestra todos los circuitos disponibles.
     */
    public static void ListarCarreras() {
        ListarCarreras listaCarreras = new ListarCarreras();
        listaCarreras.mostrarCircuitos();
    }
    
    /**
     * Muestra la lista de circuitos con su número correspondiente.
     */
    public void mostrarCircuitos() {
        System.out.println("\n===== CARRERAS FORMULA 1 - TEMPORADA 2024 =====\n");
        
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
        
        // Agregar la pausa para continuar
        System.out.println("Presione Enter para continuar...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }
}