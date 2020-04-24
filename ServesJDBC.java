package practica8;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

public class ServesJDBC {

    private static String SENTENCIA_SELECT = "select * from serves where ";
    private static String SENTENCIA_INSERT = "insert into serves (bar,beer, price) values (?,?,?)";
    private static String SENTENCIA_UPDATE = "update serves set bar=? set beer=? set price=? where bar=? and beer=?";
    private static String SENTENCIA_DELETE = "delete from serves where bar=? and beer=?";

    private Connection conexion;
    private Statement st;
    private ResultSet rs;
    private ResultSetMetaData rsmd;
    private PreparedStatement pdst;

    //se instancia el Logger
    static Logger log = Logger.getLogger(ServesJDBC.class);
    URL url = ServesJDBC.class.getResource("Log4j.properties");

// sql injection
//The Edge'
    public List<Serves> selectST(String campo, String valor) throws SQLException, ClassNotFoundException, IOException {
        //configuración
        PropertyConfigurator.configure(url);
        String rutaEscribir = "C:/Users/sly/Documents/NetBeansProjects/Practica8/src/practica8/consultaSQL.txt";
        File fichero = new File(rutaEscribir);

        if (!fichero.exists()) {
            fichero.createNewFile();
            System.out.println("\nEl fichero se ha creado. Se comienza a escribir\n");

        } else {
            System.out.println("\nEl fichero existe. Se comienza a escribir\n");
        }
        BufferedWriter escritor = new BufferedWriter(new FileWriter(fichero, true));

        List<Serves> listaServes = new ArrayList<>();

        //inicia la conexión
        abrirConectar();
        //se ingresa la consulta
        rs = st.executeQuery("select * from serves where " + campo + "='" + valor + "'");
        rsmd = rs.getMetaData();

        escritor.write("--- selectST ---");
        escritor.newLine();
        escritor.write(rsmd.getColumnName(1) + "    " + rsmd.getColumnName(2)
                + "    " + rsmd.getColumnName(3) + "\n");
        while (rs.next()) {
            //se cogen los valores que devolvió la consulta
            String bar = rs.getString("bar");
            String beer = rs.getString("beer");
            Double price = rs.getDouble("price");
            //las variables construyen un obj nuevo
            Serves serves = new Serves(bar, beer, price);
            //los objetos nuevos se agregan a la lista - no se escriben
            listaServes.add(serves);
            escritor.write(bar + "    " + beer + "    " + price + "\n");
        }
        escritor.newLine();
        escritor.close();

        closeGeneral();

        return listaServes;

    }

    public void consultaSQLInjection(String campo, String valor) throws SQLException, ClassNotFoundException, IOException {

        String rutaEscribir = "C:/Users/sly/Documents/NetBeansProjects/Practica8/src/practica8/consultaSQLinjection.txt";
        File fichero = new File(rutaEscribir);
        if (!fichero.exists()) {
            fichero.createNewFile();
            System.out.println("\nEl fichero se ha creado. Se comienza a escribir\n");

        } else {
            System.out.println("\nEl fichero existe. Se comienza a escribir\n");
        }
        BufferedWriter escritor = new BufferedWriter(new FileWriter(fichero, true));
        String consulta = "";

        //inicia la conexión
        abrirConectar();

        // compruebo qué campo está ingresando
        if (campo.equals(
                "bar") || campo.equals("BAR")) {

            if (valor.equals("Down Under Pub")) {

                //se ingresa la consulta
                // con esta sintaxis se da la posibilidad que la cadena pueda ser
                // cambiada. como consecuencia la consulta puede proporcionar más
                //información de la inicialmente pensada.
                consulta = "select * from serves where " + campo + "='" + valor + "'";
            } else if (valor.equals("James Joyce Pub")) {

                consulta = "select * from serves where " + campo + "='" + valor + "'";
            } else if (valor.equals("Satisfaction")) {

                consulta = "select * from serves where " + campo + "='" + valor + "'";
            } else if (valor.equals("Talk of the Town ")) {

                consulta = "select * from serves where " + campo + "='" + valor + "'";
            } else if (valor.equals("The Edge")) {
                consulta = "select * from serves where " + campo + "='" + valor + "'";
            } else {
                System.out.println("No existe el bar");
            }

        } else if (campo.equals(
                "beer") || campo.equals("BEER")) {
        } else if (campo.equals(
                "price") || campo.equals("PRICE")) {
        } else {
            System.out.println("Ese campo no existe");
        }
        rs = st.executeQuery(consulta);
        escritor.write(consulta + "\n");
        rsmd = rs.getMetaData();
        escritor.write(rsmd.getColumnName(1) + "    " + rsmd.getColumnName(2)
                + "    " + rsmd.getColumnName(3) + "\n");
        while (rs.next()) {
            //se cogen los valores que devolvió la consulta
            String bar = rs.getString("bar");
            String beer = rs.getString("beer");
            Double price = rs.getDouble("price");
            //las variables construyen un obj nuevo
            //los objetos nuevos se agregan a la lista - no se escriben
            System.out.println(bar + "    " + beer + "    " + price + "\n");
            escritor.write(bar + "    " + beer + "    " + price + "\n");
        }
        escritor.close();
        closeGeneral();

    }

