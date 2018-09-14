package com.alfred.wha.ctrladmin;

import com.alfred.wha.serv.AdminUserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "AdminLogin",urlPatterns = "/admin/admin/login")
/**
 * localhost:8080/admin/admin/login?user_name=111&pwd=222
 */
public class AdminLogin extends BaseServlet {

    private static final long serialVersionUID = 1L;

    private AdminUserService adminUserService = new AdminUserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
    }

    @Override
    protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) {
        out.append(SUCCESS);
    }

    @Override
    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) {
        super.dealWithSessionDead(request, response, session, out);
        String user_name = request.getParameter("user_name");
        String password = request.getParameter("pwd");

        String result = adminUserService.loginCheck(user_name,password);
        if (result.startsWith("{")){
            out.append(result);
        }else {
            System.out.println("AdminLogin:session_id=" + session.getId());
            String [] strings = result.split("|");
            System.out.println("AdminLogin:session_attr:id=" + strings[0]);
            System.out.println("AdminLogin:session_attr:type=" + strings[2]);
            session.setAttribute("id",strings[0]);
            session.setAttribute("type",strings[2]);
            out.append(BaseServlet.SUCCESS);
        }
    }
}
