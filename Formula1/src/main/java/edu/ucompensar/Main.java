package edu.ucompensar;

import edu.ucompensar.API.RecoleccionDatos;

public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();
        
        // Guardar datos de circuitos, carreras y resultados
        RecoleccionDatos.guardarDatosAPI("http://ergast.com/api/f1/2024/circuits.json", "circuitos_f1_2024.json");
        RecoleccionDatos.guardarDatosAPI("http://ergast.com/api/f1/2024/results.json", "resultados_f1_2024.json");
        RecoleccionDatos.guardarDatosAPI("http://ergast.com/api/f1/2024/drivers.json", "pilotos_f1_2024.json");
        RecoleccionDatos.guardarDatosAPI("http://ergast.com/api/f1/2024/constructors.json", "constructores_f1_2024.json");     
        RecoleccionDatos.guardarDatosAPI("https://api.jolpi.ca/ergast/f1/1/2024/results/", "resultados_f1_2024.json");     
        menu.menuPrincipal();
    }
}