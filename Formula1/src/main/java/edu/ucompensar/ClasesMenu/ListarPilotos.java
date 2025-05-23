package edu.ucompensar.ClasesMenu;

import java.util.List;
import java.util.Scanner;

import edu.ucompensar.datamanagers.PilotosDataManager;
import edu.ucompensar.model.Piloto;

/**
 * Clase para listar los pilotos de Fórmula 1.
 */
public class ListarPilotos {
    
    /**
     * Método estático que muestra todos los pilotos disponibles.
     * Este método es llamado desde el menú principal.
     */
    public static void ListarPilotos() {
        // Obtener el gestor de datos de pilotos
        PilotosDataManager pilotosManager = PilotosDataManager.getInstance();
        
        // Obtener la lista de todos los pilotos
        List<Piloto> pilotos = pilotosManager.getPilotos();
        
        // Mostrar la información en formato tabla
        mostrarPilotosEnTabla(pilotos);
    }
    
    /**
     * Muestra una lista de pilotos en formato de tabla.
     * 
     * @param pilotos Lista de pilotos a mostrar
     */
    private static void mostrarPilotosEnTabla(List<Piloto> pilotos) {
        System.out.println("\n===== PILOTOS FORMULA 1 - TEMPORADA 2024 =====\n");

        if (pilotos.isEmpty()) {
            System.out.println("No se encontraron pilotos para mostrar.");
            return;
        }
        
        System.out.printf("%-3s %-30s %-20s %-10s\n", "N°", "NOMBRE", "NACIONALIDAD", "EQUIPO");
        System.out.println("------------------------------------------------------------------");

        int contador = 1;
        for (Piloto piloto : pilotos) {
            System.out.printf("%-3d %-30s %-20s %-10s\n", 
                contador++, 
                piloto.getNombre() + " " + piloto.getApellido(),
                piloto.getPaisOrigen(),
                piloto.getEquipoActual());
        }
        
        System.out.println("\n===================================================\n");
        
        // Agregar la pausa para continuar
        System.out.println("Presione Enter para continuar...");
        new Scanner(System.in).nextLine();
    }
}