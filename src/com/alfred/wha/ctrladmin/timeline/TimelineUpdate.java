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

@WebServlet(name = "TimelineUpdate",urlPatterns = "/admin/timeline/edit")
/**
 * http://localhost:8080/admin/timeline/edit?event=0&title=111&content=111&happen_time=2018-06-30 18:30:00
 */
public class TimelineUpdate extends BaseServlet {
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
        long id = Tool.requestToLong(request,"id");
        String summary = request.getParameter("summary");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String happen_time = request.getParameter("happen_time");
        out.append(timelineService.update(true,id,current_user,0,title,summary,content,happen_time));
    }

    @Override
    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) throws IOException {
        super.dealWithSessionDead(request, response, session, out);

    }
}
