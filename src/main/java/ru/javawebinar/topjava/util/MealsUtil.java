package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MealsUtil {
    public static void main(String[] args) {
        List<Meal> meals = Arrays.asList(
                new Meal(1, LocalDateTime.of( 2020, Month.JANUARY, 30, 10, 0 ), "123", 500),
                new Meal(2, LocalDateTime.of( 2020, Month.JANUARY, 30, 10, 0 ), "123", 500),
                new Meal(3, LocalDateTime.of( 2020, Month.JANUARY, 30, 10, 0 ), "123", 500),
                new Meal(4, LocalDateTime.of( 2020, Month.JANUARY, 30, 10, 0 ), "123", 500),
                new Meal(5, LocalDateTime.of( 2020, Month.JANUARY, 30, 10, 0 ), "123", 500),
                new Meal(6, LocalDateTime.of( 2020, Month.JANUARY, 30, 10, 0 ), "123", 500),
                new Meal(7, LocalDateTime.of( 2020, Month.JANUARY, 30, 10, 0 ), "123", 500)
        );
    }

    public static List<MealTo> filteredByStreams(List<Meal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );

        return meals.stream()
                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getTime(), startTime, endTime))
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}
