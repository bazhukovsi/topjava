package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.store.MealsStore;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");
        MealsStore mealsStore = new MealsStore();
        List<MealTo> mealToList = MealsUtil.mealToByStreams(mealsStore.findAll(), mealsStore.getCaloriesPerDay());
        HttpSession session = request.getSession();
        session.setAttribute("mealsTo", mealToList);
        for (MealTo mealTo : mealToList) {
            System.out.println(mealTo.toString());
        }
        response.sendRedirect("meals.jsp");
    }
}
