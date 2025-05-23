package edu.ucompensar.datamanagers.ResultadosDataManager;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import edu.ucompensar.API.RecoleccionDatosResultadosEquipo;
import edu.ucompensar.model.resultados.ResultadoEquipo;

/**
 * Clase que se encarga de cargar y gestionar los datos de resultados de equipos.
 * Sigue el patrón Singleton para asegurar una única instancia.
 */
public class ResultadosEquipoDataManager {
    
    private static ResultadosEquipoDataManager instance;
    private Map<String, List<ResultadoEquipo>> resultadosPorEquipo;
    
    /**
     * Constructor privado que inicializa la colección de resultados.
     */
    private ResultadosEquipoDataManager() {
        this.resultadosPorEquipo = new HashMap<>();
    }
    
    /**
     * Obtiene la instancia única del gestor de datos de resultados de equipos.
     * @return Instancia única de ResultadosEquipoDataManager
     */
    public static synchronized ResultadosEquipoDataManager getInstance() {
        if (instance == null) {
            instance = new ResultadosEquipoDataManager();
        }
        return instance;
    }
    
    /**
     * Carga los datos de resultados para un equipo específico.
     * Si los datos ya están cargados, devuelve los datos existentes.
     * 
     * @param constructorId ID del constructor/equipo
     * @return Lista de ResultadoEquipo con los datos cargados
     */
    public List<ResultadoEquipo> getResultadosPorEquipo(String constructorId) {
        // Si ya tenemos los resultados cargados, los devolvemos
        if (resultadosPorEquipo.containsKey(constructorId)) {
            return resultadosPorEquipo.get(constructorId);
        }
        
        // Si no, los cargamos desde el JSON
        String rutaArchivo = RecoleccionDatosResultadosEquipo.obtenerResultadosEquipo(constructorId);
        List<ResultadoEquipo> resultados = cargarResultadosDesdeJSON(rutaArchivo, constructorId);
        
        // Almacenamos en caché para futuras consultas
        if (resultados != null && !resultados.isEmpty()) {
            resultadosPorEquipo.put(constructorId, resultados);
        }
        
        return resultados;
    }
    
    /**
     * Carga los datos de resultados desde un archivo JSON.
     * 
     * @param rutaArchivo Ruta al archivo JSON
     * @param constructorId ID del constructor/equipo
     * @return Lista de ResultadoEquipo con los datos cargados o lista vacía si ocurre un error
     */
    private List<ResultadoEquipo> cargarResultadosDesdeJSON(String rutaArchivo, String constructorId) {
        List<ResultadoEquipo> resultados = new ArrayList<>();
        
        try (Reader reader = new FileReader(rutaArchivo)) {
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            
            // Extraer datos del JSON
            JsonObject mrData = jsonObject.getAsJsonObject("MRData");
            JsonObject raceTable = mrData.getAsJsonObject("RaceTable");
            JsonArray races = raceTable.getAsJsonArray("Races");
            
            // Si no hay carreras, retornamos lista vacía
            if (races.size() == 0) {
                return resultados;
            }
            
            // Procesar cada carrera
            for (JsonElement raceElement : races) {
                JsonObject race = raceElement.getAsJsonObject();
                
                String circuitName = race.getAsJsonObject("Circuit").get("circuitName").getAsString();
                JsonArray results = race.getAsJsonArray("Results");
                
                // Si hay menos de 2 pilotos, pasamos a la siguiente carrera
                if (results.size() < 2) {
                    continue;
                }
                
                // Creamos un nuevo objeto ResultadoEquipo
                ResultadoEquipo resultado = new ResultadoEquipo();
                
                // Establecer datos del equipo
                resultado.setConstructorId(constructorId);
                resultado.setCircuitName(circuitName);
                
                // Procesar datos de los pilotos (normalmente hay 2)
                JsonObject piloto1 = results.get(0).getAsJsonObject();
                JsonObject piloto2 = results.get(1).getAsJsonObject();
                
                // Nombre del equipo (igual para ambos pilotos)
                String constructorName = piloto1.getAsJsonObject("Constructor").get("name").getAsString();
                resultado.setConstructorName(constructorName);
                
                // Datos del primer piloto
                JsonObject driver1 = piloto1.getAsJsonObject("Driver");
                resultado.setGivenName1(driver1.get("givenName").getAsString());
                resultado.setFamilyName1(driver1.get("familyName").getAsString());
                resultado.setPosicionCarrera1(piloto1.get("position").getAsInt());
                resultado.setPuntosCarrera1(piloto1.get("points").getAsDouble());
                
                // Datos del segundo piloto
                JsonObject driver2 = piloto2.getAsJsonObject("Driver");
                resultado.setGivenName2(driver2.get("givenName").getAsString());
                resultado.setFamilyName2(driver2.get("familyName").getAsString());
                resultado.setPosicionCarrera2(piloto2.get("position").getAsInt());
                resultado.setPuntosCarrera2(piloto2.get("points").getAsDouble());
                
                // Añadir a la lista de resultados
                resultados.add(resultado);
            }
            
            return resultados;
            
        } catch (IOException e) {
            System.err.println("Error al cargar los resultados del equipo: " + e.getMessage());
            return resultados; // Devolvemos lista vacía
        }
    }
    
    /**
     * Obtiene un resultado específico de un equipo en un circuito.
     * 
     * @param constructorId ID del constructor/equipo
     * @param circuitName Nombre del circuito
     * @return ResultadoEquipo o null si no se encuentra
     */
    public ResultadoEquipo getResultadoEquipoEnCircuito(String constructorId, String circuitName) {
        List<ResultadoEquipo> resultados = getResultadosPorEquipo(constructorId);
        
        if (resultados != null) {
            for (ResultadoEquipo resultado : resultados) {
                if (resultado.getCircuitName().equalsIgnoreCase(circuitName)) {
                    return resultado;
                }
            }
        }
        
        return null;
    }
    
    /**
     * Obtiene un mapa con todos los resultados de equipos cargados.
     * 
     * @return Map con los resultados por equipos
     */
    public Map<String, List<ResultadoEquipo>> getTodosResultadosPorEquipo() {
        return resultadosPorEquipo;
    }
    
    /**
     * Calcula los puntos totales de un equipo sumando los de todas sus carreras.
     * 
     * @param constructorId ID del constructor/equipo
     * @return Total de puntos acumulados
     */
    public double calcularPuntosTotalesEquipo(String constructorId) {
        List<ResultadoEquipo> resultados = getResultadosPorEquipo(constructorId);
        double totalPuntos = 0;
        
        if (resultados != null) {
            for (ResultadoEquipo resultado : resultados) {
                totalPuntos += resultado.getTotalPuntosEquipo();
            }
        }
        
        return totalPuntos;
    }
    
    /**
     * Limpia la caché de resultados.
     */
    public void limpiarCache() {
        resultadosPorEquipo.clear();
    }
}