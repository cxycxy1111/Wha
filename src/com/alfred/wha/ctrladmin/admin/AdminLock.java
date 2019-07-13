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

@WebServlet(name = "AdminLock",urlPatterns = "/admin/admin/lock")
/**
 * localhost:8080/admin/admin/lock?id=1
 */
public class AdminLock extends BaseServlet {

    private static final long serialVersionUID = 1L;

    private AdminUserService adminUserService = new AdminUserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
    }

    @Override
    protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out, long current_user, int current_user_type) throws IOException {
        super.dealWithSessionAlive(request, response, session, out, current_user, current_user_type);
        out.append(adminUserService.lock(Tool.requestToLong(request,"id"),current_user,0));
    }

    @Override
    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) throws IOException {
        super.dealWithSessionDead(request, response, session, out);

    }
}
