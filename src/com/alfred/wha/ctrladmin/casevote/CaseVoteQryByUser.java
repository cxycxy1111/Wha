package com.alfred.wha.ctrladmin.casevote;

import com.alfred.wha.serv.CaseVoteService;
import com.alfred.wha.util.BaseServlet;
import com.alfred.wha.util.Tool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "EventSubscribeQryByEvent",urlPatterns = "/admin/casevote/qry/user")
/**
 * http://localhost:8080/admin/casevote/qry/user?id=1
 */
public class CaseVoteQryByUser extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private CaseVoteService caseVoteService = new CaseVoteService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
    }

    @Override
    protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out, long current_user, int current_user_type) {
        super.dealWithSessionAlive(request, response, session, out, current_user, current_user_type);
        long id = Tool.requestToLong(request,"id");
        int type = Tool.requestToInt(request,"type");
        out.append(caseVoteService.queryByCreator(id,type));
    }

    @Override
    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) {
        super.dealWithSessionDead(request, response, session, out);
        out.append(SESSION_EXPIRED);
    }
}
