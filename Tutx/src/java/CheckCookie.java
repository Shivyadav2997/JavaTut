
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Bhushan
 */
public class CheckCookie {
    
    public static int checkstatus(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    Cookie[] cookie = request.getCookies();
            for(Cookie c:cookie)
            {
                if(c.getName().equals("mail"))
                {
                    String mail = c.getValue();
                   if(!mail.equals("") || mail!=null){
                       return 1;
                   }
                }
                else
                    return 0;
            }
            return 0;
    }
}
