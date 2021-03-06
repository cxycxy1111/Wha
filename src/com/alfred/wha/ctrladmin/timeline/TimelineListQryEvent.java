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

@WebServlet(name = "TimelineListQryEvent",urlPatterns = "/admin/timeline/qry/event")
/**
 * http://localhost:8080/admin/timeline/qry/event?event=1
 */
public class TimelineListQryEvent extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private TimelineService timelineService = new TimelineService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
    }

    @Override
    protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out, long current_user, int current_user_type) throws IOException {
        super.dealWithSessionAlive(request, response, session, out, current_user, current_user_type);
        long event = Tool.requestToLong(request,"event");
        int page_no = Tool.requestToInt(request,"page_no");
        out.append(timelineService.queryByEvent(event,page_no,20));
    }

    @Override
    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) throws IOException {
        long event = Tool.requestToLong(request,"event");
        int page_no = Tool.requestToInt(request,"page_no");
        out.append(timelineService.queryByEvent(event,page_no,20));

    }
}
