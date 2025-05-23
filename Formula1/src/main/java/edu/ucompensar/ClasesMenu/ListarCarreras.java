package edu.ucompensar.ClasesMenu;

import java.util.List;
import java.util.Scanner;

import edu.ucompensar.datamanagers.CircuitosDataManager;
import edu.ucompensar.model.Circuito;

/**
 * Clase para listar las carreras (circuitos) de Fórmula 1.
 */
public class ListarCarreras {
    
    /**
     * Método estático que muestra todos los circuitos disponibles.
     * Este método es llamado desde el menú principal.
     */
    public static void ListarCarreras() {
        // Obtener el gestor de datos de circuitos
        CircuitosDataManager circuitosManager = CircuitosDataManager.getInstance();
        
        // Obtener la lista de todos los circuitos
        List<Circuito> circuitos = circuitosManager.getCircuitos();
        
        // Mostrar la información en formato tabla
        mostrarCircuitosEnTabla(circuitos);
    }
    
    /**
     * Muestra una lista de circuitos en formato de tabla.
     * 
     * @param circuitos Lista de circuitos a mostrar
     */
    private static void mostrarCircuitosEnTabla(List<Circuito> circuitos) {
        System.out.println("\n===== CARRERAS FORMULA 1 - TEMPORADA 2024 =====\n");
        
        if (circuitos.isEmpty()) {
            System.out.println("No se encontraron circuitos para mostrar.");
            return;
        }
        
        System.out.printf("%-3s %-45s %-25s %-15s\n", "N°", "NOMBRE DEL CIRCUITO", "UBICACIÓN", "FECHA");
        System.out.println("-------------------------------------------------------------------------------------");
        
        int contador = 1;
        for (Circuito circuito : circuitos) {
            System.out.printf("%-3d %-45s %-25s %-15s\n", 
                contador++, 
                circuito.getCircuitName(), 
                circuito.getCountry(),
                (circuito.getFechaCarrera() != null ? circuito.getFechaCarrera() : "No disponible"));
        }
        
        System.out.println("\n===================================================\n");
        
        // Agregar la pausa para continuar
        System.out.println("Presione Enter para continuar...");
        new Scanner(System.in).nextLine();
    }
}