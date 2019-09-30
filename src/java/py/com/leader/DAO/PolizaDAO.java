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
import py.com.leader.modelo.Poliza;
import py.com.leader.modelo.Poliza_bien;
import py.com.leader.DAO.Poliza_bienDAO;
import py.com.leader.modelo.Aseguradora;
import py.com.leader.modelo.Cliente;
import py.com.leader.modelo.Poliza_cuota;
import py.com.leader.modelo.Vendedor;
import py.com.leader.util.Configuracion;
import static py.com.leader.util.util.parseDateSql;

/**
 *
 * @author esteban
 */
public class PolizaDAO {
    public boolean guardar(Poliza poliza) {
        boolean guardado = false;
        int id = -1;
        Conexion conexion = new Conexion();

        if (conexion.conectar()) {
            try {
                String sql = "";
                if (poliza.getId_poliza() == 0) {
                    //System.out.println("Insertando.."+usuario.getId_usuario());
                    sql = "insert into polizas (id_cliente_poliza, " +
                        "			id_aseguradora_poliza, " +
                        "			id_vendedor_poliza, " +
                        "			vigendia_desde_poliza, " +
                        "			vigencia_hasta_poliza, " +
                        //"			prima_poliza, " +
                        "			premio_poliza, " +
                        "			suma_asegurada_poliza, " +
                        "			seccion_poliza, " +
                        "			cant_cuotas_poliza, " +
                        "			fecha_poliza, " +
                        "			fecha_vencimiento_poliza) " +
                        "values ("+poliza.getCliente().getId_cliente()+", " +
                        "	"+poliza.getAseguradora().getId_aseguradora()+", " +
                        "	"+poliza.getVendedor().getId_vendedor()+", " +
                        "	'"+poliza.getVigendia_desde_poliza()+"', " +
                        "	'"+poliza.getVigencia_hasta_poliza()+"', " +
                        "	"+poliza.getPremio_poliza()+", " +
                        "	"+poliza.getSuma_asegurada_poliza()+", " +
                        "	"+poliza.getSeccion_poliza()+", " +
                        "	"+poliza.getCant_cuotas_poliza()+", " +
                        "	'"+poliza.getFecha_poliza()+"', " +
                        "	'"+poliza.getFecha_vencimiento_poliza()+"')";
                    //System.out.println(sql);
                    conexion.getSt().executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
                    ResultSet keyset = conexion.getSt().getGeneratedKeys();
                    if (keyset.next()) {
                        id = keyset.getInt(1);
                        //System.out.println("Guardado.."+id);
                        guardado = GuardarPolizaBien(poliza.getPoliza_bien(), id, conexion);
                        if (guardado){
                            guardado = GuardarPolizaCuota(poliza.getPoliza_cuota(), id, conexion);
                        }
                        // Ahora carga poliza bien en el controlador y proba, nde vairo
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
    
    private boolean GuardarPolizaBien(ArrayList<Poliza_bien>  poliza_bien, int id_poliza, Conexion conexion){
        boolean guardado = false;
        int id = -1;
        Poliza_bienDAO poliza_bienDAO = new Poliza_bienDAO();
            try {
                for (int i = 0;i<poliza_bien.size();i++){
                    System.out.println("Nro: "+i);
                    if (!poliza_bienDAO.buscarPolizaBienBool(id_poliza, poliza_bien.get(i).getBien().getId_bien())){
                    String sql = "";
                    if (poliza_bien.get(i).getId_poliza_bien()== 0) {
                        //System.out.println("Insertando.."+usuario.getId_usuario());
                        sql = "insert into polizas_bienes (id_bien_poliza_bien, " +
                                    "			  id_poliza_poliza_bien) " +
                                    "			values ("+poliza_bien.get(i).getBien().getId_bien()+", " +
                                    "			        "+id_poliza+") ";
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
    
    private boolean GuardarPolizaCuota(ArrayList<Poliza_cuota>  poliza_cuota, int id_poliza, Conexion conexion){
        boolean guardado = false;
        int id = -1;
        String sql = "";
        Poliza_cuotaDAO poliza_cuotaDAO = new Poliza_cuotaDAO();
            try {
                    if (poliza_cuotaDAO.existenCuotas(id_poliza)){
                        sql = "delete from polizas_cuotas "
                             + "where id poliza = "+id_poliza;
                        System.out.println(sql);
                        conexion.getSt().executeUpdate(sql);
                    }
                for (int i = 0;i<poliza_cuota.size();i++){
                    System.out.println("Nro: "+i);
                        //System.out.println("Insertando.."+usuario.getId_usuario());
                        sql = "insert into polizas_cuotas (id_poliza_poliza_cuota, " +
                                "			      nro_cuota_poliza_cuota, " +
                                "			      vencimiento_poliza_cuota, " +
                                "			      monto_cuota_poliza_cuota) " +
                                "                             values ("+id_poliza+","+
                                                                      poliza_cuota.get(i).getNro_cuota_poliza_cuota()+", "+
                                                                      "'"+poliza_cuota.get(i).getVencimiento_poliza_cuota()+"', "+
                                                                      poliza_cuota.get(i).getMonto_cuota_poliza_cuota()+") ";
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
    
    public Poliza buscarId(int id_poliza) {
        Poliza poliza = new Poliza();
        Conexion conexion = new Conexion();
        if (conexion.conectar()) {
            try {
                // Buscar poliza
                String sql = "select * " +
                "from polizas p " +
                "where p.id_poliza = ? ";
                try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                    ps.setInt(1, id_poliza);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        poliza.setId_poliza(rs.getInt("id_poliza"));
                        Cliente cliente;
                        ClienteDAO cd = new ClienteDAO();
                        cliente = cd.buscarId(rs.getInt("id_cliente_poliza"));
                        poliza.setCliente(cliente);
                        Aseguradora aseguradora;
                        AseguradoraDAO ad = new AseguradoraDAO();
                        aseguradora = ad.buscarId(rs.getInt("id_aseguradora_poliza"));
                        poliza.setAseguradora(aseguradora);
                        Vendedor vendedor;
                        VendedorDAO vd = new VendedorDAO();
                        vendedor = vd.buscarId(rs.getInt("id_vendedor_poliza"));
                        poliza.setVendedor(vendedor);
                        poliza.setFecha_poliza(rs.getDate("fecha_poliza"));
                        poliza.setFecha_vencimiento_poliza(rs.getDate("fecha_vencimiento_poliza"));
                        poliza.setVigendia_desde_poliza(rs.getDate("vigendia_desde_poliza"));
                        poliza.setVigencia_hasta_poliza(rs.getDate("vigencia_hasta_poliza"));
                        poliza.setSeccion_poliza(rs.getString("seccion_poliza"));
                        poliza.setPremio_poliza(rs.getBigDecimal("premio_poliza"));
                        poliza.setSuma_asegurada_poliza(rs.getBigDecimal("suma_asegurada_poliza"));
                        poliza.setCant_cuotas_poliza(rs.getInt("cant_cuotas_poliza"));  
                    }
                    ps.close();
                }
                // Buscar bienes
                ArrayList <Poliza_bien> polizas_bienes = new ArrayList <Poliza_bien>();
                sql = "select * " +
                      "from polizas_bienes b " +
                      "where b.id_poliza_poliza_bien = ?";
                try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                    ps.setInt(1, id_poliza);
                    Poliza_bien poliza_bien;
                    BienDAO bienDAO = new BienDAO();
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        poliza_bien = new Poliza_bien();
                        poliza_bien.setId_poliza_bien(rs.getInt("id_poliza_bien"));
                        poliza_bien.setBien(bienDAO.buscarId(rs.getInt("id_bien_poliza_bien")));
                        poliza_bien.setId_poliza_bien(rs.getInt("id_poliza_bien"));
                        polizas_bienes.add(poliza_bien);
                    }
                    poliza.setPoliza_bien(polizas_bienes);
                    ps.close();
                }
                // Buscar cuotas
                ArrayList <Poliza_cuota> polizas_cuotas = new ArrayList <Poliza_cuota>();
                Poliza_cuota poliza_cuota;
                sql = "select * " +
                      "from polizas_cuotas " +
                     "where id_poliza_poliza_cuota = ? ";
                
                try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                    ps.setInt(1, id_poliza);
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        poliza_cuota = new Poliza_cuota();
                        poliza_cuota.setId_poliza(id_poliza);
                        poliza_cuota.setId_poliza_cuota(rs.getInt("id_poliza_cuota"));
                        poliza_cuota.setNro_cuota_poliza_cuota(rs.getInt("nro_cuota_poliza_cuota"));
                        poliza_cuota.setVencimiento_poliza_cuota(rs.getDate("vencimiento_poliza_cuota"));
                        poliza_cuota.setMonto_cuota_poliza_cuota(rs.getBigDecimal("monto_cuota_poliza_cuota"));
                        polizas_cuotas.add(poliza_cuota);
                    }
                    poliza.setPoliza_cuota(polizas_cuotas);
                    ps.close();
                }
                
            } catch (SQLException ex) {
                System.out.println("--> " + ex.getLocalizedMessage());
            }
            conexion.cerrar();
        }
        return poliza;
    }
    
    public ArrayList buscarNombre(String texto, int pagina) {
        int limit = Configuracion.REGISTROS_POR_PAGINA;
        int offset = (pagina-1)*limit;
        ArrayList polizas = new ArrayList();
        Conexion conexion = new Conexion();
        if (conexion.conectar()) {
            try {
                String sql = "select p.id_poliza " +
                "from polizas p " +
                "     left join clientes c " +
                "     on p.id_cliente_poliza = c.id_cliente " +
                "     left join personas pc " +
                "     on c.id_persona = pc.id_persona " +
                "     left join vendedores v " +
                "     on p.id_vendedor_poliza = v.id_vendedor " +
                "     left join personas pv " +
                "     on v.id_persona = pv.id_persona " +
                "     left join aseguradoras a " +
                "     on p.id_aseguradora_poliza = a.id_aseguradora " +
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
                    Poliza poliza;
                    PolizaDAO polizaDAO = new PolizaDAO();
                    while (rs.next()) {
                        //System.out.println("Poliza: "+rs.getInt("id_poliza"));
                        poliza = polizaDAO.buscarId(rs.getInt("id_poliza"));
                        polizas.add(poliza);
                    }
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println("--> " + ex.getLocalizedMessage());
            }
            conexion.cerrar();
        }
        return polizas;
    }
}
