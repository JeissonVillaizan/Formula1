package edu.ucompensar.ClasesMenu;

import java.util.Scanner;

import edu.ucompensar.datamanagers.CircuitosDataManager;
import edu.ucompensar.model.Circuito;

/**
 * Clase para mostrar información de un circuito.
 */
public class ImprimirInformacionCircuito {
    
    /**
     * Método estático para imprimir información de un circuito por su ID.
     * 
     * @param circuitId ID del circuito
     */
    public static void ImprimirInformacionCircuito(String circuitId) {
        // Obtener el gestor de datos
        CircuitosDataManager manager = CircuitosDataManager.getInstance();
        
        // Obtener el circuito usando el ID proporcionado
        Circuito circuito = manager.getCircuitoPorId(circuitId);
        
        if (circuito != null) {
            // Mostrar la información del circuito específico
            System.out.println("\n===== INFORMACIÓN DEL CIRCUITO =====");
            System.out.println("ID: " + circuito.getCircuitId());
            System.out.println("Nombre: " + circuito.getCircuitName());
            System.out.println("Ubicación: " + circuito.getCountry());
            System.out.println("Fecha de carrera: " + (circuito.getFechaCarrera() != null ? circuito.getFechaCarrera() : "No disponible"));
            System.out.println("Carrera sprint: " + (circuito.tieneSprint() ? "Sí, el " + circuito.getFechaSprint() : "No"));
            System.out.println("Número de vueltas: " + circuito.getNumeroVueltas());
            System.out.println("Longitud: " + circuito.getLongitud() + " km");
            System.out.println("Distancia total: " + circuito.getDistanciaTotal() + " km");
            System.out.println("====================================\n");
            
            // Esperar para que el usuario pueda leer la información
            System.out.println("Presione Enter para continuar...");
            new Scanner(System.in).nextLine();
        } else {
            System.out.println("No se encontró información para el circuito con ID: " + circuitId);
        }
    }
}