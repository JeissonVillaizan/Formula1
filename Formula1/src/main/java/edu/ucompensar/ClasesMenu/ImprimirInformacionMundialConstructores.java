package edu.ucompensar.ClasesMenu;

import edu.ucompensar.model.resultados.ResultadoCircuito;
import edu.ucompensar.datamanagers.ResultadosDataManager.ResultadosCircuitoDataManager;

import java.util.*;

public class ImprimirInformacionMundialConstructores {

    public void imprimirInformacionMundialConstructores(String carreraSeleccionada) {
        // Obtener el gestor de datos de resultados por circuito
        ResultadosCircuitoDataManager manager = ResultadosCircuitoDataManager.getInstance();

        // Obtener los resultados del circuito seleccionado
        ResultadoCircuito resultadoCircuito = manager.getResultadoCircuito(carreraSeleccionada);


        // Obtener las puntuaciones de los equipos
        Map<String, Double> puntuacionEscuderia = resultadoCircuito.getPuntuacionEscuderia();



        // Ordenar los equipos por puntuación de forma descendente
        List<Map.Entry<String, Double>> listaOrdenada = new ArrayList<>(puntuacionEscuderia.entrySet());
        listaOrdenada.sort((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()));

        // Imprimir la información de los equipos
        System.out.println("\n===== INFORMACIÓN DEL MUNDIAL DE CONSTRUCTORES EN ESTA CARRERA =====");
        System.out.printf("%-30s %-10s\n", "Equipo", "Puntos");
        System.out.println("---------------------------------------------------");
        for (Map.Entry<String, Double> entry : listaOrdenada) {
            System.out.printf("%-30s %-10.2f\n", entry.getKey(), entry.getValue());
        }
    }
}