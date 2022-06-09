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

    {
        save(new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0),
                "Завтрак", 400));
        save(new Meal(2, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0),
                "Обед", 1000));
        save(new Meal(3, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0),
                "Ужин", 500));
        save(new Meal(4, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0),
                "Еда на граничное значение", 100));
        save(new Meal(5, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0),
                "Завтрак", 1000));
        save(new Meal(6, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0),
                "Обед", 500));
        save(new Meal(7, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0),
                "Ужин", 410));
    }

    public Collection<Meal> findAll() {
        return meals.values();
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(ids.incrementAndGet());
        }
        return meals.put(meal.getId(), meal);
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
