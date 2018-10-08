package com.alfred.wha.ctrladmin.cases;

import com.alfred.wha.serv.AdminUserService;
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

@WebServlet(name = "CaseAdd",urlPatterns = "/admin/case/add")
/**
 * http://localhost:8080/admin/case/add?event=0&title=111&content=111
 */
public class CaseAdd extends BaseServlet {
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
        long event = Tool.requestToLong(request,"event");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        out.append(caseService.add(true,event,title,content,current_user,0));
    }

    @Override
    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) {
        super.dealWithSessionDead(request, response, session, out);

    }
}
