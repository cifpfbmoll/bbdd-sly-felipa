package practica8;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServesJDBC {
    
    private static final String SENTENCIA_SELECT = "select * from serves";
    private static final String SENTENCIA_INSERT = "insert into serves (bar,beer, price) values (?,?,?)";
    private static final String SENTENCIA_UPDATE = "update serves set bar=? set beer=? set price=? where bar=? and beer=?";
    private static final String SENTENCIA_DELETE = "delete from serves where bar=? and beer=?";
    
    private Connection conexion;
    private Statement st;
    private ResultSet rs;
    private ResultSetMetaData rsmd;
    private PreparedStatement pdst;
    
    public List<Serves> selectST(String campo, String valor) throws SQLException, ClassNotFoundException, IOException {
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
    
    public Serves selectPDST(String bar_valor, String beer_valor) throws SQLException, ClassNotFoundException, IOException {
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
        PreparedStatement pdst = conexion.prepareStatement("select * from serves where bar=? and beer=?");
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
            System.out.print(bar + "    " + beer + "    " + price);
            
        }
        escribir.writeObject("selectPDST");
        escribir.writeObject(serves);
        escribir.close();
        closeGeneral();
        
        return serves;
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
        Class.forName("oracle.jdbc.OracleDriver");
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
