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

@WebServlet(name = "AdminEditPassword",urlPatterns = "/admin/admin/edit/password")
/**
 * http://localhost:8080/admin/admin/edit/password?id=1&pwd=111
 */
public class AdminEditPassword extends BaseServlet {
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
        String pwd = request.getParameter("pwd");
        out.append(adminUserService.changePwd(id,operator,operator_type,pwd));
    }

    @Override
    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) {
        super.dealWithSessionDead(request, response, session, out);
        out.append(SESSION_EXPIRED);
    }
}
