package com.alfred.wha.ctrladmin.cases;

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

@WebServlet(name = "CaseReject",urlPatterns = "/admin/case/reject")
/**
 * http://localhost:8080/admin/case/reject?id=1
 */
public class CaseReject extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private CaseService caseService = new CaseService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
    }

    @Override
    protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out, long current_user, int current_user_type) {
        super.dealWithSessionAlive(request, response, session, out, current_user, current_user_type);
        long id = Tool.requestToLong(request,"id");
        out.append(caseService.reject(id,current_user,current_user_type));
    }

    @Override
    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) {
        super.dealWithSessionDead(request, response, session, out);
        out.append(SESSION_EXPIRED);
    }
}
