package py.com.leader.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import py.com.leader.modelo.Sistema;
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
public class SistemaDAO {

    public Sistema buscarId(int id) {
        Sistema sistema = new Sistema();
        sistema.setId_sistema(0);
        sistema.setNombre_sistema("");
        sistema.setUrl_sistema("");
        Usuario usuario = new Usuario();
        usuario.setId_usuario(0);
        usuario.setUsuario_usuario("");
        usuario.setNombre_usuario("");
        sistema.setUsuarioauditoria(usuario);
        Conexion conexion = new Conexion();
        if (conexion.conectar()) {
            try {
                String sql = "select * from sistemas r "
                        + "left join usuarios u on r.id_usuarioauditoria = u.id_usuario "
                        + "where id_sistema=?";
                try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                    ps.setInt(1, id);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        sistema.setId_sistema(rs.getInt("id_sistema"));
                        sistema.setNombre_sistema(rs.getString("nombre_sistema"));
                        sistema.setUrl_sistema(rs.getString("url_sistema"));
                        sistema.getUsuarioauditoria().setId_usuario(rs.getInt("id_usuario"));
                        sistema.getUsuarioauditoria().setUsuario_usuario(rs.getString("usuario_usuario"));
                        sistema.getUsuarioauditoria().setNombre_usuario(rs.getString("nombre_usuario"));
                    }
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println("--> " + ex.getLocalizedMessage());
            }
            conexion.cerrar();
        }
        return sistema;
    }

    public boolean guardar(Sistema sistema, Usuario usuario) {
        boolean guardado = false;
        int id = -1;
        Conexion conexion = new Conexion();
        if (conexion.conectar()) {
            try {
                String sql = "";
                if (sistema.getId_sistema() == 0) {
                    sql = "insert into sistemas ("
                            + "nombre_sistema,"
                            + "url_sistema,"
                            + "id_usuarioauditoria"
                            + ") "
                            + "values (?,?,?)";
                } else {
                    sql = "update sistemas set "
                            + "nombre_sistema = ?, "
                            + "url_sistema = ? ,"
                            + "id_usuarioauditoria = ? "
                            + "where id_sistema = ?";
                }
                try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                    ps.setString(1, sistema.getNombre_sistema());
                    ps.setString(2, sistema.getUrl_sistema());
                    ps.setInt(3, usuario.getId_usuario());
                    if (sistema.getId_sistema() != 0) {
                        ps.setInt(4, sistema.getId_sistema());
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
                String sql = "delete from sistemas "
                        + "where id_sistema = ?";
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
        ArrayList sistema = new ArrayList();
        Conexion conexion = new Conexion();
        Usuario usuario = new Usuario();
        usuario.setId_usuario(0);
        usuario.setUsuario_usuario("");
        usuario.setNombre_usuario("");
        if (conexion.conectar()) {
            try {
                String sql = "select * from sistemas r "
                        + "left join usuarios u on r.id_usuarioauditoria = u.id_usuario "
                        + "where upper(nombre_sistema) like ? "
                        + "order by id_sistema "
                        + "limit ? offset ?";
                try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                    ps.setString(1, "%" + texto.toUpperCase() + "%");
                    ps.setInt(2, limit);
                    ps.setInt(3, offset);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        usuario.setId_usuario(rs.getInt("id_usuario"));
                        usuario.setUsuario_usuario(rs.getString("usuario_usuario"));
                        usuario.setNombre_usuario(rs.getString("nombre_usuario"));
                        sistema.add(new Sistema(
                                rs.getInt("id_sistema"),
                                rs.getString("nombre_sistema"),
                                rs.getString("url_sistema"),
                                usuario
                        )
                        );
                    }
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println("SistemaDao--> " + ex.getLocalizedMessage());
            }
            conexion.cerrar();
        }
        return sistema;
    }
}
