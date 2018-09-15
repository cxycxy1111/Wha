package com.alfred.wha.ctrladmin.eventsubscribe;

import com.alfred.wha.serv.EventSubscribeService;
import com.alfred.wha.util.BaseServlet;
import com.alfred.wha.util.Tool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "EventSubscribeQryByEvent",urlPatterns = "/admin/eventsubscribe/qry/user")
/**
 * http://localhost:8080/admin/eventsubscribe/qry/user?id=1&type=1
 */
public class EventSubscribeQryByUser extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private EventSubscribeService eventSubscribeService = new EventSubscribeService();

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
        out.append(eventSubscribeService.queryByUser(id,type));
    }

    @Override
    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) {
        super.dealWithSessionDead(request, response, session, out);
        out.append(SESSION_EXPIRED);
    }
}
