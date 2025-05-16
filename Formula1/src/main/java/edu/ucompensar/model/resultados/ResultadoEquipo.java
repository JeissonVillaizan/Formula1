package edu.ucompensar.model.resultados;

/**
 * Clase que representa el resultado de un equipo en una carrera específica,
 * incluyendo la información de sus dos pilotos.
 */
public class ResultadoEquipo {
    
    // Datos del equipo
    private String constructorId;    // ID del constructor (equipo)
    private String constructorName;  // Nombre del constructor (equipo)
    private String circuitName;      // Nombre del circuito donde se realizó la carrera
    
    // Datos del primer piloto
    private String givenName1;       // Nombre del primer piloto
    private String familyName1;      // Apellido del primer piloto
    private int posicionCarrera1;    // Posición final del primer piloto
    private double puntosCarrera1;   // Puntos obtenidos por el primer piloto
    
    // Datos del segundo piloto
    private String givenName2;       // Nombre del segundo piloto
    private String familyName2;      // Apellido del segundo piloto
    private int posicionCarrera2;    // Posición final del segundo piloto
    private double puntosCarrera2;   // Puntos obtenidos por el segundo piloto
    
    /**
     * Constructor por defecto.
     */
    public ResultadoEquipo() {
    }
    
    /**
     * Constructor con todos los parámetros.
     */
    public ResultadoEquipo(String constructorId, String constructorName, String circuitName,
                          String givenName1, String familyName1, int posicionCarrera1, double puntosCarrera1,
                          String givenName2, String familyName2, int posicionCarrera2, double puntosCarrera2) {
        this.constructorId = constructorId;
        this.constructorName = constructorName;
        this.circuitName = circuitName;
        this.givenName1 = givenName1;
        this.familyName1 = familyName1;
        this.posicionCarrera1 = posicionCarrera1;
        this.puntosCarrera1 = puntosCarrera1;
        this.givenName2 = givenName2;
        this.familyName2 = familyName2;
        this.posicionCarrera2 = posicionCarrera2;
        this.puntosCarrera2 = puntosCarrera2;
    }
    
    // Getters y setters
    
    public String getConstructorId() {
        return constructorId;
    }
    
    public void setConstructorId(String constructorId) {
        this.constructorId = constructorId;
    }
    
    public String getConstructorName() {
        return constructorName;
    }
    
    public void setConstructorName(String constructorName) {
        this.constructorName = constructorName;
    }
    
    public String getCircuitName() {
        return circuitName;
    }
    
    public void setCircuitName(String circuitName) {
        this.circuitName = circuitName;
    }
    
    public String getGivenName1() {
        return givenName1;
    }
    
    public void setGivenName1(String givenName1) {
        this.givenName1 = givenName1;
    }
    
    public String getFamilyName1() {
        return familyName1;
    }
    
    public void setFamilyName1(String familyName1) {
        this.familyName1 = familyName1;
    }
    
    public int getPosicionCarrera1() {
        return posicionCarrera1;
    }
    
    public void setPosicionCarrera1(int posicionCarrera1) {
        this.posicionCarrera1 = posicionCarrera1;
    }
    
    public double getPuntosCarrera1() {
        return puntosCarrera1;
    }
    
    public void setPuntosCarrera1(double puntosCarrera1) {
        this.puntosCarrera1 = puntosCarrera1;
    }
    
    public String getGivenName2() {
        return givenName2;
    }
    
    public void setGivenName2(String givenName2) {
        this.givenName2 = givenName2;
    }
    
    public String getFamilyName2() {
        return familyName2;
    }
    
    public void setFamilyName2(String familyName2) {
        this.familyName2 = familyName2;
    }
    
    public int getPosicionCarrera2() {
        return posicionCarrera2;
    }
    
    public void setPosicionCarrera2(int posicionCarrera2) {
        this.posicionCarrera2 = posicionCarrera2;
    }
    
    public double getPuntosCarrera2() {
        return puntosCarrera2;
    }
    
    public void setPuntosCarrera2(double puntosCarrera2) {
        this.puntosCarrera2 = puntosCarrera2;
    }
    
    /**
     * Calcula la puntuación total del equipo en esta carrera.
     * 
     * @return Suma de los puntos de ambos pilotos
     */
    public double getTotalPuntosEquipo() {
        return puntosCarrera1 + puntosCarrera2;
    }
    
    /**
     * Obtiene el nombre completo del primer piloto.
     * 
     * @return Nombre completo del piloto 1
     */
    public String getNombreCompletoPiloto1() {
        return givenName1 + " " + familyName1;
    }
    
    /**
     * Obtiene el nombre completo del segundo piloto.
     * 
     * @return Nombre completo del piloto 2
     */
    public String getNombreCompletoPiloto2() {
        return givenName2 + " " + familyName2;
    }
}