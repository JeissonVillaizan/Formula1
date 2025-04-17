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

public class ImprimirInformacionCircuito {
    
    private static final String CIRCUITOS_FILE = "datos_f1/circuitos_f1_2024.json";
    private List<String> nombreCircuitos;
    private List<String> paisesCircuitos;
    private List<String> latitudes;
    private List<String> longitudes;
    private List<String> urls;
    
    /**
     * Constructor que carga los datos de los circuitos desde el archivo JSON.
     */
    public ImprimirInformacionCircuito() {
        cargarCircuitos();
    }
    
    /**
     * Carga los datos de los circuitos desde el archivo JSON.
     */
    private void cargarCircuitos() {
        nombreCircuitos = new ArrayList<>();
        paisesCircuitos = new ArrayList<>();
        latitudes = new ArrayList<>();
        longitudes = new ArrayList<>();
        urls = new ArrayList<>();
        
        try (Reader reader = new FileReader(CIRCUITOS_FILE)) {
            // Parsear el JSON usando Gson
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            
            // Navegar por la estructura JSON para obtener los circuitos
            JsonObject mrData = jsonObject.getAsJsonObject("MRData");
            JsonObject circuitTable = mrData.getAsJsonObject("CircuitTable");
            JsonArray circuits = circuitTable.getAsJsonArray("Circuits");
            
            // Recorrer todos los circuitos y guardar sus datos
            for (JsonElement circuit : circuits) {
                JsonObject circuitObj = circuit.getAsJsonObject();
                String circuitName = circuitObj.get("circuitName").getAsString();
                
                JsonObject location = circuitObj.getAsJsonObject("Location");
                String country = location.get("country").getAsString();
                String locality = location.get("locality").getAsString();
                String latitude = location.get("lat").getAsString();
                String longitude = location.get("long").getAsString();
                String url = circuitObj.get("url").getAsString();
                
                nombreCircuitos.add(circuitName);
                paisesCircuitos.add(locality + ", " + country);
                latitudes.add(latitude);
                longitudes.add(longitude);
                urls.add(url);
            }
            
            System.out.println("Se han cargado " + nombreCircuitos.size() + " circuitos.");
            
        } catch (IOException e) {
            System.err.println("Error al cargar los datos de circuitos: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Permite al usuario seleccionar un circuito y muestra su información.
     */
    public void seleccionarEImprimirCircuito() {
        Scanner scanner = new Scanner(System.in);
        mostrarCircuitos(); // Mostrar la lista de circuitos disponibles
        
        System.out.print("Seleccione el número del circuito: ");
        int seleccion = scanner.nextInt(); // Leer la selección del usuario
        
        mostrarInformacionCircuito(seleccion); // Mostrar la información del circuito seleccionado
    }
    
    /**
     * Muestra la lista de circuitos con su número correspondiente.
     */
    public void mostrarCircuitos() {
        System.out.println("\n===== CARRERAS FORMULA 1 - TEMPORADA 2024 =====\n");
        
        if (nombreCircuitos.isEmpty()) {
            System.out.println("No se encontraron circuitos para mostrar.");
            return;
        }
        
        System.out.printf("%-3s %-45s %-25s\n", "N°", "NOMBRE DEL CIRCUITO", "UBICACIÓN");
        System.out.println("-------------------------------------------------------------------------------------");
        
        for (int i = 0; i < nombreCircuitos.size(); i++) {
            System.out.printf("%-3d %-45s %-25s\n", 
                (i + 1), 
                nombreCircuitos.get(i), 
                paisesCircuitos.get(i));
        }
        
        System.out.println("\n===================================================\n");
    }
    
    /**
     * Muestra la información detallada de un circuito seleccionado.
     * @param indice Índice del circuito seleccionado (basado en 1).
     */
    public void mostrarInformacionCircuito(int indice) {
        if (indice < 1 || indice > nombreCircuitos.size()) {
            System.out.println("Índice inválido. Por favor, seleccione un circuito válido.");
            return;
        }
        
        int i = indice - 1; // Convertir a índice basado en 0
        System.out.println("\n===== INFORMACIÓN DEL CIRCUITO =====");
        System.out.println("Nombre: " + nombreCircuitos.get(i));
        System.out.println("Ubicación: " + paisesCircuitos.get(i));
        System.out.println("Latitud: " + latitudes.get(i));
        System.out.println("Longitud: " + longitudes.get(i));
        System.out.println("URL: " + urls.get(i));
        System.out.println("====================================\n");
    }
}