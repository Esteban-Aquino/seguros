/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.leader.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import py.com.leader.modelo.Rol;
import py.com.leader.modelo.Usuario;
import py.com.leader.modelo.Usuariorol;

/**
 *
 * @author esteban
 */
public class UsuarioRolDAO {
    public ArrayList buscarIdUsuario(int id_usuario) {
        ArrayList usuariosroles = new ArrayList();
        Conexion conexion = new Conexion();
        if (conexion.conectar()) {
            try {
                /*String sql = "select * from usuariosroles ur "
                           + "left join usuarios u on ur.id_usuario=u.id_usuario "
                           + "left join roles r on ur.id_rol=r.id_rol "
                           + "where ur.id_usuario = ? "
                           + "order by id_usuariorol ";*/
                String sql = "select ur.*, u.*, r.*, ua.id_usuario id_usuario_auditoria, ua.nombre_usuario nombre_auditoria, ua.usuario_usuario usuario_auditoria "+
                            "from usuariosroles ur "+
                            "left join usuarios u "+
                            "on ur.id_usuario=u.id_usuario "+
                            "left join roles r "+
                            "on ur.id_rol=r.id_rol "+
                            "left join usuarios ua "+
                            "on ur.id_usuarioauditoria = ua.id_usuario "+
                            "where ur.id_usuario = ? "+
                            "order by id_usuariorol ";
                //System.out.println(sql);
                try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                    ps.setInt(1, id_usuario);
                    ResultSet rs = ps.executeQuery();
                    
                    while (rs.next()) {
                        Usuariorol usuariorol = new Usuariorol();
                        usuariorol.setId_usuariorol(rs.getInt("id_usuariorol"));
                        
                        Usuario usuario = new Usuario();
                        usuario.setId_usuario(rs.getInt("id_usuario"));
                        usuario.setNombre_usuario(rs.getString("nombre_usuario"));
                        usuario.setUsuario_usuario(rs.getString("usuario_usuario"));
                        /*System.out.println(rs.getInt("id_usuario"));
                        System.out.println(rs.getString("nombre_usuario"));
                        System.out.println(rs.getString("usuario_usuario"));*/
                        Rol rol = new Rol();
                        rol.setId_rol(rs.getInt("id_rol"));
                        rol.setNombre_rol(rs.getString("nombre_rol"));

                        //System.out.println(rol.getJSONObject());
                        
                        usuariorol.setUsuario(usuario);
                        //System.out.println(usuario.getJSONObject());
                        usuariorol.setRol(rol);
                        //System.out.println(usuariorol.getJSONObject());
                        usuariosroles.add(usuariorol);
                    }
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println("--> " + ex.getLocalizedMessage());
            }
            conexion.cerrar();
        }
        return usuariosroles;
    }
}
