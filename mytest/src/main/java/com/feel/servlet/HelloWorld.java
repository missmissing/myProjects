package com.feel.servlet;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name="exampleServlet", urlPatterns="/servlet/example")
public class HelloWorld extends HttpServlet {
    private int a = 0;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("Hello World. " + (a++));
        destroy();
    }

    @Override
    public void init() throws ServletException {
        System.out.println("init");
        super.init();
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        System.out.println("service" + req.hashCode() + "|" + res.hashCode());
        super.service(req, res);
    }

    @Override
    public void destroy() {
        System.out.println("destroy");
        super.destroy();
    }
}
