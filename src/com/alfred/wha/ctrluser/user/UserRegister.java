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

@WebServlet(name = "UserRegister",urlPatterns = "/user/user/register")
/**
 * http://localhost:8080/admin/user/delete?id=1
 */
public class UserRegister extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
    }

    @Override
    protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out, long current_user, int current_user_type) throws IOException  {
        super.dealWithSessionAlive(request, response, session, out, current_user, current_user_type);
    }

    @Override
    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) throws IOException {
        String user_name = request.getParameter("user_name");
        String pwd = request.getParameter("pwd");
        String email = request.getParameter("email");
        out.append(userService.register(user_name,pwd,user_name,email,""));

    }
}
