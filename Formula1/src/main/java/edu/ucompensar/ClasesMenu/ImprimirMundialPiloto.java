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

import edu.ucompensar.API.RecoleccionDatos;

public class ImprimirMundialPiloto {
    
    private static final String PILOTOS_FILE = "datos_f1/conductores_f1_2024.json";
    private static final String SEASON = "2024";
    
    private List<String> nombrePilotos;
    private List<String> idPilotos;
    private List<String> codigoPilotos;
    private List<String> nacionalidadPilotos;
    
    /**
     * Constructor que carga los datos de los pilotos desde el archivo JSON.
     */
    public ImprimirMundialPiloto() {
        cargarPilotos();
    }
    
    /**
     * Carga los datos de los pilotos desde el archivo JSON.
     */
    private void cargarPilotos() {
        nombrePilotos = new ArrayList<>();
        idPilotos = new ArrayList<>();
        codigoPilotos = new ArrayList<>();
        nacionalidadPilotos = new ArrayList<>();
        
        try (Reader reader = new FileReader(PILOTOS_FILE)) {
            // Parsear el JSON usando Gson
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            
            // Navegar por la estructura JSON para obtener los pilotos
            JsonObject mrData = jsonObject.getAsJsonObject("MRData");
            JsonObject driverTable = mrData.getAsJsonObject("DriverTable");
            JsonArray drivers = driverTable.getAsJsonArray("Drivers");
            
            // Recorrer todos los pilotos y guardar sus datos
            for (JsonElement driver : drivers) {
                JsonObject driverObj = driver.getAsJsonObject();
                String nombre = driverObj.get("givenName").getAsString() + " " + 
                               driverObj.get("familyName").getAsString();
                String driverId = driverObj.get("driverId").getAsString();
                String code = driverObj.get("code").getAsString();
                String nationality = driverObj.get("nationality").getAsString();
                
                nombrePilotos.add(nombre);
                idPilotos.add(driverId);
                codigoPilotos.add(code);
                nacionalidadPilotos.add(nationality);
            }
            
        } catch (IOException e) {
            System.err.println("Error al cargar los datos de pilotos: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Método estático principal que permite seleccionar un piloto y mostrar sus resultados.
     */
    public static void ImprimirMundialPiloto() {
        ImprimirMundialPiloto imprimir = new ImprimirMundialPiloto();
        imprimir.seleccionarPilotoYMostrarResultados();
    }
    
    /**
     * Permite al usuario seleccionar un piloto y muestra sus resultados en el mundial.
     */
    public void seleccionarPilotoYMostrarResultados() {
        Scanner scanner = new Scanner(System.in);
        mostrarPilotos(); // Mostrar la lista de pilotos disponibles
        
        System.out.print("Seleccione el número del piloto: ");
        int seleccion = scanner.nextInt(); // Leer la selección del usuario
        
        if (seleccion < 1 || seleccion > nombrePilotos.size()) {
            System.out.println("Número de piloto inválido.");
            return;
        }
        
        // Obtener el ID del piloto seleccionado
        String pilotoId = idPilotos.get(seleccion - 1);
        
        // Construir la URL para la API
        String apiUrl = "http://ergast.com/api/f1/" + SEASON + "/drivers/" + pilotoId + "/results.json";
        
        // Nombre del archivo donde se guardarán los datos
        String fileName = "resultados_piloto_" + pilotoId + "_" + SEASON + ".json";
        
        // Descargar los datos de resultados para este piloto
        RecoleccionDatos.guardarDatosAPI(apiUrl, fileName);
        
        // Mostrar los resultados del piloto en el mundial
        mostrarResultadosPiloto(seleccion, fileName);
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
        
        System.out.printf("%-3s %-30s %-15s %-10s\n", "N°", "NOMBRE", "NACIONALIDAD", "CÓDIGO");
        System.out.println("------------------------------------------------------------------");
        
        for (int i = 0; i < nombrePilotos.size(); i++) {
            System.out.printf("%-3d %-30s %-15s %-10s\n", 
                (i + 1), 
                nombrePilotos.get(i), 
                nacionalidadPilotos.get(i),
                codigoPilotos.get(i));
        }
        
        System.out.println("\n===================================================\n");
    }
    
    /**
     * Muestra los resultados del piloto seleccionado en el mundial.
     * @param indicePiloto Índice del piloto seleccionado (basado en 1).
     * @param fileName Nombre del archivo donde se guardaron los datos de resultados.
     */
    public void mostrarResultadosPiloto(int indicePiloto, String fileName) {
        if (indicePiloto < 1 || indicePiloto > nombrePilotos.size()) {
            System.out.println("Índice inválido. Por favor, seleccione un piloto válido.");
            return;
        }
        
        int idxPiloto = indicePiloto - 1; // Convertir a índice basado en 0
        String nombrePiloto = nombrePilotos.get(idxPiloto);
        String nacionalidadPiloto = nacionalidadPilotos.get(idxPiloto);
        
        try {
            // Ruta completa al archivo de datos
            String filePath = "datos_f1/" + fileName;
            
            // Leer los datos del archivo
            List<ResultadoCarrera> resultados = cargarResultadosDesdeArchivo(filePath);
            
            if (resultados.isEmpty()) {
                System.out.println("No se encontraron resultados para este piloto en la temporada actual.");
                return;
            }
            
            // Calcular puntos totales
            int puntosTotales = 0;
            for (ResultadoCarrera resultado : resultados) {
                puntosTotales += resultado.puntos;
            }
            
            // Mostrar la información
            System.out.println("\n===== RESULTADOS DE " + nombrePiloto + " EN EL MUNDIAL DE PILOTOS =====");
            System.out.println("Nacionalidad: " + nacionalidadPiloto);
            System.out.println("Temporada: " + SEASON);
            System.out.println("Carreras disputadas: " + resultados.size());
            System.out.println("Puntos totales: " + puntosTotales);
            System.out.println("----------------------------------------------------------------");
            
            System.out.printf("%-4s %-30s %-15s %-15s %-10s %-10s\n", 
                "GP", "CIRCUITO", "POSICIÓN", "GRID", "STATUS", "PUNTOS");
            System.out.println("----------------------------------------------------------------");
            
            for (ResultadoCarrera resultado : resultados) {
                System.out.printf("%-4d %-30s %-15s %-15s %-10s %-10s\n", 
                    resultado.ronda, 
                    resultado.nombreCircuito,
                    resultado.posicion,
                    resultado.posicionSalida,
                    resultado.status,
                    resultado.puntos);
            }
            
            System.out.println("\n===================================================\n");
            
        } catch (IOException e) {
            System.err.println("Error al cargar los datos: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Clase interna para almacenar los resultados de cada carrera.
     */
    private class ResultadoCarrera {
        int ronda;
        String nombreCircuito;
        String posicion;
        String posicionSalida;
        String status;
        int puntos;
        
        public ResultadoCarrera(int ronda, String nombreCircuito, String posicion, 
                               String posicionSalida, String status, int puntos) {
            this.ronda = ronda;
            this.nombreCircuito = nombreCircuito;
            this.posicion = posicion;
            this.posicionSalida = posicionSalida;
            this.status = status;
            this.puntos = puntos;
        }
    }
    
    /**
     * Carga los resultados del piloto desde el archivo JSON descargado.
     * @param filePath Ruta al archivo JSON con los datos de resultados.
     * @return Lista de objetos ResultadoCarrera con la información de cada carrera.
     * @throws IOException Si hay un error al leer el archivo.
     */
    private List<ResultadoCarrera> cargarResultadosDesdeArchivo(String filePath) throws IOException {
        List<ResultadoCarrera> resultados = new ArrayList<>();
        
        try (Reader reader = new FileReader(filePath)) {
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            
            JsonObject mrData = jsonObject.getAsJsonObject("MRData");
            
            // Verificar si hay tabla de resultados
            if (mrData.has("RaceTable")) {
                JsonObject raceTable = mrData.getAsJsonObject("RaceTable");
                
                if (raceTable.has("Races")) {
                    JsonArray races = raceTable.getAsJsonArray("Races");
                    
                    // Recorrer todas las carreras
                    for (JsonElement raceElement : races) {
                        JsonObject race = raceElement.getAsJsonObject();
                        
                        int ronda = race.get("round").getAsInt();
                        String circuitName = race.getAsJsonObject("Circuit").get("circuitName").getAsString();
                        
                        // Obtener los resultados del piloto en esta carrera
                        JsonArray results = race.getAsJsonArray("Results");
                        if (results != null && results.size() > 0) {
                            JsonObject result = results.get(0).getAsJsonObject();
                            
                            String posicion = result.has("position") ? 
                                result.get("position").getAsString() : "N/A";
                            
                            String posicionSalida = result.has("grid") ? 
                                result.get("grid").getAsString() : "N/A";
                            
                            String status = result.has("status") ? 
                                result.get("status").getAsString() : "N/A";
                            
                            int puntos = result.has("points") ? 
                                result.get("points").getAsInt() : 0;
                            
                            resultados.add(new ResultadoCarrera(ronda, circuitName, posicion, 
                                                               posicionSalida, status, puntos));
                        }
                    }
                }
            }
        }
        
        return resultados;
    }
}