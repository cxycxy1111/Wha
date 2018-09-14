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

@WebServlet(name = "AdminAdd",urlPatterns = "/admin/admin/add")
/**
 * http://localhost:8080/admin/admin/add?company=0&user_name=111&pwd=111&type=1&email=1023054095@qq&creator=0
 */
public class AdminAdd extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private AdminUserService adminUserService = new AdminUserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
    }

    @Override
    protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) {
        super.dealWithSessionAlive(request, response, session, out);
        long company_id = Tool.requestToLong(request,"company");
        String username = request.getParameter("user_name");
        String pwd = request.getParameter("pwd");
        int type = Tool.requestToInt(request,"type");
        String email = request.getParameter("email");
        long creator = Tool.requestToLong(request,"creator");
        out.append(adminUserService.add(company_id,username,pwd,type,email,creator));
    }

    @Override
    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) {
        super.dealWithSessionDead(request, response, session, out);
        out.append(SESSION_EXPIRED);
    }
}
