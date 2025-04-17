package edu.ucompensar.ClasesMenu;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ImprimirInformacionEscuderia {

    private static final String ESCUDERIAS_FILE = "datos_f1/constructores_f1_2024.json";
    private List<String> nombreEscuderias;
    private List<String> nacionalidadesEscuderias;
    private List<String> idsEscuderias;

    /**
     * Constructor que carga los datos de las escuderías desde el archivo JSON.
     */
    public ImprimirInformacionEscuderia() {
        cargarEscuderias();
    }

    /**
     * Carga los datos de las escuderías desde el archivo JSON.
     */
    private void cargarEscuderias() {
        nombreEscuderias = new ArrayList<>();
        nacionalidadesEscuderias = new ArrayList<>();
        idsEscuderias = new ArrayList<>();

        try (Reader reader = new FileReader(ESCUDERIAS_FILE)) {
            // Parsear el JSON usando Gson
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);

            // Navegar por la estructura JSON para obtener las escuderías
            JsonObject mrData = jsonObject.getAsJsonObject("MRData");
            JsonObject constructorTable = mrData.getAsJsonObject("ConstructorTable");
            JsonArray constructors = constructorTable.getAsJsonArray("Constructors");

            // Recorrer todas las escuderías y guardar sus datos
            for (JsonElement constructor : constructors) {
                JsonObject constructorObj = constructor.getAsJsonObject();
                String constructorName = constructorObj.get("name").getAsString();
                String nationality = constructorObj.get("nationality").getAsString();
                String constructorId = constructorObj.get("constructorId").getAsString();

                nombreEscuderias.add(constructorName);
                nacionalidadesEscuderias.add(nationality);
                idsEscuderias.add(constructorId);
            }

            System.out.println("Se han cargado " + nombreEscuderias.size() + " escuderías.");

        } catch (IOException e) {
            System.err.println("Error al cargar los datos de escuderías: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Método estático principal que permite seleccionar e imprimir la información de una escudería.
     */
    public static void ImprimirInformacionEscuderia() {
        ImprimirInformacionEscuderia imprimir = new ImprimirInformacionEscuderia();
        imprimir.seleccionarYMostrarEscuderia();
    }

    /**
     * Permite al usuario seleccionar una escudería y muestra su información.
     */
    public void seleccionarYMostrarEscuderia() {
        Scanner scanner = new Scanner(System.in);
        mostrarEscuderias(); // Mostrar la lista de escuderías disponibles

        System.out.print("Seleccione el número de la escudería: ");
        int seleccion = scanner.nextInt(); // Leer la selección del usuario

        if (seleccion < 1 || seleccion > nombreEscuderias.size()) {
            System.out.println("Número de escudería inválido.");
            return;
        }

        // Mostrar la información de la escudería seleccionada
        mostrarInformacionEscuderia(seleccion);
    }

    /**
     * Muestra la lista de escuderías con su número correspondiente.
     */
    public void mostrarEscuderias() {
        System.out.println("\n===== ESCUDERÍAS FORMULA 1 - TEMPORADA 2024 =====\n");

        if (nombreEscuderias.isEmpty()) {
            System.out.println("No se encontraron escuderías para mostrar.");
            return;
        }

        System.out.printf("%-3s %-30s %-20s %-15s\n", "N°", "NOMBRE", "NACIONALIDAD", "ID");
        System.out.println("--------------------------------------------------------------------------------");

        for (int i = 0; i < nombreEscuderias.size(); i++) {
            System.out.printf("%-3d %-30s %-20s %-15s\n",
                (i + 1),
                nombreEscuderias.get(i),
                nacionalidadesEscuderias.get(i),
                idsEscuderias.get(i));
        }

        System.out.println("\n===================================================\n");
    }

    /**
     * Muestra la información detallada de una escudería seleccionada.
     * @param indice Índice de la escudería seleccionada (basado en 1).
     */
    public void mostrarInformacionEscuderia(int indice) {
        int i = indice - 1; // Convertir a índice basado en 0

        System.out.println("\n===== INFORMACIÓN DE LA ESCUDERÍA =====");
        System.out.println("Nombre: " + nombreEscuderias.get(i));
        System.out.println("Nacionalidad: " + nacionalidadesEscuderias.get(i));
        System.out.println("ID: " + idsEscuderias.get(i));
        System.out.println("========================================");
    }
}