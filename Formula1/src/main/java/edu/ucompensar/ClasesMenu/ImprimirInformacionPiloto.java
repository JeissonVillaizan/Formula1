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

public class ImprimirInformacionPiloto {

    private static final String PILOTOS_FILE = "datos_f1/conductores_f1_2024.json";
    private List<String> nombrePilotos;
    private List<String> nacionalidadesPilotos;
    private List<String> fechasNacimientoPilotos;
    private List<String> idsPilotos;

    /**
     * Constructor que carga los datos de los pilotos desde el archivo JSON.
     */
    public ImprimirInformacionPiloto() {
        cargarPilotos();
    }

    /**
     * Carga los datos de los pilotos desde el archivo JSON.
     */
    private void cargarPilotos() {
        nombrePilotos = new ArrayList<>();
        nacionalidadesPilotos = new ArrayList<>();
        fechasNacimientoPilotos = new ArrayList<>();
        idsPilotos = new ArrayList<>();

        try (Reader reader = new FileReader(PILOTOS_FILE)) {
            // Parsear el JSON usando Gson
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);

            // Navegar por la estructura JSON para obtener los pilotos
            JsonObject mrData = jsonObject.getAsJsonObject("MRData");
            JsonObject driverTable = mrData.getAsJsonObject("DriverTable");
            JsonArray drivers = driverTable.getAsJsonArray("Drivers");

            // Recorrer todos los pilotos y guardar sus datos
            for (JsonElement driver : drivers) {
                JsonObject driverObj = driver.getAsJsonObject();
                String driverName = driverObj.get("givenName").getAsString() + " " +
                                    driverObj.get("familyName").getAsString();
                String nationality = driverObj.get("nationality").getAsString();
                String dateOfBirth = driverObj.get("dateOfBirth").getAsString();
                String driverId = driverObj.get("driverId").getAsString();

                nombrePilotos.add(driverName);
                nacionalidadesPilotos.add(nationality);
                fechasNacimientoPilotos.add(dateOfBirth);
                idsPilotos.add(driverId);
            }

            System.out.println("Se han cargado " + nombrePilotos.size() + " pilotos.");

        } catch (IOException e) {
            System.err.println("Error al cargar los datos de pilotos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Método estático principal que permite seleccionar un piloto y mostrar su información.
     */
    public static void ImprimirInformacionPiloto() {
        ImprimirInformacionPiloto imprimir = new ImprimirInformacionPiloto();
        imprimir.seleccionarYMostrarPiloto();
    }

    /**
     * Permite al usuario seleccionar un piloto y muestra su información.
     */
    public void seleccionarYMostrarPiloto() {
        Scanner scanner = new Scanner(System.in);
        mostrarPilotos(); // Mostrar la lista de pilotos disponibles

        System.out.print("Seleccione el número del piloto: ");
        int seleccion = scanner.nextInt(); // Leer la selección del usuario

        if (seleccion < 1 || seleccion > nombrePilotos.size()) {
            System.out.println("Número de piloto inválido.");
            return;
        }

        // Mostrar la información del piloto seleccionado
        mostrarInformacionPiloto(seleccion);
    }

    /**
     * Muestra la lista de pilotos con su número correspondiente.
     */
    public void mostrarPilotos() {
        System.out.println("\n===== LISTA DE PILOTOS =====");

        if (nombrePilotos.isEmpty()) {
            System.out.println("No se encontraron pilotos para mostrar.");
            return;
        }

        for (int i = 0; i < nombrePilotos.size(); i++) {
            System.out.printf("%-3d %s\n", (i + 1), nombrePilotos.get(i));
        }
    }

    /**
     * Muestra la información detallada de un piloto seleccionado.
     * @param indice Índice del piloto seleccionado (basado en 1).
     */
    public void mostrarInformacionPiloto(int indice) {
        int i = indice - 1; // Convertir a índice basado en 0

        System.out.println("\n===== INFORMACIÓN DEL PILOTO =====");
        System.out.println("Nombre: " + nombrePilotos.get(i));
        System.out.println("Nacionalidad: " + nacionalidadesPilotos.get(i));
        System.out.println("Fecha de Nacimiento: " + fechasNacimientoPilotos.get(i));
        System.out.println("ID del Piloto: " + idsPilotos.get(i));
        System.out.println("===================================");
    }
}