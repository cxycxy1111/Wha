package com.alfred.wha.ctrladmin.admin;

import com.alfred.wha.serv.AdminUserService;
import com.alfred.wha.util.BaseServlet;
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
    protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out, long current_user, int current_user_type) {
        super.dealWithSessionAlive(request, response, session, out, current_user, current_user_type);
        long company_id = Tool.requestToLong(request,"company");
        String username = request.getParameter("user_name");
        String pwd = request.getParameter("pwd");
        int type = Tool.requestToInt(request,"type");
        String email = request.getParameter("email");
        String motto = request.getParameter("motto");
        String nick_name= request.getParameter("nick_name");
        long creator = Tool.transformSessionValueToLong(session,"id");
        out.append(adminUserService.add(company_id,username,pwd,type,email,creator,nick_name,motto));
    }

    @Override
    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) {
        super.dealWithSessionDead(request, response, session, out);
    }
}
