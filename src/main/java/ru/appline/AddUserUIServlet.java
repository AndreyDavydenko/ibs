package ru.appline;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.appline.logic.Model;
import ru.appline.logic.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Double.parseDouble;

@WebServlet(urlPatterns = "/new_user")
public class AddUserUIServlet extends HttpServlet {
    private AtomicInteger counter = new AtomicInteger(5);
    Model model = Model.getInstance();


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String salaryParam = request.getParameter("salary");
        double salary = parseDouble(salaryParam);
        User user = new User(name, surname, salary);
        model.add(user, counter.incrementAndGet());
        out.println("<html>" +
                "<h3>Пользователь "+name+" "+surname+" с зарплатой "+salary+"руб. успешно создан</h3>"+
                "<a href=\"addUser.html\">Создать нового пользователя</a><br/>"+
                "<a href=\"index.jsp\">Домашняя страница</a>"+
                "</html>");
    }
}
