package py.com.leader.DAO;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import py.com.leader.modelo.Persona;
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
public class UsuarioDAO {

    public boolean validar(Usuario usuario, HttpServletRequest request) {
        boolean acceso = false;
        Conexion con = new Conexion();
        if (con.conectar()) {
            System.out.println("--> Conectado");
            String sql = "select * from usuarios"
                    +" where usuario_usuario = ? "
                    +" and clave_usuario = ? ";
            //System.out.println("sql: "+sql);
            ResultSet rs;
            try (PreparedStatement ps = con.getCon().prepareStatement(sql)){
                ps.setString(1, usuario.getUsuario_usuario());
                ps.setString(2, usuario.getClave_usuario());
                //rs = con.getSt().executeQuery(sql);
                rs = ps.executeQuery();
                if(rs.next()){
                    //Creo la session
                    HttpSession session = request.getSession(true);
                    //Creo un usuario para identificar el usuario logueado
                    Usuario usuarioLogueado = new Usuario();
                    usuarioLogueado.setId_usuario(rs.getInt("id_usuario"));
                    usuarioLogueado.setUsuario_usuario(rs.getString("usuario_usuario"));
                    usuarioLogueado.setNombre_usuario(rs.getString("nombre_usuario"));
                    usuarioLogueado.setClave_usuario("AF1214D174");
                    //Cargo el usuario en la session
                    session.setAttribute("usuarioLogueado", usuarioLogueado);
                    System.out.println("New Session: "+session.getId()); 
                    acceso = true;
                }
            } catch (SQLException e) {
                System.out.println(e.getLocalizedMessage());
            }
            con.cerrar();
        }
        return acceso;
    }
    
