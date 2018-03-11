/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Bhushan
 */
public class RegisterUser extends HttpServlet {

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
        Connection conn = null;
        Statement stmt = null;
        RequestDispatcher rd = null;
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/usermgnt?user=root");
            stmt = conn.createStatement();
            
            String uname = request.getParameter("uname");
            String umail = request.getParameter("umail");
            String upass = request.getParameter("pssd");
            String cpass = request.getParameter("cpssd");
            String ugender = request.getParameter("gender");
            String udob = request.getParameter("dob");
            String umobile = request.getParameter("mobile");
            String ustate = request.getParameter("state");
            String ucity = request.getParameter("city");
           
            if(!upass.equals(cpass) || upass.length()<8 )
            {
                rd = request.getRequestDispatcher("register.html");
                out.println("<script>alert('Please input valid Data');</script>");
                rd.include(request, response);
                response.sendRedirect("Register.html");
            }
            
            
            int err = stmt.executeUpdate("Insert into user_details(Name,Email,Password,Gender,Dob,Mobile,State,City) "
                        + "values('"+uname+"','"+umail+"','"+upass+"','"+ugender+"','"+udob+"','"+umobile+"','"+ustate+"','"+ucity+"')");
                    
            if(err!=0){
                rd = request.getRequestDispatcher("index.html");
                out.println("<script>alert('Registered Successfully');</script>");
                rd.include(request, response);
                response.sendRedirect("index.html");
            }else
            {
                rd = request.getRequestDispatcher("Register.html");
                out.println("<script>alert('Some error occured');</script>");
                rd.include(request, response);
                response.sendRedirect("Register.html");
            }
        
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RegisterUser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(RegisterUser.class.getName()).log(Level.SEVERE, null, ex);
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
