package se233.chapter5part1;

import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se233.chapter5part1.model.Keys;

import static org.junit.jupiter.api.Assertions.*;

public class KeysTest {
    private Keys keys;

    @BeforeEach
    public void setUp() {
        keys = new Keys();
    }

    @Test
    public void singleKeyPress_givenKeyPressed_thenKeyIsRegistered() {
        keys.add(KeyCode.A);
        assertTrue(keys.isPressed(KeyCode.A), "Key A should be marked as pressed");
    }

    @Test
    public void sequentialKeyPresses_givenTwoKeysPressed_thenBothRemainPressed() {
        keys.add(KeyCode.A);
        keys.add(KeyCode.D);

        assertTrue(keys.isPressed(KeyCode.A), "Key A should still be pressed after D is pressed");
        assertTrue(keys.isPressed(KeyCode.D), "Key D should be marked as pressed");
    }
}
