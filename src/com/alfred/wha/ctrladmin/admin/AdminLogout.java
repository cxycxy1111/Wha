package com.alfred.wha.ctrladmin.admin;

import com.alfred.wha.util.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "AdminLogout",urlPatterns = "/admin/admin/logout")
// http:localhost:8080/admin/admin/logout
public class AdminLogout extends BaseServlet {

    private static final long serialVersionUID = 1L;

    protected void doPoAdminLogoutst(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
    }

    @Override
    protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out, long current_user, int current_user_type) {
        super.dealWithSessionAlive(request, response, session, out, current_user, current_user_type);
        session.invalidate();
        out.append(SUCCESS);
    }

    @Override
    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) {
        out.append(SUCCESS);
    }
}
