/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package py.com.leader.controlador.bien;


import py.com.leader.controlador.bien.*;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import py.com.leader.DAO.BienDAO;
import static py.com.leader.util.util.nvl;

/**
 *
 * @author Esteban
 */
@WebServlet(name = "BienEliminar", urlPatterns = {"/bien/eliminar"})
public class BienEliminar extends HttpServlet {

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
            try {
                if (!request.getParameterMap().containsKey("id_bien")) {
                    obj.put("correcto", correcto);
                    obj.put("error", "No se encuentra Id Bien");
                    out.print(obj);
                    out.flush();
                } else {
                    BienDAO bienDAO = new BienDAO();
                    correcto = bienDAO.eliminar(Integer.parseInt(request.getParameter("id_bien")));
                    obj.put("correcto", correcto);
                    out.print(obj);
                    out.flush();
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: " + e.getMessage());
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
