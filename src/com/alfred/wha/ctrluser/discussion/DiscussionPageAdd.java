package com.alfred.wha.ctrluser.discussion;

import com.alfred.wha.serv.DiscussionPageService;
import com.alfred.wha.util.BaseServlet;
import com.alfred.wha.util.Tool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "UserDiscussionPageAdd",urlPatterns = "/user/discussion/page/add")
/**
 * http://localhost:8080/admin/timeline/qry/creator?creator=1&creator_type=1
 */
public class DiscussionPageAdd extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private DiscussionPageService discussionPageService = new DiscussionPageService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
    }

    @Override
    protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out, long current_user, int current_user_type) throws IOException {
        long event_id = Tool.requestToLong(request,"event_id");
        long page_id = Tool.requestToLong(request,"reference_id");
        String summary = request.getParameter("summary");
        int source = Tool.requestToInt(request,"source");
        int anonymous = Tool.requestToInt(request,"anonymous");
        long parent_discussion_id = Tool.requestToLong(request,"parent_discussion_id");
        out.append(discussionPageService.add(page_id,parent_discussion_id,event_id,current_user,current_user_type,source,summary,anonymous));
    }

    @Override
    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) throws IOException {
        super.dealWithSessionDead(request,response,session,out);
    }
}
