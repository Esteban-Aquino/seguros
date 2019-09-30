/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.leader.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import py.com.leader.modelo.Propuesta;
import py.com.leader.modelo.Propuesta_bien;
import py.com.leader.DAO.Propuesta_bienDAO;
import py.com.leader.modelo.Aseguradora;
import py.com.leader.modelo.Cliente;
import py.com.leader.modelo.Propuesta_cuota;
import py.com.leader.modelo.Vendedor;
import py.com.leader.util.Configuracion;
import static py.com.leader.util.util.parseDateSql;

/**
 *
 * @author esteban
 */
public class PropuestaDAO {
    public boolean guardar(Propuesta propuesta) {
        boolean guardado = false;
        int id = -1;
        Conexion conexion = new Conexion();

        if (conexion.conectar()) {
            try {
                String sql = "";
                if (propuesta.getId_propuesta() == 0) {
                    //System.out.println("Insertando.."+usuario.getId_usuario());
                    sql = "insert into propuestas (id_cliente_propuesta, " +
                        "			id_aseguradora_propuesta, " +
                        "			id_vendedor_propuesta, " +
                        "			vigendia_desde_propuesta, " +
                        "			vigencia_hasta_propuesta, " +
                        //"			prima_propuesta, " +
                        "			premio_propuesta, " +
                        "			suma_asegurada_propuesta, " +
                        "			seccion_propuesta, " +
                        "			cant_cuotas_propuesta, " +
                        "			fecha_propuesta, " +
                        "			fecha_vencimiento_propuesta) " +
                        "values ("+propuesta.getCliente().getId_cliente()+", " +
                        "	"+propuesta.getAseguradora().getId_aseguradora()+", " +
                        "	"+propuesta.getVendedor().getId_vendedor()+", " +
                        "	'"+propuesta.getVigendia_desde_propuesta()+"', " +
                        "	'"+propuesta.getVigencia_hasta_propuesta()+"', " +
                        "	"+propuesta.getPremio_propuesta()+", " +
                        "	"+propuesta.getSuma_asegurada_propuesta()+", " +
                        "	"+propuesta.getSeccion_propuesta()+", " +
                        "	"+propuesta.getCant_cuotas_propuesta()+", " +
                        "	'"+propuesta.getFecha_propuesta()+"', " +
                        "	'"+propuesta.getFecha_vencimiento_propuesta()+"')";
                    //System.out.println(sql);
                    conexion.getSt().executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
                    ResultSet keyset = conexion.getSt().getGeneratedKeys();
                    if (keyset.next()) {
                        id = keyset.getInt(1);
                        //System.out.println("Guardado.."+id);
                        guardado = GuardarPropuestaBien(propuesta.getPropuesta_bien(), id, conexion);
                        if (guardado){
                            guardado = GuardarPropuestaCuota(propuesta.getPropuesta_cuota(), id, conexion);
                        }
                        // Ahora carga propuesta bien en el controlador y proba, nde vairo
                    }
                } /*else {
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
                }*/
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
    
    private boolean GuardarPropuestaBien(ArrayList<Propuesta_bien>  propuesta_bien, int id_propuesta, Conexion conexion){
        boolean guardado = false;
        int id = -1;
        Propuesta_bienDAO propuesta_bienDAO = new Propuesta_bienDAO();
            try {
                for (int i = 0;i<propuesta_bien.size();i++){
                    System.out.println("Nro: "+i);
                    if (!propuesta_bienDAO.buscarPropuestaBienBool(id_propuesta, propuesta_bien.get(i).getBien().getId_bien())){
                    String sql = "";
                    if (propuesta_bien.get(i).getId_propuesta_bien()== 0) {
                        //System.out.println("Insertando.."+usuario.getId_usuario());
                        sql = "insert into propuestas_bienes (id_bien_propuesta_bien, " +
                                    "			  id_propuesta_propuesta_bien) " +
                                    "			values ("+propuesta_bien.get(i).getBien().getId_bien()+", " +
                                    "			        "+id_propuesta+") ";
                        //System.out.println(sql);
                        conexion.getSt().executeUpdate(sql);
                        guardado = true;
                    } /*else {
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
                    }*/
                    }
                }
            } catch (SQLException ex) {
                System.out.println("Error Bienes--->" + ex.getLocalizedMessage());
            }
        return guardado;
    }
    
    private boolean GuardarPropuestaCuota(ArrayList<Propuesta_cuota>  propuesta_cuota, int id_propuesta, Conexion conexion){
        boolean guardado = false;
        int id = -1;
        String sql = "";
        Propuesta_cuotaDAO propuesta_cuotaDAO = new Propuesta_cuotaDAO();
            try {
                    if (propuesta_cuotaDAO.existenCuotas(id_propuesta)){
                        sql = "delete from propuestas_cuotas "
                             + "where id propuesta = "+id_propuesta;
                        System.out.println(sql);
                        conexion.getSt().executeUpdate(sql);
                    }
                for (int i = 0;i<propuesta_cuota.size();i++){
                    System.out.println("Nro: "+i);
                        //System.out.println("Insertando.."+usuario.getId_usuario());
                        sql = "insert into propuestas_cuotas (id_propuesta_propuesta_cuota, " +
                                "			      nro_cuota_propuesta_cuota, " +
                                "			      vencimiento_propuesta_cuota, " +
                                "			      monto_cuota_propuesta_cuota) " +
                                "                             values ("+id_propuesta+","+
                                                                      propuesta_cuota.get(i).getNro_cuota_propuesta_cuota()+", "+
                                                                      "'"+propuesta_cuota.get(i).getVencimiento_propuesta_cuota()+"', "+
                                                                      propuesta_cuota.get(i).getMonto_cuota_propuesta_cuota()+") ";
                        //System.out.println(sql);
                        conexion.getSt().executeUpdate(sql);
                        guardado = true;
                }
            } catch (SQLException ex) {
                System.out.println("Error Cuotas--->"+ex.getLocalizedMessage());
                //ex.printStackTrace();
            }
        return guardado;
    }
    
    public Propuesta buscarId(int id_propuesta) {
        Propuesta propuesta = new Propuesta();
        Conexion conexion = new Conexion();
        if (conexion.conectar()) {
            try {
                // Buscar propuesta
                String sql = "select * " +
                "from propuestas p " +
                "where p.id_propuesta = ? ";
                try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                    ps.setInt(1, id_propuesta);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        propuesta.setId_propuesta(rs.getInt("id_propuesta"));
                        Cliente cliente;
                        ClienteDAO cd = new ClienteDAO();
                        cliente = cd.buscarId(rs.getInt("id_cliente_propuesta"));
                        propuesta.setCliente(cliente);
                        Aseguradora aseguradora;
                        AseguradoraDAO ad = new AseguradoraDAO();
                        aseguradora = ad.buscarId(rs.getInt("id_aseguradora_propuesta"));
                        propuesta.setAseguradora(aseguradora);
                        Vendedor vendedor;
                        VendedorDAO vd = new VendedorDAO();
                        vendedor = vd.buscarId(rs.getInt("id_vendedor_propuesta"));
                        propuesta.setVendedor(vendedor);
                        propuesta.setFecha_propuesta(rs.getDate("fecha_propuesta"));
                        propuesta.setFecha_vencimiento_propuesta(rs.getDate("fecha_vencimiento_propuesta"));
                        propuesta.setVigendia_desde_propuesta(rs.getDate("vigendia_desde_propuesta"));
                        propuesta.setVigencia_hasta_propuesta(rs.getDate("vigencia_hasta_propuesta"));
                        propuesta.setSeccion_propuesta(rs.getString("seccion_propuesta"));
                        propuesta.setPremio_propuesta(rs.getBigDecimal("premio_propuesta"));
                        propuesta.setSuma_asegurada_propuesta(rs.getBigDecimal("suma_asegurada_propuesta"));
                        propuesta.setCant_cuotas_propuesta(rs.getInt("cant_cuotas_propuesta"));  
                    }
                    ps.close();
                }
                // Buscar bienes
                ArrayList <Propuesta_bien> propuestas_bienes = new ArrayList <Propuesta_bien>();
                sql = "select * " +
                      "from propuestas_bienes b " +
                      "where b.id_propuesta_propuesta_bien = ?";
                try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                    ps.setInt(1, id_propuesta);
                    Propuesta_bien propuesta_bien;
                    BienDAO bienDAO = new BienDAO();
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        propuesta_bien = new Propuesta_bien();
                        propuesta_bien.setId_propuesta_bien(rs.getInt("id_propuesta_bien"));
                        propuesta_bien.setBien(bienDAO.buscarId(rs.getInt("id_bien_propuesta_bien")));
                        propuesta_bien.setId_propuesta_bien(rs.getInt("id_propuesta_bien"));
                        propuestas_bienes.add(propuesta_bien);
                    }
                    propuesta.setPropuesta_bien(propuestas_bienes);
                    ps.close();
                }
                // Buscar cuotas
                ArrayList <Propuesta_cuota> propuestas_cuotas = new ArrayList <Propuesta_cuota>();
                Propuesta_cuota propuesta_cuota;
                sql = "select * " +
                      "from propuestas_cuotas " +
                     "where id_propuesta_propuesta_cuota = ? ";
                
                try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                    ps.setInt(1, id_propuesta);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        propuesta_cuota = new Propuesta_cuota();
                        propuesta_cuota.setId_propuesta(id_propuesta);
                        propuesta_cuota.setId_propuesta_cuota(rs.getInt("id_propuesta_cuota"));
                        propuesta_cuota.setNro_cuota_propuesta_cuota(rs.getInt("nro_cuota_propuesta_cuota"));
                        propuesta_cuota.setVencimiento_propuesta_cuota(rs.getDate("vencimiento_propuesta_cuota"));
                        propuesta_cuota.setMonto_cuota_propuesta_cuota(rs.getBigDecimal("monto_cuota_propuesta_cuota"));
                        propuestas_cuotas.add(propuesta_cuota);
                    }
                    propuesta.setPropuesta_cuota(propuestas_cuotas);
                    ps.close();
                }
                
            } catch (SQLException ex) {
                System.out.println("--> " + ex.getLocalizedMessage());
            }
            conexion.cerrar();
        }
        return propuesta;
    }
    
    public ArrayList buscarNombre(String texto, int pagina) {
        int limit = Configuracion.REGISTROS_POR_PAGINA;
        int offset = (pagina-1)*limit;
        ArrayList propuestas = new ArrayList();
        Conexion conexion = new Conexion();
        if (conexion.conectar()) {
            try {
                String sql = "select p.id_propuesta " +
                "from propuestas p " +
                "     left join clientes c " +
                "     on p.id_cliente_propuesta = c.id_cliente " +
                "     left join personas pc " +
                "     on c.id_persona = pc.id_persona " +
                "     left join vendedores v " +
                "     on p.id_vendedor_propuesta = v.id_vendedor " +
                "     left join personas pv " +
                "     on v.id_persona = pv.id_persona " +
                "     left join aseguradoras a " +
                "     on p.id_aseguradora_propuesta = a.id_aseguradora " +
                "     left join personas pa " +
                "     on a.id_persona_aseguradora = pa.id_persona " +
                "where upper(pc.nombre_persona) like ? " +
                "or upper(pv.nombre_persona) like ? " +
                "or upper(pa.nombre_persona) like ? "+
                "limit ? offset ?";
                try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                    ps.setString(1, "%"+texto.toUpperCase()+"%");
                    ps.setString(2, "%"+texto.toUpperCase()+"%");
                    ps.setString(3, "%"+texto.toUpperCase()+"%");
                    ps.setInt(4, limit);
                    ps.setInt(5, offset);
                    ResultSet rs = ps.executeQuery();
                    Propuesta propuesta;
                    PropuestaDAO propuestaDAO = new PropuestaDAO();
                    while (rs.next()) {
                        //System.out.println("Propuesta: "+rs.getInt("id_propuesta"));
                        propuesta = propuestaDAO.buscarId(rs.getInt("id_propuesta"));
                        propuestas.add(propuesta);
                    }
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println("--> " + ex.getLocalizedMessage());
            }
            conexion.cerrar();
        }
        return propuestas;
    }
}
