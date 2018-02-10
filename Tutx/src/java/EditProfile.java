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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Bhushan
 */
public class EditProfile extends HttpServlet {

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
            int id = Integer.parseInt(request.getParameter("id"));
            int user = Integer.parseInt(request.getParameter("user"));
            int etype = Integer.parseInt(request.getParameter("type"));
            rs = stmt.executeQuery("Select Email,Name,Password from user_details where Id="+id);
            rs.next();
            String mail = rs.getString("Email"); 
            String fname = rs.getString("Name"); 
            String upass = rs.getString("Password");
            out.println("</html>"
                    + "<head>"
                    + "<link rel='stylesheet' type='text/css' href='"+request.getContextPath()+"/styles/style.css'>"
                    + "</head>"
                    + "<body >");
            
            
            out.println("<body>"+
			"<div id='div1'>"+
                            "<form action='Updatedetails'>"+
                                "<input type='hidden' name='id' value='"+id+"'>"+
                                "<input type='hidden' name='op' value='0'>"+
                                "<input type='hidden' name='user' value='"+user+"'>"+
                                "<table cellspacing='10' cellpadding='10'>"+
                                    "<tr><th colspan='2' style='font-weight: bold;color:white'>Change Name</th></tr>"+
                                    "<tr style='width:200px;'><td >Full Name:</td><td style='position:relative;left:35px;'><input type='text' name='uname' value='"+fname+"'required></td></tr>"+
                                    "<tr style='width:200px;'><td >Email:</td><td style='position:relative;left:35px;'><input type='text' value='"+mail+"' disabled='disabled'></td></tr>"+
                                    "<tr><td colspan='2'><button type='submit' class='btn' style='width:294px;'>Update</button></td></tr>"+
                                "</table>"+
                            "</form><form action='Updatedetails'>"+
                                "<input type='hidden' name='id' value='"+id+"'>"+
                                "<input type='hidden' name='op' value='1'>"+
                                "<input type='hidden' name='user' value='"+user+"'>"+
                                "<table cellspacing='10' cellpadding='10'>"+
                                    "<tr><th colspan='2' style='font-weight: bold;color:white'>Change Password</th></tr>"+
                                    "<tr><td>Old Password:</td><td><input type='password' name='opssd' required></td></tr>"+
                                    "<tr><td>Password:</td><td><input type='password' name='npssd' required></td></tr>"+
                                    "<tr><td>Confirm Password:</td><td><input type='password' name='cpssd' required></td></tr>"+
                                    "<tr><td colspan='2'><button type='submit' class='btn'>Update</button></td></tr>"+
                                "</table>"+
                            "</form>");
                            if(user==1 && etype!=1){
                            out.println("<form action='Updatedetails'>"+
                                "<input type='hidden' name='id' value='"+id+"'>"+
                                "<input type='hidden' name='op' value='2'>"+
                                "<input type='hidden' name='user' value='1'>"+
                                "<table cellspacing='10' cellpadding='10'>"+
                                    "<tr><th colspan='2' style='font-weight: bold;color:white'>Change User Type</th></tr>"+
                                    "<tr style='width:200px;'><td>Type:</td><td style='position:relative;left:95px;width:100%'><select style='width:60%;height:100%' name='type'><option value='0'>User</option><option value='1'>Admin</option></select>"+
                                    "<tr ><td colspan='2'><button type='submit' class='btn'>Update</button></td></tr>"+
                                "</table>"+
                            "</form>");}           
                        out.println("</div>"+
                    "</body>"+
                    "</html>");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EditProfile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(EditProfile.class.getName()).log(Level.SEVERE, null, ex);
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
