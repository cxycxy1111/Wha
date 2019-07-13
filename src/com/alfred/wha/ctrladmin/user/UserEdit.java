package com.alfred.wha.ctrladmin.user;

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

@WebServlet(name = "UserEdit",urlPatterns = "/admin/user/edit")
/**
 * http://localhost:8080/admin/user/edit/?id=1&nick_name=111&motto=111&email=cxycxy1111@gmail.com
 */
public class UserEdit extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService = new UserService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
    }

    @Override
    protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out, long current_user, int current_user_type) throws IOException {
        super.dealWithSessionAlive(request, response, session, out, current_user, current_user_type);
        String nick_name = request.getParameter("nick_name");
        String email = request.getParameter("email");
        String motto = request.getParameter("motto");
        long id = Tool.requestToLong(request,"id");
        out.append(userService.changeNickNameAndMotto(id,current_user,0, nick_name,email,motto));
    }

    @Override
    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) throws IOException {
        super.dealWithSessionDead(request, response, session, out);

    }
}
