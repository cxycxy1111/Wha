package com.alfred.wha.ctrladmin.admin;

import com.alfred.wha.serv.AdminUserService;
import com.alfred.wha.util.BaseServlet;
import com.alfred.wha.util.Tool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "AdminListQryLocked",urlPatterns = "/admin/admin/qry/locked")
/**
 * http://localhost:8080/admin/admin/qry/locked?page_no=1
 */
public class AdminListQryLocked extends BaseServlet {

    private AdminUserService adminUserService = new AdminUserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
    }

    @Override
    protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out, long current_user, int current_user_type) throws IOException {
        super.dealWithSessionAlive(request, response, session, out, current_user, current_user_type);
        int page_no = Tool.requestToInt(request,"page_no");
        if (page_no > 0) {
            out.append(adminUserService.queryLocked(page_no,10));
        }
    }

    @Override
    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) throws IOException {
        super.dealWithSessionDead(request, response, session, out);
    }
}
