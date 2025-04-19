package edu.ucompensar.ClasesMenu;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class ImprimirConstructoresEscuderia {

    private static final String CONSTRUCTORES_FILE = "datos_f1/mundial_constructores_f1_2024.json";
    private List<String> nombreEscuderias;
    private List<Integer> puntosEscuderias;
    private List<Integer> posicionesEscuderias;

    /**
     * Constructor que carga los datos de los constructores desde el archivo JSON.
     */
    public ImprimirConstructoresEscuderia() {
        cargarConstructores();
    }

    /**
     * Carga los datos de los constructores desde el archivo JSON.
     */
    private void cargarConstructores() {
        nombreEscuderias = new ArrayList<>();
        puntosEscuderias = new ArrayList<>();
        posicionesEscuderias = new ArrayList<>();

        try (Reader reader = new FileReader(CONSTRUCTORES_FILE)) {
            // Aquí se intentaría cargar los datos del archivo JSON
            System.out.println("Cargando datos del mundial de constructores...");
        } catch (IOException e) {
            // Mostrar el mensaje solicitado si no se encuentra el archivo o hay un error
            System.out.println("No se encontró una API que recopile los datos de escuderías");
        }
    }

    /**
     * Método estático principal que permite seleccionar e imprimir la información del mundial de constructores para una escudería.
     */
    public static void ImprimirConstructoresEscuderia() {
        // Crear una instancia de la clase para cargar los datos
        new ImprimirConstructoresEscuderia();
    }
}

