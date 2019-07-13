package com.alfred.wha.ctrladmin.suggestion;

import com.alfred.wha.serv.SuggestionService;
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

@WebServlet(name = "SuggestionAdd",urlPatterns = "/admin/suggestion/add")
/**
 * http://localhost:8080/admin/timeline/add?event=0&title=111&content=111&happen_time=2018-06-30 18:30:00
 */
public class SuggestionAdd extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private SuggestionService suggestionService = new SuggestionService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
    }

    @Override
    protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out, long current_user, int current_user_type) throws IOException {
        super.dealWithSessionAlive(request, response, session, out, current_user, current_user_type);

    }

    @Override
    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) throws IOException {
        String str = request.getParameter("content");
        try {
            out.append(suggestionService.add(str));
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
