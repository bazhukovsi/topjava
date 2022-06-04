package ru.javawebinar.topjava.store;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MealsStore {
    private final Map<Integer, Meal> meals = new ConcurrentHashMap<>();
    private Integer caloriesPerDay = 2000;

    public MealsStore() {

        meals.put(1, new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0),
                "Завтрак", 400));
        meals.put(2, new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0),
                "Обед", 1000));
        meals.put(3, new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0),
                "Ужин", 500));
        meals.put(4, new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0),
                "Еда на граничное значение", 100));
        meals.put(5, new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0),
                "Завтрак", 1000));
        meals.put(6, new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0),
                "Обед", 500));
        meals.put(7, new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0),
                "Ужин", 410));

    }

    public Integer getCaloriesPerDay() {
        return caloriesPerDay;
    }

    public void setCaloriesPerDay(Integer caloriesPerDay) {
        this.caloriesPerDay = caloriesPerDay;
    }

    public List<Meal> findAll() {
        List<Meal> output = new ArrayList<>();
        for (Map.Entry<Integer, Meal> pair : meals.entrySet()) {
            output.add(pair.getValue());
        }
        return output;
    }
}
