package com.alfred.wha.ctrluser.discussion;

import com.alfred.wha.serv.DiscussionTimelineService;
import com.alfred.wha.util.BaseServlet;
import com.alfred.wha.util.Tool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "UserDiscussionTimelineListQryTimelineId",urlPatterns = "/user/discussion/qry/timeline")
/**
 * http://localhost:8080/admin/timeline/qry/creator?creator=1&creator_type=1
 */
public class DiscussionTimelineListQryTimelineId extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private DiscussionTimelineService discussionTimelineService = new DiscussionTimelineService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
    }

    @Override
    protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out, long current_user, int current_user_type) throws IOException {
        long id = Tool.requestToLong(request,"id");
        int page_no = Tool.requestToInt(request,"page_no");
        out.append(discussionTimelineService.queryByTimeline(id,current_user,page_no,true));
    }

    @Override
    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) throws IOException {
        long timeline_id = Tool.requestToLong(request,"id");
        int page_no = Tool.requestToInt(request,"page_no");
        out.append(discussionTimelineService.queryByTimeline(timeline_id,0,page_no,false));
    }
}
