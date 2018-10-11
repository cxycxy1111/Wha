package com.alfred.wha.ctrluser.cases;

import com.alfred.wha.serv.CaseService;
import com.alfred.wha.util.BaseServlet;
import com.alfred.wha.util.Tool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "UserCaseListQryRejected",urlPatterns = "/user/case/qry/rejected")
/**
 * http://localhost:8080/admin/case/qry/rejected?page_no=1
 */
public class CaseListQryRejected extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private CaseService caseService = new CaseService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
    }

    @Override
    protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out, long current_user, int current_user_type) throws IOException {
        super.dealWithSessionAlive(request, response, session, out, current_user, current_user_type);
        int page_no = Tool.requestToInt(request,"page_no");
        if (page_no > 0) {
            out.append(caseService.queryRejected(page_no,10));
        }
    }

    @Override
    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) {
        super.dealWithSessionDead(request, response, session, out);

    }
}