package edu.ucompensar.model.resultados;

/**
 * Clase que representa el resultado básico de un piloto en una carrera.
 * Contiene únicamente la información esencial de identificación y resultado.
 */
public class ResultadoPiloto {
    
    private String pilotoId;     // ID del piloto (ej: "hamilton")
    private String idCarrera;    // ID de la carrera/circuito (ej: "monza")
    private String posicion;     // Posición final en la carrera
    private double puntos;       // Puntos obtenidos en la carrera
    
    /**
     * Constructor por defecto.
     */
    public ResultadoPiloto() {
    }
    
    /**
     * Constructor con todos los datos necesarios.
     * 
     * @param pilotoId ID del piloto
     * @param idCarrera ID de la carrera/circuito
     * @param posicion Posición final en la carrera
     * @param puntos Puntos obtenidos
     */
    public ResultadoPiloto(String pilotoId, String idCarrera, String posicion, double puntos) {
        this.pilotoId = pilotoId;
        this.idCarrera = idCarrera;
        this.posicion = posicion;
        this.puntos = puntos;
    }
    
    // Getters y Setters
    
    public String getPilotoId() {
        return pilotoId;
    }
    
    public void setPilotoId(String pilotoId) {
        this.pilotoId = pilotoId;
    }
    
    public String getIdCarrera() {
        return idCarrera;
    }
    
    public void setIdCarrera(String idCarrera) {
        this.idCarrera = idCarrera;
    }
    
    public String getPosicion() {
        return posicion;
    }
    
    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }
    
    public double getPuntos() {
        return puntos;
    }
    
    public void setPuntos(double puntos) {
        this.puntos = puntos;
    }
}