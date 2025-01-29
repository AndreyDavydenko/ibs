package ru.appline;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.appline.logic.Model;
import ru.appline.logic.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.atomic.AtomicInteger;

@WebServlet(urlPatterns = "/add")
public class AddUserServlet extends HttpServlet {
    private AtomicInteger counter = new AtomicInteger(4);
    Model model = Model.getInstance();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException,ServletException, IOException {
//        response.setContentType("text/html;charset=UTF-8");
//        request.setCharacterEncoding("UTF-8");
//        PrintWriter out = response.getWriter();
//        String name = request.getParameter("name");
//        String surname = request.getParameter("surname");
//        Double salary = Double.parseDouble(request.getParameter("salary"));
//        User user = new User(name, surname, salary);
//        model.add(user, counter.incrementAndGet());
//        out.println("<html>" +
//                "<h3>Пользователь "+name+" "+surname+" с зарплатой "+salary+"руб. успешно создан</h3>"+
//                "<a href=\"addUser.html\">Создать нового пользователя</a><br/>"+
//                "<a href=\"index.jsp\">Домашняя страница</a>"+
//                "</html>");
//    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuffer sb = new StringBuffer();
        String line;
        try {
        BufferedReader reader = request.getReader();
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }}
        catch (IOException e) {
            System.out.println("Error, please try again later");
        }

        JsonObject jobject = gson.fromJson(String.valueOf(sb), JsonObject.class);

        request.setCharacterEncoding("UTF-8");

        String name = jobject.get("name").getAsString();
        String surname = jobject.get("surname").getAsString();
        double salary = jobject.get("salary").getAsDouble();

        User user = new User(name, surname, salary);
        model.add(user, counter.getAndIncrement());

        response.setContentType("application/json; charset=utf-8");

        PrintWriter out = response.getWriter();
        out.print(gson.toJson(model.getFromList()));
    }


}
