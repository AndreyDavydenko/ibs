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
import java.util.Map;

@WebServlet(urlPatterns = "/get")
public class UserListServlet extends HttpServlet {
    Model model = Model.getInstance();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();


//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.setContentType("text/html; charset=utf-8");
//        PrintWriter out = response.getWriter();
//
//        int id = Integer.parseInt(request.getParameter("id"));
//
//        if (id == 0) {
//            out.println("<html>" +
//                    "<h3>Доступные пользователи</h3><br/>"+
//                    "ID пользователя: " +
//                    "<ul>");
//            for (Map.Entry<Integer, User>entry : model.getFromList().entrySet()) {
//                out.print("<li>"+entry.getKey()+"</li>"+
//                        "<ul>"+
//                        "<li>Имя: "+entry.getValue().getName()+"</li>"+
//                        "<li>Фамилия: "+entry.getValue().getSurname()+"</li>"+
//                        "<li>Зарплата: "+entry.getValue().getSalary()+"</li>"+
//                        "</ul>");
//            }
//            out.print("</ul>"+
//                    "<html>"+
//                    "<a href = \"index.jsp\">На стартовую страницу</a>"+
//                    "</html>");
//        }
//
//        else if (id > 0) {
//            if (id > model.getFromList().size()) {
//                out.print("<html>"+
//                        "<h3>Пользователя с таким ID не существует</h3><br/>"+
//                        "<a href = \"index.jsp\">На стартовую страницу</a>"+
//                        "</html>");
//            }
//            else {
//                out.print("<html>"+
//                        "<h3>Пользователь:</h3><br/>"+
//                        "Имя: "+model.getFromList().get(id).getName()+"<br/>"+
//                        "Фамилия: "+model.getFromList().get(id).getSurname()+"<br/>"+
//                        "Зарплата: "+model.getFromList().get(id).getSalary()+"<br/>"+
//                        "<a href = \"index.jsp\">На стартовую страницу</a>"+
//                        "</html>");
//
//            }
//        }
//        else {
//            out.print("<html>"+
//                "<h3>ID должен быть больше 0</h3><br/>"+
//                "<a href = \"index.jsp\">На стартовую страницу</a>"+
//                "</html>");
//
//        }
//
//
//    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json; charset=utf-8");

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
        PrintWriter out = response.getWriter();

        int id = jobject.get("id").getAsInt();

        if (id == 0) {
            out.println("Доступные пользователи:");
            for (Map.Entry<Integer, User>entry : model.getFromList().entrySet()) {
                out.print(entry.getKey()+
                        " Имя: "+entry.getValue().getName()+
                        " Фамилия: "+entry.getValue().getSurname()+
                        " Зарплата: "+entry.getValue().getSalary());
            }
        }

        else if (id > 0) {
            if (id > model.getFromList().size()) {
                out.print("Пользователя с таким ID не существует");
            }
            else {
                out.println("Пользователь:"+
                        " Имя: "+model.getFromList().get(id).getName()+
                        " Фамилия: "+model.getFromList().get(id).getSurname()+
                        " Зарплата: "+model.getFromList().get(id).getSalary());

            }
        }
        else {
            out.print("ID должен быть больше 0");
        }
    }


}
