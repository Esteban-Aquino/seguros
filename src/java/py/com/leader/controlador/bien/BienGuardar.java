/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.leader.controlador.bien;

import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.json.simple.JSONObject;
import py.com.leader.DAO.BienDAO;
import py.com.leader.DAO.ClienteDAO;
import py.com.leader.DAO.Tipo_bienDAO;
import py.com.leader.DAO.UsuarioDAO;
import py.com.leader.modelo.Bien;
import py.com.leader.modelo.Cliente;
import py.com.leader.modelo.Tipo_bien;
import py.com.leader.modelo.Usuario;
import static py.com.leader.util.util.nvl;

/**
 *
 * @author Esteban
 */
@WebServlet(name = "BienGuardar", urlPatterns = {"/bien/guardar"})
public class BienGuardar extends HttpServlet {

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
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
        try (PrintWriter out = response.getWriter()) {
            if (usuario == null) {
                obj.put("correcto", correcto);
                obj.put("error", "No se encuentra logueado");
                out.print(obj);
                out.flush();
            } else if (!request.getParameterMap().containsKey("id_cliente")) {
                obj.put("correcto", correcto);
                obj.put("error", "Debe ingresar cliente");
                out.print(obj);
                out.flush();
            } else if (!request.getParameterMap().containsKey("id_tipo_bien")) {
                obj.put("correcto", correcto);
                obj.put("error", "Debe ingresar tipo de bien");
                out.print(obj);
                out.flush();
            } else {
                    Tipo_bienDAO tipo_bienDAO = new Tipo_bienDAO();
                    ClienteDAO clienteDAO = new ClienteDAO();
                    Bien bien = new Bien();
                    UsuarioDAO usuarioDAO = new UsuarioDAO();
                    bien.setId_bien(Integer.parseInt(request.getParameter("id_bien")));
                    Tipo_bien tipo_bien = tipo_bienDAO.buscarId(Integer.parseInt(request.getParameter("id_tipo_bien")));
                    Cliente cliente = clienteDAO.buscarId(Integer.parseInt(request.getParameter("id_cliente")));
                    bien.setTipo_bien(tipo_bien);
                    bien.setCliente(cliente);
                    bien.setMarca_bien(request.getParameter("marca_bien"));
                    bien.setModelo_bien(request.getParameter("modelo_bien"));
                    bien.setTip_bien(request.getParameter("tip_bien"));
                    bien.setMatricula_bien(request.getParameter("matricula_bien"));
                    bien.setAnio_bien(Integer.parseInt(request.getParameter("anio_bien")));
                    bien.setColor_bien(request.getParameter("color_bien"));
                    bien.setNum_motor_bien(request.getParameter("num_motor_bien"));
                    bien.setNum_carroceria_bien(request.getParameter("num_carroceria_bien"));
                    bien.setDescripcion_bien(request.getParameter("descripcion_bien"));
                    bien.setUsuarioauditoria(usuario);
                    BienDAO bienDAO = new BienDAO();
                    correcto = bienDAO.guardar(bien, new Usuario());
                    obj.put("correcto", correcto);
                    out.print(obj);
                    out.flush();
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            obj.put("correcto", correcto);
            obj.put("error", nvl(e.getMessage(), "No se encuentra mensaje de error"));
            out.print(obj);
            out.flush();
            e.printStackTrace();
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
