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
public class Dashboard extends HttpServlet {

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
            /*Cookie[] cookie = request.getCookies();
            for(Cookie c:cookie)
            {
                if(!c.getName().equals("mail"))
                {
                    response.sendRedirect("index.html");
                }
            }*/
            if(CheckCookie.checkstatus(request, response)==1){
            int id = Integer.parseInt(request.getParameter("id"));
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/usermgnt?user=root");
            stmt = conn.createStatement();
            out.println("</html>"
                    + "<head>"
                    + "<link rel='stylesheet' type='text/css' href='"+request.getContextPath()+"/styles/style.css'>"
                    + "<script src='"+request.getContextPath()+"/js/js.js'></script>"
                    + "</head>"
                    + "<body>");
            out.println("<div id='header'>USER DASHBOARD<a style='position:relative;right:5px;' class='profile' href='LogoutUser'>Logout</a>"
                    + " <div style='position:relative;right:10px;' class='profile'><button onclick='myFunction()'  class='dropbtn'>Profile</button><div id='myDropdown' class='dropdown-content'>"
                              + "<a href='EditProfile?id="+id+"&user=0&type=0&Aid="+id+"'>Edit Profile</a>" 
                              + "<a href='UpdatePsd?id="+id+"&user=0&type=0&Aid="+id+"'>Change Password</a>" 
                              + "</div>" 
                              + "</div></div>");
                    out.println("<div style='margin-top:3px;'><div id='sidebar'><br><br><a class='button' href='#'>Dashboard</a>");
                     out.println("<br><a class='button' href='#'>Book A Ticket</a>");       
                     out.println("<br><a class='button' href='#'>Chechk PNR Status</a>");   
                     out.println("<br><a class='button' href='#'>Trains Between Station</a>");   
                     out.println("</div></div>");
            out.println("</body>"
                    + "</html>");
            }
            else{
                    RequestDispatcher rd = request.getRequestDispatcher("index.html");
                    out.println("<script>alert('Please Log in First');</script>");
                    rd.include(request, response);
                }
        } catch (SQLException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
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
