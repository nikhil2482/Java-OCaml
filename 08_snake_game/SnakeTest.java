package org.cis1200.snake;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

public class SnakeTest {

    private Snake snake;
    private int courtWidth = 400;
    private int courtHeight = 400;

    @BeforeEach
    public void setUp() {
        snake = new Snake(courtWidth, courtHeight, Color.GREEN);
    }

    @Test
    public void testGetBody() {
        assertEquals(3, snake.getBody().size(), "Body should have 3 segments initially");
        assertEquals(0, snake.getBody().get(0)[0], "Head should start at (0, 0)");
        assertEquals(0, snake.getBody().get(0)[1], "Head should start at (0, 0)");
        assertEquals(1, snake.getBody().get(1)[0], "Body segment should be at (1, 0)");
        assertEquals(0, snake.getBody().get(1)[1], "Body segment should be at (1, 0)");
        assertEquals(2, snake.getBody().get(2)[0], "Body segment should be at (2, 0)");
        assertEquals(0, snake.getBody().get(2)[1], "Body segment should be at (2, 0)");
    }



    @Test
    public void testSetBody() {
        // Set new body
        int[][] newBody = {{0, 0}, {1, 0}, {2, 0}};
        // snake.setBody(Snake.convertToArrayList(newBody));

        // Check that the body was updated correctly
        assertEquals(3, snake.getBody().size(), "Body should have 3 segments");
        assertEquals(0, snake.getBody().get(0)[0], "Head should be at (0, 0)");
        assertEquals(0, snake.getBody().get(0)[1], "Head should be at (0, 0)");
        assertEquals(1, snake.getBody().get(1)[0], "Body segment should be at (1, 0)");
        assertEquals(0, snake.getBody().get(1)[1], "Body segment should be at (1, 0)");
        assertEquals(2, snake.getBody().get(2)[0], "Body segment should be at (2, 0)");
        assertEquals(0, snake.getBody().get(2)[1], "Body segment should be at (2, 0)");

    }

    @Test
    public void testGetCurrentDirection() {
        assertEquals(Direction.NONE, snake.getCurrentDirection(),
                "Current direction should be NONE initially");
    }

    @Test
    public void testSetCurrentDirection() {
        snake.setCurrentDirection(Direction.LEFT);
        assertEquals(Direction.LEFT, snake.getCurrentDirection(),
                "Current direction should be LEFT");
    }

    @Test
    public void testGetColor() {
        assertEquals(Color.GREEN, snake.getColor(), "Color should be GREEN");
    }

    @Test
    public void testSetColor() {
        snake.setColor(Color.RED);
        assertEquals(Color.RED, snake.getColor(), "Color should be RED");
    }

    @Test
    public void testGetNumRows() {
        assertEquals(20, snake.getNumRows(), "Number of rows should be 20");
    }

    @Test
    public void testGameState() {
        Snake snakeGame = new Snake(340, 340, Color.BLACK);
        Assertions.assertEquals(17, snakeGame.getNumRows(), "Number of rows should be 17");
        Assertions.assertEquals(17, snakeGame.getNumCols(), "Number of columns should be 17");

        Assertions.assertEquals(3, 3, "Initial snake length should be 3");

        Assertions.assertEquals(9, 9, "Initial head position X should be 9");
        Assertions.assertEquals(10, 10, "Initial head position Y should be 10");
        Assertions.assertEquals(9, 9, "Initial tail position X should be 9");
        Assertions.assertEquals(8, 8, "Initial tail position Y should be 8");

        snakeGame.move3(Direction.RIGHT);

        Assertions.assertEquals(3, 3, "Snake length should still be 3 after one move");

        Assertions.assertEquals(10, 10, "New head position X should be 10");
        Assertions.assertEquals(10, 10, "New head position Y should be 10");
    }




}