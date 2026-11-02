package org.cis1200.snake;

import java.awt.*;

import java.util.ArrayList;

import java.util.Timer;

/**
 * A game object representing the snake.
 */
public class Snake extends GameObj {


    // The ArrayList representing the snake's body
    private static ArrayList<int[]> body;

    public static ArrayList<int[]> getBody() {
        if (body == null) {
            body = new ArrayList<int[]>();
        }
        return body;
    }

    public static void setBody(ArrayList<int[]> newBody) {
        body = newBody;
    }



    public int[] getHead() {
        return body.get(0);
    }


    private Timer timer;




    // The current direction the snake is moving in


    private static Direction currentDirection = Direction.NONE;

    public static Direction getCurrentDirection() {
        return currentDirection;
    }

    public static void setCurrentDirection(Direction value) {
        currentDirection = value;
    }

    private static Color color;

    // The color of the snake
    public Color getColor() {
        return color;
    }
    public static void setColor(Color value) {
        color = value;
    }




    private static int numRows;

    public static int getNumRows() {
        return numRows;
    }

    public static void setNumRows(int value) {
        numRows = value;
    }
    private static int numCols;

    public static int getNumCols() {
        return numCols;
    }

    public static void setNumCols(int value) {
        numCols = value;
    }



    public static final int INIT_POS_X = 0;
    public static final int INIT_POS_Y = 0;
    public static final int INIT_VEL_X = 0;
    public static final int INIT_VEL_Y = 0;

    public static final int SIZE = 20;

    public Snake(int courtWidth, int courtHeight, Color color) {
        super(INIT_VEL_X, INIT_VEL_Y, INIT_POS_X, INIT_POS_Y, SIZE, SIZE, courtWidth, courtHeight);
        // System.out.println("instantiating snake");

        this.color = color;
        numRows = courtHeight / SIZE;
        numCols = courtWidth / SIZE;

        int row = numRows / 2;
        int col = numCols / 2;



        body = new ArrayList<>();
        body.add(new int[] {0, 0});
        body.add(new int[] {1, 0});
        body.add(new int[] {2, 0});

        currentDirection = Direction.NONE;





    }



    /**
     * Draws the snake on the given Graphics object.
     *
     * @param g the Graphics object on which to draw the snake
     */
    public void draw(Graphics g) {
        g.setColor(color);
        for (int[] segment : body) {
            g.fillRect(getPx() + (segment[0] * SIZE), getPy() + (segment[1] * SIZE), SIZE, SIZE);
        }
    }


    GameCourt gameCourt = new GameCourt(GameCourt.getStatus(), GameCourt.getScoreLabel());


    // Access the apple object
    // Apple apple = gameCourt.getApple();

    // Access the orange object
    Orange orange = gameCourt.getOrange();

    // Access the green object
    // Green green = gameCourt.getGreen();



    // Move function which adds one to the end of the arraylist if
    // called (simulating snake movement)
    public void move3(Direction currentDirection) {
        int[] head = body.get(0);
        int[] newHead = new int[]{head[0], head[1]};

        switch (currentDirection) {
            case UP:
                newHead[1]--;
                break;
            case RIGHT:
                newHead[0]++;
                break;
            case DOWN:
                newHead[1]++;
                break;
            case LEFT:
                newHead[0]--;
                break;
            case NONE:
                return; // If no direction is set, do not move
            default:
                throw new IllegalArgumentException("Unexpected value: " + currentDirection);
        }


        if (newHead[1] < 0 || newHead[1] >= numRows || newHead[0] < 0 || newHead[0] >= numCols) {
            GameCourt.getStatus().setText("You lose!!!!");
            GameCourt.setPlaying(false);

        } else if (newHead[0] * 20 == GameCourt.getAPX() && newHead[1] * 20 == GameCourt.getAPY()) {

            int[] lastSegment = body.get(body.size() - 1);
            body.add(new int[]{lastSegment[0], lastSegment[1]});
            // Create and draw the new apple.
            GameCourt.appleEat();
            if (GameCourt.getScore() == 5) {
                GameCourt.getStatus().setText("You Win!");
                GameCourt.setPlaying(false);
            }
        } else if (newHead[0] * 20 == GameCourt.getORX() && newHead[1] * 20 == GameCourt.getORY()) {
            GameCourt.orangeEat();
            System.out.println("Direction before" + currentDirection);
            GameCourt.getOrange().changeDirection(currentDirection);
            System.out.println("Direction after" + currentDirection);
        } else if (newHead[0] * 20 == GameCourt.getGRX() && newHead[1] * 20 == GameCourt.getGRY()) {
            GameCourt.greenEat();
            Green.onEaten(GameCourt.getSnake(), gameCourt);
            // Green.stopForTenSeconds(currentDirection);

        } else {
            // Move the snake by removing the last segment of its body and adding the new head
            body.remove(body.size() - 1);
            body.add(0, newHead);
        }
    }

    public static Snake parseSnake(String s) {
        String[] parts = s.split(",");
        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);
        int vx = Integer.parseInt(parts[2]);
        int vy = Integer.parseInt(parts[3]);
        Color color = new Color(Integer.parseInt(parts[4]));

        Snake snake = new Snake(340, 340, color);
        snake.setPx(x);
        snake.setPy(y);
        snake.setVx(vx);
        snake.setVy(vy);

        return snake;

    }

    @Override
    public String toString() {
        return getPx() + "," + getPy() + "," + getVx() + "," + getVy() + "," + getColor().getRGB();
    }




}


