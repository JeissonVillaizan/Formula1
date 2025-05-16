package edu.ucompensar.API;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Clase para recolectar y guardar datos de APIs relacionadas con Fórmula 1
 */
public class RecoleccionDatos {
    
    // Ruta donde se guardarán los archivos JSON
    public static final String DIRECTORIO_DATOS = "datos_f1";
    
    /**
     * Método para guardar datos de una API específica
     * Verifica si el archivo existe antes de hacer la petición
     * 
     * @param urlApi URL de la API a consultar
     * @param nombreArchivo Nombre del archivo donde se guardarán los datos
     */
    public static void guardarDatosAPI(String urlApi, String nombreArchivo) {
        try {
            // Crear directorio si no existe
            File directorio = new File(DIRECTORIO_DATOS);
            if (!directorio.exists()) {
                directorio.mkdir();
                System.out.println("Directorio creado: " + directorio.getAbsolutePath());
            }
            
            // Comprobar si el archivo ya existe
            File archivo = new File(directorio, nombreArchivo);
            if (archivo.exists()) {
                System.out.println("Usando archivo existente: " + archivo.getAbsolutePath());
                return;
            }
            
            // Si no existe, hacer petición a la API
            URL url = new URL(urlApi);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            
            int responseCode = conn.getResponseCode();
            System.out.println("Código de respuesta para " + nombreArchivo + ": " + responseCode);
            
            if (responseCode == 200) {
                // Leer respuesta
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                
                // Formatear JSON para mejor legibilidad
                String jsonResponse = response.toString();
                ObjectMapper mapper = new ObjectMapper();
                JsonNode jsonNode = mapper.readTree(jsonResponse);
                String prettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonNode);
                
                // Guardar respuesta en archivo
                try (FileWriter fileWriter = new FileWriter(archivo)) {
                    fileWriter.write(prettyJson);
                }
                
                System.out.println("Datos guardados exitosamente en: " + archivo.getAbsolutePath());
            } else {
                System.out.println("Error al conectar con la API: " + responseCode);
            }
            
        } catch (Exception e) {
            System.out.println("Error al guardar " + nombreArchivo + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}