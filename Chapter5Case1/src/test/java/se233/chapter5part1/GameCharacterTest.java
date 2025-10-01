package se233.chapter5part1;

import javafx.scene.input.KeyCode;
import  org.junit.jupiter.api.BeforeAll;
import  org.junit.jupiter.api.BeforeEach;
import  org.junit.jupiter.api.Test;
import se233.chapter5part1.model.GameCharacter;
import se233.chapter5part1.view.GameStage;

import java.lang.reflect.Field;

import  static org.junit.jupiter.api.Assertions.*;

public class GameCharacterTest {
    Field xVelocityField, yVelocityField, yAccelerationField, xField, canJumpField, isJumpingField, isFallingField, yMaxVelocityField;
    private GameCharacter gameCharacter;

    @BeforeAll
    public static void initJfxRuntime() {
        javafx.application.Platform.startup(() -> {});
    }
    @BeforeEach
    public void setUp() throws NoSuchFieldException {
        gameCharacter = new GameCharacter(0, 30, 30, "assets/Character1.png", 4, 3, 2, 111, 97, KeyCode.A, KeyCode.D, KeyCode.W);
        xVelocityField = gameCharacter.getClass().getDeclaredField("xVelocity");
        yVelocityField = gameCharacter.getClass().getDeclaredField("yVelocity");
        yAccelerationField = gameCharacter.getClass().getDeclaredField("yAcceleration");
        xField = gameCharacter.getClass().getDeclaredField("x");
        canJumpField = gameCharacter.getClass().getDeclaredField("canJump");
        isJumpingField = gameCharacter.getClass().getDeclaredField("isJumping");
        isFallingField = gameCharacter.getClass().getDeclaredField("isFalling");
        yMaxVelocityField = gameCharacter.getClass().getDeclaredField("yMaxVelocity");
        xVelocityField.setAccessible(true);
        yVelocityField.setAccessible(true);
        yAccelerationField.setAccessible(true);
        xField.setAccessible(true);
        canJumpField.setAccessible(true);
        isJumpingField.setAccessible(true);
        isFallingField.setAccessible(true);
        yVelocityField.setAccessible(true);
        yMaxVelocityField.setAccessible(true);
    }
    @Test
    public void respawn_givenNewGameCheracter_thenCoordinatesAre30_30() {
        gameCharacter.respawn();
        assertEquals(30,gameCharacter.getX(),"Initial X");
        assertEquals(30,gameCharacter.getY(),"Initial Y");
    }
    @Test
    public void respawn_givenNewGameCheracter_thenScoreIs0() {
        gameCharacter.respawn();
        assertEquals(0,gameCharacter.getScore(),"Initial Score");
    }
    @Test
    public void moveX_givenMoveRightOnce_thenXCoordinateIncreassByXVelocity() throws  IllegalAccessException{
        gameCharacter.respawn();
        gameCharacter.moveRight();
        gameCharacter.moveX();
        assertEquals(30 + xVelocityField.getInt(gameCharacter), gameCharacter.getX(), "Move right x");
    }
    @Test
    public void moveY_givenTwoConsecutiveCalls_thenYVelocityIncreases() throws  IllegalAccessException{
        gameCharacter.respawn();
        gameCharacter.moveY();
        int yVelocity1 = yVelocityField.getInt(gameCharacter);
        gameCharacter.moveY();
        int yVelocity2 = yVelocityField.getInt(gameCharacter);
        assertTrue(yVelocity2 > yVelocity1, "Velocity is increasing");
    }
    @Test
    public void moveY_givenTwoConsecutiveCalls_thenYAccelerationUnchanged() throws  IllegalAccessException{
        gameCharacter.respawn();
        gameCharacter.moveY();
        int yAcceleration1 = yAccelerationField.getInt(gameCharacter);
        gameCharacter.moveY();
        int yAcceleration2 = yAccelerationField.getInt(gameCharacter);
        assertTrue(yAcceleration2 == yAcceleration1, "Acceleration is not change");
    }
    @Test
    public void checkReachGameWall_givenCharacterAtLeftBoundary_thenPositionDoesNotGoNegative() throws IllegalAccessException{
        xField.setInt(gameCharacter, -10);
        gameCharacter.checkReachGameWall();
        assertEquals(0, gameCharacter.getX(), "Position does not go negative");
    }
    @Test
    public void checkReachGameWall_givenCharacterAtRightBoundary_thenPositionDoesNotExceedGameWidth() throws IllegalAccessException {
        xField.setInt(gameCharacter, se233.chapter5part1.view.GameStage.WIDTH + 50);
        gameCharacter.checkReachGameWall();
        assertEquals(GameStage.WIDTH - (int)gameCharacter.getWidth(), gameCharacter.getX(), "Position does not exceed game width");
    }
    @Test
    public void jump_givenCanJumpTrue_thenCharacterStartsJumping() throws IllegalAccessException {
        canJumpField.setBoolean(gameCharacter, true);
        gameCharacter.jump();
        int expectedYMaxVelocity = yMaxVelocityField.getInt(gameCharacter);
        assertEquals(expectedYMaxVelocity, yVelocityField.getInt(gameCharacter), "yVelocity should equal yMaxVelocity after jump");
        assertTrue(isJumpingField.getBoolean(gameCharacter), "Character should be jumping");
        assertFalse(isFallingField.getBoolean(gameCharacter), "Character should not be falling");
        assertFalse(canJumpField.getBoolean(gameCharacter), "canJump should be reset to false");
    }
    @Test
    public void jump_givenCanJumpFalse_thenCharacterDoesNotJump() throws IllegalAccessException{
        canJumpField.setBoolean(gameCharacter, false);
        int beforeVelocity = yVelocityField.getInt(gameCharacter);
        boolean beforeJumping = isJumpingField.getBoolean(gameCharacter);
        gameCharacter.jump();
        assertEquals(beforeVelocity, yVelocityField.getInt(gameCharacter), "yVelocity does not change");
        assertEquals(beforeJumping, isJumpingField.getBoolean(gameCharacter), "Jumping state does not change");
    }
    @Test
    public void collided_givenMovingRightIntoAnotherCharacter_thenStopsAtCollisionX() throws IllegalAccessException{
        GameCharacter player = new GameCharacter(0, 100, 100, "assets/Character1.png", 4, 3, 2, 50, 50, KeyCode.A, KeyCode.D, KeyCode.W);
        GameCharacter obstacle = new GameCharacter(1, 150, 100, "assets/Character1.png", 4, 3, 2, 50, 50, KeyCode.A, KeyCode.D, KeyCode.W);
        player.moveRight();
        player.collided(obstacle);
        assertEquals(obstacle.getX() - player.getCharacterWidth(), player.getX(), "Player should stop at obstacle boundary on X-axis");
    }
    @Test
    public void collided_givenFallingOnAnotherCharacter_thenLandsAndIncreasesScore() throws Exception {
        GameCharacter player = new GameCharacter(0, 100, 100, "assets/Character1.png", 4, 3, 2, 50, 50, KeyCode.A, KeyCode.D, KeyCode.W);
        GameCharacter groundTarget = new GameCharacter(1, 100, 200, "assets/Character1.png", 4, 3, 2, 50, 50, KeyCode.A, KeyCode.D, KeyCode.W);
        Field isFallingField = player.getClass().getDeclaredField("isFalling");
        isFallingField.setAccessible(true);
        isFallingField.setBoolean(player, true);
        int initialScore = player.getScore();
        boolean result = player.collided(groundTarget);
        assertTrue(result, "Collision should be detected vertically");
        assertTrue(player.getScore() > initialScore, "Score should increase after stomping");
        assertTrue(player.getY() >= groundTarget.getY(), "Player should land on or below the ground target");    }

}
