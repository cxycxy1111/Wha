package com.alfred.wha.ctrladmin.caseread;

import com.alfred.wha.serv.CaseReadService;
import com.alfred.wha.serv.UserService;
import com.alfred.wha.util.BaseServlet;
import com.alfred.wha.util.Tool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "CaseReadQryByUser",urlPatterns = "/admin/caseread/qry/user")
/**
 * http://localhost:8080/admin/user/qry/deleted
 */
public class CaseReadQryByUser extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private CaseReadService caseReadService = new CaseReadService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
    }

    @Override
    protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out, long current_user, int current_user_type) {
        super.dealWithSessionAlive(request, response, session, out, current_user, current_user_type);
        long user_id = Tool.requestToLong(request,"id");
        int user_type = Tool.requestToInt(request,"type");
        int page_no = Tool.requestToInt(request,"page_no");
        out.append(caseReadService.queryByUser(user_id,user_type,page_no,10));
    }

    @Override
    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) {
        super.dealWithSessionDead(request, response, session, out);

    }
}
