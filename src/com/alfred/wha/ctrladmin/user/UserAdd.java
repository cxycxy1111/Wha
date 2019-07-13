package com.alfred.wha.ctrladmin.user;

import com.alfred.wha.serv.UserService;
import com.alfred.wha.util.BaseServlet;
import com.alfred.wha.util.Tool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "UserAdd",urlPatterns = "/admin/user/add")
/**
 * http://localhost:8080/admin/user/delete?id=1
 */
public class UserAdd extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
    }

    @Override
    protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out, long current_user, int current_user_type) throws IOException  {
        super.dealWithSessionAlive(request, response, session, out, current_user, current_user_type);
        String user_name = request.getParameter("user_name");
        String pwd = request.getParameter("pwd");
        String nick_name = request.getParameter("nick_name");
        String email = request.getParameter("email");
        String motto = request.getParameter("motto");
        out.append(userService.register(user_name,pwd,nick_name,email,motto));
    }

    @Override
    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) throws IOException {
        super.dealWithSessionDead(request, response, session, out);

    }
}
