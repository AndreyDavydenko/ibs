package ru.appline.logic;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;


public class Model implements Serializable {
    private static final Model instance = new Model();
    private final Map<Integer, String> model;
    private final Gson gson = new Gson();

    public static Model getInstance() {
        return instance;
    }
    private Model() {
        model = new HashMap<>();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        model.put(1, gson.toJson(new User("Вася","Петров",100000)));
        model.put(2, gson.toJson(new User("Иван","Сидоров",55000)));
        model.put(3, gson.toJson(new User("Мария","Соколова",73000)));
        model.put(4, gson.toJson(new User("Светлана","Тихонова",65300)));
    }
    public void add(User user, int id) {
        model.put(id, gson.toJson(user));
    }

    public void deleteUser(int id) {model.remove(id); }

    public Map<Integer, String> getFromList() {
        return model;
    }

    public Map<Integer, User> getUsers() {
        Map<Integer, User> userMap = new HashMap<>();
        for (Map.Entry<Integer, String> entry : model.entrySet()) {
            try {
                User user = gson.fromJson(entry.getValue(), User.class);
                userMap.put(entry.getKey(), user);
            } catch (Exception e) {
                // Handle JSON parsing error if needed
                System.err.println("Error parsing JSON for ID " + entry.getKey() + ": " + e.getMessage());
            }
        }
        return userMap;
    }

    public static class UserWithId {
        private int id;
        private User user;

        public UserWithId(int id, User user) {
            this.id = id;
            this.user = user;
        }

        public int getId() {
            return id;
        }

        public User getUser() {
            return user;
        }
    }

    public String getUsersJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<Integer, User> userMap = new HashMap<>();
        for (Map.Entry<Integer, String> entry : model.entrySet()) {
            try {
                User user = gson.fromJson(entry.getValue(), User.class);
                userMap.put(entry.getKey(), user);
            } catch (Exception e) {
                // Handle JSON parsing error if needed
                System.err.println("Error parsing JSON for ID " + entry.getKey() + ": " + e.getMessage());
            }
        }

        Map<Integer, UserWithId> usersWithId = new HashMap<>();
        for(Map.Entry<Integer, User> userEntry : userMap.entrySet()){
            usersWithId.put(userEntry.getKey(), new UserWithId(userEntry.getKey(), userEntry.getValue()));
        }

        return gson.toJson(usersWithId);
    }

    public String getUserJson(int id) {
        return model.get(id);
    }

}
