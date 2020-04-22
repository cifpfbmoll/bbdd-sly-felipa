package practica8;

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

    public List<Serves> selectST(String campo, String valor) throws SQLException, ClassNotFoundException {
        List<Serves> listaServes = new ArrayList<>();

        //inicia la conexión
        abrirConectar();
        //se ingresa la consulta
        rs = st.executeQuery("select * from serves where " + campo + "=" + valor);
//        rs = st.executeQuery("select * from serves where price=" + filtro);

        System.out.println("");
        while (rs.next()) {
            //se cogen los valores que devolvió la consulta
            String bar = rs.getString("bar");
            String beer = rs.getString("beer");
            Double price = rs.getDouble("price");
            //las variables construyen un obj nuevo
            Serves serves = new Serves(bar, beer, price);
            //los objetos nuevos se agregan a la lista
            listaServes.add(serves);
            System.out.println(bar + beer + price);
        }
        System.out.println("");

        closeGeneral();

        return listaServes;

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
        ResultSetMetaData rsmd = rs.getMetaData();

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
                    + " ");
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
