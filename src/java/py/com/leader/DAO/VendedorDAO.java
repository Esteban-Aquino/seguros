package py.com.leader.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import py.com.leader.modelo.Vendedor;
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
public class VendedorDAO {

    public Vendedor buscarId(int id) {
        Vendedor vendedor = new Vendedor();
        vendedor.setId_vendedor(0);
        vendedor.setNombre_vendedor("");

        Conexion conexion = new Conexion();
        if (conexion.conectar()) {
            try {
                String sql = "select * from vendedores r "
                        + "left join personas p on r.id_persona = p.id_persona "
                        + "where id_vendedor = ?";
                try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                    ps.setInt(1, id);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        vendedor.setId_vendedor(rs.getInt("id_vendedor"));
                        vendedor.getPersona().setId_persona(rs.getInt("id_persona"));
                        vendedor.getPersona().setNombre_persona(rs.getString("nombre_persona"));
                        vendedor.getPersona().setRuc_persona(rs.getString("ruc_persona"));
                        vendedor.getPersona().setTelefono1_persona(rs.getString("telefono1_persona"));
                        vendedor.getPersona().setTelefono2_persona(rs.getString("telefono2_persona"));
                        vendedor.getPersona().setFisica_persona(rs.getBoolean("fisica_persona"));
                        vendedor.getPersona().setEmail_persona(rs.getString("email_persona"));
                        vendedor.getPersona().setWeb_persona(rs.getString("web_persona"));
                        vendedor.getPersona().setDireccion_persona(rs.getString("direccion_persona"));
                    }
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error Vendedor:--> " + ex.getLocalizedMessage());
            }
            conexion.cerrar();
        }
        return vendedor;
    }

    public boolean guardar(Vendedor vendedor, Usuario usuario) {
        boolean guardado = false;
        int id = -1;
        Conexion conexion = new Conexion();
        if (conexion.conectar()) {
            try {
                String sql = "";
                if (vendedor.getId_vendedor() == 0) {
                    sql = "insert into vendedores (nombre_vendedor,id_persona, matricula_vendedor) "
                            + "values (?,?,?)";
                } else {
                    sql = "update vendedores "
                            + "set nombre_vendedor = ?, "
                            + "    id_persona = ?, "
                            + "    matricula_vendedor = ? "
                            + "where id_vendedor = ?";
                }
                try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                    ps.setString(1, vendedor.getPersona().getNombre_fantasia_persona());
                    ps.setInt(2, vendedor.getPersona().getId_persona());
                    ps.setString(3, vendedor.getMatricula_vendedor());
                    if (vendedor.getId_vendedor() != 0) {
                        ps.setInt(4, vendedor.getId_vendedor());
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
                String sql = "delete from vendedores "
                        + "where id_vendedor = ?";
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
        ArrayList vendedor = new ArrayList();
        Conexion conexion = new Conexion();
        //Usuario usuario = new Usuario();
        Persona persona;

        if (conexion.conectar()) {
            try {
                String sql = "select * " +
                            "from vendedores s " +
                            "join personas p " +
                            "on s.id_persona = p.id_persona " +
                            "where upper(p.nombre_persona) like ? " +
                            "or upper(p.nombre_fantasia_persona) like ? " +
                            "or upper(p.ruc_persona) like ? " +
                            "or upper(p.dni_persona) like ? " +
                            "or upper(p.nombre_fantasia_persona) like ? " +
                            "or upper(p.ci_persona) like ? "+
                            "or upper(s.matricula_vendedor) like ? " +
                            "limit ? offset ? ";
                try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                    ps.setString(1, "%" + texto.toUpperCase() + "%");
                    ps.setString(2, "%" + texto.toUpperCase() + "%");
                    ps.setString(3, "%" + texto.toUpperCase() + "%");
                    ps.setString(4, "%" + texto.toUpperCase() + "%");
                    ps.setString(5, "%" + texto.toUpperCase() + "%");
                    ps.setString(6, "%" + texto.toUpperCase() + "%");
                    ps.setString(7, "%" + texto.toUpperCase() + "%");
                    ps.setInt(8, limit);
                    ps.setInt(9, offset);
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

                        vendedor.add(new Vendedor(rs.getInt("id_vendedor"),
                                                        rs.getString("nombre_vendedor"),
                                                        rs.getString("matricula_vendedor"),
                                                        persona, 
                                                        new Usuario())
                        );
                    }
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println("VendedorDao--> " + ex.getLocalizedMessage());
            }
            conexion.cerrar();
        }
        return vendedor;
    }

}
