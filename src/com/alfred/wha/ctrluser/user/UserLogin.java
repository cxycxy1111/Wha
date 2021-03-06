package com.alfred.wha.ctrluser.user;

import com.alfred.wha.serv.UserService;
import com.alfred.wha.util.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "UserLogin",urlPatterns = "/user/user/login")
/**
 * http://localhost:8080/admin/user/lock?id=1
 */
public class UserLogin extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
    }

    @Override
    protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out, long current_user, int current_user_type) throws IOException{
        super.dealWithSessionAlive(request, response, session, out, current_user, current_user_type);
        out.append(userService.queryDetailByUserSelf(current_user));
    }

    @Override
    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) throws IOException {
        //super.dealWithSessionDead(request, response, session, out);
        String user_name = request.getParameter("user_name");
        String password = request.getParameter("pwd");

        String result = userService.loginCheck(user_name,password,"","","","","");
        if (result.startsWith("{")){
            out.append(result);
        }else {
            HttpSession s = request.getSession();
            s.setMaxInactiveInterval(7*24*60*60);
            System.out.println(result);
            String [] strings = result.split("|");
            System.out.println(strings[0]);
            System.out.println(strings[2]);
            s.setAttribute("id",strings[0]);
            s.setAttribute("type","1");
            out.append(userService.queryDetailByUserSelf(Long.parseLong(strings[0])));
        }
    }
}
