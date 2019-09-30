/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.leader.controlador.formulario;

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
import py.com.leader.DAO.FormularioDAO;
import py.com.leader.modelo.Formulario;
import py.com.leader.modelo.Usuario;
import static py.com.leader.util.util.nvl;

/**
 *
 * @author Esteban
 */
@WebServlet(name = "FormularioGuardar", urlPatterns = {"/formulario/guardar"})
public class FormularioGuardar extends HttpServlet {

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
            } else if (!request.getParameterMap().containsKey("nombre_formulario")) {
                obj.put("correcto", correcto);
                obj.put("error", "No se encuentra Nombre del formulario");
                out.print(obj);
                out.flush();
            } else if (!request.getParameterMap().containsKey("url_formulario")) {
                obj.put("correcto", correcto);
                obj.put("error", "No la URL del formulario");
                out.print(obj);
                out.flush();
            } else if (!request.getParameterMap().containsKey("id_sistema")) {
                obj.put("correcto", correcto);
                obj.put("error", "No se encuentra sistema");
                out.print(obj);
                out.flush();
            } else if (!request.getParameterMap().containsKey("id_submenu")) {
                obj.put("correcto", correcto);
                obj.put("error", "No se encuentra submenu");
                out.print(obj);
                out.flush();
            } else {
                if (Integer.parseInt(request.getParameter("id_sistema"))==0){
                    obj.put("correcto", correcto);
                    obj.put("error", "No se encuentra sistema");
                    out.print(obj);
                    out.flush();
                }else if (Integer.parseInt(request.getParameter("id_submenu"))==0){
                    obj.put("correcto", correcto);
                    obj.put("error", "No se encuentra submenu");
                    out.print(obj);
                    out.flush();
                }else{
                    Formulario formulario = new Formulario();
                    formulario.setId_formulario(Integer.parseInt(request.getParameter("id_formulario")));
                    formulario.setNombre_formulario(request.getParameter("nombre_formulario"));
                    formulario.setUrl_formulario(request.getParameter("url_formulario"));
                    formulario.getSistema().setId_sistema(Integer.parseInt(request.getParameter("id_sistema")));
                    formulario.getSubmenu().setId_submenu(Integer.parseInt(request.getParameter("id_submenu")));
                    FormularioDAO formularioDAO = new FormularioDAO();
                    correcto = formularioDAO.guardar(formulario, usuario);
                    obj.put("correcto", correcto);
                    out.print(obj);
                    out.flush();
                }
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
