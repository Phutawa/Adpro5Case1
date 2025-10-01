package se233.chapter5part2;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;
import se233.chapter5part2.FoodTest;
import se233.chapter5part2.GameLoopTest;
import se233.chapter5part2.SnakeTest;

@Suite
@SelectClasses({FoodTest.class, GameLoopTest.class, SnakeTest.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JUnitTestSuite {
    @BeforeAll
    public static void initJfxRuntime() {
        javafx.application.Platform.startup(() -> {});
    }
}