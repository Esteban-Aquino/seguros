package py.com.leader.DAO;



import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
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
import static py.com.leader.util.util.nvl;


public class PermisoDAO {

    public ArrayList buscarPermisoIdRol(int id_rol) {
        ArrayList permisos = new ArrayList();
        Conexion conexion = new Conexion();
        if (conexion.conectar()) {
            try {
                /*String sql = "select * from permisos p "
                        + "left join roles r on p.id_rol = r.id_rol "
                        + "left join formularios f on p.id_formulario = f.id_formulario "
                        + "left join sistemas s on f.id_sistema = s.id_sistema "
                        + "left join submenus sm on f.id_submenu = sm.id_submenu "
                        + "left join usuarios u on p.id_usuarioauditoria = u.id_usuario "
                        + "where p.id_rol = ? "
                        + "order by f.id_sistema,f.id_submenu,f.id_formulario";*/
                String sql =  " SELECT *"
                            + " FROM V_ACCESOS_PERMISOS "
                            + " WHERE ID_ROL = ? "
                            + " ORDER BY id_sistema, id_submenu, id_formulario";
                

                try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                    ps.setInt(1, id_rol);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        Permiso permiso = new Permiso();
                        permiso.setId_permiso(rs.getInt("id_permiso"));
                        
                        Rol rol = new Rol();
                        rol.setId_rol(rs.getInt("id_rol"));
                        rol.setNombre_rol(rs.getString("nombre_rol"));
                        
                        permiso.setRol(rol);
                        
                        Formulario formulario = new Formulario();
                        formulario.setId_formulario(rs.getInt("id_formulario"));
                        formulario.setNombre_formulario(rs.getString("nombre_formulario"));
                        formulario.setUrl_formulario(rs.getString("url_formulario"));
                        
                        Sistema sistema = new Sistema();
                        sistema.setId_sistema(rs.getInt("id_sistema"));
                        sistema.setNombre_sistema(rs.getString("nombre_sistema"));
                        sistema.setUrl_sistema(rs.getString("url_sistema"));
                        
                        formulario.setSistema(sistema);
                        
                        Submenu submenu = new Submenu();
                        submenu.setId_submenu(rs.getInt("id_submenu"));
                        submenu.setNombre_submenu(rs.getString("nombre_submenu"));
                        submenu.setUrl_submenu(rs.getString("url_submenu"));
                        
                        formulario.setSubmenu(submenu);
                        
                        permiso.setFormulario(formulario);
                        
                        Usuario usuario = new Usuario();
                        usuario.setId_usuario(rs.getInt("id_usuarioauditoria"));
                        usuario.setNombre_usuario(rs.getString("nombre_usuario"));
                        usuario.setUsuario_usuario(rs.getString("usuario_usuario"));
                        usuario.setClave_usuario(rs.getString("clave_usuario"));
                        
                        permiso.setUsuarioauditoria(usuario);
                        
                        //permiso.setId_permiso(rs.getInt("id_rol"));
                        
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
    
    public boolean existePermisoRolFormulario(int id_rol,int id_formulario,Conexion conexion) {
        boolean vexiste = false;
            String sql = "SELECT * "
                    + "FROM V_ACCESOS_PERMISOS "
                    + "WHERE ID_ROL = ? AND ID_FORMULARIO = ?";
            try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)){
                ps.setInt(1, id_rol);
                ps.setInt(2, id_formulario);
                ResultSet rs = ps.executeQuery();
                 while (rs.next()) {
                     //System.out.println("id_permiso---->" + rs.getInt("id_permiso")+" con nvl "+nvl(rs.getInt("id_permiso"),0));
                     if (nvl(rs.getInt("id_permiso"),0)!=0){
                         vexiste = true;
                     }
                 }
            }catch(SQLException ex){
                System.out.println("Busca rol - Formulario--> " + ex.getLocalizedMessage());
                ex.printStackTrace();
            }
        //System.out.println("vexiste---->" + vexiste);
        return vexiste;
    }
    
    public boolean borrarPermisoRolFormulario(int id_rol,int id_formulario, Conexion conexion) {
        boolean eliminado = false;
        //if (conexion.conectar()) {
            try {
                String sql = "delete from permisos "
                        + "where id_rol=? and id_formulario = ?";
                try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                    ps.setInt(1, id_rol);
                    ps.setInt(2, id_formulario);
                    int cr = ps.executeUpdate();
                    if (cr > 0) {
                        eliminado = true;
                    }
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println("Borrar permiso--->" + ex.getLocalizedMessage());
            }
        //}
        //conexion.cerrar();
        return eliminado;
    }
    
    public boolean guardar(String detalle,Usuario usuarioauditoria){
        boolean guardado = true;
        Conexion conexion = new Conexion();
        Integer id_rol;
        Integer id_formulario;
        boolean agregar_permiso;
        boolean modificar_permiso;
        boolean eliminar_permiso;
        boolean consultar_permiso;
        boolean listar_permiso;
        boolean informes_permiso;
        boolean exportar_permiso;
        if (conexion.conectar()) {
            JsonParser parser = new JsonParser();
            JsonElement datos = parser.parse(detalle);
            //System.out.println("datos---->" + datos);
            JsonArray array = datos.getAsJsonArray();
            java.util.Iterator<JsonElement> iter = array.iterator();
            while (iter.hasNext()) {
                JsonElement entrada = iter.next();
                JsonObject obj = entrada.getAsJsonObject();
                id_rol = obj.get("id_rol").getAsInt();
                id_formulario = obj.get("id_formulario").getAsInt();
                //System.out.println("id_rol---->" + id_rol + " id_formulario---->" + id_formulario);
                if (existePermisoRolFormulario(id_rol,id_formulario,conexion)){
                    guardado = borrarPermisoRolFormulario(id_rol,id_formulario,conexion);
                    //System.out.println("guardado---->" + guardado);
                }
                if (guardado){
                    //Extraer los permisos
                    agregar_permiso = obj.get("agregar_permiso").getAsBoolean();
                    modificar_permiso = obj.get("modificar_permiso").getAsBoolean();
                    eliminar_permiso = obj.get("eliminar_permiso").getAsBoolean();
                    consultar_permiso = obj.get("consultar_permiso").getAsBoolean();
                    listar_permiso = obj.get("listar_permiso").getAsBoolean();
                    informes_permiso = obj.get("informes_permiso").getAsBoolean();
                    exportar_permiso = obj.get("exportar_permiso").getAsBoolean();
                    //Guardar los permisos
                    try {
                        String sql = "insert into "
                                    + "permisos (id_rol, "
                                              + "id_formulario, "
                                              + "agregar_permiso, "
                                              + "modificar_permiso, "
                                              + "eliminar_permiso, "
                                              + "consultar_permiso, "
                                              + "listar_permiso, "
                                              + "informes_permiso, "
                                              + "exportar_permiso, "
                                              + "id_usuarioauditoria)"
                                    + "values (?,?,?,?,?,?,?,?,?,?)";
                        try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                            ps.setInt(1, id_rol);
                            ps.setInt(2, id_formulario);
                            ps.setBoolean(3, agregar_permiso);
                            ps.setBoolean(4, modificar_permiso);
                            ps.setBoolean(5, eliminar_permiso);
                            ps.setBoolean(6, consultar_permiso);
                            ps.setBoolean(7, listar_permiso);
                            ps.setBoolean(8, informes_permiso);
                            ps.setBoolean(9, exportar_permiso);
                            ps.setInt(10, usuarioauditoria.getId_usuario());
                            
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
        }
        return guardado;
    }
    /*public boolean guardar(String detalle) {
        boolean guardado = true;
        System.out.println(detalle);
        
        String operacion = "";
        StringTokenizer st = new StringTokenizer(detalle, "}");
        Conexion conexion = new Conexion();
        if (conexion.conectar()) {
            while (st.hasMoreTokens()) {
                String registro = st.nextToken().replace("[{", "").replace(",{", "");
                if (!registro.equals("]")) {
                    //System.out.println(registro);
                    StringTokenizer st2 = new StringTokenizer(registro, ",");
                    String sql = "";

                    String sqlAgregar = "insert into permisos";
                    String camposAgregar = "(";
                    String valuesAgregar = "values(";

                    String sqlModificar = "update permisos set ";
                    String camposModificar = "";
                    String where = "";

                    while (st2.hasMoreTokens()) {
                        String registro2 = st2.nextToken();
                        //System.out.println(registro2);
                        StringTokenizer st3 = new StringTokenizer(registro2, ":");
                        while (st3.hasMoreTokens()) {
                            String campo = st3.nextToken();
                            String value = st3.nextToken();
                            //System.out.println(registro3);
                            if (!(campo.equals("\"id_permiso\""))) {
                                camposAgregar += campo.replace("\"", "") + ",";
                                valuesAgregar += value.replace("\"", "'") + ",";

                                camposModificar += campo.replace("\"", "") + "=" + value.replace("\"", "'") + ",";
                            }
                            if (campo.equals("\"id_permiso\"")) {
                                where = "where id_permiso=" + value.replace("\"", "");
                            }
                            if (campo.equals("\"id_permiso\"")) {
                                if (value.equals("\"0\"")) {
                                    operacion = "\"Agregar\"";
                                }else{
                                    operacion = "\"Modificar\"";
                                }
                            }
                        }
                    }
                    //System.out.println(operacion);
                    if (operacion.equals("\"Agregar\"")) {
                        camposAgregar = camposAgregar.substring(0, (camposAgregar.length() - 1));
                        valuesAgregar = valuesAgregar.substring(0, (valuesAgregar.length() - 1));
                        sql = sqlAgregar + camposAgregar + ") " + valuesAgregar + ")";
                    } else if (operacion.equals("\"Modificar\"")) {
                        camposModificar = camposModificar.substring(0, (camposModificar.length() - 1));
                        sql = sqlModificar + camposModificar + " " + where;
                    }
                    if (!sql.equals("")) {
                        System.out.println(sql);
                        try {
                            conexion.getSt().executeUpdate(sql);
                        } catch (SQLException ex) {
                            guardado = false;
                        }
                    }
                }
            }

        }else{
            guardado = false;
        }
        if (guardado) {
            try {
                conexion.getCon().commit();
            } catch (SQLException ex) {
                System.out.println(ex.getLocalizedMessage());
            }
        }else{
            try {
                conexion.getCon().rollback();
            } catch (SQLException ex) {
                System.out.println(ex.getLocalizedMessage());
            }
        }
        conexion.cerrar();
        return guardado;
    }*/
    
}
