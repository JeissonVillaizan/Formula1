package edu.ucompensar.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un equipo (escudería) de Formula 1.
 * Almacena la información relevante sobre un equipo para la temporada 2024.
 */
public class Equipo {
    private String nombre;                  // Nombre del equipo
    private String directorGeneral;         // Director general del equipo
    private String paisOrigen;              // País de origen del equipo
    private int numeroCampeonatos;          // Número de campeonatos ganados
    private int puntosConstructores2024;    // Puntos acumulados en el Mundial de Constructores 2024
    private List<Piloto> pilotosOficiales;  // Pilotos oficiales del equipo 2024
    
    /**
     * Constructor por defecto.
     */
    public Equipo() {
        this.pilotosOficiales = new ArrayList<>();
        this.puntosConstructores2024 = 0;
    }
    
    /**
     * Constructor con parámetros principales.
     * 
     *  Nombre del equipo
     *  Director general del equipo
     *  País de origen del equipo
     *  Número de campeonatos ganados
     */
    public Equipo(String nombre, String directorGeneral, String paisOrigen, int numeroCampeonatos) {
        this.nombre = nombre;
        this.directorGeneral = directorGeneral;
        this.paisOrigen = paisOrigen;
        this.numeroCampeonatos = numeroCampeonatos;
        this.puntosConstructores2024 = 0;
        this.pilotosOficiales = new ArrayList<>();
    }
    
    /**
     * Constructor completo con puntos actuales.
     * 
     *  Nombre del equipo
     *  Director general del equipo
     *  País de origen del equipo
     *  Número de campeonatos ganados
     *  Puntos acumulados en 2024
     */
    public Equipo(String nombre, String directorGeneral, String paisOrigen, 
                 int numeroCampeonatos, int puntosConstructores2024) {
        this.nombre = nombre;
        this.directorGeneral = directorGeneral;
        this.paisOrigen = paisOrigen;
        this.numeroCampeonatos = numeroCampeonatos;
        this.puntosConstructores2024 = puntosConstructores2024;
        this.pilotosOficiales = new ArrayList<>();
    }
    
    // Getters y Setters
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getDirectorGeneral() {
        return directorGeneral;
    }
    
    public void setDirectorGeneral(String directorGeneral) {
        this.directorGeneral = directorGeneral;
    }
    
    public String getPaisOrigen() {
        return paisOrigen;
    }
    
    public void setPaisOrigen(String paisOrigen) {
        this.paisOrigen = paisOrigen;
    }
    
    public int getNumeroCampeonatos() {
        return numeroCampeonatos;
    }
    
    public void setNumeroCampeonatos(int numeroCampeonatos) {
        this.numeroCampeonatos = numeroCampeonatos;
    }
    
    public int getPuntosConstructores2024() {
        return puntosConstructores2024;
    }
    
    public void setPuntosConstructores2024(int puntosConstructores2024) {
        this.puntosConstructores2024 = puntosConstructores2024;
    }
    
    public List<Piloto> getPilotosOficiales() {
        return pilotosOficiales;
    }
    
    public void setPilotosOficiales(List<Piloto> pilotosOficiales) {
        this.pilotosOficiales = pilotosOficiales;
    }
    

    

    
    
    /**
     * Devuelve el número de pilotos oficiales en el equipo.
     */
    public int getNumeroPilotos() {
        return this.pilotosOficiales.size();
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Equipo: ").append(nombre).append("\n");
        sb.append("Director General: ").append(directorGeneral).append("\n");
        sb.append("País de origen: ").append(paisOrigen).append("\n");
        sb.append("Campeonatos ganados: ").append(numeroCampeonatos).append("\n");
        sb.append("Puntos en Mundial de Constructores 2024: ").append(puntosConstructores2024).append("\n");
        
        sb.append("Pilotos oficiales 2024: ");
        if (pilotosOficiales.isEmpty()) {
            sb.append("Ninguno asignado");
        } else {
            sb.append("\n");
            for (int i = 0; i < pilotosOficiales.size(); i++) {
                sb.append("  ").append(i + 1).append(". ");
                sb.append(pilotosOficiales.get(i).getNombre());
                if (i < pilotosOficiales.size() - 1) {
                    sb.append("\n");
                }
            }
        }
        
        return sb.toString();
    }
}