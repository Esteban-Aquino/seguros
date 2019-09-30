package py.com.leader.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import py.com.leader.modelo.Submenu;
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
public class SubmenuDAO {

    public Submenu buscarId(int id) {
        Submenu submenu = new Submenu();
        submenu.setId_submenu(0);
        submenu.setNombre_submenu("");
        submenu.setUrl_submenu("");
        Usuario usuario = new Usuario();
        usuario.setId_usuario(0);
        usuario.setUsuario_usuario("");
        usuario.setNombre_usuario("");
        submenu.setUsuarioauditoria(usuario);
        Conexion conexion = new Conexion();
        if (conexion.conectar()) {
            try {
                String sql = "select * from submenus r "
                        + "left join usuarios u on r.id_usuarioauditoria = u.id_usuario "
                        + "where id_submenu=?";
                try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                    ps.setInt(1, id);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        submenu.setId_submenu(rs.getInt("id_submenu"));
                        submenu.setNombre_submenu(rs.getString("nombre_submenu"));
                        submenu.setUrl_submenu(rs.getString("url_submenu"));
                        submenu.getUsuarioauditoria().setId_usuario(rs.getInt("id_usuario"));
                        submenu.getUsuarioauditoria().setUsuario_usuario(rs.getString("usuario_usuario"));
                        submenu.getUsuarioauditoria().setNombre_usuario(rs.getString("nombre_usuario"));
                    }
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println("--> " + ex.getLocalizedMessage());
            }
            conexion.cerrar();
        }
        return submenu;
    }

    public boolean guardar(Submenu submenu, Usuario usuario) {
        boolean guardado = false;
        int id = -1;
        Conexion conexion = new Conexion();
        if (conexion.conectar()) {
            try {
                String sql = "";
                if (submenu.getId_submenu() == 0) {
                    sql = "insert into submenus ("
                            + "nombre_submenu,"
                            + "url_submenu,"
                            + "id_usuarioauditoria"
                            + ") "
                            + "values (?,?,?)";
                } else {
                    sql = "update submenus set "
                            + "nombre_submenu = ?, "
                            + "url_submenu = ? ,"
                            + "id_usuarioauditoria = ? "
                            + "where id_submenu = ?";
                }
                try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                    ps.setString(1, submenu.getNombre_submenu());
                    ps.setString(2, submenu.getUrl_submenu());
                    ps.setInt(3, usuario.getId_usuario());
                    if (submenu.getId_submenu() != 0) {
                        ps.setInt(4, submenu.getId_submenu());
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
                String sql = "delete from submenus "
                        + "where id_submenu = ?";
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
        ArrayList submenu = new ArrayList();
        Conexion conexion = new Conexion();
        Usuario usuario = new Usuario();
        usuario.setId_usuario(0);
        usuario.setUsuario_usuario("");
        usuario.setNombre_usuario("");
        if (conexion.conectar()) {
            try {
                String sql = "select * from submenus r "
                        + "left join usuarios u on r.id_usuarioauditoria = u.id_usuario "
                        + "where upper(nombre_submenu) like ? "
                        + "order by id_submenu "
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
                        submenu.add(new Submenu(
                                rs.getInt("id_submenu"),
                                rs.getString("nombre_submenu"),
                                rs.getString("url_submenu"),
                                usuario
                        )
                        );
                    }
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println("SubmenuDao--> " + ex.getLocalizedMessage());
            }
            conexion.cerrar();
        }
        return submenu;
    }
}
