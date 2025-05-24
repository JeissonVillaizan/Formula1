package edu.ucompensar.ClasesMenu;


import edu.ucompensar.datamanagers.ResultadosDataManager.ResultadosCircuitoDataManager;
import edu.ucompensar.model.resultados.ResultadoCircuito;

import java.util.*;

public class ImprimirInformacionMundialPilotos {

    public static void imprimirInformacionMundialPilotos(String carreraSeleccionada) {
        // Obtener el gestor de datos de resultados por circuito
        ResultadosCircuitoDataManager manager = ResultadosCircuitoDataManager.getInstance();

        // Obtener los resultados del circuito seleccionado
        ResultadoCircuito resultadoCircuito = manager.getResultadoCircuito(carreraSeleccionada);

        // Manejar el caso en que no se encuentren resultados
        if (resultadoCircuito == null) {
            System.out.println("No se encontraron resultados para la carrera seleccionada: " + carreraSeleccionada);
            esperarEnter();
            return;
        }

        // Obtener las puntuaciones de los pilotos
        Map<String, Double> puntuacionPilotos = new HashMap<>();
        List<String> nombresPilotos = resultadoCircuito.getPosicionPiloto();
        List<Double> puntosPilotos = resultadoCircuito.getPuntuacionPiloto();

        for (int i = 0; i < nombresPilotos.size(); i++) {
            puntuacionPilotos.put(nombresPilotos.get(i), puntosPilotos.get(i));
        }

        // Ordenar los pilotos por puntuación de forma descendente
        List<Map.Entry<String, Double>> listaOrdenada = new ArrayList<>(puntuacionPilotos.entrySet());
        listaOrdenada.sort((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()));

        // Imprimir la información de los pilotos con su posición
        System.out.println("\n===== INFORMACIÓN DEL MUNDIAL DE PILOTOS EN ESTA CARRERA =====");
        System.out.printf("%-10s %-30s %-10s\n", "Posición", "Piloto", "Puntos");
        System.out.println("-------------------------------------------------------------");

        int posicion = 1;
        for (Map.Entry<String, Double> entry : listaOrdenada) {
            System.out.printf("%-10d %-30s %-10.2f\n", posicion, entry.getKey(), entry.getValue());
            posicion++;
        }

        // Esperar a que el usuario presione Enter
        esperarEnter();
    }

    private static void esperarEnter() {
        System.out.println("\nPresiona Enter para continuar...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }
}