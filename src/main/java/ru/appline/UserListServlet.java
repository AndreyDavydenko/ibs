package ru.appline;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.appline.logic.Model;
import ru.appline.logic.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@WebServlet(urlPatterns = "/get")
public class UserListServlet extends HttpServlet {
    Model model = Model.getInstance();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=utf-8");
        PrintWriter out = response.getWriter();

        int id = Integer.parseInt(request.getParameter("id"));

        if (id == 0) {
            out.println("<html>" +
                    "<h3>Доступные пользователи</h3><br/>"+
                    "ID пользователя: " +
                    "<ul>");
            for (Map.Entry<Integer, User>entry : model.getFromList().entrySet()) {
                out.print("<li>"+entry.getKey()+"</li>"+
                        "<ul>"+
                        "<li>Имя: "+entry.getValue().getName()+"</li>"+
                        "<li>Фамилия: "+entry.getValue().getSurname()+"</li>"+
                        "<li>Зарплата: "+entry.getValue().getSalary()+"</li>"+
                        "</ul>");
            }
            out.print("</ul>"+
                    "<html>"+
                    "<a href = \"index.jsp\">На стартовую страницу</a>"+
                    "</html>");
        }

        else if (id > 0) {
            if (id > model.getFromList().size()) {
                out.print("<html>"+
                        "<h3>Пользователя с таким ID не существует</h3><br/>"+
                        "<a href = \"index.jsp\">На стартовую страницу</a>"+
                        "</html>");
            }
            else {
                out.print("<html>"+
                        "<h3>Пользователь:</h3><br/>"+
                        "Имя: "+model.getFromList().get(id).getName()+"<br/>"+
                        "Фамилия: "+model.getFromList().get(id).getSurname()+"<br/>"+
                        "Зарплата: "+model.getFromList().get(id).getSalary()+"<br/>"+
                        "<a href = \"index.jsp\">На стартовую страницу</a>"+
                        "</html>");

            }
        }
        else {
            out.print("<html>"+
                "<h3>ID должен быть больше 0</h3><br/>"+
                "<a href = \"index.jsp\">На стартовую страницу</a>"+
                "</html>");

        }


    }
}
