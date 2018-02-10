/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Student
 */
public class ValidateLogin extends HttpServlet {

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
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/usermgnt?user=root");
            stmt = conn.createStatement();
            
            String mail = request.getParameter("mail");
            String upass = request.getParameter("pssd");
            rs = stmt.executeQuery("Select * from user_details where Email='"+mail+"' and Password='"+upass+"'");
            if(rs.next())
            {
                Cookie cookie = new Cookie("mail",rs.getString("Email"));
                response.addCookie(cookie);
                int id = rs.getInt("Id");
                if(rs.getInt("Type")==1)
                {
                  
                  response.sendRedirect("AdminDashboard?id="+id+"&uid=1");
                }
                else
                {
                    int stat = rs.getInt("Status");
                  if(stat==1)
                    response.sendRedirect("Dashboard?id="+id);
                  else if(stat==0){
                    RequestDispatcher rd = request.getRequestDispatcher("index.html");
                    out.println("<script>alert('You have been Deactivated');</script>");
                    rd.include(request, response);
                  }
                  else if(stat==2){
                    RequestDispatcher rd = request.getRequestDispatcher("index.html");
                    out.println("<script>alert('Your request not yet Accepted');</script>");
                    rd.include(request, response);
                  }
                  else if(stat==3){
                    RequestDispatcher rd = request.getRequestDispatcher("index.html");
                    out.println("<script>alert('You are Rejected');</script>");
                    rd.include(request, response);
                  }
                }
                
            }
            else
            {
                RequestDispatcher rd = request.getRequestDispatcher("index.html");
                out.println("<script>alert('Invalid Credentials');</script>");
                rd.include(request, response);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ValidateLogin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ValidateLogin.class.getName()).log(Level.SEVERE, null, ex);
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
