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

import edu.ucompensar.model.Piloto;

/**
 * Clase que se encarga de cargar y gestionar los datos de los pilotos de F1.
 */
public class PilotosDataManager {
    
    private static final String PILOTOS_FILE = "datos_f1/pilotos_f1_2024.json";
    private static PilotosDataManager instance;
    
    private List<Piloto> pilotos;
    private Map<String, Piloto> pilotosPorId;
    
    /**
     * Constructor privado que carga los datos de los pilotos.
     */
    private PilotosDataManager() {
        cargarPilotos();
    }
    
    /**
     * Obtiene la instancia única del gestor de datos de pilotos.
     * @return Instancia única de PilotosDataManager
     */
    public static synchronized PilotosDataManager getInstance() {
        if (instance == null) {
            instance = new PilotosDataManager();
        }
        return instance;
    }
    
    /**
     * Obtiene la lista de todos los pilotos.
     * @return Lista de pilotos
     */
    public List<Piloto> getPilotos() {
        return pilotos;
    }
    
    /**
     * Obtiene un piloto por su ID.
     * @param id ID del piloto
     * @return Piloto correspondiente al ID o null si no existe
     */
    public Piloto getPilotoPorId(String id) {
        return pilotosPorId.get(id);
    }
    
    /**
     * Carga los datos de los pilotos desde el archivo JSON.
     * Asume que todos los atributos necesarios están en el JSON.
     */
    private void cargarPilotos() {
        pilotos = new ArrayList<>();
        pilotosPorId = new HashMap<>();
        
        try (Reader reader = new FileReader(PILOTOS_FILE)) {
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            
            JsonObject mrData = jsonObject.getAsJsonObject("MRData");
            JsonObject driverTable = mrData.getAsJsonObject("DriverTable");
            JsonArray drivers = driverTable.getAsJsonArray("Drivers");
            
            for (JsonElement element : drivers) {
                JsonObject driverObj = element.getAsJsonObject();
                
                // Crear un nuevo objeto Piloto
                Piloto piloto = new Piloto();
                
                // Datos básicos del piloto
                piloto.setNombre(driverObj.get("givenName").getAsString());
                piloto.setApellido(driverObj.get("familyName").getAsString());
                
                // Fecha de nacimiento
                String fechaNacimientoStr = driverObj.get("dateOfBirth").getAsString();
                piloto.setFechaNacimiento(LocalDate.parse(fechaNacimientoStr));
                
                // País de origen
                piloto.setPaisOrigen(driverObj.get("nationality").getAsString());
                
                // Equipo actual
                if (driverObj.has("team")) {
                    piloto.setEquipoActual(driverObj.get("team").getAsString());
                }
                
                // Número de campeonatos
                if (driverObj.has("worldChampionships")) {
                    piloto.setNumeroCampeonatos(driverObj.get("worldChampionships").getAsInt());
                }
                
                // Carreras disputadas
                if (driverObj.has("grandPrixEntered")) {
                    piloto.setCarrerasDisputadas(driverObj.get("grandPrixEntered").getAsInt());
                }
                
                // Puntos de la temporada actual
                // Nota: Asumimos que los puntos en el JSON son del total histórico
                // Para la temporada actual se inicializa en 0 como está en el constructor
                
                // Guardar el piloto en las colecciones
                String driverId = driverObj.get("driverId").getAsString();
                pilotos.add(piloto);
                pilotosPorId.put(driverId, piloto);
            }
            
        } catch (IOException e) {
            System.err.println("Error al cargar datos de pilotos: " + e.getMessage());
        }
    }
    
    /**
     * Busca pilotos por nombre o apellido.
     * @param criterio Texto a buscar en el nombre o apellido
     * @return Lista de pilotos que coinciden con el criterio
     */
    public List<Piloto> buscarPilotos(String criterio) {
        List<Piloto> resultado = new ArrayList<>();
        String criterioBusqueda = criterio.toLowerCase();
        
        for (Piloto piloto : pilotos) {
            if (piloto.getNombre().toLowerCase().contains(criterioBusqueda) || 
                piloto.getApellido().toLowerCase().contains(criterioBusqueda)) {
                resultado.add(piloto);
            }
        }
        
        return resultado;
    }
    
    /**
     * Obtiene la lista de pilotos ordenada por puntos de la temporada 2024.
     * @return Lista ordenada de pilotos
     */
    public List<Piloto> getPilotosOrdenadosPorPuntos() {
        List<Piloto> ordenados = new ArrayList<>(pilotos);
        ordenados.sort((p1, p2) -> Integer.compare(p2.getPuntosTemporada2024(), p1.getPuntosTemporada2024()));
        return ordenados;
    }
    
    /**
     * Obtiene los pilotos de un equipo específico.
     * @param equipo Nombre del equipo
     * @return Lista de pilotos del equipo
     */
    public List<Piloto> getPilotosPorEquipo(String equipo) {
        List<Piloto> pilotosEquipo = new ArrayList<>();
        String equipoBusqueda = equipo.toLowerCase();
        
        for (Piloto piloto : pilotos) {
            if (piloto.getEquipoActual() != null && 
                piloto.getEquipoActual().toLowerCase().contains(equipoBusqueda)) {
                pilotosEquipo.add(piloto);
            }
        }
        
        return pilotosEquipo;
    }
}