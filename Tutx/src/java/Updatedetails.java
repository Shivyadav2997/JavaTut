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
public class Updatedetails extends HttpServlet {

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
        RequestDispatcher rd=null;
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            if(CheckCookie.checkstatus(request, response)==1)
            {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/usermgnt?user=root");
            stmt = conn.createStatement();
         
            int user = Integer.parseInt(request.getParameter("user"));
            int op = Integer.parseInt(request.getParameter("op"));
            int id = Integer.parseInt(request.getParameter("id"));
            int Aid = Integer.parseInt(request.getParameter("Aid"));
            String origPssd = request.getParameter("opass");
            
            switch(op){
                case 0: 
                    String name = request.getParameter("uname");
                    String gender = request.getParameter("gender");
                    String dob = request.getParameter("dob");
                    String mobile = request.getParameter("mobile");
                    String state = request.getParameter("state");
                    String city = request.getParameter("city");
                    stmt.executeUpdate("Update user_details SET Name='"+name+"',Gender='"+gender+"',Dob='"+dob+"',Mobile='"+mobile+"',State='"+state+"',City='"+city+"' where Id='"+id+"'");
                    break;
                case 1: 
                    String opass = request.getParameter("opssd");
                    String npass = request.getParameter("npssd");
                    String cpass = request.getParameter("cpssd");
                    
                    if(opass.equals(origPssd)){
                        if(npass.equals(cpass))
                        {
                            if(npass.length()>=8){
                                stmt.executeUpdate("Update user_details SET Password='"+npass+"'");
                            }
                            else{
                                rd = request.getRequestDispatcher("EditProfile?id="+id+"");
                            out.println("<script>alert('Password length must be atleast 8 ');</script>");
                            rd.include(request, response);
                            }
                        }
                        else{
                            rd = request.getRequestDispatcher("EditProfile?id="+id+"");
                            out.println("<script>alert('Password do not match!!');</script>");
                            rd.include(request, response);
                        }
                    }
                    else{
                        rd = request.getRequestDispatcher("EditProfile?id="+id+"");
                        out.println("<script>alert('Password Incorrect!!');</script>");
                        rd.include(request, response);
                    }
                    break;
                case 2: 
                    int typ = Integer.parseInt(request.getParameter("type"));
                    stmt.executeUpdate("Update user_details SET Type='"+typ+"' where Id='"+id+"'");
                    break;
                    
            }
            
            if(user==0){
                rd = request.getRequestDispatcher("Dashboard");
                out.println("<script>alert('Updated Successfully!!');</script>");
                rd.include(request, response);
                //response.sendRedirect("Dashboard");
            }
            else{
                
                response.sendRedirect("AdminDashboard?id="+Aid+"&uid=1");
                //out.println("<script>alert('Updated Successfully!!');</script>");
            }
        }else{
                   rd = request.getRequestDispatcher("index.html");
                    out.println("<script>alert('Please Log in First');</script>");
                    rd.include(request, response);
                }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Updatedetails.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Updatedetails.class.getName()).log(Level.SEVERE, null, ex);
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
