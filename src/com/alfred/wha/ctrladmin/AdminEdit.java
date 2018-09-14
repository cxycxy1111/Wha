package com.alfred.wha.ctrladmin;

import com.alfred.wha.serv.AdminUserService;
import com.alfred.wha.util.Tool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "AdminEdit",urlPatterns = "/admin/admin/edit")
/**
 * http://localhost:8080/admin/admin/edit?id=1&nick_name=AlfredTeng&email=1023054095@qq.com&motto=冯静霞
 */
public class AdminEdit extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private AdminUserService adminUserService = new AdminUserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
    }

    @Override
    protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) {
        super.dealWithSessionAlive(request, response, session, out);
        long id = Tool.requestToLong(request,"id");
        long operator = Tool.transformSessionValueToLong(session,"id");
        int operator_type = Tool.transformSessionValueToInteger(session,"type");
        String nick_name = request.getParameter("nick_name");
        String email = request.getParameter("email");
        String motto = request.getParameter("motto");
        out.append(adminUserService.changeNickNameAndMotto(id,operator,operator_type,nick_name,email,motto));
    }

    @Override
    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) {
        super.dealWithSessionDead(request, response, session, out);
        out.append(SESSION_EXPIRED);
    }
}
