package edu.ucompensar.API;

import java.io.File;

/**
 * Clase para recolectar datos específicos de un circuito y sus resultados.
 * Se encarga únicamente de obtener y guardar el JSON de los resultados.
 */
public class RecoleccionDatosCircuito {
    
    // Constantes para construir las URLs y rutas
    private static final String BASE_URL = "https://ergast.com/api/f1";
    private static final String TEMPORADA_ACTUAL = "2024";
    private static final String DIRECTORIO_DATOS = "datos_f1";
    
    /**
     * Obtiene los resultados de un circuito específico para la temporada actual.
     * Verifica primero si el archivo ya existe para evitar peticiones innecesarias.
     * 
     * @param circuitId ID del circuito (ej: "monza", "spa", "monaco")
     * @return Ruta del archivo donde están/fueron guardados los datos
     */
    public static String obtenerResultadosCircuito(String circuitId) {
        // Construir nombre del archivo
        String nombreArchivo = "circuito_" + circuitId + "_" + TEMPORADA_ACTUAL + ".json";
        File archivo = new File(DIRECTORIO_DATOS, nombreArchivo);
        
        // Verificar si el archivo ya existe
        if (archivo.exists()) {
            System.out.println("El archivo de resultados para el circuito " + circuitId + 
                             " ya existe. Usando archivo existente.");
            return DIRECTORIO_DATOS + File.separator + nombreArchivo;
        }
        
        // Si no existe, construir URL para la API de Ergast y descargar datos
        String urlApi = BASE_URL + "/" + TEMPORADA_ACTUAL + "/circuits/" + circuitId + "/results.json";
        
        // Utilizar el método existente en RecoleccionDatos para descargar y guardar el JSON
        RecoleccionDatos.guardarDatosAPI(urlApi, nombreArchivo);
        
        // Devolver la ruta al archivo
        return DIRECTORIO_DATOS + File.separator + nombreArchivo;
    }
}