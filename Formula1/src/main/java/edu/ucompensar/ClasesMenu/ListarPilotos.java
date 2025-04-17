package edu.ucompensar.ClasesMenu;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class ListarPilotos {
    
    private static final String PILOTOS_FILE = "datos_f1/conductores_f1_2024.json";
    private List<String> nombrePilotos;

    /**
     * Constructor que carga los datos de los pilotos desde el archivo JSON.
     */
    public ListarPilotos() {
        cargarPilotos();
    }

    /**
     * Carga los datos de los pilotos desde el archivo JSON.
     */
    private void cargarPilotos() {
        nombrePilotos = new ArrayList<>();

        try (Reader reader = new FileReader(PILOTOS_FILE)) {
            // Parsear el JSON usando Gson
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);

            // Navegar por la estructura JSON para obtener los pilotos
            JsonObject mrData = jsonObject.getAsJsonObject("MRData");
            JsonObject driverTable = mrData.getAsJsonObject("DriverTable");
            JsonArray drivers = driverTable.getAsJsonArray("Drivers");

            // Recorrer todos los pilotos y guardar sus nombres
            for (JsonElement driver : drivers) {
                JsonObject driverObj = driver.getAsJsonObject();
                String driverName = driverObj.get("givenName").getAsString() + " " +
                                    driverObj.get("familyName").getAsString();
                nombrePilotos.add(driverName);
            }

            System.out.println("Se han cargado " + nombrePilotos.size() + " pilotos.");

        } catch (IOException e) {
            System.err.println("Error al cargar los datos de pilotos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Muestra la lista de pilotos con su número correspondiente.
     */
    public void listarPilotos() {
        System.out.println("\n===== LISTA DE PILOTOS =====");

        if (nombrePilotos.isEmpty()) {
            System.out.println("No se encontraron pilotos para mostrar.");
            return;
        }

        for (int i = 0; i < nombrePilotos.size(); i++) {
            System.out.printf("%-3d %s\n", (i + 1), nombrePilotos.get(i));
        }
    }
}