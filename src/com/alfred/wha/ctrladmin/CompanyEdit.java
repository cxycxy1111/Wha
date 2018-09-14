package com.alfred.wha.ctrladmin;

import com.alfred.wha.serv.CompanyService;
import com.alfred.wha.util.Tool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CompanyEdit",urlPatterns = "/admin/company/edit")
/**
 * http://localhost:8080/admin/company/edit?id=1&name=腾讯计算机系统有限公司
 */
public class CompanyEdit extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private CompanyService companyService = new CompanyService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
    }

    @Override
    protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) {
        super.dealWithSessionAlive(request, response, session, out);
        out.append(companyService.changeName(Tool.requestToLong(request,"id"),
                Tool.transformSessionValueToLong(session,"id"),
                Tool.transformSessionValueToInteger(session,"type"),
                false,
                request.getParameter("name")));
    }

    @Override
    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) {
        super.dealWithSessionDead(request, response, session, out);
        out.append(SESSION_EXPIRED);
    }
}
