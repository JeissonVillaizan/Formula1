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

public class ListarCarreras {
    
    private static final String CIRCUITOS_FILE = "datos_f1/circuitos_f1_2024.json";
    private List<String> nombreCircuitos;
    private List<String> paisesCircuitos;
    
    /**
     * Constructor que carga los datos de los circuitos desde el archivo JSON.
     */
    public ListarCarreras() {
        cargarCircuitos();
    }
    
    /**
     * Carga los datos de los circuitos desde el archivo JSON.
     */
    private void cargarCircuitos() {
        nombreCircuitos = new ArrayList<>();
        paisesCircuitos = new ArrayList<>();
        
        try (Reader reader = new FileReader(CIRCUITOS_FILE)) {
            // Parsear el JSON usando Gson
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            
            // Navegar por la estructura JSON para obtener los circuitos
            JsonObject mrData = jsonObject.getAsJsonObject("MRData");
            JsonObject circuitTable = mrData.getAsJsonObject("CircuitTable");
            JsonArray circuits = circuitTable.getAsJsonArray("Circuits");
            
            // Recorrer todos los circuitos y guardar sus nombres
            for (JsonElement circuit : circuits) {
                JsonObject circuitObj = circuit.getAsJsonObject();
                String circuitName = circuitObj.get("circuitName").getAsString();
                
                JsonObject location = circuitObj.getAsJsonObject("Location");
                String country = location.get("country").getAsString();
                String locality = location.get("locality").getAsString();
                
                nombreCircuitos.add(circuitName);
                paisesCircuitos.add(locality + ", " + country);
            }
            
            System.out.println("Se han cargado " + nombreCircuitos.size() + " circuitos.");
            
        } catch (IOException e) {
            System.err.println("Error al cargar los datos de circuitos: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Método estático que muestra todos los circuitos disponibles.
     */
    public static void ListarCarreras() {
        ListarCarreras listaCarreras = new ListarCarreras();
        listaCarreras.mostrarCircuitos();
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
    

}