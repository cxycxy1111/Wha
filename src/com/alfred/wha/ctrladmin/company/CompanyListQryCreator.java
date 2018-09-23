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

@WebServlet(name = "CompanyListQryCreator",urlPatterns = "/admin/company/qry/creator")
/**
 * http://localhost:8080/admin/company/qry/creator?id=111&creator_type=0&page_no=1
 */
public class CompanyListQryCreator extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private CompanyService companyService = new CompanyService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
    }

    @Override
    protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out, long current_user, int current_user_type) {
        super.dealWithSessionAlive(request, response, session, out, current_user, current_user_type);
        long creator = Tool.requestToLong(request,"id");
        int creator_type = Tool.requestToInt(request,"creator_type");
        int page_no = Tool.requestToInt(request,"page_no");
        out.append(companyService.queryByCreator(creator,creator_type,page_no,10));
    }

    @Override
    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) {
        super.dealWithSessionDead(request, response, session, out);

    }
}
