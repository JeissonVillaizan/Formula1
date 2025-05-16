package edu.ucompensar.model;

import java.time.LocalDate;

/**
 * Clase que representa un circuito de Formula 1.
 * Almacena la información relevante sobre un circuito y su carrera.
 */
public class Circuito {
    private String circuitName;       // Nombre del circuito
    private LocalDate fechaCarrera;   // Fecha de la carrera principal
    private LocalDate fechaSprint;    // Fecha de la carrera sprint (puede ser null)
    private String country;           // País donde se encuentra el circuito
    private int numeroVueltas;        // Número de vueltas
    private double longitud;          // Longitud del circuito en kilómetros
    
    /**
     * Constructor por defecto.
     */
    public Circuito() {
    }
    
    /**
     * Constructor con los parámetros principales sin carrera sprint.
     * 
     *  Nombre del circuito
     *  Fecha de la carrera principal
     *  País donde se encuentra el circuito
     *  Número de vueltas
     *  Longitud del circuito en kilómetros
     */
    public Circuito(String circuitName, LocalDate fechaCarrera, String country, 
                   int numeroVueltas, double longitud) {
        this.circuitName = circuitName;
        this.fechaCarrera = fechaCarrera;
        this.country = country;
        this.numeroVueltas = numeroVueltas;
        this.longitud = longitud;
        this.fechaSprint = null; // No tiene carrera sprint
    }
    
    /**
     * Constructor con todos los parámetros incluyendo carrera sprint.
     * 
     *  Nombre del circuito
     *  Fecha de la carrera principal
     *  Fecha de la carrera sprint
     *  País donde se encuentra el circuito
     *  Número de vueltas
     *  Longitud del circuito en kilómetros
     */
    public Circuito(String circuitName, LocalDate fechaCarrera, LocalDate fechaSprint, 
                   String country, int numeroVueltas, double longitud) {
        this.circuitName = circuitName;
        this.fechaCarrera = fechaCarrera;
        this.fechaSprint = fechaSprint;
        this.country = country;
        this.numeroVueltas = numeroVueltas;
        this.longitud = longitud;
    }
    
    // Getters y Setters
    
    public String getCircuitName() {
        return circuitName;
    }
    
    public void setCircuitName(String circuitName) {
        this.circuitName = circuitName;
    }
    
    public LocalDate getFechaCarrera() {
        return fechaCarrera;
    }
    
    public void setFechaCarrera(LocalDate fechaCarrera) {
        this.fechaCarrera = fechaCarrera;
    }
    
    public LocalDate getFechaSprint() {
        return fechaSprint;
    }
    
    public void setFechaSprint(LocalDate fechaSprint) {
        this.fechaSprint = fechaSprint;
    }
    
    public String getCountry() {
        return country;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    public int getNumeroVueltas() {
        return numeroVueltas;
    }
    
    public void setNumeroVueltas(int numeroVueltas) {
        this.numeroVueltas = numeroVueltas;
    }
    
    public double getLongitud() {
        return longitud;
    }
    
    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }
    

    

    /**
     * Determina si el circuito tiene carrera sprint.
     * 
     * Devuelve true si tiene carrera sprint, false si no
     */

    public boolean tieneSprint() {
        return fechaSprint != null;
    }
    
    /**
     * Calcula la distancia total de la carrera en kilómetros.
     * 
     * Devuelve distancia total (longitud * número de vueltas)
     */
    public double getDistanciaTotal() {
        return longitud * numeroVueltas;
    }
    
    @Override
    public String toString() {
        String sprintInfo = tieneSprint() ? "Sí, el " + fechaSprint : "No";
        
        return "Circuito: " + circuitName + 
               "\nPaís: " + country + 
               "\nFecha de carrera: " + fechaCarrera + 
               "\nCarrera sprint: " + sprintInfo + 
               "\nNúmero de vueltas: " + numeroVueltas + 
               "\nLongitud: " + longitud + " km" +
               "\nDistancia total: " + getDistanciaTotal() + " km";
    }
}