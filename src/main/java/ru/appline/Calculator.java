package ru.appline;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = "/calculator")
public class CalculatorServlet extends HttpServlet {

    private static final Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");

        // Чтение JSON из тела запроса
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        // Преобразование JSON в объект
        CalculatorInput input;
        try {
            input = gson.fromJson(sb.toString(), CalculatorInput.class);
        } catch (Exception e) {
            // Обработка ошибок парсинга JSON
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid JSON format");
            String jsonError = gson.toJson(errorResponse);
            try (PrintWriter out = response.getWriter()) {
                out.print(jsonError);
                out.flush();
            }
            return;
        }

        // Выполнение арифметической операции
        double result = 0;
        String operation = input.getMath();

        try {
            switch (operation) {
                case "+":
                    result = input.getA() + input.getB();
                    break;
                case "-":
                    result = input.getA() - input.getB();
                    break;
                case "*":
                    result = input.getA() * input.getB();
                    break;
                case "/":
                    if (input.getB() == 0) {
                        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        Map<String, String> errorResponse = new HashMap<>();
                        errorResponse.put("error", "Division by zero");
                        String jsonError = gson.toJson(errorResponse);
                        try (PrintWriter out = response.getWriter()) {
                            out.print(jsonError);
                            out.flush();
                        }
                        return;
                    }
                    result = input.getA() / input.getB();
                    break;
                default:
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    Map<String, String> errorResponse = new HashMap<>();
                    errorResponse.put("error", "Invalid operation: " + operation);
                    String jsonError = gson.toJson(errorResponse);
                    try (PrintWriter out = response.getWriter()) {
                        out.print(jsonError);
                        out.flush();
                    }
                    return;
            }
        } catch (Exception e) {
             response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
             Map<String, String> errorResponse = new HashMap<>();
             errorResponse.put("error", "Internal server error during calculation: " + e.getMessage());
             String jsonError = gson.toJson(errorResponse);
             try (PrintWriter out = response.getWriter()) {
                 out.print(jsonError);
                 out.flush();
             }
             return;
        }


        // Формирование JSON-ответа
        Map<String, Double> responseMap = new HashMap<>();
        responseMap.put("result", result);
        String jsonResult = gson.toJson(responseMap);

        // Отправка JSON-ответа
        try (PrintWriter out = response.getWriter()) {
            out.print(jsonResult);
            out.flush();
        }
    }

    // Класс для представления входных данных JSON
    private static class CalculatorInput {
        private double a;
        private double b;
        private String math;

        public double getA() {
            return a;
        }

        public double getB() {
            return b;
        }

        public String getMath() {
            return math;
        }
    }
}