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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Bhushan
 */
public class UpdatePsd extends HttpServlet {

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
            if(CheckCookie.checkstatus(request, response)==1)
            {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/usermgnt?user=root");
            stmt = conn.createStatement();
            int id = Integer.parseInt(request.getParameter("id"));
            int Aid = Integer.parseInt(request.getParameter("Aid"));
            int user = Integer.parseInt(request.getParameter("user"));
            int etype = Integer.parseInt(request.getParameter("type"));
            rs = stmt.executeQuery("Select Password from user_details where Id="+id);
            rs.next();
            String upass = rs.getString("Password");
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Update Password</title>");
            out.println("<link rel='stylesheet' type='text/css' href='"+request.getContextPath()+"/styles/style.css'>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<div id='div1'>");
            out.println("<form action='Updatedetails'>"+
                                "<input type='hidden' name='id' value='"+id+"'>"+
                                "<input type='hidden' name='op' value='1'>"+
                                "<input type='hidden' name='user' value='"+user+"'>"+
                                "<input type='hidden' name='Aid' value='"+Aid+"'>"+
                                "<table cellspacing='10' cellpadding='10'>"+
                                    "<tr><th colspan='2' style='font-weight: bold;color:white'>Change Password</th></tr>"+
                                    "<tr><td>Old Password:</td><td><input type='password' name='opssd' required></td></tr>"+
                                    "<tr><td>Password:</td><td><input type='password' name='npssd' required></td></tr>"+
                                    "<tr><td>Confirm Password:</td><td><input type='password' name='cpssd' required></td></tr>"+
                                    "<tr><td colspan='2'><button type='submit' class='btn'>Update</button></td></tr>"+
                                "</table>"+
                            "</form>");
                            if(user==1){
                                out.println("<form action='AdminDashboard'>"
                                        + "<input type='hidden' name='id' value='"+Aid+"'>"
                                        + "<input type='hidden' name='uid' value='1'>"
                                        + "<table style='width:100%;' cellspacing='10' cellpadding='10'>"
                                        +"<tr ><td colspan='3'><button type='submit' class='btn'>Cancel</button></td></tr>"
                                        +"</table>"
                                        + "</form>");
                            }
                            else{
                                out.println("<form action='Dashboard'>"
                                        + "<input type='hidden' name='id' value='"+id+"'>"
                                        + "<table style='width:100%;' cellspacing='10' cellpadding='10'>"
                                        +"<tr><td colspan='3'><button type='submit' class='btn'>Cancel</button></td></tr>"
                                        +"</table>"
                                        + "</form>");
                            }
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }else{
                    RequestDispatcher rd = request.getRequestDispatcher("index.html");
                    out.println("<script>alert('Please Log in First');</script>");
                    rd.include(request, response);
                }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(UpdatePsd.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(UpdatePsd.class.getName()).log(Level.SEVERE, null, ex);
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
