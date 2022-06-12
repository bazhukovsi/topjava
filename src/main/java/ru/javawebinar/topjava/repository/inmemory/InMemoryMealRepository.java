package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Comparator;
import java.util.List;
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
        save(new Meal(LocalDateTime.of(2022, Month.JUNE, 11, 10, 0),
                "ЗавтракUser1", 10), USER_ID_ONE);
        save(new Meal(LocalDateTime.of(2022, Month.JUNE, 11, 10, 0),
                "УжинUser1", 20), USER_ID_ONE);
        save(new Meal(LocalDateTime.of(2022, Month.JUNE, 11, 13, 0),
                "ОбедUser1", 30), USER_ID_ONE);
        save(new Meal(LocalDateTime.of(2022, Month.JUNE, 11, 20, 0),
                "УжинUser2", 10), USER_ID_TWO);
        save(new Meal(LocalDateTime.of(2022, Month.JUNE, 11, 0, 0),
                "Еда на граничное значениеUser2", 10), USER_ID_TWO);
        save(new Meal(LocalDateTime.of(2022, Month.JUNE, 11, 10, 0),
                "ЗавтракUser2", 10), USER_ID_TWO);
        save(new Meal(LocalDateTime.of(2022, Month.JUNE, 11, 13, 0),
                "ОбедAdmin", 10), ADMIN_ID);
        save(new Meal(LocalDateTime.of(2022, Month.JUNE, 11, 20, 0),
                "УжинAdmin", 10), ADMIN_ID);
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
    public boolean delete(int id, int userId) {
        Map<Integer, Meal> userMeals = repository.get(userId);
        return userMeals != null && userMeals.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        Map<Integer, Meal> userMeals = repository.get(userId);
        return userMeals == null ? null : userMeals.get(id);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.get(userId).values().stream().sorted(MEAL_COMPARATOR).toList();
    }

    @Override
    public List<Meal> getBeetweenDateTime(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return getAll(userId).stream()
                .filter(meal -> DateTimeUtil.isBetweenDateTime(meal.getDateTime(), startDateTime, endDateTime))
                .sorted(MEAL_COMPARATOR)
                .toList();
    }


}

