package py.com.leader.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import py.com.leader.modelo.Formulario;
import py.com.leader.modelo.Permiso;
import py.com.leader.modelo.Rol;
import py.com.leader.modelo.Sistema;
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
public class FormularioDAO {

    public Formulario buscarId(int id) {
        Formulario formulario = new Formulario();
        formulario.setId_formulario(0);
        formulario.setNombre_formulario("");
        formulario.setUrl_formulario("");
        Conexion conexion = new Conexion();
        if (conexion.conectar()) {
            try {
                /*String sql = "select * from formularios r "
                        + "left join usuarios u on r.id_usuarioauditoria = u.id_usuario "
                        + "where id_formulario=?";*/
                String sql = "select * from formularios r " +
                             "left join usuarios u on r.id_usuarioauditoria = u.id_usuario " +
                             "left join sistemas s on r.id_sistema = s.id_sistema " +
                             "left join submenus m on r.id_submenu = m.id_submenu "+
                             "where id_formulario = ?";
                try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                    ps.setInt(1, id);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        formulario.setId_formulario(rs.getInt("id_formulario"));
                        formulario.setNombre_formulario(rs.getString("nombre_formulario"));
                        formulario.setUrl_formulario(rs.getString("url_formulario"));
                        
                        formulario.getUsuarioauditoria().setId_usuario(rs.getInt("id_usuario"));
                        formulario.getUsuarioauditoria().setUsuario_usuario(rs.getString("usuario_usuario"));
                        formulario.getUsuarioauditoria().setNombre_usuario(rs.getString("nombre_usuario"));
                        
                        formulario.getSistema().setId_sistema(rs.getInt("id_sistema"));
                        formulario.getSistema().setNombre_sistema(rs.getString("nombre_sistema"));
                        formulario.getSistema().setUrl_sistema(rs.getString("url_sistema"));
                        
                        formulario.getSubmenu().setId_submenu(rs.getInt("id_submenu"));
                        formulario.getSubmenu().setNombre_submenu(rs.getString("nombre_submenu"));
                        formulario.getSubmenu().setUrl_submenu(rs.getString("url_submenu"));
                    }
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println("--> " + ex.getLocalizedMessage());
            }
            conexion.cerrar();
        }
        return formulario;
    }

    public boolean guardar(Formulario formulario, Usuario usuario) {
        boolean guardado = false;
        int id = -1;
        Conexion conexion = new Conexion();
        if (conexion.conectar()) {
            try {
                String sql = "";
                if (formulario.getId_formulario() == 0) {
                    sql = "insert into formularios ("
                            + "nombre_formulario,"
                            + "url_formulario,"
                            + "id_sistema,"
                            + "id_submenu,"
                            + "id_usuarioauditoria"
                            + ") "
                            + "values (?,?,?,?,?)";
                } else {
                    sql = "update formularios set "
                            + "nombre_formulario = ?, "
                            + "url_formulario = ? , "
                            + "id_sistema = ?, "
                            + "id_submenu = ?, "
                            + "id_usuarioauditoria = ? "
                            + "where id_formulario = ?";
                }
                try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                    ps.setString(1, formulario.getNombre_formulario());
                    ps.setString(2, formulario.getUrl_formulario());
                    ps.setInt(3, formulario.getSistema().getId_sistema());
                    ps.setInt(4, formulario.getSubmenu().getId_submenu());
                    ps.setInt(5, usuario.getId_usuario());
                    if (formulario.getId_formulario() != 0) {
                        ps.setInt(6, formulario.getId_formulario());
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
                String sql = "delete from formularios "
                        + "where id_formulario = ?";
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
        ArrayList formulario = new ArrayList();
        Conexion conexion = new Conexion();
        Usuario usuario = new Usuario();
        Sistema sistema = new Sistema();
        Submenu submenu = new Submenu();
        
        if (conexion.conectar()) {
            try {
                /*String sql = "select * from formularios r "
                        + "left join usuarios u on r.id_usuarioauditoria = u.id_usuario "
                        + "where upper(nombre_formulario) like ? "
                        + "order by id_formulario "
                        + "limit ? offset ?";*/
                String sql = "select * from formularios r " +
                             "left join usuarios u on r.id_usuarioauditoria = u.id_usuario " +
                             "left join sistemas s on r.id_sistema = s.id_sistema " +
                             "left join submenus m on r.id_submenu = m.id_submenu "+
                             "where upper(nombre_formulario) like ? "+
                             "order by id_formulario "+
                             "limit ? offset ?";
                        ;
                try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                    ps.setString(1, "%" + texto.toUpperCase() + "%");
                    ps.setInt(2, limit);
                    ps.setInt(3, offset);
                    //System.out.println(ps.toString());
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        usuario = new Usuario();
                        usuario.setId_usuario(rs.getInt("id_usuario"));
                        usuario.setUsuario_usuario(rs.getString("usuario_usuario"));
                        usuario.setNombre_usuario(rs.getString("nombre_usuario"));
                        sistema = new Sistema();
                        sistema.setId_sistema(rs.getInt("id_sistema"));
                        sistema.setNombre_sistema(rs.getString("nombre_sistema"));
                        sistema.setUrl_sistema(rs.getString("url_sistema"));
                        submenu = new Submenu();
                        submenu.setId_submenu(rs.getInt("id_submenu"));
                        submenu.setNombre_submenu(rs.getString("nombre_submenu"));
                        submenu.setUrl_submenu(rs.getString("url_submenu"));
                        
                        //System.out.println("Formulario: "+rs.getString("nombre_formulario")+" Sistema: "+sistema.getNombre_sistema()+" Submenu: "+submenu.getNombre_submenu());
                        formulario.add(new Formulario(
                                rs.getInt("id_formulario"),
                                rs.getString("nombre_formulario"),
                                rs.getString("url_formulario"),
                                sistema,
                                submenu,
                                usuario)
                        );
                    }
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println("FormularioDao--> " + ex.getLocalizedMessage());
            }
            conexion.cerrar();
        }
        return formulario;
    }
    
    public ArrayList listar(int id_rol) {
        ArrayList permisos = new ArrayList();
        Conexion conexion = new Conexion();
        if (conexion.conectar()) {
            try {
                String sql = "select * from formularios f "
                           + "left join sistemas s on f.id_sistema=s.id_sistema "
                           + "left join submenus sm on f.id_submenu=sm.id_submenu "
                           + "left join permisos p on f.id_formulario=p.id_formulario and p.id_rol=? "
                           + "left join roles r on p.id_rol = r.id_rol "
                           + "left join usuarios u on p.id_usuarioauditoria = u.id_usuario "
                           + "order by f.id_sistema,f.id_submenu,f.id_formulario ";
                try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                    ps.setInt(1, id_rol);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        Permiso permiso = new Permiso();
                        permiso.setId_permiso(rs.getInt("id_permiso"));
                        
                        Formulario formulario = new Formulario();
                        formulario.setId_formulario(rs.getInt("id_formulario"));
                        formulario.setNombre_formulario(rs.getString("nombre_formulario"));
                        formulario.setUrl_formulario(rs.getString("url_formulario"));
                        
                        Sistema sistema = new Sistema();
                        sistema.setId_sistema(rs.getInt("id_sistema"));
                        sistema.setNombre_sistema(rs.getString("nombre_sistema"));
                        sistema.setUrl_sistema(rs.getString("url_sistema"));
                        
                        Submenu submenu = new Submenu();
                        submenu.setId_submenu(rs.getInt("id_submenu"));
                        submenu.setNombre_submenu(rs.getString("nombre_submenu"));
                        submenu.setUrl_submenu(rs.getString("url_submenu"));
                        
                        Rol rol = new Rol();
                        rol.setId_rol(rs.getInt("id_rol"));
                        rol.setNombre_rol(rs.getString("nombre_rol"));
                        
                        permiso.setRol(rol);
                                               
                        formulario.setSistema(sistema);
                        formulario.setSubmenu(submenu);
                        
                        permiso.setFormulario(formulario);
                        
                        Usuario usuario = new Usuario();
                        usuario.setId_usuario(rs.getInt("id_usuarioauditoria"));
                        usuario.setNombre_usuario(rs.getString("nombre_usuario"));
                        usuario.setUsuario_usuario(rs.getString("usuario_usuario"));
                        usuario.setClave_usuario(rs.getString("clave_usuario"));
                        
                        permiso.setUsuarioauditoria(usuario);
                                               
                        permiso.setAgregar_permiso(rs.getBoolean("agregar_permiso"));
                        permiso.setModificar_permiso(rs.getBoolean("modificar_permiso"));
                        permiso.setEliminar_permiso(rs.getBoolean("eliminar_permiso"));
                        permiso.setConsultar_permiso(rs.getBoolean("consultar_permiso"));
                        permiso.setListar_permiso(rs.getBoolean("listar_permiso"));
                        permiso.setInformes_permiso(rs.getBoolean("informes_permiso"));
                        permiso.setExportar_permiso(rs.getBoolean("exportar_permiso"));
                        
                        permisos.add(permiso);
                    }
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println("--> " + ex.getLocalizedMessage());
            }
            conexion.cerrar();
        }
        return permisos;
    }
}
