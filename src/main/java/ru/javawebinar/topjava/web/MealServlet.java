package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealsDao;
import ru.javawebinar.topjava.model.*;
import ru.javawebinar.topjava.dao.MealsDaoImpl;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger log = getLogger(MealServlet.class);
    private static final String INSERT_OR_EDIT = "/meal_add_update.jsp";
    private static final String LIST_MEAL = "/meals.jsp";
    String forward = "";
    private MealsDao dao;

    @Override
    public void init() throws ServletException {
        super.init();
        dao = new MealsDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String forward = "";
        List<MealTo> mealToList = MealsUtil.filteredByStreams(dao.findAll(),
                LocalTime.MIN, LocalTime.MAX, 2000);
        request.setAttribute("mealsTo", mealToList);
        String action = request.getParameter("action");
        if (action != null) {
            if (action.equalsIgnoreCase("edit")) {
                forward = INSERT_OR_EDIT;
                int mealId = Integer.parseInt(request.getParameter("id"));
                Meal meal = dao.findById(mealId);
                request.setAttribute("meal", meal);
            } else if (action.equalsIgnoreCase("delete")) {
                int mealId = Integer.parseInt(request.getParameter("id"));
                dao.delete(mealId);
                forward = LIST_MEAL;
                request.setAttribute("mealsTo", MealsUtil.filteredByStreams(dao.findAll(),
                        LocalTime.MIN, LocalTime.MAX, 2000));
            } else if (action.equalsIgnoreCase("listMeals")) {
                forward = LIST_MEAL;
                request.setAttribute("mealsTo", MealsUtil.filteredByStreams(dao.findAll(),
                        LocalTime.MIN, LocalTime.MAX, 2000));
            } else {
                forward = INSERT_OR_EDIT;
            }
            RequestDispatcher view = request.getRequestDispatcher(forward);
            view.forward(request, response);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Meal meal = new Meal();
        meal.setDescription(request.getParameter("description"));
        meal.setCalories(Integer.parseInt(request.getParameter("calories")));
        LocalDateTime dateTimeMeal = LocalDateTime.parse(request.getParameter("dateTime"));
        meal.setDateTime(dateTimeMeal);
        String mealsId = request.getParameter("id");
//        if (mealsId == null || mealsId.isEmpty()) {
//            dao.save(meal);
//        } else {
//            meal.setId(Integer.parseInt(mealsId));
//            dao.update(Integer.parseInt(mealsId), meal);
//        }
        dao.save(meal);
//        response.sendRedirect("meals.jsp");
        request.setAttribute("mealsTo", MealsUtil.filteredByStreams(dao.findAll(),
                LocalTime.MIN, LocalTime.MAX, 2000));
        RequestDispatcher view = request.getRequestDispatcher(LIST_MEAL);
        view.forward(request, response);
    }
}
