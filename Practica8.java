package practica8;

import java.sql.*;
import java.lang.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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
                            System.out.println("Ingresa un precio (por ejemplo: 2.75 ó 3.00): ");
                            String filtro = read.next();
                            s.selectST(filtro);
                        } catch (ClassNotFoundException c) {
                            System.out.println("No se pudo conectar");
                            c.printStackTrace();

                        } catch (SQLException e) {
                            System.out.println("No se pudo conectar");
                            e.printStackTrace();

                        }

                    } else if (op2 == 2) {

                    } else if (op2 == 3) {
                        seguir2 = false;

                    } else {
                        System.out.println("Opción no valida.");
                    }

                }// while

            } else if (op1 == 2) {
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
        System.out.println("2- salir");
        System.out.println("********************************");
        System.out.println("Elige una opción: ");

        return read.nextInt();
    }

    public static int menuSecundario() {
        Scanner read = new Scanner(System.in);

        System.out.println("********************************");
        System.out.println("1- Consulta statement");
        System.out.println("2- Consulta prepared statement");
        System.out.println("3- Volver");
        System.out.println("********************************");
        System.out.println("Elige una opción: ");

        return read.nextInt();
    }

}//
