package edu.ucompensar.datamanagers;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import edu.ucompensar.model.Circuito;

/**
 * Clase que se encarga de cargar y gestionar los datos de los circuitos.
 */
public class CircuitosDataManager {
    
    private static final String CIRCUITOS_FILE = "datos_f1/circuitos_f1_2024.json";
    private static CircuitosDataManager instance;
    
    private List<Circuito> circuitos;
    private Map<String, Circuito> circuitosPorId;
    
    /**
     * Constructor privado que carga los datos de los circuitos.
     */
    private CircuitosDataManager() {
        cargarCircuitos();
    }
    
    /**
     * Obtiene la instancia única del gestor de datos de circuitos.
     */
    public static synchronized CircuitosDataManager getInstance() {
        if (instance == null) {
            instance = new CircuitosDataManager();
        }
        return instance;
    }
    
    /**
     * Carga los datos de los circuitos desde el archivo JSON.
     * Maneja la estructura de array en el nivel raíz.
     */
    private void cargarCircuitos() {
        circuitos = new ArrayList<>();
        circuitosPorId = new HashMap<>();
        
        try (Reader reader = new FileReader(CIRCUITOS_FILE)) {
            Gson gson = new Gson();
            
            // Cargar el array de objetos en lugar de un objeto único
            JsonArray jsonArray = gson.fromJson(reader, JsonArray.class);
            
            // Tomamos solo el primer elemento del array que contiene todos los datos
            // (Los elementos posteriores parecen ser duplicados con menos información)
            if (jsonArray.size() > 0) {
                JsonObject jsonObject = jsonArray.get(0).getAsJsonObject();
                
                JsonObject mrData = jsonObject.getAsJsonObject("MRData");
                JsonObject circuitTable = mrData.getAsJsonObject("CircuitTable");
                JsonArray circuits = circuitTable.getAsJsonArray("Circuits");
                
                for (JsonElement element : circuits) {
                    JsonObject circuitObj = element.getAsJsonObject();
                    
                    // Crear un nuevo objeto Circuito
                    Circuito circuito = new Circuito();
                    
                    // Leer todos los datos disponibles en el JSON
                    circuito.setCircuitId(circuitObj.get("circuitId").getAsString());
                    circuito.setCircuitName(circuitObj.get("circuitName").getAsString());
                    
                    // Ubicación
                    JsonObject location = circuitObj.getAsJsonObject("Location");
                    circuito.setCountry(location.get("country").getAsString());
                    
                    // Fechas - asumimos que están en formato ISO (YYYY-MM-DD)
                    if (circuitObj.has("date")) {
                        String fechaStr = circuitObj.get("date").getAsString();
                        circuito.setFechaCarrera(LocalDate.parse(fechaStr)); 
                    }
                    
                    if (circuitObj.has("sprintDate")) {
                        String fechaSprintStr = circuitObj.get("sprintDate").getAsString();
                        circuito.setFechaSprint(LocalDate.parse(fechaSprintStr));
                    }
                    
                    // Detalles técnicos
                    if (circuitObj.has("laps")) {
                        circuito.setNumeroVueltas(circuitObj.get("laps").getAsInt());
                    }
                    
                    if (circuitObj.has("length")) {
                        circuito.setLongitud(circuitObj.get("length").getAsDouble());
                    }
                    
                    // Añadir a las colecciones
                    circuitos.add(circuito);
                    circuitosPorId.put(circuito.getCircuitId(), circuito);
                }
            }
            
        } catch (IOException e) {
            System.err.println("Error al cargar datos: " + e.getMessage());
        }
    }
    
    /**
     * Obtiene todos los circuitos.
     */
    public List<Circuito> getCircuitos() {
        return circuitos;
    }
    
    /**
     * Busca un circuito por su ID.
     */
    public Circuito getCircuitoPorId(String circuitId) {
        return circuitosPorId.get(circuitId);
    }
}