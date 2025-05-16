package edu.ucompensar;

import edu.ucompensar.ClasesMenu.RecoleccionDatos;

public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();
        
        // Guardar datos de circuitos, carreras y resultados
        RecoleccionDatos.guardarDatosAPI("http://ergast.com/api/f1/2024/circuits.json", "circuitos_f1_2024.json");
        RecoleccionDatos.guardarDatosAPI("http://ergast.com/api/f1/2024/results.json", "resultados_f1_2024.json");
        RecoleccionDatos.guardarDatosAPI("http://ergast.com/api/f1/2024/drivers.json", "conductores_f1_2024.json");
        RecoleccionDatos.guardarDatosAPI("http://ergast.com/api/f1/2024/constructors.jso3n", "constructores_f1_2024.json");        
        menu.menuPrincipal();
    }
}