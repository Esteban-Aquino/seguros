package py.com.leader.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import py.com.leader.modelo.Tipo_bien;
import py.com.leader.modelo.Usuario;
import py.com.leader.util.Configuracion;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Esteban
 */
public class Tipo_bienDAO {

    public Tipo_bien buscarId(int id) {
        Tipo_bien tipo_bien = new Tipo_bien();
        tipo_bien.setId_tipo_bien(0);
        tipo_bien.setNombre_tipo_bien("");

        Conexion conexion = new Conexion();
        if (conexion.conectar()) {
            try {
                String sql = "select * from tipos_bienes r "
                        + "where id_tipo_bien = ?";
                try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                    ps.setInt(1, id);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        tipo_bien.setId_tipo_bien(rs.getInt("id_tipo_bien"));
                        tipo_bien.setNombre_tipo_bien(rs.getString("nombre_tipo_bien"));
                    }
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error Tipo_bien:--> " + ex.getLocalizedMessage());
            }
            conexion.cerrar();
        }
        return tipo_bien;
    }

    public boolean guardar(Tipo_bien tipo_bien, Usuario usuario) {
        boolean guardado = false;
        int id = -1;
        Conexion conexion = new Conexion();
        if (conexion.conectar()) {
            try {
                String sql = "";
                if (tipo_bien.getId_tipo_bien() == 0) {
                    sql = "insert into tipos_bienes (nombre_tipo_bien) "
                            + "values (?)";
                } else {
                    sql = "update tipos_bienes "
                            + "set nombre_tipo_bien = ?, "
                            + "where id_tipo_bien = ?";
                }
                try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                    ps.setString(1, tipo_bien.getNombre_tipo_bien());
                    if (tipo_bien.getId_tipo_bien() != 0) {
                        ps.setInt(2, tipo_bien.getId_tipo_bien());
                    }
                    int cr = ps.executeUpdate();
                    if (cr > 0) {
                        guardado = true;
                        conexion.getCon().commit();
                    }
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println("--->" + ex.getLocalizedMessage());
            }
        }
        conexion.cerrar();
        return guardado;
    }

    public boolean eliminar(int id) {
        boolean eliminado = false;
        Conexion conexion = new Conexion();
        if (conexion.conectar()) {
            try {
                String sql = "delete from tipos_bienes "
                        + "where id_tipo_bien = ?";
                try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                    ps.setInt(1, id);
                    int cr = ps.executeUpdate();
                    if (cr > 0) {
                        eliminado = true;
                        conexion.getCon().commit();
                    }
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println("--->" + ex.getLocalizedMessage());
                try {
                    conexion.getCon().rollback();
                } catch (SQLException ex1) {
                    System.out.println("-->" + ex.getLocalizedMessage());
                }
            }
        }
        conexion.cerrar();
        return eliminado;
    }

    public ArrayList buscarNombre(String texto, int pagina) {
        int limit = Configuracion.REGISTROS_POR_PAGINA;
        int offset = (pagina - 1) * limit;
        //System.out.println(texto);
        ArrayList tipo_bien = new ArrayList();
        Conexion conexion = new Conexion();
        //Usuario usuario = new Usuario();
        if (conexion.conectar()) {
            try {
                String sql = "select *" +
                             "from tipos_bienes " +
                             "where to_char(id_tipo_bien,'') like ?" +
                             "or nombre_tipo_bien like ? "+
                             "limit ? offset ? ";
                try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                    ps.setString(1, "%" + texto.toUpperCase() + "%");
                    ps.setString(2, "%" + texto.toUpperCase() + "%");
                    ps.setInt(3, limit);
                    ps.setInt(4, offset);
                    //System.out.println(ps.toString());
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        tipo_bien.add(new Tipo_bien(rs.getInt("id_tipo_bien"),
                                                    rs.getString("nombre_tipo_bien"),
                                                    new Usuario())
                        );
                    }
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println("Tipo_bienDao--> " + ex.getLocalizedMessage());
            }
            conexion.cerrar();
        }
        return tipo_bien;
    }

}
