package py.com.leader.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import py.com.leader.modelo.Aseguradora;
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
public class AseguradoraDAO {

    public Aseguradora buscarId(int id) {
        Aseguradora aseguradora = new Aseguradora();
        aseguradora.setId_aseguradora(0);
        aseguradora.setNombre_aseguradora("");

        Conexion conexion = new Conexion();
        if (conexion.conectar()) {
            try {
                String sql = "select * from aseguradoras r "
                        + "left join personas p on r.id_persona_aseguradora = p.id_persona "
                        + "where id_aseguradora = ?";
                try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                    ps.setInt(1, id);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        aseguradora.setId_aseguradora(rs.getInt("id_aseguradora"));
                        aseguradora.getPersona().setId_persona(rs.getInt("id_persona"));
                        aseguradora.getPersona().setNombre_persona(rs.getString("nombre_persona"));
                        aseguradora.getPersona().setRuc_persona(rs.getString("ruc_persona"));
                        aseguradora.getPersona().setTelefono1_persona(rs.getString("telefono1_persona"));
                        aseguradora.getPersona().setTelefono2_persona(rs.getString("telefono2_persona"));
                        aseguradora.getPersona().setFisica_persona(rs.getBoolean("fisica_persona"));
                        aseguradora.getPersona().setEmail_persona(rs.getString("email_persona"));
                        aseguradora.getPersona().setWeb_persona(rs.getString("web_persona"));
                        aseguradora.getPersona().setDireccion_persona(rs.getString("direccion_persona"));
                    }
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error Aseguradora:--> " + ex.getLocalizedMessage());
            }
            conexion.cerrar();
        }
        return aseguradora;
    }

    public boolean guardar(Aseguradora aseguradora, Usuario usuario) {
        boolean guardado = false;
        int id = -1;
        Conexion conexion = new Conexion();
        if (conexion.conectar()) {
            try {
                String sql = "";
                if (aseguradora.getId_aseguradora() == 0) {
                    sql = "insert into aseguradoras (nombre_aseguradora, ruc, id_persona_aseguradora) "
                            + "values (?,?,?)";
                } else {
                    sql = "update aseguradoras "
                            + "set nombre_aseguradora = ?, "
                            + "    ruc= ?, "
                            + "    id_persona_aseguradora = ? "
                            + "where id_aseguradora = ?";
                }
                try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                    ps.setString(1, aseguradora.getPersona().getNombre_fantasia_persona());
                    ps.setString(2, aseguradora.getPersona().getRuc_persona());
                    ps.setInt(3, aseguradora.getPersona().getId_persona());
                    if (aseguradora.getId_aseguradora() != 0) {
                        ps.setInt(4, aseguradora.getId_aseguradora());
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
                String sql = "delete from aseguradoras "
                        + "where id_aseguradora = ?";
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
        ArrayList aseguradora = new ArrayList();
        Conexion conexion = new Conexion();
        //Usuario usuario = new Usuario();
        Persona persona;

        if (conexion.conectar()) {
            try {
                String sql = "select * " +
                            "from aseguradoras s " +
                            "join personas p " +
                            "on s.id_persona_aseguradora = p.id_persona " +
                            "where upper(p.nombre_persona) like ? " +
                            "or upper(p.nombre_fantasia_persona) like ? " +
                            "or upper(p.ruc_persona) like ? " +
                            "or upper(p.dni_persona) like ? " +
                            "or upper(p.nombre_fantasia_persona) like ? " +
                            "or upper(p.ci_persona) like ? "+
                            "limit ? offset ? ";
                try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                    ps.setString(1, "%" + texto.toUpperCase() + "%");
                    ps.setString(2, "%" + texto.toUpperCase() + "%");
                    ps.setString(3, "%" + texto.toUpperCase() + "%");
                    ps.setString(4, "%" + texto.toUpperCase() + "%");
                    ps.setString(5, "%" + texto.toUpperCase() + "%");
                    ps.setString(6, "%" + texto.toUpperCase() + "%");
                    ps.setInt(7, limit);
                    ps.setInt(8, offset);
                    //System.out.println(ps.toString());
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        persona = new Persona();
                        persona.setCi_persona(rs.getString("ci_persona"));
                        persona.setDireccion_persona(rs.getString("direccion_persona"));
                        persona.setDni_persona(rs.getString("dni_persona"));
                        persona.setEmail_persona(rs.getString("email_persona"));
                        persona.setFecha_nacimiento_persona(rs.getDate("fecha_nacimiento_persona"));
                        persona.setFisica_persona(rs.getBoolean("fisica_persona"));
                        persona.setId_persona(rs.getInt("id_persona"));
                        persona.setImagen_persona(rs.getString("imagen_persona"));
                        persona.setNombre_fantasia_persona(rs.getString("nombre_fantasia_persona"));
                        persona.setNombre_persona(rs.getString("nombre_persona"));
                        persona.setRuc_persona(rs.getString("ruc_persona"));
                        persona.setTelefono1_persona(rs.getString("telefono1_persona"));
                        persona.setTelefono2_persona(rs.getString("telefono2_persona"));
                        persona.setWeb_persona(rs.getString("web_persona"));

                        aseguradora.add(new Aseguradora(rs.getInt("id_aseguradora"),
                                                        rs.getString("nombre_aseguradora"),
                                                        persona, 
                                                        new Usuario())
                        );
                    }
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println("AseguradoraDao--> " + ex.getLocalizedMessage());
            }
            conexion.cerrar();
        }
        return aseguradora;
    }

}
