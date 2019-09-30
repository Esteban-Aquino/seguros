package py.com.leader.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import py.com.leader.modelo.Cliente;
import py.com.leader.modelo.Cobrador;
import py.com.leader.modelo.Persona;
import py.com.leader.modelo.Usuario;
import py.com.leader.modelo.Vendedor;
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
public class ClienteDAO {

    public Cliente buscarId(int id) {
        Cliente cliente = new Cliente();
        cliente.setId_cliente(0);
        cliente.setNombre_cliente("");

        Conexion conexion = new Conexion();
        if (conexion.conectar()) {
            try {
                String sql =      "select  r.id_cliente, "
                                + "r.nombre_cliente, "
                                + "p.id_persona id_persona_cliente, "
                                + "p.nombre_persona nombre_persona_cliente, "
                                + "p.nombre_fantasia_persona nombre_fantasia_persona_cliente, "
                                + "p.fisica_persona fisica_persona_cliente, "
                                + "p.ci_persona ci_persona_cliente, "
                                + "p.ruc_persona ruc_persona_cliente, "
                                + "p.dni_persona dni_persona_cliente, "
                                + "p.telefono1_persona telefono1_persona_cliente, "
                                + "p.telefono2_persona telefono2_persona_cliente, "
                                + "p.email_persona email_persona_cliente, "
                                + "p.web_persona web_persona_cliente, "
                                + "p.imagen_persona imagen_persona_cliente, "
                                + "p.direccion_persona direccion_persona_cliente, "
                                + "p.fecha_nacimiento_persona fecha_nacimiento_persona_cliente, "
                                + "v.id_vendedor, "
                                + "v.matricula_vendedor, "
                                + "vp.id_persona id_persona_vendedor, "
                                + "vp.nombre_persona nombre_persona_vendedor, "
                                + "vp.nombre_fantasia_persona nombre_fantasia_persona_vendedor, "
                                + "vp.fisica_persona fisica_persona_vendedor, "
                                + "vp.ci_persona ci_persona_vendedor, "
                                + "vp.ruc_persona ruc_persona_vendedor, "
                                + "vp.dni_persona dni_persona_vendedor, "
                                + "vp.telefono1_persona telefono1_persona_vendedor, "
                                + "vp.telefono2_persona telefono2_persona_vendedor, "
                                + "vp.email_persona email_persona_vendedor, "
                                + "vp.web_persona web_persona_vendedor, "
                                + "vp.imagen_persona imagen_persona_vendedor, "
                                + "vp.direccion_persona direccion_persona_vendedor, "
                                + "vp.fecha_nacimiento_persona fecha_nacimiento_persona_vendedor, "
                                + "c.id_cobrador, "
                                + "cp.id_persona id_persona_cobrador, "
                                + "cp.nombre_persona nombre_persona_cobrador, "
                                + "cp.nombre_fantasia_persona nombre_fantasia_persona_cobrador, "
                                + "cp.fisica_persona fisica_persona_cobrador, "
                                + "cp.ci_persona ci_persona_cobrador, "
                                + "cp.ruc_persona ruc_persona_cobrador, "
                                + "cp.dni_persona dni_persona_cobrador, "
                                + "cp.telefono1_persona telefono1_persona_cobrador, "
                                + "cp.telefono2_persona telefono2_persona_cobrador, "
                                + "cp.email_persona email_persona_cobrador, "
                                + "cp.web_persona web_persona_cobrador, "
                                + "cp.imagen_persona imagen_persona_cobrador, "
                                + "cp.direccion_persona direccion_persona_cobrador, "
                                + "cp.fecha_nacimiento_persona fecha_nacimiento_persona_cobrador "
                                + "from clientes r "
                                + "left join personas p on r.id_persona = p.id_persona "
                                + "left join cobradores c on r.id_cobrador = c.id_cobrador "
                                + "left join personas cp on c.id_persona = cp.id_persona "
                                + "left join vendedores v on r.id_vendedor = v.id_vendedor "
                                + "left join personas vp on v.id_persona = vp.id_persona "
                                + "where id_cliente = ?";
                try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                    ps.setInt(1, id);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        cliente.setId_cliente(rs.getInt("id_cliente"));
                        Persona persona = new Persona();
                        persona.setId_persona(rs.getInt("id_persona_cliente"));
                        cliente.setPersona(persona);
                        /*cliente.getPersona().setId_persona(rs.getInt("id_persona_cliente"));
                        cliente.getPersona().setNombre_persona(rs.getString("nombre_persona_cliente"));
                        cliente.getPersona().setRuc_persona(rs.getString("ruc_persona_cliente"));
                        cliente.getPersona().setTelefono1_persona(rs.getString("telefono1_persona_cliente"));
                        cliente.getPersona().setTelefono2_persona(rs.getString("telefono2_persona_cliente"));
                        cliente.getPersona().setFisica_persona(rs.getBoolean("fisica_persona_cliente"));
                        cliente.getPersona().setEmail_persona(rs.getString("email_persona_cliente"));
                        cliente.getPersona().setWeb_persona(rs.getString("web_persona_cliente"));
                        cliente.getPersona().setDireccion_persona(rs.getString("direccion_persona_cliente"));*/
                        
                        cliente.getVendedor().setId_vendedor(rs.getInt("id_vendedor"));
                        cliente.getVendedor().setMatricula_vendedor(rs.getString("matricula_vendedor"));
                        cliente.getVendedor().getPersona().setId_persona(rs.getInt("id_persona_vendedor"));
                        cliente.getVendedor().getPersona().setNombre_persona(rs.getString("nombre_persona_vendedor"));
                        cliente.getVendedor().getPersona().setRuc_persona(rs.getString("ruc_persona_vendedor"));
                        cliente.getVendedor().getPersona().setTelefono1_persona(rs.getString("telefono1_persona_vendedor"));
                        cliente.getVendedor().getPersona().setTelefono2_persona(rs.getString("telefono2_persona_vendedor"));
                        cliente.getVendedor().getPersona().setFisica_persona(rs.getBoolean("fisica_persona_vendedor"));
                        cliente.getVendedor().getPersona().setEmail_persona(rs.getString("email_persona_vendedor"));
                        cliente.getVendedor().getPersona().setWeb_persona(rs.getString("web_persona_vendedor"));
                        cliente.getVendedor().getPersona().setDireccion_persona(rs.getString("direccion_persona_vendedor"));
                        
                        cliente.getCobrador().setId_cobrador(rs.getInt("id_cobrador"));
                        cliente.getCobrador().getPersona().setId_persona(rs.getInt("id_persona_cobrador"));
                        cliente.getCobrador().getPersona().setNombre_persona(rs.getString("nombre_persona_cobrador"));
                        cliente.getCobrador().getPersona().setRuc_persona(rs.getString("ruc_persona_cobrador"));
                        cliente.getCobrador().getPersona().setTelefono1_persona(rs.getString("telefono1_persona_cobrador"));
                        cliente.getCobrador().getPersona().setTelefono2_persona(rs.getString("telefono2_persona_cobrador"));
                        cliente.getCobrador().getPersona().setFisica_persona(rs.getBoolean("fisica_persona_cobrador"));
                        cliente.getCobrador().getPersona().setEmail_persona(rs.getString("email_persona_cobrador"));
                        cliente.getCobrador().getPersona().setWeb_persona(rs.getString("web_persona_cobrador"));
                        cliente.getCobrador().getPersona().setDireccion_persona(rs.getString("direccion_persona_cobrador"));
                    }
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error Cliente:--> " + ex.getLocalizedMessage());
            }
            conexion.cerrar();
        }
        return cliente;
    }

    public boolean guardar(Cliente cliente, Usuario usuario) {
        boolean guardado = false;
        int id = -1;
        Conexion conexion = new Conexion();
        if (conexion.conectar()) {
            try {
                String sql = "";
                if (cliente.getId_cliente() == 0) {
                    sql = "insert into clientes (nombre_cliente,id_persona,id_cobrador,id_vendedor) "
                            + "values (?,?,?,?)";
                } else {
                    sql = "update clientes "
                            + "set nombre_cliente = ?, "
                            + "    id_persona = ?, "
                            + "    id_cobrador = ?, "
                            + "    id_vendedor = ? "
                            + " where id_cliente = ?";
                }
                try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                    ps.setString(1, cliente.getPersona().getNombre_fantasia_persona());
                    ps.setInt(2, cliente.getPersona().getId_persona());
                    ps.setInt(3, cliente.getCobrador().getId_cobrador());
                    ps.setInt(4, cliente.getVendedor().getId_vendedor());
                    if (cliente.getId_cliente() != 0) {
                        ps.setInt(5, cliente.getId_cliente());
                    }
                    int cr = ps.executeUpdate();
                    if (cr > 0) {
                        guardado = true;
                        conexion.getCon().commit();
                    }
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println("ClienteGuardarDAO--->" + ex.getLocalizedMessage());
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
                String sql = "delete from clientes "
                        + "where id_cliente = ?";
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
        ArrayList cliente = new ArrayList();
        Conexion conexion = new Conexion();
        //Usuario usuario = new Usuario();
        Persona persona;
        Cobrador cobrador;
        Vendedor vendedor;

        if (conexion.conectar()) {
            try {
                String sql = "select  s.id_cliente, "
                                + "s.nombre_cliente, "
                                + "p.id_persona id_persona_cliente, "
                                + "p.nombre_persona nombre_persona_cliente, "
                                + "p.nombre_fantasia_persona nombre_fantasia_persona_cliente, "
                                + "p.fisica_persona fisica_persona_cliente, "
                                + "p.ci_persona ci_persona_cliente, "
                                + "p.ruc_persona ruc_persona_cliente, "
                                + "p.dni_persona dni_persona_cliente, "
                                + "p.telefono1_persona telefono1_persona_cliente, "
                                + "p.telefono2_persona telefono2_persona_cliente, "
                                + "p.email_persona email_persona_cliente, "
                                + "p.web_persona web_persona_cliente, "
                                + "p.imagen_persona imagen_persona_cliente, "
                                + "p.direccion_persona direccion_persona_cliente, "
                                + "p.fecha_nacimiento_persona fecha_nacimiento_persona_cliente, "
                                + "v.id_vendedor, "
                                + "v.matricula_vendedor, "
                                + "vp.id_persona id_persona_vendedor, "
                                + "vp.nombre_persona nombre_persona_vendedor, "
                                + "vp.nombre_fantasia_persona nombre_fantasia_persona_vendedor, "
                                + "vp.fisica_persona fisica_persona_vendedor, "
                                + "vp.ci_persona ci_persona_vendedor, "
                                + "vp.ruc_persona ruc_persona_vendedor, "
                                + "vp.dni_persona dni_persona_vendedor, "
                                + "vp.telefono1_persona telefono1_persona_vendedor, "
                                + "vp.telefono2_persona telefono2_persona_vendedor, "
                                + "vp.email_persona email_persona_vendedor, "
                                + "vp.web_persona web_persona_vendedor, "
                                + "vp.imagen_persona imagen_persona_vendedor, "
                                + "vp.direccion_persona direccion_persona_vendedor, "
                                + "vp.fecha_nacimiento_persona fecha_nacimiento_persona_vendedor, "
                                + "c.id_cobrador, "
                                + "cp.id_persona id_persona_cobrador, "
                                + "cp.nombre_persona nombre_persona_cobrador, "
                                + "cp.nombre_fantasia_persona nombre_fantasia_persona_cobrador, "
                                + "cp.fisica_persona fisica_persona_cobrador, "
                                + "cp.ci_persona ci_persona_cobrador, "
                                + "cp.ruc_persona ruc_persona_cobrador, "
                                + "cp.dni_persona dni_persona_cobrador, "
                                + "cp.telefono1_persona telefono1_persona_cobrador, "
                                + "cp.telefono2_persona telefono2_persona_cobrador, "
                                + "cp.email_persona email_persona_cobrador, "
                                + "cp.web_persona web_persona_cobrador, "
                                + "cp.imagen_persona imagen_persona_cobrador, "
                                + "cp.direccion_persona direccion_persona_cobrador, "
                                + "cp.fecha_nacimiento_persona fecha_nacimiento_persona_cobrador "+
                                "from clientes s join personas p on s.id_persona = p.id_persona " +
                                "left join cobradores c on s.id_cobrador = c.id_cobrador "+
                                "left join personas cp on c.id_persona = cp.id_persona "+
                                "left join vendedores v on v.id_vendedor = p.id_persona "+
                                "left join personas vp on v.id_persona = vp.id_persona "+
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
                        persona.setCi_persona(rs.getString("ci_persona_cliente"));
                        persona.setDireccion_persona(rs.getString("direccion_persona_cliente"));
                        persona.setDni_persona(rs.getString("dni_persona_cliente"));
                        persona.setEmail_persona(rs.getString("email_persona_cliente"));
                        persona.setFecha_nacimiento_persona(rs.getDate("fecha_nacimiento_persona_cliente"));
                        persona.setFisica_persona(rs.getBoolean("fisica_persona_cliente"));
                        persona.setId_persona(rs.getInt("id_persona_cliente"));
                        persona.setImagen_persona(rs.getString("imagen_persona_cliente"));
                        persona.setNombre_fantasia_persona(rs.getString("nombre_fantasia_persona_cliente"));
                        persona.setNombre_persona(rs.getString("nombre_persona_cliente"));
                        persona.setRuc_persona(rs.getString("ruc_persona_cliente"));
                        persona.setTelefono1_persona(rs.getString("telefono1_persona_cliente"));
                        persona.setTelefono2_persona(rs.getString("telefono2_persona_cliente"));
                        persona.setWeb_persona(rs.getString("web_persona_cliente"));
                        
                        vendedor = new Vendedor();
                        vendedor.setId_vendedor(rs.getInt("id_vendedor"));
                        vendedor.setMatricula_vendedor(rs.getString("matricula_vendedor"));
                        
                        vendedor.getPersona().setCi_persona(rs.getString("ci_persona_vendedor"));
                        vendedor.getPersona().setDireccion_persona(rs.getString("direccion_persona_vendedor"));
                        vendedor.getPersona().setDni_persona(rs.getString("dni_persona_vendedor"));
                        vendedor.getPersona().setEmail_persona(rs.getString("email_persona_vendedor"));
                        vendedor.getPersona().setFecha_nacimiento_persona(rs.getDate("fecha_nacimiento_persona_vendedor"));
                        vendedor.getPersona().setFisica_persona(rs.getBoolean("fisica_persona_vendedor"));
                        vendedor.getPersona().setId_persona(rs.getInt("id_persona_vendedor"));
                        vendedor.getPersona().setImagen_persona(rs.getString("imagen_persona_vendedor"));
                        vendedor.getPersona().setNombre_fantasia_persona(rs.getString("nombre_fantasia_persona_vendedor"));
                        vendedor.getPersona().setNombre_persona(rs.getString("nombre_persona_vendedor"));
                        vendedor.getPersona().setRuc_persona(rs.getString("ruc_persona_vendedor"));
                        vendedor.getPersona().setTelefono1_persona(rs.getString("telefono1_persona_vendedor"));
                        vendedor.getPersona().setTelefono2_persona(rs.getString("telefono2_persona_vendedor"));
                        vendedor.getPersona().setWeb_persona(rs.getString("web_persona_vendedor"));
                        
                        cobrador = new Cobrador();
                        cobrador.setId_cobrador(rs.getInt("id_cobrador"));
                        cobrador.getPersona().setCi_persona(rs.getString("ci_persona_cobrador"));
                        cobrador.getPersona().setDireccion_persona(rs.getString("direccion_persona_cobrador"));
                        cobrador.getPersona().setDni_persona(rs.getString("dni_persona_cobrador"));
                        cobrador.getPersona().setEmail_persona(rs.getString("email_persona_cobrador"));
                        cobrador.getPersona().setFecha_nacimiento_persona(rs.getDate("fecha_nacimiento_persona_cobrador"));
                        cobrador.getPersona().setFisica_persona(rs.getBoolean("fisica_persona_cobrador"));
                        cobrador.getPersona().setId_persona(rs.getInt("id_persona_cobrador"));
                        cobrador.getPersona().setImagen_persona(rs.getString("imagen_persona_cobrador"));
                        cobrador.getPersona().setNombre_fantasia_persona(rs.getString("nombre_fantasia_persona_cobrador"));
                        cobrador.getPersona().setNombre_persona(rs.getString("nombre_persona_cobrador"));
                        cobrador.getPersona().setRuc_persona(rs.getString("ruc_persona_cobrador"));
                        cobrador.getPersona().setTelefono1_persona(rs.getString("telefono1_persona_cobrador"));
                        cobrador.getPersona().setTelefono2_persona(rs.getString("telefono2_persona_cobrador"));
                        cobrador.getPersona().setWeb_persona(rs.getString("web_persona_cobrador"));
                        
                        cliente.add(new Cliente(rs.getInt("id_cliente"),
                                                        rs.getString("nombre_cliente"),
                                                        persona, 
                                                        vendedor,
                                                        cobrador,
                                                        new Usuario())
                        );
                    }
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println("ClienteDao--> " + ex.getLocalizedMessage());
            }
            conexion.cerrar();
        }
        return cliente;
    }

}
