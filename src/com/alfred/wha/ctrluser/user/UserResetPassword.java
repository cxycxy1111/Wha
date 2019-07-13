package com.alfred.wha.ctrluser.user;

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

@WebServlet(name = "UserResetPassword",urlPatterns = "/user/user/resetPassword")
/**
 * http://localhost:8080/admin/user/edit/password?id=1&pwd=111
 */
public class UserResetPassword extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
    }

    @Override
    protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out, long current_user, int current_user_type)  throws IOException {
        super.dealWithSessionAlive(request, response, session, out, current_user, current_user_type);
        String pwd = request.getParameter("pwd");
        String user_name = request.getParameter("user_name");
        String email_address = request.getParameter("email_address");
        String str = userService.changePwdByUserNameAndEmail(user_name, email_address,1, pwd);
        System.out.println(str);
        out.append(str);

    }

    @Override
    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) throws IOException {
        String pwd = request.getParameter("pwd");
        String email_address = request.getParameter("email_address");
        String user_name = request.getParameter("user_name");
        out.append(userService.changePwdByUserNameAndEmail(user_name,email_address, 1, pwd));
    }
}
