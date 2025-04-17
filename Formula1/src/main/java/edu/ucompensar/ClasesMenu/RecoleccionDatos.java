package edu.ucompensar.ClasesMenu;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RecoleccionDatos {
    
    // Ruta donde se guardarán los archivos JSON
    private static final String DIRECTORIO_DATOS = "datos_f1";
    
    
    /**
     * Método para guardar datos de una API específica
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
            
            File archivo = new File(directorio, nombreArchivo);
            
            // Verificar si el archivo ya existe
            if (archivo.exists()) {
                System.out.println("El archivo " + archivo.getAbsolutePath() + " ya existe.");
                System.out.println("Usando datos guardados previamente.");
                return; // Salir del método si el archivo ya existe
            }
            
            // Crear la URL para la API
            URL url = new URL(urlApi);
            
            // Abrir conexión HTTP
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            
            // Configurar método de petición
            conn.setRequestMethod("GET");
            
            // Verificar si la conexión fue exitosa
            int responseCode = conn.getResponseCode();
            System.out.println("Código de respuesta para " + nombreArchivo + ": " + responseCode);
            
            // Leer la respuesta si fue exitosa (código 200)
            if (responseCode == 200) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();
                
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                
                // Guardar la respuesta completa en un archivo JSON
                String jsonResponse = response.toString();
                
                try (FileWriter fileWriter = new FileWriter(archivo)) {
                    fileWriter.write(jsonResponse);
                }
                
                System.out.println("Datos guardados exitosamente en: " + archivo.getAbsolutePath());
                System.out.println("Tamaño del archivo: " + archivo.length() + " bytes");
                
            } else {
                System.out.println("Error al conectar con la API para " + nombreArchivo + ". Código: " + responseCode);
            }
            
            // Cerrar la conexión
            conn.disconnect();
            
        } catch (Exception e) {
            System.out.println("Error al guardar " + nombreArchivo + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
    
}