package edu.ucompensar.util;

import java.util.Scanner;

import edu.ucompensar.ClasesMenu.ImprimirInformacionCircuito;
import edu.ucompensar.ClasesMenu.ImprimirInformacionMundialPilotos;
import edu.ucompensar.ClasesMenu.ListarCarreras;
import edu.ucompensar.ClasesMenu.ListarPilotos;
import edu.ucompensar.ClasesMenu.SeleccionarCarrera;
import edu.ucompensar.ClasesMenu.ImprimirInformacionMundialConstructores;
import static edu.ucompensar.util.Banner.banner2;
import static edu.ucompensar.util.Banner.banner4;
import static edu.ucompensar.util.Banner.bannerMenuCarreras;
import static edu.ucompensar.util.Banner.bannerMenuEscuderias;
import static edu.ucompensar.util.Banner.bannerMenuPilotos;
import static edu.ucompensar.util.Banner.bannerSeleccionCarrera;
import static edu.ucompensar.util.Banner.bannerSeleccionPiloto;
import static edu.ucompensar.util.Banner.bannerSeleccionarEscuderia;
public class Menu {

    private int opcion =0;
    Scanner scanner = new Scanner(System.in);
    public void menuPrincipal (){

        do {
            System.out.println(banner4);
            System.out.println(banner2);
            System.out.println("MENU PRINCIPAL");

            System.out.println("1. CONSULTAR POR  CARRERAS");
            System.out.println("2. CONSULTAR POR PILOTOS");
            System.out.println("3. CONSULTAR POR ESCUDERIAS");
            System.out.println("4. SALIR DEL SISTEMA");
            System.out.print("Ingrese su opción:  ");
            this.opcion = scanner.nextInt();

                switch (opcion) {
                    case 1:
                            menuCarreras();
                            break;
                    case 2:
                            menuPilotos();
                            break;
                    case 3:
                            menuEscuderias();
                        break;
                    case 4:
                        return;
                        //break;
                    default:
                        System.out.println("Opcion no valida REPITELA");
                        break;
                }

        } while (opcion != 4);


    }

    public void menuCarreras(){
        this.opcion =0;

        do {
            limpiarPantalla();
            System.out.println(banner4);
            System.out.println(bannerMenuCarreras);
            System.out.println("MENU CARRERAS");
            System.out.println("11. LISTAR CARRERAS");
            System.out.println("12. SELECCIONAR CARRERA");
            System.out.println("13. VOLVER MENU PRINCIPAL");
            System.out.print("Ingrese su opción:  ");
            this.opcion = scanner.nextInt();
            switch (opcion) {
                case 11:
                    System.out.println("LLAMAR LISTAR CARRERAS"); //Completado
                    ListarCarreras.ListarCarreras();
                    break;
                case 12:
                    menuSeleccionarCarrera();
                    break;
                case 13:
                    //return;
                    break;
                default:
                    System.out.println("Opcion no valida REPITELA");
                    break;
            }
        }while (this.opcion != 13);

        // menuPrincipal();
    }
    public void menuSeleccionarCarrera(){
        this.opcion = 0;
            limpiarPantalla();
            String carreraSeleccionada = SeleccionarCarrera.seleccionarCarreraStatic();
            limpiarPantalla();
            do{
            System.out.println(banner4);
            System.out.println(bannerSeleccionCarrera);
            System.out.println();
            
            // Mostramos la carrera seleccionada
            System.out.println("Carrera seleccionada: " + carreraSeleccionada);
            
            
            System.out.println("121. IMPRIMIR INFORMACION DEL CIRCUITO"); //Completado
            System.out.println("122. IMPRIMIR INFORMACION DEL MUNDIAL DE CONSTRUCTORES EN ESTA CARRERA");//Completado
            System.out.println("123. IMPRIMIR INFORMACION DEL MUNDIAL DE PILOTOS EN ESTA CARRERA");//Completado
            System.out.println("124. VOLVER MENU DE CARRERAS");
            System.out.println("125. VOLVER MENU PRINCIPAL");
            System.out.print("Ingrese su opción:  ");
            this.opcion = scanner.nextInt();

            switch (opcion) {
                case 121:
                    ImprimirInformacionCircuito.ImprimirInformacionCircuito(carreraSeleccionada);
                    break;
                case 122:
                    System.out.println("LLAMAR IMPRIMIR INFORMACION DEL MUNDIAL DE CONSTRUCTORES EN ESTA CARRERA ");
                    ImprimirInformacionMundialConstructores.ImprimirInformacionMundialConstructores(carreraSeleccionada);
                    break;
                case 123:
                    System.out.println("LLAMAR IMPRIMIR INFORMACION DEL MUNDIAL DE PILOTOS EN ESTA CARRERA");
                    ImprimirInformacionMundialPilotos.imprimirInformacionMundialPilotos(carreraSeleccionada);
                    break;
                case 124:
                    return;
                    //menuCarreras();
                case 125:
                    break;
                default:
                    System.out.println("Opcion no valida REPITELA");
                    break;
            }
        } while (this.opcion != 125);
    }