    public Serves selectPDST(String bar_valor, String beer_valor) throws SQLException, ClassNotFoundException, IOException {
        PropertyConfigurator.configure(url);

        String rutaEscribir = "C:/Users/sly/Documents/NetBeansProjects/Practica8/src/practica8/consultaSQLobj.txt";
        File fichero = new File(rutaEscribir);
        Serves serves = new Serves();

        if (!fichero.exists()) {
            fichero.createNewFile();
            System.out.println("\nEl fichero se ha creado. Se comienza a escribir\n");

        } else {

            System.out.println("\nEl fichero existe. Se comienza a escribir\n");
        }
        ObjectOutputStream escribir = new ObjectOutputStream(
                new BufferedOutputStream(
                        new FileOutputStream(rutaEscribir, true)));

        //inicia la conexión
        abrirConectar();
        //se prepara la consulta
        pdst = conexion.prepareStatement("select * from serves where bar=? and beer=?");
        //se ingresan los valores

        pdst.setString(
                1, bar_valor);
        pdst.setString(
                2, beer_valor);
        //se ejecuta la consulta
        pdst.execute();
        //registro devuelto
        rs = pdst.executeQuery();
        rsmd = rs.getMetaData();

        System.out.println(
                "");
//        
        while (rs.next()) {
            //se cogen los valores que devolvió la consulta
            String bar = rs.getString("bar");
            String beer = rs.getString("beer");
            Double price = rs.getDouble("price");
            //las variables construyen un obj nuevo
            serves.setBar(bar);
            serves.setBeer(beer);
            serves.setPrice(price);
            System.out.print(rsmd.getColumnName(1) + "    " + rsmd.getColumnName(2)
                    + "    " + rsmd.getColumnName(3) + "\n");
            System.out.print(bar + "    " + beer + "    " + price + "\n");

        }

        System.out.println(serves.getBar() + serves.getPrice());
        escribir.writeObject(serves);

        log.trace("mensaje de trace");
        log.debug("mensaje de debug");
        log.info("mensaje de info");
        escribir.close();

        closeGeneral();

        return serves;
    }

    public void LeerObjeto() throws IOException, ClassNotFoundException {
        String rutaLeer = "C:/Users/sly/Documents/NetBeansProjects/Practica8/src/practica8/consultaSQLobj.txt";
        File fichero = new File(rutaLeer);
//        ObjectInputStream leer = new ObjectInputStream(
//                new BufferedInputStream(
//                        new FileInputStream(fichero)));
        FileInputStream f = new FileInputStream(fichero);

        System.out.println("pruebaLeerObj");
        try {
            //devuelve byte 
            while (f.available() > 0) {
                //para solucionar el tema de las cabeceras de los objetos
                ObjectInputStream leer = new ObjectInputStream(f);

                Serves ser = (Serves) leer.readObject();
                System.out.println(((Serves) ser).getBar() + "    "
                        + ((Serves) ser).getBeer() + "   " + ((Serves) ser).getPrice());
            }
        } finally {
            f.close();
        }

    }

    public void mostrarInfo(String campo) throws SQLException, ClassNotFoundException {
        if (campo == "bar") {
            mostrarCampo(campo);
        } else if (campo == "beer") {
            mostrarCampo(campo);
        } else if (campo == "price") {
            mostrarCampo(campo);

        }

    }

    public void mostrarCampo(String campo) throws SQLException, ClassNotFoundException {
        abrirConectar();
        rs = st.executeQuery("select distinct " + campo + " from serves");
        rsmd = rs.getMetaData();

        System.out.println("");
        System.out.print("opciones de la columnas: ");
        while (rs.next()) {
            System.out.print(rs.getString(campo) + "    ");

        }
        System.out.println("");
        closeGeneral();

    }

    public void mostrarColumna() throws SQLException, ClassNotFoundException {
        abrirConectar();
        rs = st.executeQuery("select * from serves");
        ResultSetMetaData rsmd = rs.getMetaData();
        int i = 0;
        System.out.print("\nColumnas: ");
        while (rs.next() && i < rsmd.getColumnCount()) {
            System.out.print(rsmd.getColumnName(i + 1)
                    + "  ");
            i++;
        }
        System.out.println();
        closeGeneral();
    }

    public void abrirConectar() throws SQLException, ClassNotFoundException {
//        Class.forName("oracle.jdbc.OracleDriver");
        String url = "jdbc:oracle:thin:@localhost:1521:XE";
        conexion = DriverManager.getConnection(url, "system", "oracle2019");
        st = conexion.createStatement();
    }

    public void closeGeneral() throws SQLException {
        st.close();
        rs.close();
        conexion.close();
    }

}////
