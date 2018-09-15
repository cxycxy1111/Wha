package com.alfred.wha.util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class BaseServlet extends HttpServlet {

    public static final String prefix = "{\"status\":\"";
    public static final String id_prefix = "{\"id\":";
    public static final String datasuffix = "}";
    public static final String dataprefix = "{\"data\":";
    public static final String suffix = "\"}";
    public static final String NO_SUCH_RECORD = prefix + "no_such_record" + suffix;
    public static final String LOCKED = prefix + "locked" + suffix;
    public static final String FAIL =prefix +  "fail" + suffix;
    public static final String PARTLY_FAIL =prefix +  "partly_fail" + suffix;
    public static final String SUCCESS = prefix + "success" + suffix;
    public static final String DUPLICATE = prefix + "duplicate" + suffix;
    public static final String NOT_MATCH = prefix + "not_match" + suffix;
    public static final String ILLEGAL_INVOKE = prefix + "illegal_invoke" + suffix;
    public static final String QRY_RESULT_EMPTY = prefix + "empty" + suffix;
    public static final String SESSION_EXPIRED=prefix + "session_expired" + suffix;
    public static final String AUTHORIZE_FAIL=prefix + "authorize_fail" + suffix;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("id") != null) {
            long current_user = Tool.transformSessionValueToLong(session,"id");
            int current_user_type = Tool.transformSessionValueToInteger(session,"type");
            dealWithSessionAlive(request,response,session,out,current_user,current_user_type);
        }else {
            dealWithSessionDead(request,response,session,out);
        }
    }

    protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out, long current_user, int current_user_type) {
    }

    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) {

    }

}
