package py.com.leader.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexion {
    private final String driver = "org.postgresql.Driver";
    private final String servidor = "localhost";
    private final String puerto = "5432";
    private final String usuario = "postgres";
    private final String clave = "postgres";
    private final String baseDato = "leaderbr";
    private Connection con;
    private Statement st;

    public boolean conectar() {
        boolean ok = false;
        try {
            Class.forName(driver);
            String connectString = "jdbc:postgresql://"+servidor+":"+puerto+"/"+baseDato;
            con = DriverManager.getConnection(connectString, usuario, clave);
            st = con.createStatement();
            con.setAutoCommit(false);
            st.setFetchSize(100);
            ok = true;
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("error Conexion --> "+ex.getLocalizedMessage());
        }
        return ok;
    }
       
    public boolean cerrar(){
        boolean ok=false;
        try {
            st.close();
            con.close();
            ok=true;
        } catch (SQLException ex) {
            System.out.println("--> "+ex.getLocalizedMessage());
        }
        return ok;
    }

    public Connection getCon() {
        return con;
    }

    public Statement getSt() {
        return st;
    }
   
}