    public Usuario buscarId(int id) {
        Usuario usuario = new Usuario();
        usuario.setId_usuario(0);
        usuario.setNombre_usuario("");
        usuario.setUsuario_usuario("");
        usuario.setClave_usuario("");
        
        Conexion conexion = new Conexion();
        if (conexion.conectar()) {
            try {
                String sql = "select * "
                        + " from usuarios left join personas "
                        + " on id_persona_usuario = id_persona "
                        + " where id_usuario = ? ";
                try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                    ps.setInt(1, id);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        usuario.setId_usuario(rs.getInt("id_usuario"));
                        usuario.setNombre_usuario(rs.getString("nombre_usuario"));
                        Persona persona = new Persona();
                        persona.setId_persona(rs.getInt("id_persona"));
                        persona.setNombre_persona(rs.getString("nombre_persona"));
                        persona.setNombre_fantasia_persona(rs.getString("nombre_fantasia_persona"));
                        persona.setFisica_persona(rs.getBoolean("fisica_persona"));
                        persona.setCi_persona(rs.getString("ci_persona"));
                        persona.setRuc_persona(rs.getString("ruc_persona"));
                        persona.setDni_persona(rs.getString("dni_persona"));
                        persona.setTelefono1_persona(rs.getString("telefono1_persona"));
                        persona.setTelefono2_persona(rs.getString("telefono2_persona"));
                        persona.setEmail_persona(rs.getString("email_persona"));
                        persona.setWeb_persona(rs.getString("web_persona"));
                        persona.setImagen_persona(rs.getString("imagen_persona"));
                        persona.setDireccion_persona(rs.getString("direccion_persona"));
                        persona.setFecha_nacimiento_persona(rs.getDate("fecha_nacimiento_persona"));
                        
                        usuario.setPersona(persona);
                        usuario.setUsuario_usuario(rs.getString("usuario_usuario"));
                        usuario.setClave_usuario(rs.getString("clave_usuario"));
                    }
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println("--> " + ex.getLocalizedMessage());
            }
            conexion.cerrar();
        }
        return usuario;
    }
    
    public boolean agregar(Usuario usuario) {
        boolean agregado = false;
        Conexion conexion = new Conexion();
        if (conexion.conectar()) {
            try {
                String sql = "insert into usuarios ("
                        + "nombre_usuario,"
                        + "usuario_usuario,"
                        + "clave_usuario "
                        + ") "
                        + "values (?,?,?)";
                try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                    ps.setString(1, usuario.getNombre_usuario());
                    ps.setString(2, usuario.getUsuario_usuario());
                    ps.setString(3, usuario.getClave_usuario());
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
    
    public boolean modificar(Usuario usuario) {
        boolean modificado = false;
        Conexion conexion = new Conexion();
        if (conexion.conectar()) {
            try {
                String sql = "update usuarios set "
                        + "nombre_usuario = ?, "
                        + "usuario_usuario = ?, "
                        + "clave_usuario = ? "
                        + "id_persona_usuario = ? "
                        + "where id_usuario = ? ;";
                
                //System.out.println(sql);
                try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                    ps.setString(1, usuario.getNombre_usuario());
                    ps.setString(2, usuario.getUsuario_usuario());
                    ps.setString(3, usuario.getClave_usuario());
                    ps.setInt(4, usuario.getPersona().getId_persona());
                    ps.setInt(5, usuario.getId_usuario());
                    //System.out.println("id_usuario: "+usuario.getId_usuario()+"Nombre: "+usuario.getNombre_usuario()+" usuario: "+usuario.getUsuario_usuario()+" Clave: "+usuario.getClave_usuario());
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
                String sql = "delete from usuarios "
                        + "where id_usuario=?";
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
        ArrayList usuarios = new ArrayList();
        Conexion conexion = new Conexion();
        if (conexion.conectar()) {
            try {
                String sql = "select * "
                           + "from usuarios join personas "
                           + "on id_persona_usuario = id_persona "
                           + "where upper(nombre_persona) like ? "
                           + "or upper(usuario_usuario) like ? "
                           + "order by id_usuario "
                           + "limit ? offset ?";
                System.out.println(sql);
                try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                    ps.setString(1, "%"+texto.toUpperCase()+"%");
                    ps.setString(2, "%"+texto.toUpperCase()+"%");
                    ps.setInt(3, limit);
                    ps.setInt(4, offset);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        
                        Persona persona = new Persona();
                        persona.setId_persona(rs.getInt("id_persona"));
                        persona.setNombre_persona(rs.getString("nombre_persona"));
                        persona.setNombre_fantasia_persona(rs.getString("nombre_fantasia_persona"));
                        persona.setFisica_persona(rs.getBoolean("fisica_persona"));
                        persona.setCi_persona(rs.getString("ci_persona"));
                        persona.setRuc_persona(rs.getString("ruc_persona"));
                        persona.setDni_persona(rs.getString("dni_persona"));
                        persona.setTelefono1_persona(rs.getString("telefono1_persona"));
                        persona.setTelefono2_persona(rs.getString("telefono2_persona"));
                        persona.setEmail_persona(rs.getString("email_persona"));
                        persona.setWeb_persona(rs.getString("web_persona"));
                        persona.setImagen_persona(rs.getString("imagen_persona"));
                        persona.setDireccion_persona(rs.getString("direccion_persona"));
                        persona.setFecha_nacimiento_persona(rs.getDate("fecha_nacimiento_persona"));
                        
                        usuarios.add(new Usuario(
                                                rs.getInt("id_usuario"), 
                                                rs.getString("usuario_usuario"), 
                                                rs.getString("nombre_usuario"), 
                                                rs.getString("clave_usuario"),
                                                persona
                                                )
                                    );
                    }
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println("UsuarioDAO--> " + ex.getLocalizedMessage());
            }
            conexion.cerrar();
        }
        return usuarios;
    }
    
    public boolean guardar(Usuario usuario, String detalle) {
        boolean guardado = false;
        int id = -1;
        Conexion conexion = new Conexion();
        if (conexion.conectar()) {
            try {
                String sql = "";
                if (usuario.getId_usuario() == 0) {
                    //System.out.println("Insertando.."+usuario.getId_usuario());
                    sql = "insert into usuarios(nombre_usuario,usuario_usuario,clave_usuario, id_persona_usuario) "
                            + "values("
                            + "'" + usuario.getNombre_usuario() + "',"
                            + "'" + usuario.getUsuario_usuario() + "',"
                            + "'" + usuario.getClave_usuario() + "',"
                            + "'" + usuario.getPersona().getId_persona() + "'"
                            + ")";

                    conexion.getSt().executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
                    ResultSet keyset = conexion.getSt().getGeneratedKeys();
                    if (keyset.next()) {
                        id = keyset.getInt(1);
                        guardado = guardarDetalle(id, detalle, conexion);
                    }
                } else {
                    //System.out.println("Actualizando.."+usuario.getId_usuario());
                    if (!usuario.getClave_usuario().isEmpty()){
                        sql = "update usuarios set "
                            + "nombre_usuario='" + usuario.getNombre_usuario() + "',"
                            + "usuario_usuario='" + usuario.getUsuario_usuario() + "',"
                            + "clave_usuario='" + usuario.getClave_usuario() + "', "
                            + "id_persona_usuario='" + usuario.getPersona().getId_persona() + "' "
                            + "where id_usuario=" + usuario.getId_usuario();
                    }else{
                        sql = "update usuarios set "
                            + "nombre_usuario='" + usuario.getNombre_usuario() + "',"
                            + "usuario_usuario='" + usuario.getUsuario_usuario() + "',"
                            + "id_persona_usuario='" + usuario.getPersona().getId_persona() + "' "
                            + "where id_usuario=" + usuario.getId_usuario();
                    }
                    
                    int cr = conexion.getSt().executeUpdate(sql);
                    id = usuario.getId_usuario();
                    if (cr > 0) {
                        guardado = guardarDetalle(id, detalle, conexion);
                    }
                }
            } catch (SQLException ex) {
                System.out.println("--->" + ex.getLocalizedMessage());
            }
        }
        if (guardado) {
            try {
                conexion.getCon().commit();
            } catch (SQLException ex) {
                System.out.println("--->" + ex.getLocalizedMessage());
            }
        } else {
            try {
                conexion.getCon().rollback();
            } catch (SQLException ex) {
                System.out.println("--->" + ex.getLocalizedMessage());
            }
        }
        conexion.cerrar();
        return guardado;
    }

    public boolean guardarDetalle(int id, String detalle, Conexion conexion) {
        boolean guardado = true;
        guardado = eliminarDetalle(id, conexion);
        if (guardado) {
            //System.out.println("detalle-->" + detalle);
            JsonParser parser = new JsonParser();
            JsonElement datos = parser.parse(detalle);
            //System.out.println("datos---->" + datos);
            JsonArray array = datos.getAsJsonArray();
            java.util.Iterator<JsonElement> iter = array.iterator();
            while (iter.hasNext()) {
                JsonElement entrada = iter.next();
                JsonObject obj = entrada.getAsJsonObject();
                try {
                    String sql = "insert into usuariosroles ("
                            + "id_usuario,"
                            + "id_rol"
                            + ") "
                            + "values (?,?)";
                    try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                        ps.setInt(1, id);
                        ps.setInt(2, obj.get("id_rol").getAsInt());
                        int cr = ps.executeUpdate();
                        if (cr <= 0) {
                            guardado = false;
                            break;
                        }
                    }
                } catch (SQLException ex) {
                    System.out.println("--->" + ex.getLocalizedMessage());
                    guardado = false;
                    break;
                }
            }
        }
        return guardado;
    }

    public boolean eliminarDetalle(int id, Conexion conexion) {
        boolean guardado = false;
        try {
            String sql = "delete from usuariosroles "
                    + "where id_usuario=?";
            try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                ps.setInt(1, id);
                int cr = ps.executeUpdate();
                guardado = true;
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("--->" + ex.getLocalizedMessage());
        }
        return guardado;
    }
}
