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
            
            // Intentar hacer la petición a la API
            URL url = new URL(urlApi);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            
            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Leer la respuesta y guardar en el archivo
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                     FileWriter writer = new FileWriter(archivo)) {
                    String linea;
                    while ((linea = reader.readLine()) != null) {
                        writer.write(linea);
                    }
                    System.out.println("Datos guardados en: " + archivo.getAbsolutePath());
                }
            } else {
                System.err.println("Error en la conexión: Código de respuesta " + responseCode);
            }
        } catch (javax.net.ssl.SSLHandshakeException e) {
            System.err.println("Error SSL: " + e.getMessage());
            System.out.println("Intentando con HTTP en lugar de HTTPS...");
            // Reemplazar https con http y volver a intentar
            if (urlApi.startsWith("https://")) {
                String urlHttp = urlApi.replaceFirst("https://", "http://");
                guardarDatosAPI(urlHttp, nombreArchivo);
            }
        } catch (Exception e) {
            System.err.println("Error al guardar datos de la API: " + e.getMessage());
        }
    }
}