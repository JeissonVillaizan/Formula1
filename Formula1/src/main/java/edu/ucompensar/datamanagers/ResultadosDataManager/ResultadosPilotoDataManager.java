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

import edu.ucompensar.API.RecoleccionDatosResultadoPiloto;
import edu.ucompensar.model.resultados.ResultadoPiloto;

/**
 * Clase que se encarga de cargar y gestionar los datos de resultados de pilotos.
 * Sigue el patrón Singleton para asegurar una única instancia.
 */
public class ResultadosPilotoDataManager {
    
    private static ResultadosPilotoDataManager instance;
    private Map<String, List<ResultadoPiloto>> resultadosPorPiloto;
    
    /**
     * Constructor privado que inicializa la colección de resultados.
     */
    private ResultadosPilotoDataManager() {
        this.resultadosPorPiloto = new HashMap<>();
    }
    
    /**
     * Obtiene la instancia única del gestor de datos de resultados de pilotos.
     * @return Instancia única de ResultadosPilotoDataManager
     */
    public static synchronized ResultadosPilotoDataManager getInstance() {
        if (instance == null) {
            instance = new ResultadosPilotoDataManager();
        }
        return instance;
    }
    
    /**
     * Carga los datos de resultados para un piloto específico.
     * Si los datos ya están cargados, devuelve los datos existentes.
     * 
     * @param pilotoId ID del piloto
     * @return Lista de ResultadoPiloto con los datos cargados
     */
    public List<ResultadoPiloto> getResultadosPorPiloto(String pilotoId) {
        // Si ya tenemos los resultados cargados, los devolvemos
        if (resultadosPorPiloto.containsKey(pilotoId)) {
            return resultadosPorPiloto.get(pilotoId);
        }
        
        // Si no, los cargamos desde el JSON
        String rutaArchivo = RecoleccionDatosResultadoPiloto.obtenerResultadosPiloto(pilotoId);
        List<ResultadoPiloto> resultados = cargarResultadosDesdeJSON(rutaArchivo, pilotoId);
        
        // Almacenamos en caché para futuras consultas
        if (resultados != null && !resultados.isEmpty()) {
            resultadosPorPiloto.put(pilotoId, resultados);
        }
        
        return resultados;
    }
    
    /**
     * Carga los datos de resultados desde un archivo JSON.
     * 
     * @param rutaArchivo Ruta al archivo JSON
     * @param pilotoId ID del piloto
     * @return Lista de ResultadoPiloto con los datos cargados o lista vacía si ocurre un error
     */
    private List<ResultadoPiloto> cargarResultadosDesdeJSON(String rutaArchivo, String pilotoId) {
        List<ResultadoPiloto> resultados = new ArrayList<>();
        
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
                
                // Obtener ID del circuito
                JsonObject circuit = race.getAsJsonObject("Circuit");
                String circuitId = circuit.get("circuitId").getAsString();
                
                // Obtener resultados del piloto en esta carrera
                JsonArray results = race.getAsJsonArray("Results");
                
                // Debería haber solo un elemento en results (el piloto específico)
                if (results.size() > 0) {
                    JsonObject result = results.get(0).getAsJsonObject();
                    
                    // Crear un nuevo objeto ResultadoPiloto
                    ResultadoPiloto resultadoPiloto = new ResultadoPiloto();
                    
                    // Establecer datos básicos
                    resultadoPiloto.setPilotoId(pilotoId);
                    resultadoPiloto.setIdCarrera(circuitId);
                    
                    // Posición y puntos
                    resultadoPiloto.setPosicion(result.get("position").getAsString());
                    resultadoPiloto.setPuntos(Double.parseDouble(result.get("points").getAsString()));
                    
                    // Añadir a la lista de resultados
                    resultados.add(resultadoPiloto);
                }
            }
            
            return resultados;
            
        } catch (IOException e) {
            System.err.println("Error al cargar los resultados del piloto: " + e.getMessage());
            return resultados; // Devolvemos lista vacía
        }
    }
    
    /**
     * Obtiene un resultado específico de un piloto en un circuito.
     * 
     * @param pilotoId ID del piloto
     * @param circuitId ID del circuito
     * @return ResultadoPiloto o null si no se encuentra
     */
    public ResultadoPiloto getResultadoPilotoEnCircuito(String pilotoId, String circuitId) {
        List<ResultadoPiloto> resultados = getResultadosPorPiloto(pilotoId);
        
        if (resultados != null) {
            for (ResultadoPiloto resultado : resultados) {
                if (resultado.getIdCarrera().equals(circuitId)) {
                    return resultado;
                }
            }
        }
        
        return null;
    }
    
    /**
     * Obtiene un mapa con todos los resultados de pilotos cargados.
     * 
     * @return Map con los resultados por pilotos
     */
    public Map<String, List<ResultadoPiloto>> getTodosResultadosPorPiloto() {
        return resultadosPorPiloto;
    }
    
    /**
     * Calcula los puntos totales de un piloto sumando los de todas sus carreras.
     * 
     * @param pilotoId ID del piloto
     * @return Total de puntos acumulados
     */
    public double calcularPuntosTotalesPiloto(String pilotoId) {
        List<ResultadoPiloto> resultados = getResultadosPorPiloto(pilotoId);
        double totalPuntos = 0;
        
        if (resultados != null) {
            for (ResultadoPiloto resultado : resultados) {
                totalPuntos += resultado.getPuntos();
            }
        }
        
        return totalPuntos;
    }
    
    /**
     * Busca la mejor posición lograda por un piloto en la temporada.
     * 
     * @param pilotoId ID del piloto
     * @return Mejor posición como entero (1 es lo mejor) o -1 si no hay resultados
     */
    public int obtenerMejorPosicionPiloto(String pilotoId) {
        List<ResultadoPiloto> resultados = getResultadosPorPiloto(pilotoId);
        int mejorPosicion = Integer.MAX_VALUE;
        
        if (resultados != null && !resultados.isEmpty()) {
            for (ResultadoPiloto resultado : resultados) {
                try {
                    int posicion = Integer.parseInt(resultado.getPosicion());
                    if (posicion < mejorPosicion) {
                        mejorPosicion = posicion;
                    }
                } catch (NumberFormatException e) {
                    // Ignorar valores no numéricos
                }
            }
        }
        
        return (mejorPosicion == Integer.MAX_VALUE) ? -1 : mejorPosicion;
    }
    
    /**
     * Obtiene la carrera donde el piloto logró su mejor resultado.
     * 
     * @param pilotoId ID del piloto
     * @return ID del circuito donde logró su mejor posición o null si no hay resultados
     */
    public String obtenerCircuitoMejorResultado(String pilotoId) {
        List<ResultadoPiloto> resultados = getResultadosPorPiloto(pilotoId);
        int mejorPosicion = Integer.MAX_VALUE;
        String circuitoId = null;
        
        if (resultados != null && !resultados.isEmpty()) {
            for (ResultadoPiloto resultado : resultados) {
                try {
                    int posicion = Integer.parseInt(resultado.getPosicion());
                    if (posicion < mejorPosicion) {
                        mejorPosicion = posicion;
                        circuitoId = resultado.getIdCarrera();
                    }
                } catch (NumberFormatException e) {
                    // Ignorar valores no numéricos
                }
            }
        }
        
        return circuitoId;
    }
    
    /**
     * Limpia la caché de resultados.
     */
    public void limpiarCache() {
        resultadosPorPiloto.clear();
    }
}