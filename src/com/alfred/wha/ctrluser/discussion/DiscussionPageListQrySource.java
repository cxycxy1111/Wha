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

@WebServlet(name = "UserDiscussionPageListQrySource",urlPatterns = "/user/discussion/page/qry/source")
/**
 * http://localhost:8080/admin/timeline/qry/creator?creator=1&creator_type=1
 */
public class DiscussionPageListQrySource extends BaseServlet {
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
        int source = Tool.requestToInt(request,"source");
        int page_no = Tool.requestToInt(request,"page_no");
        out.append(discussionPageService.queryBySource(source,page_no));
    }

    @Override
    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) throws IOException {
        int source = Tool.requestToInt(request,"source");
        int page_no = Tool.requestToInt(request,"page_no");
        out.append(discussionPageService.queryBySource(source,page_no));
    }
}
