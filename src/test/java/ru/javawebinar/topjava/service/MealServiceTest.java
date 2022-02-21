package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.function.ThrowingRunnable;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.NOT_FOUND;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(USER_MEAL_ID, USER_ID);
        assertMatch(meal, userMeal);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, ADMIN_ID));
    }

    @Test
    public void getOtherUserMeal() {
        assertThrows(NotFoundException.class, () -> service.get(USER_MEAL_ID, ADMIN_ID));
    }

    @Test
    public void delete() {
        service.delete(USER_MEAL_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(USER_MEAL_ID, USER_ID));
    }

    @Test
    public void deleteOtherUserMeal() {
        assertThrows(NotFoundException.class, () -> service.delete(USER_MEAL_ID, ADMIN_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
    }

    @Test
    public void getBetweenInclusive() {

        List<Meal> filteredUser = service.getBetweenInclusive(null, null, USER_ID);
        List<Meal> filteredAdmin = service.getBetweenInclusive(null, null, ADMIN_ID);
        assertMatch(filteredAdmin, adminMeal4, adminMeal3, adminMeal2, adminMeal);
        assertMatch(filteredUser, userMeal3, userMeal2, userMeal);

        filteredUser = service.getBetweenInclusive(LocalDate.of(2020, 1, 30), null, USER_ID);
        filteredAdmin = service.getBetweenInclusive(LocalDate.of(2020, 1, 31), null, ADMIN_ID);
        assertMatch(filteredAdmin, adminMeal4, adminMeal3, adminMeal2, adminMeal);
        assertMatch(filteredUser, userMeal3, userMeal2, userMeal);

        filteredUser = service.getBetweenInclusive(LocalDate.of(2020, 1, 31), null, USER_ID);
        filteredAdmin = service.getBetweenInclusive(LocalDate.of(2020, 2, 20), null, ADMIN_ID);
        assertMatch(filteredAdmin);
        assertMatch(filteredUser);
    }

    @Test
    public void getAll() {
        List<Meal> allUser = service.getAll(USER_ID);
        List<Meal> allAdmin = service.getAll(ADMIN_ID);
        assertMatch(allAdmin, adminMeal4, adminMeal3, adminMeal2, adminMeal);
        assertMatch(allUser, userMeal3, userMeal2, userMeal);
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(USER_MEAL_ID, USER_ID), updated);
    }

    @Test
    public void updateOtherUserMeal() {
        Meal updated = getUpdated();
        assertThrows(NotFoundException.class, () -> service.update(updated, ADMIN_ID));
    }

    @Test
    public void updateDateDuplicate() {
        Meal updated = getDuplicateUpdated();
        assertThrows(DuplicateKeyException.class, () -> service.update(updated, USER_ID));
    }

    @Test
    public void create() {
        Meal created = service.create(getNew(), USER_ID);
        Meal newMeal = getNew();
        Integer newId = created.getId();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, USER_ID), newMeal);
    }
}