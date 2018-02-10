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
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Student
 */
public class AdminDashboard extends HttpServlet {

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
            int Aid = Integer.parseInt(request.getParameter("id"));
            int Uid = Integer.parseInt(request.getParameter("uid"));
            rs = stmt.executeQuery("Select * from user_details where Status=2 and Type=0");
             out.println("</html>"
                    + "<head>"
                    + "<link rel='stylesheet' type='text/css' href='"+request.getContextPath()+"/styles/style.css'>"
                    + "</head>"
                    + "<body >");
                      out.println("<div id='header'>ADMIN DASHBOARD<a style='position:relative;right:5px;' class='profile' href='LogoutUser'>Logout</a><a style='position:relative;right:10px;' class='profile' href='EditProfile?id="+Aid+"&user=1&type=1'>Profile</a></div>");
                      out.println("<div style='margin-top:3px;'><div align='center' style='left:40%;background-color: black;'>");
                     out.println("<table  border=2 cellspacing='10' cellpadding='10'>");
            
                      if(rs.next())
                      {
                          out.println("<tr><th colspan=3>New Users</th></tr>");
                      
                      while(true){
                       int id = rs.getInt("Id");
                       out.println("<tr><td>"+rs.getString("Name")+"</td><td><a class='button' href='Operate?op=1&id="+id+"'>Accept</a></td><td><a class='button' href='Operate?op=3&id="+id+"'>Reject</a></td></tr>");
                       if(!rs.next())
                           break;
                      }
                      }
                     out.println("</table>");
                     out.println("</div>");
                     out.println("<div id='sidebar'>USERS:-<br><br><a class='button' href='AdminDashboard?id="+Aid+"&uid=1'>Active</a>");
                     out.println("<br><a class='button' href='AdminDashboard?id="+Aid+"&uid=3'>Rejected</a>");       
                     out.println("<br><a class='button' href='AdminDashboard?id="+Aid+"&uid=0'>Deactive</a>");   
                     out.println("<br><a class='button' href='AdminDashboard?id="+Aid+"&uid=4'>Admins</a>");   
                     out.println("</div>");
                     out.println("<div id='tab'>");
                     out.println("<table  id='data' border=2 cellspacing='9' cellpadding='9'>");
                     switch(Uid){
                         case 0:
                              out.println("<tr><th colspan=3>Deactive Users</th></tr>");
                              rs = stmt.executeQuery("Select * from user_details where Status=0 and Type=0");
                             break;
                         case 1:
                              out.println("<tr><th colspan=3>Active Users</th></tr>");
                              rs = stmt.executeQuery("Select * from user_details where Status=1 and Type=0");
                             break;
                         case 3:
                              out.println("<tr><th colspan=3>Rejected Users</th></tr>");
                              rs = stmt.executeQuery("Select * from user_details where Status=3 and Type=0");
                             break;
                         case 4:
                              out.println("<tr><th colspan=3>Admins</th></tr>");
                              rs = stmt.executeQuery("Select * from user_details where Type=1 and Id!="+Aid+"");
                             break;
                     }
                     
                     
                    
                     if(!rs.next())
                     {
                         out.println("<tr colspan=3 style='text-align:center;'><td>No users</td></tr>");
                     }
                     else
                     {
                         while(true)
                         {
                           int id = rs.getInt("Id");
                           switch(Uid){
                                case 0:
                                    out.println("<tr><td style='text-align:center;'>"+rs.getString("Name")+"</td><td colspan='2'><a class='button' "
                                            + "href='Operate?op=1&id="+id+"'>Activate</a></tr>");
                                    break;
                                case 1:
                                    out.println("<tr><td style='text-align:center;'>"+rs.getString("Name")+"</td><td><a class='button' "
                                            + "href='EditProfile?id="+id+"&user=1&type=0'>Edit</a></td><td><a class='button' "
                                                    + "href='Operate?op=0&id="+id+"'>Deactive</a></td></tr>");
                                    break;
                                case 3:
                                    out.println("<tr><td style='text-align:center;'>"+rs.getString("Name")+"</td><td colspan='2'><a class='button' "
                                            + "href='Operate?op=1&id="+id+"'>Accept</a></tr>");
                                    break;
                                case 4:
                                    out.println("<tr><td colspan='3' style='text-align:center;'>"+rs.getString("Name")+"</td></tr>");
                                    break;
                            }
                            
                            if(!rs.next())
                                break;
                         }
                     }
                     out.println("</table>");
                     out.println("</div>");
                     out.println("</div>");
                     out.println("</body>"
                    +"</html>");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(AdminDashboard.class.getName()).log(Level.SEVERE, null, ex);
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
