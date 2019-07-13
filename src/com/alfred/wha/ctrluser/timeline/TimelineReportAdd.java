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
import java.sql.SQLException;

@WebServlet(name = "TimelineReportAdd",urlPatterns = "/user/timeline/report/add")
/**
 * http://localhost:8080/admin/timeline/add?event=0&title=111&content=111&happen_time=2018-06-30 18:30:00
 */
public class TimelineReportAdd extends BaseServlet {
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
        long timeline = Tool.requestToLong(request,"timeline");
        int type = Tool.requestToInt(request,"type");
        int content_type = Tool.requestToInt(request,"content_type");
        try {
            out.append(timelineService.addReport(timeline,content_type,type));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) throws IOException {
        long timeline = Tool.requestToLong(request,"timeline");
        int type = Tool.requestToInt(request,"type");
        int content_type = Tool.requestToInt(request,"content_type");
        try {
            out.append(timelineService.addReport(timeline,content_type,type));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
