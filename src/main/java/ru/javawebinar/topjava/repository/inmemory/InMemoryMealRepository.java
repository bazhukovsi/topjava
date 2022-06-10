package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository.USER_ID_ONE;
import static ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository.USER_ID_TWO;
import static ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository.ADMIN_ID;

@Repository
public class InMemoryMealRepository implements MealRepository {
    // Map<userId -> Map<mealId, ,meal>>
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);
    public static final Comparator<Meal> MEAL_COMPARATOR
            = (m1, m2) -> m2.getDateTime().compareTo(m1.getDateTime());

    {
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0),
                "Завтрак", 10), USER_ID_ONE);
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 25, 10, 0),
                "Завтрак", 20), USER_ID_ONE);
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 20, 13, 0),
                "Обед", 30), USER_ID_ONE);
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0),
                "Ужин", 10), USER_ID_TWO);
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0),
                "Еда на граничное значение", 10), USER_ID_TWO);
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0),
                "Завтрак", 10), USER_ID_TWO);
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0),
                "Обед", 10), ADMIN_ID);
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0),
                "Ужин", 10), ADMIN_ID);
    }

    @Override
    public Meal save(Meal meal, int userId) {
        Integer mealId = meal.getId();
        if (meal.isNew()) {
            mealId = counter.incrementAndGet();
            meal.setId(mealId);
        } else if (get(mealId, userId) == null) {
            return null;
        }
        // handle case: update, but not present in storage
        Map<Integer, Meal> userMeals = repository.computeIfAbsent(userId, ConcurrentHashMap::new);
        userMeals.put(mealId, meal);
        return meal;
    }

    @Override
    public boolean delete(int id) {
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> userMeals = repository.get(userId);
        return userMeals == null ? null : userMeals.get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.get(userId).values().stream().sorted(MEAL_COMPARATOR).toList();
    }
}

