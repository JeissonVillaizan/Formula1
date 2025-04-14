package edu.ucompensar;

import edu.ucompensar.ClasesMenu.ListarCarreras;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Menu menu = new Menu();
        
        // Guardar datos de circuitos, carreras y resultados
        ListarCarreras.guardarDatosAPI("http://ergast.com/api/f1/2024/circuits.json", "circuitos_f1_2024.json");
        ListarCarreras.guardarDatosAPI("http://ergast.com/api/f1/2024/results.json", "resultados_f1_2024.json");
        ListarCarreras.guardarDatosAPI("http://ergast.com/api/f1/2024/qualifying.json", "clasificacion_f1_2024.json");
        ListarCarreras.guardarDatosAPI("http://ergast.com/api/f1/2024/drivers.json", "conductores_f1_2024.json");
        
        
        menu.menuPrincipal();
    }
}