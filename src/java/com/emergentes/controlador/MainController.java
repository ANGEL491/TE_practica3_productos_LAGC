package com.emergentes.controlador;

import com.emergentes.ConexionDB;
import com.emergentes.modelo.Producto;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Usuario
 */
@WebServlet(name = "MainController", urlPatterns = {"/MainController"})
public class MainController extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet MainController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet MainController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
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

        String op;
        op = (request.getParameter("op") != null) ? request.getParameter("op") : "list";
        ArrayList<Producto> lista = new ArrayList<Producto>();
        ConexionDB canal = new ConexionDB();
        Connection conn = canal.conectar();
        PreparedStatement ps;
        ResultSet rs;

        if (op.equals("list")) {
            try {
                String sql = "select * from productos";
                ps = conn.prepareStatement(sql);
                rs = ps.executeQuery();

                while (rs.next()) {
                    Producto pro = new Producto();
                    pro.setId(rs.getInt("id"));
                    pro.setNombre_producto(rs.getString("nombre_producto"));
                    pro.setPrecio(rs.getDouble("precio"));
                    pro.setStock(rs.getInt("stock"));
                    lista.add(pro);
                }
                request.setAttribute("lista", lista);
                request.getRequestDispatcher("index.jsp").forward(request, response);
            } catch (SQLException ex) {
                System.out.println("Error en SQL: " + ex.getMessage());
            } finally {
                canal.desconectar();
            }
        }
        if (op.equals("nuevo")) {
            Producto p = new Producto();
            request.setAttribute("producto", p);
            request.getRequestDispatcher("editar.jsp").forward(request, response);
        }
        if (op.equals("eliminar")) {
            int id = Integer.parseInt(request.getParameter("id"));
            try {
                String sql = "delete from productos where id= ?";
                ps = conn.prepareStatement(sql);
                ps.setInt(1, id);
                ps.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("Error en SQL:" + ex.getMessage());
            } finally {
                canal.desconectar();
            }
            response.sendRedirect("MainController");
        }

        /*if (op.equals("modificar")) {
            Producto p = new Producto();
            int id = Integer.parseInt(request.getParameter("id"));
            p=lista.get(id);
            request.setAttribute("producto", p);
            request.getRequestDispatcher("editar.jsp").forward(request, response);
        }*/
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
        int id = Integer.parseInt(request.getParameter("id"));
        String nombre_producto = request.getParameter("nombre_producto");
        double precio = Double.parseDouble(request.getParameter("precio"));
        int stock = Integer.parseInt(request.getParameter("stock"));

        Producto p = new Producto();
        p.setId(id);
        p.setNombre_producto(nombre_producto);
        p.setPrecio(precio);
        p.setStock(stock);

        ConexionDB canal = new ConexionDB();
        Connection conn = canal.conectar();
        PreparedStatement ps;
        ResultSet rs;
        if (id == 0) {
            try {
                String sql = "insert into productos(nombre_producto,precio,stock)values(?,?,?)";
                ps = conn.prepareStatement(sql);
                ps.setString(1, p.getNombre_producto());
                ps.setDouble(2, p.getPrecio());
                ps.setInt(3, p.getStock());
                ps.executeUpdate();

            } catch (SQLException ex) {
                System.out.println("Error en SQL:" + ex.getMessage());
            } finally {
                canal.desconectar();
            }
            response.sendRedirect("MainController");
        }
        
        /*else {
            try {
                String sql = "update productos set nombre_producto=?,precion=?,stock=?";
                ps = conn.prepareStatement(sql);
                ps.setString(1, p.getNombre_producto());
                ps.setDouble(2, p.getPrecio());
                ps.setInt(3, p.getStock());
                ps.executeUpdate();
            } catch (SQLException ex) {
                System.out.println("Error en SQL:" + ex.getMessage());
            } finally {
                canal.desconectar();
            }
        }*/
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
