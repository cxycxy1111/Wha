package com.alfred.wha.ctrluser.timeline;

import com.alfred.wha.serv.TimelineUserFavouriteService;
import com.alfred.wha.util.BaseServlet;
import com.alfred.wha.util.Tool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "TimelineFavouriteDelete",urlPatterns = "/user/timeline/favourite/delete")
/**
 * http://localhost:8080/admin/timeline/qry/creator?creator=1&creator_type=1
 */
public class TimelineFavouriteDelete extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private TimelineUserFavouriteService timelineUserFavouriteService = new TimelineUserFavouriteService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
    }

    @Override
    protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out, long current_user, int current_user_type) throws IOException {
        long timeline_id = Tool.requestToLong(request,"timeline_id");
        out.append(timelineUserFavouriteService.deleteFavouriteTimeline(current_user,timeline_id));
    }

    @Override
    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) throws IOException {
        super.dealWithSessionDead(request,response,session,out);
    }
}
