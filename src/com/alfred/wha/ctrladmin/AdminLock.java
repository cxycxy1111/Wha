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
    protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) {
        super.dealWithSessionAlive(request, response, session, out);
        if (session == null || session.getAttribute("id") == null) {
            out.append(SESSION_EXPIRED);
        }else {
            long id = Tool.transformSessionValueToLong(session,"id");
            int type = Tool.transformSessionValueToInteger(session,"type");
            out.append(adminUserService.lock(Tool.requestToLong(request,"id"),id,type));
        }
    }

    @Override
    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) {
        super.dealWithSessionDead(request, response, session, out);
        out.append(SESSION_EXPIRED);
    }
}
