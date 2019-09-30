/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.leader.controlador.poliza;


import py.com.leader.controlador.poliza.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import py.com.leader.DAO.AseguradoraDAO;
import py.com.leader.DAO.BienDAO;
import py.com.leader.DAO.ClienteDAO;
import py.com.leader.DAO.PolizaDAO;
import py.com.leader.DAO.VendedorDAO;
import py.com.leader.modelo.Aseguradora;
import py.com.leader.modelo.Bien;
import py.com.leader.modelo.Cliente;
import py.com.leader.modelo.Poliza;
import py.com.leader.modelo.Poliza_bien;
import py.com.leader.modelo.Poliza_cuota;
import py.com.leader.modelo.Vendedor;
//import py.com.leader.DAO.PolizaDAO;
//import py.com.leader.modelo.Poliza;
import static py.com.leader.util.util.nvl;
import static py.com.leader.util.util.parseDateSql;

/**
 *
 * @author Esteban
 */
@WebServlet(name = "PolizaGuardar", urlPatterns = {"/poliza/guardar"})
public class PolizaGuardar extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        boolean correcto = false;
        JSONObject obj = new JSONObject();
        try (PrintWriter out = response.getWriter()) {
            /*System.out.println(request.getParameter("poliza"));
            obj.put("error", "ok");
            out.print(obj);
            out.flush();*/
            try {
                if (!request.getParameterMap().containsKey("poliza")) {
                    obj.put("correcto", correcto);
                    obj.put("error", "No se encuentra Poliza");
                    out.print(obj);
                    out.flush();
                } else {
                    String JsonPoliza = request.getParameter("poliza");
                    JsonParser parser = new JsonParser();
                    JsonElement datos = parser.parse(JsonPoliza);
                    JsonObject objPoliza = datos.getAsJsonObject();
                    //System.err.println("Yes - "+objPoliza.get("id_poliza").toString().replace("\"","").replace("\"",""));
                    
                    Poliza poliza = new Poliza();
                    poliza.setId_poliza(Integer.parseInt(objPoliza.get("id_poliza").toString().replace("\"","")));
                    Cliente cliente;
                    ClienteDAO cd = new ClienteDAO();
                    cliente = cd.buscarId(Integer.parseInt(objPoliza.get("id_cliente_poliza").toString().replace("\"","")));
                    poliza.setCliente(cliente);
                    Aseguradora aseguradora;
                    AseguradoraDAO ad = new AseguradoraDAO();
                    aseguradora = ad.buscarId(Integer.parseInt(objPoliza.get("id_aseguradora_poliza").toString().replace("\"","")));
                    poliza.setAseguradora(aseguradora);
                    Vendedor vendedor;
                    VendedorDAO vd = new VendedorDAO();
                    vendedor = vd.buscarId(Integer.parseInt(objPoliza.get("id_vendedor_poliza").toString().replace("\"","")));
                    poliza.setVendedor(vendedor);
                    poliza.setFecha_poliza(parseDateSql(objPoliza.get("fecha_poliza").toString().replace("\"",""),"yyyy-MM-dd"));
                    poliza.setFecha_vencimiento_poliza(parseDateSql(objPoliza.get("fecha_vencimiento_poliza").toString().replace("\"",""),"yyyy-MM-dd"));
                    poliza.setVigendia_desde_poliza(parseDateSql(objPoliza.get("vigendia_desde_poliza").toString().replace("\"",""),"yyyy-MM-dd"));
                    poliza.setVigencia_hasta_poliza(parseDateSql(objPoliza.get("vigencia_hasta_poliza").toString().replace("\"",""),"yyyy-MM-dd"));
                    poliza.setSeccion_poliza(objPoliza.get("seccion_poliza").toString().replace("\"",""));
                    poliza.setPremio_poliza(new BigDecimal(objPoliza.get("premio_poliza").toString().replace("\"","")));
                    //poliza.setPrima_poliza(new BigDecimal(request.getParameter("prima_poliza")));
                    poliza.setSuma_asegurada_poliza(new BigDecimal(objPoliza.get("suma_asegurada_poliza").toString().replace("\"","")));
                    poliza.setCant_cuotas_poliza(Integer.parseInt(objPoliza.get("cant_cuotas_poliza").toString().replace("\"","")));
                    PolizaDAO polizaDAO = new PolizaDAO();
                    // Bienes
                    ArrayList <Poliza_bien> polizas_bienes = new ArrayList <Poliza_bien>();
                    Bien bien;
                    BienDAO bienDAO = new BienDAO();
                    String JsonBienes = objPoliza.get("bienes").toString();
                    JsonElement datosBienes = parser.parse(JsonBienes);
                    JsonObject objBienes = datosBienes.getAsJsonObject();
                    int i = 0;
                    while (i >= 0) {
                        try {
                            if (objBienes.has(String.valueOf(i))){
                                //System.out.print(i+":");
                                //System.out.println(parser.parse(objBienes.get(String.valueOf(i)).toString()).getAsJsonObject().get("id_bien").toString().replace("\"",""));
                                polizas_bienes.add(new Poliza_bien());
                                bien = bienDAO.buscarId(Integer.parseInt(parser.parse(objBienes.get(String.valueOf(i)).toString()).getAsJsonObject().get("id_bien").toString().replace("\"","")));
                                polizas_bienes.get(i).setBien(bien);
                                polizas_bienes.get(i).setId_poliza(poliza.getId_poliza());
                                i += 1;
                            }else{
                                i = -1;
                            }
                        }catch(Exception e){
                            i = -1;
                            e.printStackTrace();
                        }
                    }
                    poliza.setPoliza_bien(polizas_bienes);
                    // Cuotas
                    ArrayList <Poliza_cuota> polizas_cuotas = new ArrayList <Poliza_cuota>();
                    Poliza_cuota poliza_cuota;
                    String JsonCuotas = objPoliza.get("cuotas").toString();
                    JsonElement datosCuotas = parser.parse(JsonCuotas);
                    JsonObject objCuotas = datosCuotas.getAsJsonObject();
                    i = 0;
                    while (i >= 0) {
                        try {
                            if (objCuotas.has(String.valueOf(i+1))){
                                //System.out.print(i+":");
                                //System.out.println(parser.parse(objBienes.get(String.valueOf(i)).toString()).getAsJsonObject().get("id_bien").toString().replace("\"",""));
                                polizas_cuotas.add(new Poliza_cuota());
                                polizas_cuotas.get(i).setNro_cuota_poliza_cuota(Integer.parseInt(parser.parse(objCuotas.get(String.valueOf(i+1)).toString()).getAsJsonObject().get("nro_cuota").toString().replace("\"","")));
                                polizas_cuotas.get(i).setVencimiento_poliza_cuota(parseDateSql(parser.parse(objCuotas.get(String.valueOf(i+1)).toString()).getAsJsonObject().get("vencimiento").toString().replace("\"",""),"yyyy-MM-dd"));
                                polizas_cuotas.get(i).setMonto_cuota_poliza_cuota(new BigDecimal(parser.parse(objCuotas.get(String.valueOf(i+1)).toString()).getAsJsonObject().get("monto").toString().replace("\"","")));
                                i += 1;
                            }else{
                                i = -1;
                            }
                        }catch(Exception e){
                            i = -1;
                            e.printStackTrace();
                        }
                    }
                    poliza.setPoliza_cuota(polizas_cuotas);
                    // Guardar Cuotas
                    
                    correcto = polizaDAO.guardar(poliza);
                    obj.put("correcto", correcto);
                    out.print(obj);
                    out.flush();
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                e.printStackTrace();
                obj.put("correcto", correcto);
                obj.put("error", nvl(e.getMessage(), "No se encuentra mensaje de error"));
                out.print(obj);
                out.flush();
            }
        } catch (IOException hex) {
            System.out.println("Error: " + hex.getMessage());
            
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
