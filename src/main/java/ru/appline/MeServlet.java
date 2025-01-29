package ru.appline;

import jakarta.servlet.http.HttpServlet;

import java.io.*;

import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "meServlet", value = "/me-servlet")
public class MeServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "ololo";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("Моя заглушка");
    }

    public void destroy() {
    }
}
