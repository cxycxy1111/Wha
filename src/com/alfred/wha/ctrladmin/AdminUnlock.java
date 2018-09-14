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

@WebServlet(name = "AdminDelete",urlPatterns = "/admin/admin/unlock")
/**
 * localhost:8080/admin/admin/unlock?id=1
 */
public class AdminUnlock extends BaseServlet {

    private static final long serialVersionUID = 1L;

    private AdminUserService adminUserService = new AdminUserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("id") == null) {
        }else {

        }
    }

    @Override
    protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) {
        super.dealWithSessionAlive(request, response, session, out);
        long id = Tool.transformSessionValueToLong(session,"id");
        int type = Tool.transformSessionValueToInteger(session,"type");
        out.append(adminUserService.unlock(Tool.requestToLong(request,"id"),id,type));
        out.close();
    }

    @Override
    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) {
        super.dealWithSessionDead(request, response, session, out);
        out.append(SESSION_EXPIRED);
        out.close();
    }
}
