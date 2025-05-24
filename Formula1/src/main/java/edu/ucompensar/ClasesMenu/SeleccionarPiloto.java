package edu.ucompensar.ClasesMenu;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import edu.ucompensar.datamanagers.PilotosDataManager;
import edu.ucompensar.model.Piloto;

/**
 * Clase que permite seleccionar un piloto de la temporada 2024 de F1.
 * Muestra la lista de pilotos y permite al usuario elegir uno.
 */
public class SeleccionarPiloto {

    private List<String> nombresPilotos;
    private List<String> idsPilotos;
    private List<String> equiposPilotos;
    private static String pilotoSeleccionado;

    /**
     * Constructor que carga los datos de los pilotos desde el PilotosDataManager.
     */
    public SeleccionarPiloto() {
        cargarPilotos();
    }

    /**
     * Carga los datos de los pilotos desde el PilotosDataManager.
     */
    private void cargarPilotos() {
        nombresPilotos = new ArrayList<>();
        idsPilotos = new ArrayList<>();
        equiposPilotos = new ArrayList<>();

        // Obtener la instancia del gestor de datos de pilotos
        PilotosDataManager pilotosManager = PilotosDataManager.getInstance();
        List<Piloto> pilotos = pilotosManager.getPilotos();

        // Recorrer todos los pilotos y guardar sus datos
        for (Piloto piloto : pilotos) {
            nombresPilotos.add(piloto.getNombreCompleto());
            idsPilotos.add(piloto.getIdPiloto());
            equiposPilotos.add(piloto.getEquipo());
        }
    }

    /**
     * Muestra la lista de pilotos y permite al usuario seleccionar uno.
     * 
     * @return El ID del piloto seleccionado o null si hubo un error
     */
    public String seleccionarPiloto() {
        mostrarPilotos();

        Scanner scanner = new Scanner(System.in);
        int seleccion = -1;

        while (seleccion < 1 || seleccion > nombresPilotos.size()) {
            System.out.print("\nSeleccione un piloto (1-" + nombresPilotos.size() + "): ");
            try {
                seleccion = scanner.nextInt();
                if (seleccion < 1 || seleccion > nombresPilotos.size()) {
                    System.out.println("Número inválido. Intente nuevamente.");
                }
            } catch (Exception e) {
                System.out.println("Entrada inválida. Ingrese un número.");
                scanner.nextLine(); // Limpiar buffer
            }
        }

        // Obtener el piloto seleccionado (índice base 0)
        int index = seleccion - 1;
        pilotoSeleccionado = nombresPilotos.get(index);
        String pilotoId = idsPilotos.get(index);

        System.out.println("\nHa seleccionado: " + pilotoSeleccionado + 
                          " (" + equiposPilotos.get(index) + ")");

        return pilotoId;
    }

    /**
     * Muestra la lista de pilotos con su número correspondiente.
     */
    private void mostrarPilotos() {
        System.out.println("\n===== PILOTOS FORMULA 1 - TEMPORADA 2024 =====\n");

        if (nombresPilotos.isEmpty()) {
            System.out.println("No se encontraron pilotos para mostrar.");
            return;
        }

        System.out.printf("%-3s %-30s %-25s\n", "N°", "NOMBRE DEL PILOTO", "EQUIPO");
        System.out.println("-------------------------------------------------------------");

        for (int i = 0; i < nombresPilotos.size(); i++) {
            System.out.printf("%-3d %-30s %-25s\n", 
                (i + 1), 
                nombresPilotos.get(i), 
                equiposPilotos.get(i));
        }

        System.out.println("\n===================================================\n");
    }

    /**
     * Método estático para obtener una instancia y seleccionar un piloto.
     * 
     * @return El ID del piloto seleccionado
     */
    public static String seleccionarPilotoStatic() {
        SeleccionarPiloto selector = new SeleccionarPiloto();
        return selector.seleccionarPiloto();
    }

    /**
     * Método estático para obtener el ID del piloto seleccionado.
     * 
     * @return El ID del piloto seleccionado o null si no hay selección
     */
    public static String getIdPilotoSeleccionadoStatic() {
        SeleccionarPiloto selector = new SeleccionarPiloto();

        // Si ya hay un piloto seleccionado
        if (pilotoSeleccionado != null) {
            int index = selector.nombresPilotos.indexOf(pilotoSeleccionado);
            if (index >= 0) {
                return selector.idsPilotos.get(index);
            }
        }

        return null;
    }
}