package com.alfred.wha.ctrladmin;

import com.alfred.wha.serv.ProductService;
import com.alfred.wha.util.Tool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ProductEdit",urlPatterns = "/admin/product/edit")
/**
 * http://localhost:8080/admin/product/edit?id=1&name=微信?company=1
 */
public class ProductEdit extends BaseServlet {
    private static final long serialVersionUID = 1L;
    private ProductService productService = new ProductService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        super.doGet(request,response);
    }

    @Override
    protected void dealWithSessionAlive(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) {
        super.dealWithSessionAlive(request, response, session, out);
        out.append(productService.changeName(Tool.requestToLong(request,"id"),Tool.transformSessionValueToLong(session,"id"),
                Tool.transformSessionValueToInteger(session,"type"),Tool.requestToLong(request,"company"),request.getParameter("name")));
    }

    @Override
    protected void dealWithSessionDead(HttpServletRequest request, HttpServletResponse response, HttpSession session, PrintWriter out) {
        super.dealWithSessionDead(request, response, session, out);
        out.append(SESSION_EXPIRED);
    }
}