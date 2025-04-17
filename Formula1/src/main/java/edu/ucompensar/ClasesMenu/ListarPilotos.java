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
    private List<String> nacionalidadPilotos;
    private List<String> codigoPilotos;

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
        nacionalidadPilotos = new ArrayList<>();
        codigoPilotos = new ArrayList<>();

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
                String nationality = driverObj.get("nationality").getAsString();
                String code = driverObj.get("code").getAsString();
                
                nombrePilotos.add(driverName);
                nacionalidadPilotos.add(nationality);
                codigoPilotos.add(code);
            }

            System.out.println("Se han cargado " + nombrePilotos.size() + " pilotos.");

        } catch (IOException e) {
            System.err.println("Error al cargar los datos de pilotos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Método estático que muestra todos los pilotos disponibles.
     * Este método es llamado desde el menú principal.
     */
    public static void ListarPilotos() {
        ListarPilotos listaPilotos = new ListarPilotos();
        listaPilotos.mostrarPilotos();
    }
    
    /**
     * Muestra la lista de pilotos con su número correspondiente.
     */
    public void mostrarPilotos() {
        System.out.println("\n===== PILOTOS FORMULA 1 - TEMPORADA 2024 =====\n");

        if (nombrePilotos.isEmpty()) {
            System.out.println("No se encontraron pilotos para mostrar.");
            return;
        }
        
        System.out.printf("%-3s %-30s %-20s %-10s\n", "N°", "NOMBRE", "NACIONALIDAD", "CÓDIGO");
        System.out.println("------------------------------------------------------------------");

        for (int i = 0; i < nombrePilotos.size(); i++) {
            System.out.printf("%-3d %-30s %-20s %-10s\n", 
                (i + 1), 
                nombrePilotos.get(i), 
                nacionalidadPilotos.get(i),
                codigoPilotos.get(i));
        }
        
        System.out.println("\n===================================================\n");
    }
}