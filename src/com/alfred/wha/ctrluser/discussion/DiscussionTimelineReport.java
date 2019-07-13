package com.alfred.wha.ctrluser.discussion;

import com.alfred.wha.serv.DiscussionPageService;
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

@WebServlet(name = "UserDiscussionTimelineReport",urlPatterns = "/user/discussion/timeline/report")
/**
 * http://localhost:8080/admin/timeline/qry/creator?creator=1&creator_type=1
 */
public class DiscussionTimelineReport extends BaseServlet {
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

        long discussion_id = Tool.requestToLong(request,"discussion_id");
        int type = Tool.requestToInt(request,"type");
        out.append(discussionTimelineService.report(discussion_id,type));
    }

    @Override
    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) throws IOException {
        long discussion_id = Tool.requestToLong(request,"discussion_id");
        int type = Tool.requestToInt(request,"type");
        out.append(discussionTimelineService.report(discussion_id,type));
    }
}
