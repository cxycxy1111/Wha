package com.alfred.wha.ctrladmin.event;

import com.alfred.wha.serv.EventService;
import com.alfred.wha.util.BaseServlet;
import com.alfred.wha.util.Tool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "EventListQryCreator",urlPatterns = "/admin/event/qry/creator")
/**
 * http://localhost:8080/admin/event/qry/creator?creator=1&creator_type=1
 */
public class EventListQryCreator extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private EventService eventService = new EventService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
    }

    @Override
    protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out, long current_user, int current_user_type) {
        super.dealWithSessionAlive(request, response, session, out, current_user, current_user_type);
        long creator = Tool.requestToLong(request,"creator");
        int creator_type = Tool.requestToInt(request,"creator_type");
        int page_no = Tool.requestToInt(request,"page_no");
        out.append(eventService.queryByCreator(creator,creator_type,page_no,10));
    }

    @Override
    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) {
        super.dealWithSessionDead(request, response, session, out);

    }
}
