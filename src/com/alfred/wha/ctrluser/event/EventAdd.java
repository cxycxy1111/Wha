package com.alfred.wha.ctrluser.event;

import com.alfred.wha.serv.EventService;
import com.alfred.wha.util.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "UserEventAdd",urlPatterns = "/user/event/add")
/**
 * http://localhost:8080/admin/event/add?title=111&happen_time=2018-09-26 00:00:00
 */
public class EventAdd extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private EventService eventService = new EventService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
    }

    @Override
    protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out, long current_user, int current_user_type) throws IOException {
        super.dealWithSessionAlive(request, response, session, out, current_user, current_user_type);
        String title = request.getParameter("title");
        String happen_time = request.getParameter("happen_time");
        out.append(eventService.add(title,true,current_user,0,happen_time));
    }

    @Override
    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) {
        super.dealWithSessionDead(request, response, session, out);

    }
}