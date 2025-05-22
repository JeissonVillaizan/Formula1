package edu.ucompensar.model.resultados;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Clase que representa los resultados de una carrera en un circuito específico.
 * Contiene información sobre posiciones y puntos de pilotos y escuderías.
 */
public class ResultadoCircuito {
    
    private List<String> posicionPiloto;         // Lista de posiciones de los pilotos
    private List<Double> puntuacionPiloto;       // Lista de puntos obtenidos por cada piloto
    private List<String> escuderiaPiloto;        // Lista de escuderías de cada piloto
    private Map<String, Double> puntuacionEscuderia; // Puntos por escudería (suma de puntos de sus pilotos)
    private Map<String, Integer> posicionEscuderia; // Posición de cada escudería según sus puntos
    
    /**
     * Constructor por defecto.
     */
    public ResultadoCircuito() {
        this.posicionPiloto = new ArrayList<>();
        this.puntuacionPiloto = new ArrayList<>();
        this.escuderiaPiloto = new ArrayList<>();
        this.puntuacionEscuderia = new HashMap<>();
        this.posicionEscuderia = new HashMap<>();
    }
    
    // Getters y Setters para pilotos
    
    public List<String> getPosicionPiloto() {
        return posicionPiloto;
    }
    
    public void setPosicionPiloto(List<String> posicionPiloto) {
        this.posicionPiloto = posicionPiloto;
    }
    
    public List<Double> getPuntuacionPiloto() {
        return puntuacionPiloto;
    }
    
    public void setPuntuacionPiloto(List<Double> puntuacionPiloto) {
        this.puntuacionPiloto = puntuacionPiloto;
    }
    
    public List<String> getEscuderiaPiloto() {
        return escuderiaPiloto;
    }
    
    public void setEscuderiaPiloto(List<String> escuderiaPiloto) {
        this.escuderiaPiloto = escuderiaPiloto;
    }
    
 
    public Map<String, Double> getPuntuacionEscuderia() {
        return puntuacionEscuderia;
    }
    

    public void setPuntuacionEscuderia(Map<String, Double> puntuacionEscuderia) {
        this.puntuacionEscuderia = puntuacionEscuderia;
    }
    

    public Map<String, Integer> getPosicionEscuderia() {
        return posicionEscuderia;
    }
    

    public void setPosicionEscuderia(Map<String, Integer> posicionEscuderia) {
        this.posicionEscuderia = posicionEscuderia;
    }
}