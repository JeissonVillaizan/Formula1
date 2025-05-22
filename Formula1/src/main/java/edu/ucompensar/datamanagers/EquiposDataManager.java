package edu.ucompensar.datamanagers;

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

import edu.ucompensar.model.Equipo;

/**
 * Clase que se encarga de cargar y gestionar los datos de los equipos de F1.
 */
public class EquiposDataManager {
    
    private static final String EQUIPOS_FILE = "datos_f1/constructores_f1_2024.json";
    private static EquiposDataManager instance;
    
    private List<Equipo> equipos;
    private Map<String, Equipo> equiposPorId;
    
    /**
     * Constructor privado que carga los datos de los equipos.
     */
    private EquiposDataManager() {
        cargarEquipos();
    }
    
    /**
     * Obtiene la instancia única del gestor de datos de equipos.
     * @return Instancia única de EquiposDataManager
     */
    public static synchronized EquiposDataManager getInstance() {
        if (instance == null) {
            instance = new EquiposDataManager();
        }
        return instance;
    }
    
    /**
     * Obtiene la lista de todos los equipos.
     * @return Lista de equipos
     */
    public List<Equipo> getEquipos() {
        return equipos;
    }
    
    /**
     * Obtiene un equipo por su ID.
     * @param id ID del equipo
     * @return Equipo correspondiente al ID o null si no existe
     */
    public Equipo getEquipoPorId(String id) {
        return equiposPorId.get(id);
    }
    
    /**
     * Carga los datos de los equipos desde el archivo JSON.
     * Asume que todos los atributos necesarios están en el JSON.
     */
    private void cargarEquipos() {
        equipos = new ArrayList<>();
        equiposPorId = new HashMap<>();
        
        try (Reader reader = new FileReader(EQUIPOS_FILE)) {
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            
            JsonObject mrData = jsonObject.getAsJsonObject("MRData");
            JsonObject constructorTable = mrData.getAsJsonObject("ConstructorTable");
            JsonArray constructors = constructorTable.getAsJsonArray("Constructors");
            
            for (JsonElement element : constructors) {
                JsonObject constructorObj = element.getAsJsonObject();
                
                // Crear un nuevo objeto Equipo
                Equipo equipo = new Equipo();
                
                // Datos básicos del equipo
                equipo.setNombre(constructorObj.get("name").getAsString());
                equipo.setDirectorGeneral(constructorObj.get("teamPrincipal").getAsString());
                
                // País de origen
                String nacionalidad = constructorObj.get("nationality").getAsString();
                equipo.setPaisOrigen(nacionalidad);
                
                // Campeonatos
                equipo.setNumeroCampeonatos(constructorObj.get("championships").getAsInt());
                
                // Puntos 2024
                equipo.setPuntosConstructores2024(constructorObj.get("points2024").getAsInt());
                
                // Guardar el equipo en las colecciones
                String constructorId = constructorObj.get("constructorId").getAsString();
                equipos.add(equipo);
                equiposPorId.put(constructorId, equipo);
            }
            
        } catch (IOException e) {
            System.err.println("Error al cargar datos de equipos: " + e.getMessage());
        }
    }
    
    /**
     * Obtiene la lista de equipos ordenada por puntos del Mundial de Constructores 2024.
     * @return Lista ordenada de equipos
     */
    public List<Equipo> getEquiposOrdenadosPorPuntos() {
        List<Equipo> ordenados = new ArrayList<>(equipos);
        ordenados.sort((e1, e2) -> Integer.compare(e2.getPuntosConstructores2024(), e1.getPuntosConstructores2024()));
        return ordenados;
    }
    
    /**
     * Busca equipos por nombre.
     * @param criterio Texto a buscar en el nombre
     * @return Lista de equipos que coinciden con el criterio
     */
    public List<Equipo> buscarEquipos(String criterio) {
        List<Equipo> resultado = new ArrayList<>();
        String criterioBusqueda = criterio.toLowerCase();
        
        for (Equipo equipo : equipos) {
            if (equipo.getNombre().toLowerCase().contains(criterioBusqueda)) {
                resultado.add(equipo);
            }
        }
        
        return resultado;
    }
}