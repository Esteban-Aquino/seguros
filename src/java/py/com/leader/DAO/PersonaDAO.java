/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.leader.DAO;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import py.com.leader.modelo.Persona;
import py.com.leader.util.Configuracion;

/**
 *
 * @author esteban
 */
public class PersonaDAO {

    public Persona buscarId(int id) {
        Persona persona = new Persona();
        persona.setId_persona(0);
        persona.setNombre_persona("");
        Conexion conexion = new Conexion();
        if (conexion.conectar()) {
            try {
                String sql = "select * from personas r "
                        + "where id_persona=?";
                try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                    ps.setInt(1, id);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        persona.setId_persona(rs.getInt("id_persona"));
                        persona.setNombre_persona(rs.getString("nombre_persona"));
                        persona.setNombre_fantasia_persona(rs.getString("nombre_fantasia_persona"));
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
                    }
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println("--> " + ex.getLocalizedMessage());
            }
            conexion.cerrar();
        }
        return persona;
    }
    
    public boolean guardar(Persona persona) {
        boolean guardado = false;
        int id = -1;
        Conexion conexion = new Conexion();
        if (conexion.conectar()) {
            try {
                String sql = "";
                if (persona.getId_persona()== 0) {
                    sql = "insert into personas"
                        + "( nombre_persona,"
                        + "  nombre_fantasia_persona,"
                        + "  fisica_persona,"
                        + "  ci_persona,"
                        + "  ruc_persona,"
                        + "  dni_persona,"
                        + "  telefono1_persona,"
                        + "  telefono2_persona,"
                        + "  email_persona,"
                        + "  web_persona,"
                        + "  imagen_persona,"
                        + "  direccion_persona,"
                        + "  fecha_nacimiento_persona)"
                        + "  values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
                } else {
                    sql = "update personas"
                        + " set nombre_persona = ?,"
                        + "  nombre_fantasia_persona= ?,"
                        + "  fisica_persona= ?,"
                        + "  ci_persona= ?,"
                        + "  ruc_persona= ?,"
                        + "  dni_persona= ?,"
                        + "  telefono1_persona= ?,"
                        + "  telefono2_persona= ?,"
                        + "  email_persona= ?,"
                        + "  web_persona= ?,"
                        + "  imagen_persona = ?,"
                        + "  direccion_persona = ?,"
                        + "  fecha_nacimiento_persona = ?"
                        + "  where id_persona = ?";
                }
                try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                    ps.setString(1, persona.getNombre_persona());
                    ps.setString(2, persona.getNombre_fantasia_persona());
                    ps.setBoolean(3, persona.Fisica_persona());
                    ps.setString(4, persona.getCi_persona());
                    ps.setString(5, persona.getRuc_persona());
                    ps.setString(6, persona.getDni_persona());
                    ps.setString(7, persona.getTelefono1_persona());
                    ps.setString(8, persona.getTelefono2_persona());
                    ps.setString(9, persona.getEmail_persona());
                    ps.setString(10, persona.getWeb_persona());
                    ps.setString(11, persona.getImagen_persona());
                    ps.setString(12, persona.getDireccion_persona());
                    ps.setDate(13,  persona.getFecha_nacimiento_persona());
                    
                    if (persona.getId_persona()!= 0) {
                        ps.setInt(14, persona.getId_persona());
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
                String sql = "delete from personas "
                        + "where id_persona=?";
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
        ArrayList personas = new ArrayList();
        Conexion conexion = new Conexion();
        if (conexion.conectar()) {
            try {
                String sql = "select * from personas r "
                        + " where upper(nombre_persona) like ? "
                        + " or upper(nombre_fantasia_persona) like ? "
                        + " or ci_persona like ?"
                        + " or ruc_persona like ?"
                        + " or dni_persona like ?"
                        + " or email_persona like ?"
                        + " order by id_persona "
                        + " limit ? offset ?";
                try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                    ps.setString(1, "%" + texto.toUpperCase() + "%");
                    ps.setString(2, "%" + texto.toUpperCase() + "%");
                    ps.setString(3, "%" + texto.toUpperCase() + "%");
                    ps.setString(4, "%" + texto.toUpperCase() + "%");
                    ps.setString(5, "%" + texto.toUpperCase() + "%");
                    ps.setString(6, "%" + texto.toUpperCase() + "%");
                    ps.setInt(7, limit);
                    ps.setInt(8, offset);
                    ResultSet rs = ps.executeQuery();
                    //System.out.println(ps.toString());
                    while (rs.next()) {
                        personas.add(new Persona(rs.getInt("id_persona"),
                                 rs.getString("nombre_persona"),
                                 rs.getString("nombre_fantasia_persona"),
                                 rs.getBoolean("fisica_persona"),
                                 rs.getString("ci_persona"),
                                 rs.getString("ruc_persona"),
                                 rs.getString("dni_persona"),
                                 rs.getString("telefono1_persona"),
                                 rs.getString("telefono2_persona"),
                                 rs.getString("email_persona"),
                                 rs.getString("web_persona"),
                                 rs.getString("imagen_persona"),
                                 rs.getString("direccion_persona"),
                                 rs.getDate("fecha_nacimiento_persona")));
                    }
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println("PersonaDao--> " + ex.getLocalizedMessage());
            }
            conexion.cerrar();
        }
        return personas;
    }
}
