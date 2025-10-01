package se233.chapter5part2;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se233.chapter5part2.model.Food;
import se233.chapter5part2.model.SpecialFood;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class FoodTest{
    private Food food;


    @BeforeEach
    public void setup() {
        food = new Food(new Point2D(0, 0));
    }
    @Test
    public void respawn_shouldBeOnNewPosition () {
        food.respawn();
        assertNotSame(food.getPosition(), new Point2D(0, 0));
    }
    @Test
    public void specialFood_shouldBeWorthFivePoints() {
        SpecialFood specialFood = new SpecialFood(new Point2D(0, 0));
        assertEquals(5, specialFood.getPoints());
    }
}