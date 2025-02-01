package ru.appline.logic;

import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;

public class Model implements Serializable {
    private static final Model instance = new Model();
    private final Map<Integer, User> model;

    public static Model getInstance() {
        return instance;
    }
    private Model() {
        model = new HashMap<>();

        model.put(1, new User("Вася","Петров",100000));
        model.put(2, new User("Иван","Сидоров",55000));
        model.put(3, new User("Мария","Соколова",73000));
        model.put(4, new User("Светлана","Тихонова",65300));
    }
    public void add(User user, int id) {
        model.put(id, user);
    }
    public Map<Integer, User> getFromList() {
        return model;
    }

}
