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

@WebServlet(name = "TimelineListQryFull",urlPatterns = "/user/timeline/qry/full")
/**
 * http://localhost:8080/admin/timeline/qry/creator?creator=1&creator_type=1
 */
public class TimelineListQryFull extends BaseServlet {
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
        int page_no = Tool.requestToInt(request,"page_no");
        String type = request.getParameter("type");
        out.append(timelineService.queryFull(page_no));
        out.append(timelineService.queryFull(page_no));
    }

    @Override
    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) throws IOException {
        int page_no = Tool.requestToInt(request,"page_no");
        String type = request.getParameter("type");
        out.append(timelineService.queryFull(page_no));
    }
}
