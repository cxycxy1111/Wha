package com.alfred.wha.ctrladmin.timeline;

import com.alfred.wha.serv.TimelineService;
import com.alfred.wha.util.BaseServlet;
import com.alfred.wha.util.Tool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "TimelineDelete",urlPatterns = "/admin/timeline/delete")
/**
 * http://localhost:8080/admin/timeline/delete?id=1
 */
public class TimelineDelete extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private TimelineService timelineService = new TimelineService();

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
        out.append(timelineService.delete(id,current_user,current_user_type));
    }

    @Override
    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) {
        super.dealWithSessionDead(request, response, session, out);

    }
}
