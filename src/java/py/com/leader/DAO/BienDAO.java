package py.com.leader.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import py.com.leader.modelo.Bien;
import py.com.leader.modelo.Cliente;
import py.com.leader.modelo.Tipo_bien;
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
public class BienDAO {

    Tipo_bienDAO tipo_bienDAO = new Tipo_bienDAO();
    ClienteDAO clienteDAO = new ClienteDAO();
    UsuarioDAO usuarioDAO = new UsuarioDAO();

    public Bien buscarId(int id) {
        Bien bien = new Bien();
        bien.setId_bien(0);
        Conexion conexion = new Conexion();
        if (conexion.conectar()) {
            try {
                String sql = "select * from bienes r "
                        + "where id_bien = ? ";
                try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                    ps.setInt(1, id);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        bien.setId_bien(rs.getInt("id_bien"));
                        Tipo_bien tipo_bien = tipo_bienDAO.buscarId(rs.getInt("id_tipo_bien_bien"));
                        Cliente cliente = clienteDAO.buscarId(rs.getInt("id_cliente_bien"));
                        bien.setTipo_bien(tipo_bien);
                        bien.setCliente(cliente);
                        bien.setMarca_bien(rs.getString("marca_bien"));
                        bien.setModelo_bien(rs.getString("modelo_bien"));
                        bien.setTip_bien(rs.getString("tip_bien"));
                        bien.setMatricula_bien(rs.getString("matricula_bien"));
                        bien.setAnio_bien(rs.getInt("anio_bien"));
                        bien.setColor_bien(rs.getString("color_bien"));
                        bien.setNum_motor_bien(rs.getString("num_motor_bien"));
                        bien.setNum_carroceria_bien(rs.getString("num_carroceria_bien"));
                        bien.setDescripcion_bien(rs.getString("descripcion_bien"));
                        bien.setUsuarioauditoria(usuarioDAO.buscarId(rs.getInt("id_usuario_auditoria")));

                    }
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error Bien:--> " + ex.getLocalizedMessage());
            }
            conexion.cerrar();
        }
        return bien;
    }

    public boolean guardar(Bien bien, Usuario usuario) {
        boolean guardado = false;
        int id = -1;
        Conexion conexion = new Conexion();
        if (conexion.conectar()) {
            try {
                String sql = "";
                if (bien.getId_bien() == 0) {
                    sql = "insert into bienes (id_tipo_bien_bien, id_cliente_bien, marca_bien, modelo_bien, matricula_bien, anio_bien, color_bien, num_motor_bien, num_carroceria_bien, tip_bien, descripcion_bien) "
                            + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                } else {
                    sql = "update bienes "
                            + "set id_tipo_bien_bien = ?, "
                            + "id_cliente_bien = ?, "
                            + "marca_bien = ?, "
                            + "modelo_bien = ?, "
                            + "matricula_bien = ?, "
                            + "anio_bien = ?, "
                            + "color_bien = ?, "
                            + "num_motor_bien = ?, "
                            + "num_carroceria_bien = ?, "
                            + "tip_bien = ?, "
                            + "descripcion_bien = ? "
                            + "where id_bien = ?";
                }
                try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                    ps.setInt(1, bien.getTipo_bien().getId_tipo_bien());
                    ps.setInt(2, bien.getCliente().getId_cliente());
                    ps.setString(3, bien.getMarca_bien());
                    ps.setString(4, bien.getModelo_bien());
                    ps.setString(5, bien.getMatricula_bien());
                    ps.setInt(6, bien.getAnio_bien());
                    ps.setString(7, bien.getColor_bien());
                    ps.setString(8, bien.getNum_motor_bien());
                    ps.setString(9, bien.getNum_carroceria_bien());
                    ps.setString(10, bien.getTip_bien());
                    ps.setString(11, bien.getDescripcion_bien());

                    if (bien.getId_bien() != 0) {
                        ps.setInt(12, bien.getId_bien());
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
                String sql = "delete from bienes "
                        + "where id_bien = ?";
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
        ArrayList bien = new ArrayList();
        Conexion conexion = new Conexion();
        //Usuario usuario = new Usuario();
        if (conexion.conectar()) {
            try {
                String sql = "select *"
                        + "from bienes b left join tipos_bienes t on b.id_tipo_bien_bien = t.id_tipo_bien "
                        + "left join clientes c on b.id_cliente_bien = c.id_cliente "
                        + "left join personas p on c.id_persona = p.id_persona "
                        + "where marca_bien like ? "
                        + "or modelo_bien like ? "
                        + "or matricula_bien like ? "
                        + "or to_char(anio_bien,'9999999') like ? "
                        + "or color_bien like ? "
                        + "or num_motor_bien like ? "
                        + "or num_carroceria_bien like ? "
                        + "or tip_bien like ? "
                        + "or descripcion_bien like ? "
                        + "limit ? offset ? ";
                try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                    ps.setString(1, "%" + texto.toUpperCase() + "%");
                    ps.setString(2, "%" + texto.toUpperCase() + "%");
                    ps.setString(3, "%" + texto.toUpperCase() + "%");
                    ps.setString(4, "%" + texto.toUpperCase() + "%");
                    ps.setString(5, "%" + texto.toUpperCase() + "%");
                    ps.setString(6, "%" + texto.toUpperCase() + "%");
                    ps.setString(7, "%" + texto.toUpperCase() + "%");
                    ps.setString(8, "%" + texto.toUpperCase() + "%");
                    ps.setString(9, "%" + texto.toUpperCase() + "%");
                    ps.setInt(10, limit);
                    ps.setInt(11, offset);
                    //System.out.println(ps.toString());
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        bien.add(new Bien(rs.getInt("id_bien"),
                                tipo_bienDAO.buscarId(rs.getInt("id_tipo_bien")),
                                clienteDAO.buscarId(rs.getInt("id_cliente")),
                                rs.getString("marca_bien"),
                                rs.getString("modelo_bien"),
                                rs.getString("tip_bien"),
                                rs.getString("matricula_bien"),
                                rs.getInt("anio_bien"),
                                rs.getString("color_bien"),
                                rs.getString("num_motor_bien"),
                                rs.getString("num_carroceria_bien"),
                                rs.getString("descripcion_bien"),
                                usuarioDAO.buscarId(rs.getInt("id_usuario_auditoria")))
                        );
                    }
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println("BienDao--> " + ex.getLocalizedMessage());
            }
            conexion.cerrar();
        }
        return bien;
    }

}
