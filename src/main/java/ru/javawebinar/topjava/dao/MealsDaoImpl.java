package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealsDaoImpl implements MealsDao {
    private final Map<Integer, Meal> meals = new ConcurrentHashMap<>();
    public final AtomicInteger ids = new AtomicInteger(7);
    private Integer caloriesPerDay = 2000;

    public MealsDaoImpl() {
    }

    public void init() {
        meals.put(1, new Meal(1,LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0),
                "Завтрак", 400));
        meals.put(2, new Meal(2,LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0),
                "Обед", 1000));
        meals.put(3, new Meal(3,LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0),
                "Ужин", 500));
        meals.put(4, new Meal(4,LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0),
                "Еда на граничное значение", 100));
        meals.put(5, new Meal(5,LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0),
                "Завтрак", 1000));
        meals.put(6, new Meal(6,LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0),
                "Обед", 500));
        meals.put(7, new Meal(7,LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0),
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

    @Override
    public void add(Meal meal) {
        meal.setId(ids.incrementAndGet());
        meals.put(meal.getId(), meal);
    }

    @Override
    public Meal findById(int id) {
        return meals.get(id);
    }

    @Override
    public void update(int id, Meal meal) {
        meals.replace(meal.getId(), meal);

    }

    @Override
    public void delete(int id) {
        meals.remove(id);
    }
}
