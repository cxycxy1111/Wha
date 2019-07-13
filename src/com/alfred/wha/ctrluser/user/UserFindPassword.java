package com.alfred.wha.ctrluser.user;

import com.alfred.wha.serv.ResetPasswordService;
import com.alfred.wha.serv.UserService;
import com.alfred.wha.util.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "UserFindPassword",urlPatterns = "/user/user/findPassword")
/**
 * http://localhost:8080/admin/user/lock?id=1
 */
public class UserFindPassword extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private ResetPasswordService resetPasswordService = new ResetPasswordService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
    }

    @Override
    protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out, long current_user, int current_user_type) throws IOException{
        super.dealWithSessionAlive(request, response, session, out, current_user, current_user_type);

    }

    @Override
    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) throws IOException {
        //super.dealWithSessionDead(request, response, session, out);
        out.append(resetPasswordService.resetPasswordCodeInit(request.getParameter("email_address")));
    }
}
