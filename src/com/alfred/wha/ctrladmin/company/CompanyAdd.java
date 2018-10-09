package com.alfred.wha.ctrladmin.company;

import com.alfred.wha.serv.CompanyService;
import com.alfred.wha.util.BaseServlet;
import com.alfred.wha.util.Tool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CompanyAdd",urlPatterns = "/admin/company/add")
/**
 * http://localhost:8080/admin/company/add?name=腾讯计算机系统有限公司
 */
public class CompanyAdd extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private CompanyService companyService = new CompanyService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
    }

    @Override
    protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out, long current_user, int current_user_type) throws IOException {
        super.dealWithSessionAlive(request, response, session, out, current_user, current_user_type);
        out.append(companyService.add(request.getParameter("name"),
                Tool.transformSessionValueToLong(session,"id"),0));
    }

    @Override
    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) {
        super.dealWithSessionDead(request, response, session, out);

    }
}
