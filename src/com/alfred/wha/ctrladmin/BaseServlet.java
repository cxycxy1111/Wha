package com.alfred.wha.ctrladmin;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class BaseServlet extends HttpServlet {

    public static final String prefix = "{\"status\":\"";
    static final String id_prefix = "{\"id\":";
    static final String datasuffix = "}";
    static final String dataprefix = "{\"data\":";
    static final String suffix = "\"}";
    static final String NO_SUCH_RECORD = prefix + "no_such_record" + suffix;
    static final String LOCKED = prefix + "locked" + suffix;
    static final String FAIL =prefix +  "fail" + suffix;
    static final String PARTLY_FAIL =prefix +  "partly_fail" + suffix;
    static final String SUCCESS = prefix + "success" + suffix;
    static final String DUPLICATE = prefix + "duplicate" + suffix;
    static final String NOT_MATCH = prefix + "not_match" + suffix;
    static final String QRY_RESULT_EMPTY = prefix + "empty" + suffix;
    static final String SESSION_EXPIRED=prefix + "session_expired" + suffix;
    static final String AUTHORIZE_FAIL=prefix + "authorize_fail" + suffix;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("id") != null) {
            dealWithSessionAlive(request,response,session,out);
        }else {
            dealWithSessionDead(request,response,session,out);
        }
    }

    protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) {
    }

    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) {

    }

}
