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

public class ImprimirPilotosCarrera {
    
    private static final String CIRCUITOS_FILE = "datos_f1/circuitos_f1_2024.json";
    private static final String SEASON = "2024";
    
    private List<String> nombreCircuitos;
    private List<String> idCircuitos;
    private List<String> paisesCircuitos;
    
    /**
     * Constructor que carga los datos de los circuitos desde el archivo JSON.
     */
    public ImprimirPilotosCarrera() {
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
            e.printStackTrace();
        }
    }
    
    /**
     * Método estático principal que permite seleccionar un circuito y mostrar los pilotos
     * que participaron en él.
     */
    public static void ImprimirPilotosCarrera() {
        ImprimirPilotosCarrera imprimir = new ImprimirPilotosCarrera();
        imprimir.seleccionarCircuitoYMostrarPilotos();
    }
    
    /**
     * Permite al usuario seleccionar un circuito y muestra los pilotos que participaron en él.
     */
    public void seleccionarCircuitoYMostrarPilotos() {
        Scanner scanner = new Scanner(System.in);
        mostrarCircuitos(); // Mostrar la lista de circuitos disponibles
        
        System.out.print("Seleccione el número del circuito: ");
        int seleccion = scanner.nextInt(); // Leer la selección del usuario
        
        if (seleccion < 1 || seleccion > nombreCircuitos.size()) {
            System.out.println("Número de circuito inválido.");
            return;
        }
        
        // Obtener el ID del circuito seleccionado
        String circuitId = idCircuitos.get(seleccion - 1);
        
        // Construir la URL para la API
        String apiUrl = "http://ergast.com/api/f1/" + SEASON + "/circuits/" + circuitId + "/drivers.json";
        
        // Nombre del archivo donde se guardarán los datos
        String fileName = "pilotos_circuito_" + circuitId + "_" + SEASON + ".json";
        
        // Descargar los datos de pilotos para este circuito
        RecoleccionDatos.guardarDatosAPI(apiUrl, fileName);
        
        // Mostrar los pilotos que participaron en este circuito
        mostrarPilotosDeCircuito(seleccion, fileName);
    }
    
    /**
     * Muestra la lista de circuitos con su número correspondiente.
     */
    public void mostrarCircuitos() {
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
     * Muestra los pilotos que participaron en el circuito seleccionado.
     * @param indiceCircuito Índice del circuito seleccionado (basado en 1).
     * @param fileName Nombre del archivo donde se guardaron los datos de pilotos.
     */
    public void mostrarPilotosDeCircuito(int indiceCircuito, String fileName) {
        if (indiceCircuito < 1 || indiceCircuito > nombreCircuitos.size()) {
            System.out.println("Índice inválido. Por favor, seleccione un circuito válido.");
            return;
        }
        
        int idxCircuito = indiceCircuito - 1; // Convertir a índice basado en 0
        String nombreCircuito = nombreCircuitos.get(idxCircuito);
        String ubicacionCircuito = paisesCircuitos.get(idxCircuito);
        
        try {
            // Ruta completa al archivo de datos
            String filePath = "datos_f1/" + fileName;
            
            // Leer los datos del archivo
            List<JsonObject> pilotosInfo = cargarPilotosDesdeArchivo(filePath);
            
            if (pilotosInfo.isEmpty()) {
                System.out.println("No se encontraron pilotos para este circuito.");
                return;
            }
            
            // Mostrar la información
            System.out.println("\n===== PILOTOS EN " + nombreCircuito + " =====");
            System.out.println("Ubicación: " + ubicacionCircuito);
            System.out.println("Temporada: " + SEASON);
            System.out.println("Pilotos participantes: " + pilotosInfo.size());
            System.out.println("--------------------------------------------");
            
            System.out.printf("%-5s %-25s %-15s %-15s %-10s\n", "N°", "PILOTO", "NACIONALIDAD", "CÓDIGO", "NÚMERO");
            System.out.println("--------------------------------------------");
            
            int num = 1;
            for (JsonObject piloto : pilotosInfo) {
                String nombre = piloto.get("givenName").getAsString() + " " + piloto.get("familyName").getAsString();
                String nacionalidad = piloto.get("nationality").getAsString();
                String codigo = piloto.get("code").getAsString();
                String numero = piloto.has("permanentNumber") ? piloto.get("permanentNumber").getAsString() : "N/A";
                
                System.out.printf("%-5d %-25s %-15s %-15s %-10s\n", num++, nombre, nacionalidad, codigo, numero);
            }
            
            System.out.println("\n===================================================\n");
            
        } catch (IOException e) {
            System.err.println("Error al cargar los datos: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Carga la información de los pilotos desde el archivo JSON descargado.
     * @param filePath Ruta al archivo JSON con los datos de pilotos.
     * @return Lista de objetos JSON con la información de los pilotos.
     * @throws IOException Si hay un error al leer el archivo.
     */
    private List<JsonObject> cargarPilotosDesdeArchivo(String filePath) throws IOException {
        List<JsonObject> pilotosInfo = new ArrayList<>();
        
        try (Reader reader = new FileReader(filePath)) {
            Gson gson = new Gson();
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            
            JsonObject mrData = jsonObject.getAsJsonObject("MRData");
            
            // Verificar si hay datos de pilotos
            if (mrData.has("DriverTable")) {
                JsonObject driverTable = mrData.getAsJsonObject("DriverTable");
                
                if (driverTable.has("Drivers")) {
                    JsonArray drivers = driverTable.getAsJsonArray("Drivers");
                    
                    // Recorrer los pilotos y guardar su información
                    for (JsonElement driverElement : drivers) {
                        JsonObject driver = driverElement.getAsJsonObject();
                        pilotosInfo.add(driver);
                    }
                }
            }
        }
        
        return pilotosInfo;
    }
}