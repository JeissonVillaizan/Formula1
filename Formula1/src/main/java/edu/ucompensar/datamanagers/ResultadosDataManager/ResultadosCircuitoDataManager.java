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

import edu.ucompensar.API.RecoleccionDatosResultadoCircuito;
import edu.ucompensar.model.resultados.ResultadoCircuito;

/**
 * Clase que se encarga de cargar y gestionar los datos de resultados de circuitos.
 * Sigue el patrón Singleton para asegurar una única instancia.
 */
public class ResultadosCircuitoDataManager {
    
    private static ResultadosCircuitoDataManager instance;
    private Map<String, ResultadoCircuito> resultadosPorCircuito;
    
    /**
     * Constructor privado que inicializa la colección de resultados.
     */
    private ResultadosCircuitoDataManager() {
        this.resultadosPorCircuito = new HashMap<>();
    }
    
    /**
     * Obtiene la instancia única del gestor de datos de resultados.
     * @return Instancia única de ResultadosDataManager
     */
    public static synchronized ResultadosCircuitoDataManager getInstance() {
        if (instance == null) {
            instance = new ResultadosCircuitoDataManager();
        }
        return instance;
    }
    
    /**
     * Carga los datos de resultados para un circuito específico.
     * Si los datos ya están cargados, devuelve los datos existentes.
     * 
     * @param circuitId ID del circuito
     * @return ResultadoCircuito con los datos cargados
     */
    public ResultadoCircuito getResultadoCircuito(String circuitId) {
        // Si ya tenemos los resultados cargados, los devolvemos
        if (resultadosPorCircuito.containsKey(circuitId)) {
            return resultadosPorCircuito.get(circuitId);
        }
        
        // Si no, los cargamos desde el JSON
        String rutaArchivo = RecoleccionDatosResultadoCircuito.obtenerResultadosCircuito(circuitId);
        ResultadoCircuito resultado = cargarResultadosDesdeJSON(rutaArchivo);
        
        // Almacenamos en caché para futuras consultas
        if (resultado != null) {
            resultadosPorCircuito.put(circuitId, resultado);
        }
        
        return resultado;
    }
    
    /**
     * Carga los datos de resultados desde un archivo JSON.
     * 
     * @param rutaArchivo Ruta al archivo JSON
     * @return ResultadoCircuito con los datos cargados o null si ocurre un error
     */
    private ResultadoCircuito cargarResultadosDesdeJSON(String rutaArchivo) {
        try (Reader reader = new FileReader(rutaArchivo)) {
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            
            // Crear el objeto ResultadoCircuito
            ResultadoCircuito resultadoCircuito = new ResultadoCircuito();
            
            // Extraer datos del JSON
            JsonObject mrData = jsonObject.getAsJsonObject("MRData");
            JsonObject raceTable = mrData.getAsJsonObject("RaceTable");
            JsonArray races = raceTable.getAsJsonArray("Races");
            
            // Si no hay carreras, retornamos null
            if (races.size() == 0) {
                return null;
            }
            
            // Tomamos la primera (y debería ser única) carrera
            JsonObject race = races.get(0).getAsJsonObject();
            JsonArray results = race.getAsJsonArray("Results");
            
            // Listas para almacenar los datos
            List<String> posicionPiloto = new ArrayList<>();
            List<Double> puntuacionPiloto = new ArrayList<>();
            List<String> escuderiaPiloto = new ArrayList<>();
            Map<String, Double> puntuacionEscuderia = new HashMap<>();
            Map<String, Integer> posicionEscuderia = new HashMap<>();
            
            // Procesar cada resultado
            for (JsonElement resultElement : results) {
                JsonObject result = resultElement.getAsJsonObject();
                
                // Obtener datos del piloto
                JsonObject driver = result.getAsJsonObject("Driver");
                String nombreCompletoPiloto = driver.get("givenName").getAsString() + " " + 
                                             driver.get("familyName").getAsString();
                
                // Obtener datos de la escudería
                JsonObject constructor = result.getAsJsonObject("Constructor");
                String nombreEscuderia = constructor.get("name").getAsString();
                
                // Posición y puntos
                String posicion = result.get("position").getAsString();
                double puntos = result.get("points").getAsDouble();
                
                // Añadir a las listas
                posicionPiloto.add(nombreCompletoPiloto);
                puntuacionPiloto.add(puntos);
                escuderiaPiloto.add(nombreEscuderia);
                
                // Acumular puntos por escudería
                puntuacionEscuderia.put(nombreEscuderia, 
                    puntuacionEscuderia.getOrDefault(nombreEscuderia, 0.0) + puntos);
            }
            
            // Calcular posiciones de escuderías según sus puntos
            List<Map.Entry<String, Double>> escuderiasOrdenadas = new ArrayList<>(puntuacionEscuderia.entrySet());
            escuderiasOrdenadas.sort((e1, e2) -> Double.compare(e2.getValue(), e1.getValue()));
            
            for (int i = 0; i < escuderiasOrdenadas.size(); i++) {
                posicionEscuderia.put(escuderiasOrdenadas.get(i).getKey(), i + 1);
            }
            
            // Asignar todos los datos al resultado
            resultadoCircuito.setPosicionPiloto(posicionPiloto);
            resultadoCircuito.setPuntuacionPiloto(puntuacionPiloto);
            resultadoCircuito.setEscuderiaPiloto(escuderiaPiloto);
            
             resultadoCircuito.setPuntuacionEscuderia(puntuacionEscuderia);
             resultadoCircuito.setPosicionEscuderia(posicionEscuderia);
            
            return resultadoCircuito;
            
        } catch (IOException e) {
            System.err.println("Error al cargar los resultados del circuito: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Obtiene un mapa con todos los resultados de circuitos cargados.
     * 
     * @return Map con los resultados por circuito
     */
    public Map<String, ResultadoCircuito> getResultadosPorCircuito() {
        return resultadosPorCircuito;
    }
    
    /**
     * Limpia la caché de resultados.
     */
    public void limpiarCache() {
        resultadosPorCircuito.clear();
    }
}