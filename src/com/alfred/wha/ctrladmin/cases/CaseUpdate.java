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

@WebServlet(name = "CaseUpdate",urlPatterns = "/admin/case/update")
/**
 * http://localhost:8080/admin/case/update?id=1
 */
public class CaseUpdate extends BaseServlet {
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
        long event_id = Tool.requestToLong(request,"event");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        out.append(caseService.update(true,id,event_id,current_user,current_user_type,title,content));
    }

    @Override
    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) {
        super.dealWithSessionDead(request, response, session, out);

    }
}
