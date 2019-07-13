package com.alfred.wha.ctrladmin.page;

import com.alfred.wha.serv.PageService;
import com.alfred.wha.util.BaseServlet;
import com.alfred.wha.util.Tool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "AdminPageAdd",urlPatterns = "/admin/page/add")
/**
 * http://localhost:8080/admin/timeline/qry/creator?creator=1&creator_type=1
 */
public class PageAdd extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private PageService pageService = new PageService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
    }

    @Override
    protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out, long current_user, int current_user_type) throws IOException {
        long event_id = Tool.requestToLong(request,"event_id");
        String page_title = request.getParameter("title");
        String page_summary = request.getParameter("summary");
        String content = request.getParameter("content");
        String author = request.getParameter("author");
        String source = request.getParameter("source");
        String happent_time = request.getParameter("happen_time");
        String url = request.getParameter("url");
        out.append(pageService.addPage(event_id,page_title,page_summary,content,0,current_user,current_user_type,author,source,happent_time,url));
    }

    @Override
    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) throws IOException {
        super.dealWithSessionDead(request,response,session,out);
    }
}
