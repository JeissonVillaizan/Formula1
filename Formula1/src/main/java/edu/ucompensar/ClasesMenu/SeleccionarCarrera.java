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

/**
 * Clase que permite seleccionar una carrera de la temporada 2024 de F1.
 * Muestra la lista de circuitos y permite al usuario elegir uno.
 */
public class SeleccionarCarrera {
    
    private static final String CIRCUITOS_FILE = "datos_f1/circuitos_f1_2024.json";
    private static final String SEASON = "2024";
    
    private List<String> nombreCircuitos;
    private List<String> idCircuitos;
    private List<String> paisesCircuitos;
    private static String carreraSeleccionada;
    
    /**
     * Constructor que carga los datos de los circuitos desde el archivo JSON.
     */
    public SeleccionarCarrera() {
        cargarCircuitos();
    }
    
    /**
     * Carga los datos de los circuitos desde el archivo JSON.
     */
    private void cargarCircuitos() {
        nombreCircuitos = new ArrayList<>();
        idCircuitos = new ArrayList<>();
        paisesCircuitos = new ArrayList<>();
        
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
                String circuitId = circuitObj.get("circuitId").getAsString();
                
                JsonObject location = circuitObj.getAsJsonObject("Location");
                String country = location.get("country").getAsString();
                String locality = location.get("locality").getAsString();
                
                nombreCircuitos.add(circuitName);
                idCircuitos.add(circuitId);
                paisesCircuitos.add(locality + ", " + country);
            }
            
        } catch (IOException e) {
            System.err.println("Error al cargar los datos de circuitos: " + e.getMessage());
        }
    }
    
    /**
     * Muestra la lista de circuitos y permite al usuario seleccionar uno.
     * 
     * @return El nombre de la carrera seleccionada o null si hubo un error
     */
    public int seleccionarCarrera() {
        mostrarCircuitos();
        
        
        Scanner scanner = new Scanner(System.in);
        int seleccion = -1; 
        
        while (seleccion < 1 || seleccion > nombreCircuitos.size()) {
            System.out.print("\nSeleccione un circuito (1-" + nombreCircuitos.size() + "): ");
            try {
                seleccion = scanner.nextInt();
                if (seleccion < 1 || seleccion > nombreCircuitos.size()) {
                    System.out.println("Número inválido. Intente nuevamente.");
                }
            } catch (Exception e) {
                System.out.println("Entrada inválida. Ingrese un número.");
                scanner.nextLine(); // Limpiar buffer
            }
        }
        

        System.out.println("\nHa seleccionado la opcion : " + seleccion);
        
        return seleccion;
    }
    



    /**
     * Muestra la lista de circuitos con su número correspondiente.
     */
    private void mostrarCircuitos() {
        System.out.println("\n===== CIRCUITOS FORMULA 1 - TEMPORADA 2024 =====\n");
        
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
     * Obtiene el ID del circuito para la carrera seleccionada actualmente.
     * 
     * @return El ID del circuito o null si no hay carrera seleccionada
     */
    public String getIdCarreraSeleccionada() {
        if (carreraSeleccionada == null) {
            return null;
        }
        
        int index = nombreCircuitos.indexOf(carreraSeleccionada);
        if (index >= 0) {
            return idCircuitos.get(index);
        }
        
        return null;
    }
    
    /**
     * Devuelve el nombre de la carrera seleccionada actualmente.
     * 
     * @return El nombre de la carrera o null si no hay carrera seleccionada
     */
    public static String getCarreraSeleccionada() {
        return carreraSeleccionada;
    }
    
    /**
     * Método estático para obtener una instancia y seleccionar una carrera.
     * 
     * @return El nombre de la carrera seleccionada
     */
    public static int seleccionarCarreraStatic() {
        SeleccionarCarrera selector = new SeleccionarCarrera();
        return selector.seleccionarCarrera();
    }

    /**
     * Método estático para obtener el ID de la carrera seleccionada.
     * 
     * @return El ID de la carrera seleccionada o null si no hay selección
     */
    public static String getIdCarreraSeleccionadaStatic() {
        SeleccionarCarrera selector = new SeleccionarCarrera();
        
        // Si ya hay una carrera seleccionada
        if (carreraSeleccionada != null) {
            int index = selector.nombreCircuitos.indexOf(carreraSeleccionada);
            if (index >= 0) {
                return selector.idCircuitos.get(index);
            }
        }
        
        return null;
    }
}