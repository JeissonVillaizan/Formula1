package edu.ucompensar.ClasesMenu;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImprimirPilotosEscuderia {

    private static final String PILOTOS_FILE = "datos_f1/pilotos_por_escuderia_f1_2024.json";
    private Map<String, List<String>> pilotosPorEscuderia;

    /**
     * Constructor que carga los datos de los pilotos por escudería desde el archivo JSON.
     */
    public ImprimirPilotosEscuderia() {
        cargarPilotosPorEscuderia();
    }

    /**
     * Carga los datos de los pilotos por escudería desde el archivo JSON.
     */
    private void cargarPilotosPorEscuderia() {
        pilotosPorEscuderia = new HashMap<>();

        try (Reader reader = new FileReader(PILOTOS_FILE)) {
            // Aquí se intentaría cargar los datos del archivo JSON
            System.out.println("Cargando datos de pilotos por escudería...");
        } catch (IOException e) {
            // Mostrar el mensaje solicitado si no se encuentra el archivo o hay un error
            System.out.println("No se encontró una API que recopile los datos de escuderías");
        }
    }

    /**
     * Método estático principal que permite seleccionar una escudería y mostrar sus pilotos.
     */
    public static void ImprimirPilotosEscuderia() {
        // Crear una instancia de la clase para cargar los datos
        new ImprimirPilotosEscuderia();
    }
}