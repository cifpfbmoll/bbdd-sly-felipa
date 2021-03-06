package practica8;

import java.io.IOException;
import java.sql.*;
import java.lang.*;
import java.util.*;

public class Practica8 {

    public static void main(String[] args) {

        boolean seguir1 = true;
        boolean seguir2 = true;
        int op1;
        int op2;
        Scanner read = new Scanner(System.in);

        while (seguir1) {
            op1 = menuPrincipal();
            if (op1 == 1) {
                while (seguir2) {
                    op2 = menuSecundario();

                    if (op2 == 1) {

                        try {
                            ServesJDBC s = new ServesJDBC();
                            s.mostrarColumna();
                            System.out.println("Ingresa un campo: ");
                            String campo = read.nextLine();
                            s.mostrarCampo(campo);
                            System.out.println("Ingresa un filtro: ");
                            String valor = read.nextLine();
                            s.selectST(campo, valor);
                        } catch (ClassNotFoundException c) {
                            System.out.println("No se pudo conectar");
                            c.printStackTrace();

                        } catch (SQLException e) {
                            System.out.println("No se pudo conectar");
                            e.printStackTrace();
                        } catch (IOException e) {
                            System.out.println("No se pudo escribir en el fichero");
                            e.printStackTrace();

                        }

                    } else if (op2 == 2) {
                        try {
                            ServesJDBC s = new ServesJDBC();
                            s.mostrarColumna();
                            System.out.println("Ingresa un campo: ");
                            String campo = read.nextLine();
                            s.mostrarCampo(campo);
                            System.out.println("Ingresa un filtro: ");
                            String valor = read.nextLine();
                            s.consultaSQLInjection(campo, valor);
                        } catch (ClassNotFoundException c) {
                            System.out.println("No se pudo conectar");
                            c.printStackTrace();

                        } catch (SQLException e) {
                            System.out.println("No se pudo conectar");
                            e.printStackTrace();
                        } catch (IOException e) {
                            System.out.println("No se pudo escribir en el fichero");
                            e.printStackTrace();

                        }
                    } else if (op2 == 3) {
                        ServesJDBC s = new ServesJDBC();
                        try {
                            s.mostrarCampo("bar");
                            System.out.println("Ingresa el nomre de tu bar favorito: ");
                            String valor1 = read.nextLine();
                            s.mostrarCampo("beer");
                            System.out.println("Ingresa el nomre de tu cerveza favorita: ");
                            String valor2 = read.nextLine();
                            s.selectPDST(valor1, valor2);
                        } catch (ClassNotFoundException c) {
                            System.out.println("No se pudo conectar");
                            c.printStackTrace();

                        } catch (SQLException e) {
                            System.out.println("No se pudo conectar");
                            e.printStackTrace();

                        } catch (IOException e) {
                            System.out.println("No se pudo escribir en el fichero");
                            e.printStackTrace();
                        }
                    } else if (op2 == 4) {
                        seguir2 = false;
                    } else {
                        System.out.println("Opción no valida.");
                    }

                }// while

            } else if (op1 == 2) {
                seguir1 = false;
            } else if (op1 == 3) {
                seguir1 = false;

            } else {
                System.out.println("Opción no valida");

            }
        }//while

    }////

    public static int menuPrincipal() {
        Scanner read = new Scanner(System.in);

        System.out.println("********************************");
        System.out.println("1- Consulta");
        System.out.println("2- Update");
        System.out.println("3- salir");
        System.out.println("********************************");
        System.out.println("Elige una opción: ");

        return read.nextInt();
    }

    public static int menuSecundario() {
        Scanner read = new Scanner(System.in);

        System.out.println("********************************");
        System.out.println("1- Consulta statement");
        System.out.println("2- Consulta sql injection");
        System.out.println("3- Consulta prepared statement");
        System.out.println("4- Volver");
        System.out.println("********************************");
        System.out.println("Elige una opción: ");

        return read.nextInt();
    }

}//