    public void menuPilotos(){
        this.opcion =0;

        do {
            limpiarPantalla();
            System.out.println(banner4);
            System.out.println(bannerMenuPilotos);
            System.out.println("MENU PILOTOS");
            System.out.println("21. LISTAR PILOTOS");
            System.out.println("22. SELECCIONAR PILOTO");
            System.out.println("23. VOLVER MENU PRINCIPAL");
            System.out.print("Ingrese su opción:  ");
            this.opcion = scanner.nextInt();
            switch (opcion) {
                case 21:
                    System.out.println("LLAMAR LISTAR PILOTOS");
                    ListarPilotos.ListarPilotos();
                    break; 
                case 22:
                    menuSeleccionarPiloto();
                    break;
                case 23:
                    break;
                default:
                    System.out.println("Opcion no valida REPITELA");
                    break;
            }
        }while (this.opcion != 23);
        menuPrincipal();
    }

    public void menuSeleccionarPiloto(){
        this.opcion =0;

        do {
            System.out.println(banner4);
            System.out.println(bannerSeleccionPiloto);
            System.out.println();
            System.out.println("221. IMPRIMIR INFORMACION DEL PILOTO");
            System.out.println("222. IMPRIMIR INFORMACION DEL MUNDIAL DE PILOTOS PARA ESTE PILOTO");
            System.out.println("223. VOLVER MENU DE PILOTOS");
            System.out.println("224. VOLVER MENU PRINCIPAL");
            System.out.print("Ingrese su opción:  ");
            this.opcion = scanner.nextInt();

            switch (opcion) {
                case 221:
                    System.out.println("LLAMAR IMPRIMIR INFORMACION DEL PILOTO");
                    //ImprimirInformacionPiloto.ImprimirInformacionPiloto();
                    break;
                case 222:
                    System.out.println("LLAMAR IMPRIMIR INFORMACION DEL MUNDIAL DE PILOTOS PARA ESTE PILOTO");
                    //ImprimirMundialPiloto.ImprimirMundialPiloto();
                    break;
                case 223:
                    this.menuPilotos();
                    break;
                case 224:
                    break;
                default:
                    System.out.println("Opcion no valida REPITELA");
                    break;
            }
        }while (this.opcion != 224);
    }

    public void menuEscuderias(){
        this.opcion =0;

        do {
            limpiarPantalla();
            System.out.println(banner4);
            System.out.println(bannerMenuEscuderias);
            System.out.println("MENU ESCUDERIAS");
            System.out.println("31. LISTAR ESCUDERIAS");
            System.out.println("32. SELECCIONAR ESCUDERIA");
            System.out.println("33. VOLVER MENU PRINCIPAL");
            System.out.print("Ingrese su opción:  ");
            this.opcion = scanner.nextInt();
            switch (opcion) {
                case 31:
                    System.out.println("LLAMAR LISTAR ESCUDERIAS");
                    //ListarEscuderia.listarEscuderias(); 
                    break;
                case 32:
                    menuSeleccionarEscuderia();
                    break;
                case 33:
                    System.out.println("RETORNANDO AL MENU PRINCIPAL");
                    break;
                default:
                    System.out.println("Opcion no valida REPITELA");
                    break;
            }
        }while (this.opcion != 33);
        menuPrincipal();
    }

    public void menuSeleccionarEscuderia(){
        this.opcion =0;

        do {
            System.out.println(banner4);
            System.out.println(bannerSeleccionarEscuderia);
            System.out.println();
            System.out.println("321. IMPRIMIR INFORMACION DE LA ESCUDERIA");
            System.out.println("322. IMPRIMIR INFORMACION DEL MUNDIAL DE CONSTRUCTORES PARA ESTA ESCUDERIA");
            System.out.println("323. IMPRIMIR INFORMACION DEL MUNDIAL DE PILOTOS PARA ESTE PILOTOS DE ESTA ESCUDERIA");
            System.out.println("324. VOLVER MENU DE ESCUDERIAS");
            System.out.println("325. VOLVER MENU PRINCIPAL");
            System.out.print("Ingrese su opción:  ");
            this.opcion = scanner.nextInt();

            switch (opcion) {
                case 321:
                    System.out.println("LLAMAR IMPRIMIR INFORMACION DE LA ESCUDERIA");
                    //ImprimirInformacionEscuderia.ImprimirInformacionEscuderia();
                    break;
                case 322:
                    System.out.println("LLAMAR IMPRIMIR INFORMACION DEL MUNDIAL DE CONSTRUCTORES PARA ESTA ESCUDERIA");
                    //ImprimirConstructoresEscuderia.ImprimirConstructoresEscuderia();
                    break;
                case 323:
                    System.out.println("LLAMAR IMPRIMIR INFORMACION DEL MUNDIAL DE PILOTOS PARA LOS PILOTOS DE ESTE ESCUDERIA");
                    //ImprimirPilotosEscuderia.ImprimirPilotosEscuderia();
                    break;
                case 324:
                    this.menuEscuderias();
                    break;
                case 325:
                    break;
                default:
                    System.out.println("Opcion no valida REPITELA");
                    break;
            }
        }while (this.opcion != 325);
    }

    public static void limpiarPantalla() {
        for (int i = 0; i < 95; i++) {
            System.out.println();
        }

    }
}
