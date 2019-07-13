package com.alfred.wha.ctrladmin.util;

import com.alfred.wha.util.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

@WebServlet(name = "IconLoad",urlPatterns = "/picload")
public class IconLoad extends BaseServlet {

    private static final String content_type_jpeg = "image/jpeg";
    private static final String content_type_bmp = "image/bmp";
    private static final String content_type_png = "image/png";
    private static final String content_type_tiff = "image/tiff";
    private static final String content_type_gif = "image/gif";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doPost(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
    }

    @Override
    protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out, long current_user, int current_user_type) throws IOException {
        super.dealWithSessionAlive(request, response, session, out, current_user, current_user_type);
        String name = request.getParameter("imgName");
        String suffix = name.substring(name.lastIndexOf(".") + 1);
        FileInputStream inputStream = new FileInputStream("/usr/local/img/casetrace/icon"+name);
        int i = inputStream.available();
        //byte数组用于存放图片字节数据
        byte[] buff = new byte[i];
        inputStream.read(buff);
        //记得关闭输入流
        inputStream.close();
        //设置发送到客户端的响应内容类型
        if (suffix.equals("jpg")) {
            response.setContentType(content_type_jpeg);
        }else if (suffix.equals("jpeg")) {
            response.setContentType(content_type_jpeg);
        }else if (suffix.equals("png")) {
            response.setContentType(content_type_png);
        }else if (suffix.equals("bmp")) {
            response.setContentType(content_type_bmp);
        }else if (suffix.equals("gif")) {
            response.setContentType(content_type_gif);
        }else if (suffix.equals("tiff")) {
            response.setContentType(content_type_tiff);
        }
        response.setHeader("Content-Length" ,i +"");
        OutputStream o = response.getOutputStream();
        o.write(buff);
        //关闭响应输出流
        o.close();
    }

    @Override
    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) throws IOException {
        super.dealWithSessionDead(request, response, session, out);
    }
}
