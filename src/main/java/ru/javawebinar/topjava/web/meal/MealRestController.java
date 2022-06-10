package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public Meal create(Meal meal) {
        int userId = SecurityUtil.authUserId();
        return service.save(meal, userId);
    }
    public void update(Meal meal, int userId) {
        checkNotFoundWithId(service.save(meal, userId), meal.getId());
    }

    public void delete(int id) {
        service.delete(id);
    }

    public Meal get(int id) {
        int userId = SecurityUtil.authUserId();
        return checkNotFoundWithId(service.get(id, userId), id);
    }

    public Collection<MealTo> getAll() {
//        int userId = SecurityUtil.authUserId();
        int userId = 2;
        return MealsUtil.getTos(service.getAll(userId), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }


}