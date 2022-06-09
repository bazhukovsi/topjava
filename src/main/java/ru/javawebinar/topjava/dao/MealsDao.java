package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import java.util.Collection;

public interface MealsDao {
    Collection<Meal> findAll();
    Meal save(Meal meal);
    Meal findById(int id);
    void update(int id, Meal meal);
    void delete(int id);
}
