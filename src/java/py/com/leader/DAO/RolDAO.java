package py.com.leader.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import py.com.leader.modelo.Rol;
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
public class RolDAO {
    
    public Rol buscarId(int id) {
        Rol rol = new Rol();
        rol.setId_rol(0);
        rol.setNombre_rol("");
        Usuario usuario = new Usuario();
        usuario.setId_usuario(0);
        usuario.setUsuario_usuario("");
        usuario.setNombre_usuario("");
        rol.setUsuarioauditoria(usuario);
        Conexion conexion = new Conexion();
        if (conexion.conectar()) {
            try {
                String sql = "select * from roles r "
                           +"left join usuarios u on r.id_usuarioauditoria = u.id_usuario "
                           + "where id_rol=?";
                try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                    ps.setInt(1, id);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        rol.setId_rol(rs.getInt("id_rol"));
                        rol.setNombre_rol(rs.getString("nombre_rol"));
                        rol.getUsuarioauditoria().setId_usuario(rs.getInt("id_usuario"));
                        rol.getUsuarioauditoria().setUsuario_usuario(rs.getString("usuario_usuario"));
                        rol.getUsuarioauditoria().setNombre_usuario(rs.getString("nombre_usuario"));
                    }
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println("--> " + ex.getLocalizedMessage());
            }
            conexion.cerrar();
        }
        return rol;
    }
    
    public boolean agregar(Rol rol, Usuario usuario) {
        boolean agregado = false;
        Conexion conexion = new Conexion();
        if (conexion.conectar()) {
            try {
                String sql = "insert into roles ("
                        + "nombre_rol,"
                        + "id_usuarioauditoria"
                        + ") "
                        + "values (?,?)";
                try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                    ps.setString(1, rol.getNombre_rol());
                    ps.setInt(2, usuario.getId_usuario());
                    int cr = ps.executeUpdate();
                    if (cr > 0) {
                        agregado = true;
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
        return agregado;
    }
    
    public boolean modificar(Rol rol, Usuario usuario) {
        boolean modificado = false;
        Conexion conexion = new Conexion();
        if (conexion.conectar()) {
            try {
                String sql = "update roles set "
                        + "nombre_rol = ?, "
                        + "id_usuarioauditoria = ? "
                        + "where id_rol = ?";
                
                //System.out.println(sql);
                try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                    ps.setString(1, rol.getNombre_rol());
                    ps.setInt(2, usuario.getId_usuario());
                    ps.setInt(3, rol.getId_rol());
                    int cr = ps.executeUpdate();
                    if (cr > 0) {
                        modificado = true;
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
        return modificado;
    }
    
    public boolean eliminar(int id) {
        boolean eliminado = false;
        Conexion conexion = new Conexion();
        if (conexion.conectar()) {
            try {
                String sql = "delete from roles "
                        + "where id_rol=?";
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
        int offset = (pagina-1)*limit;
        ArrayList roles = new ArrayList();
        Conexion conexion = new Conexion();
        Usuario usuario = new Usuario();
        usuario.setId_usuario(0);
        usuario.setUsuario_usuario("");
        usuario.setNombre_usuario("");
        if (conexion.conectar()) {
            try {
                String sql = "select * from roles r "
                           +"left join usuarios u on r.id_usuarioauditoria = u.id_usuario "
                           + "where upper(nombre_rol) like ? "
                           + "order by id_rol "
                           + "limit ? offset ?";
                try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                    ps.setString(1, "%"+texto.toUpperCase()+"%");
                    ps.setInt(2, limit);
                    ps.setInt(3, offset);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        usuario.setId_usuario(rs.getInt("id_usuario"));
                        usuario.setUsuario_usuario(rs.getString("usuario_usuario"));
                        usuario.setNombre_usuario(rs.getString("nombre_usuario"));
                        roles.add(new Rol(
                                                rs.getInt("id_rol"), 
                                                rs.getString("nombre_rol"), 
                                                usuario
                                                )
                                    );
                    }
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println("RolDao--> " + ex.getLocalizedMessage());
            }
            conexion.cerrar();
        }
        return roles;
    }
}
