package edu.ucompensar.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashMap;
import java.util.Map;

/**
 * Clase que representa un piloto de Formula 1.
 * Almacena la información relevante sobre un piloto para la temporada 2024.
 */
public class Piloto {
    private String nombre;                     // Nombre del piloto
    private String apellido;                   // Apellido del piloto
    private String equipoActual;               // Equipo al que pertenece en 2024
    private LocalDate fechaNacimiento;         // Fecha de nacimiento para calcular edad
    private String paisOrigen;                 // País de origen
    private int numeroCampeonatos;             // Número de campeonatos ganados
    private int carrerasDisputadas;            // Cantidad total de carreras disputadas
    private int puntosTemporada2024;           // Puntos acumulados en la temporada 2024
    
    // Mapa para registrar posiciones de cada carrera en 2024
    // La clave es el nombre del circuito y el valor es un array de 2 enteros: [posiciónPartida, posiciónLlegada]
    private Map<String, int[]> posicionesCarreras2024;
    
    /**
     * Constructor por defecto.
     */
    public Piloto() {
        this.posicionesCarreras2024 = new HashMap<>();
        this.puntosTemporada2024 = 0;
    }
    
    /**
     * Constructor con parámetros básicos.
     * 
     * @param nombre Nombre del piloto
     * @param apellido Apellido del piloto
     * @param fechaNacimiento Fecha de nacimiento
     * @param paisOrigen País de origen
     */
    public Piloto(String nombre, String apellido, LocalDate fechaNacimiento, String paisOrigen) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaNacimiento = fechaNacimiento;
        this.paisOrigen = paisOrigen;
        this.numeroCampeonatos = 0;
        this.carrerasDisputadas = 0;
        this.puntosTemporada2024 = 0;
        this.posicionesCarreras2024 = new HashMap<>();
    }
    
    /**
     * Constructor completo.
     * 
     * @param nombre Nombre del piloto
     * @param apellido Apellido del piloto
     * @param equipoActual Equipo al que pertenece en 2024
     * @param fechaNacimiento Fecha de nacimiento
     * @param paisOrigen País de origen
     * @param numeroCampeonatos Número de campeonatos ganados
     * @param carrerasDisputadas Cantidad total de carreras disputadas
     */
    public Piloto(String nombre, String apellido, String equipoActual, 
                 LocalDate fechaNacimiento, String paisOrigen,
                 int numeroCampeonatos, int carrerasDisputadas) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.equipoActual = equipoActual;
        this.fechaNacimiento = fechaNacimiento;
        this.paisOrigen = paisOrigen;
        this.numeroCampeonatos = numeroCampeonatos;
        this.carrerasDisputadas = carrerasDisputadas;
        this.puntosTemporada2024 = 0;
        this.posicionesCarreras2024 = new HashMap<>();
    }
    
    // Getters y Setters
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getApellido() {
        return apellido;
    }
    
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
    public String getEquipoActual() {
        return equipoActual;
    }
    
    public void setEquipoActual(String equipoActual) {
        this.equipoActual = equipoActual;
    }
    
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
    
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
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
    
    public int getCarrerasDisputadas() {
        return carrerasDisputadas;
    }
    
    public void setCarrerasDisputadas(int carrerasDisputadas) {
        this.carrerasDisputadas = carrerasDisputadas;
    }
    
    public int getPuntosTemporada2024() {
        return puntosTemporada2024;
    }
    
    public void setPuntosTemporada2024(int puntosTemporada2024) {
        this.puntosTemporada2024 = puntosTemporada2024;
    }
    
    public Map<String, int[]> getPosicionesCarreras2024() {
        return posicionesCarreras2024;
    }
    
    public void setPosicionesCarreras2024(Map<String, int[]> posicionesCarreras2024) {
        this.posicionesCarreras2024 = posicionesCarreras2024;
    }
    
    /**
     * Calcula la edad actual del piloto.
     * 
     * @return edad en años
     */
    public int getEdad() {
        if (fechaNacimiento == null) {
            return 0;
        }
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }
    
    /**
     * Obtiene el nombre completo del piloto.
     * 
     * @return nombre y apellido concatenados
     */
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }
    
    /**
     * Añade puntos a la temporada actual.
     * 
     * @param puntos Puntos a añadir
     */
    public void sumarPuntos(int puntos) {
        this.puntosTemporada2024 += puntos;
    }
    
    /**
     * Añade una carrera disputada.
     */
    public void añadirCarreraDisputada() {
        this.carrerasDisputadas++;
    }
    
    /**
     * Registra la posición de partida y llegada en una carrera.
     * 
     * @param nombreCircuito Nombre del circuito
     * @param posicionPartida Posición en la parrilla de salida
     * @param posicionLlegada Posición final de la carrera
     */
    public void registrarPosicionesCarrera(String nombreCircuito, int posicionPartida, int posicionLlegada) {
        posicionesCarreras2024.put(nombreCircuito, new int[]{posicionPartida, posicionLlegada});
    }
    
    /**
     * Obtiene las posiciones de partida y llegada para un circuito específico.
     * 
     * @param nombreCircuito Nombre del circuito
     * @return array con [posiciónPartida, posiciónLlegada] o null si no hay datos
     */
    public int[] getPosicionesEnCircuito(String nombreCircuito) {
        return posicionesCarreras2024.get(nombreCircuito);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Piloto: ").append(getNombreCompleto()).append("\n");
        sb.append("Edad: ").append(getEdad()).append(" años\n");
        sb.append("País de origen: ").append(paisOrigen).append("\n");
        sb.append("Equipo actual: ").append(equipoActual != null ? equipoActual : "Sin equipo").append("\n");
        sb.append("Campeonatos ganados: ").append(numeroCampeonatos).append("\n");
        sb.append("Carreras disputadas: ").append(carrerasDisputadas).append("\n");
        sb.append("Puntos temporada 2024: ").append(puntosTemporada2024).append("\n");
        
        sb.append("Resultados en 2024:\n");
        if (posicionesCarreras2024.isEmpty()) {
            sb.append("  Sin participación en carreras registradas\n");
        } else {
            for (Map.Entry<String, int[]> entry : posicionesCarreras2024.entrySet()) {
                String circuito = entry.getKey();
                int[] posiciones = entry.getValue();
                sb.append("  ").append(circuito).append(": ");
                sb.append("Salida P").append(posiciones[0]).append(" → ");
                sb.append("Llegada P").append(posiciones[1]).append("\n");
            }
        }
        
        return sb.toString();
    }
}