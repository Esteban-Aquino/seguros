/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.leader.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import py.com.leader.modelo.Poliza_bien;
import py.com.leader.modelo.Poliza_cuota;

/**
 *
 * @author esteban
 */
public class Poliza_cuotaDAO {
    
    public boolean existenCuotas (int id_poliza){
        boolean exi = false;
        Conexion conexion = new Conexion();
        if (conexion.conectar()) {
            try {
                String sql = "select distinct 1 existe " +
                             "from polizas_cuotas " +
                             "where id_poliza_poliza_cuota = ? ";
                try (PreparedStatement ps = conexion.getCon().prepareStatement(sql)) {
                    ps.setInt(1, id_poliza);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        exi = true;
                    }
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error Poliza_CuotaDAO:--> " + ex.getLocalizedMessage());
            }
            conexion.cerrar();
        }
        return exi;
    }
    
}
