package com.alfred.wha.ctrluser.eventsubscribe;

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

@WebServlet(name = "UserEventSubscribeQryByEvent",urlPatterns = "/user/eventsubscribe/qry/case")
/**
 * http://localhost:8080/admin/eventsubscribe/qry/case?id=1
 */
public class EventSubscribeQryByEvent extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private EventSubscribeService eventSubscribeService = new EventSubscribeService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
    }

    @Override
    protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out, long current_user, int current_user_type) throws IOException {
        super.dealWithSessionAlive(request, response, session, out, current_user, current_user_type);
        long event = Tool.requestToLong(request,"id");
        int page_no = Tool.requestToInt(request,"page_no");
        out.append(eventSubscribeService.queryByEvent(event,page_no,10));
    }

    @Override
    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) {
        super.dealWithSessionDead(request, response, session, out);

    }
}
