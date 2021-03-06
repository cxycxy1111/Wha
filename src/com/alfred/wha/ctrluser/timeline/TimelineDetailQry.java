package com.alfred.wha.ctrluser.timeline;

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

@WebServlet(name = "UserTimelineDetailQry",urlPatterns = "/user/timeline/qry/detail")
/**
 * http://localhost:8080/admin/timeline/qry/detail?id=1
 */
public class TimelineDetailQry extends BaseServlet {
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
        super.dealWithSessionAlive(request, response, session, out, current_user, 0);
        long id = Tool.requestToLong(request,"id");
        out.append(timelineService.queryByTimeline(id,current_user,true));
    }

    @Override
    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) throws IOException {
        long id = Tool.requestToLong(request,"id");
        out.append(timelineService.queryByTimeline(id,0,false));
    }
}
