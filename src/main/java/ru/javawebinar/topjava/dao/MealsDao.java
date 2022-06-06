package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import java.util.List;

public interface MealsDao {
    public List<Meal> findAll();

    public void add(Meal meal);

    public Meal findById(int id);

    public void update(int id, Meal meal);

    public void delete(int id);
}
