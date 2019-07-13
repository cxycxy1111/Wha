package com.alfred.wha.ctrluser.page;

import com.alfred.wha.serv.PageUserFavouriteService;
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

@WebServlet(name = "PageFavouriteAdd",urlPatterns = "/user/page/favourite/add")
/**
 * http://localhost:8080/admin/timeline/qry/creator?creator=1&creator_type=1
 */
public class PageFavouriteAdd extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private PageUserFavouriteService pageUserFavouriteService = new PageUserFavouriteService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
    }

    @Override
    protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out, long current_user, int current_user_type) throws IOException {
        long timeline_id = Tool.requestToLong(request,"page");
        String operate = request.getParameter("operate");
        String result = "";
        if (operate.equals("add") ) {
            out.append(pageUserFavouriteService.addFavouriteTimeline(current_user,timeline_id));
        }else  {
            out.append(pageUserFavouriteService.deleteFavouriteTimeline(current_user,timeline_id));
        }

    }

    @Override
    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) throws IOException {
        super.dealWithSessionDead(request,response,session,out);
    }
}
