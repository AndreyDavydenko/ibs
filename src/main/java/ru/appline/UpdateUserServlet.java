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

@WebServlet(urlPatterns = "/update")
public class UpdateUserServlet extends HttpServlet {
    Model model = Model.getInstance();
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

        int id = Integer.parseInt(jobject.get("id").getAsString());
        String name = jobject.get("name").getAsString();
        String surname = jobject.get("surname").getAsString();
        double salary = jobject.get("salary").getAsDouble();

        User user = new User(name, surname, salary);
        model.updateUser(user,id);

        response.setContentType("application/json; charset=utf-8");

        PrintWriter out = response.getWriter();
        out.print(model.getUsersJson());
    }
}
