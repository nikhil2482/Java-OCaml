package org.cis1200.snake;

import java.awt.*;

import java.util.Random;



/**
 * A circular food object for the Snake game.
 */
public class Orange extends Food {

    public static final int SIZE = 20;
    public static final int INIT_POS_X = 100;
    public static final int INIT_POS_Y = 170;


    public Orange(int courtWidth, int courtHeight, Color color) {

        super(INIT_POS_X, INIT_POS_Y, SIZE, SIZE, courtWidth, courtHeight);

        // Create random points to put the object in within bounds
        Random rand = new Random();
        int maxNum = 340;
        int num = rand.nextInt(maxNum / 20 + 1) * 20;
        setPx(num);
        int num2 = rand.nextInt(maxNum / 20 + 1) * 20;
        setPy(num2);

    }

    /**
     * Draws the Circle on the specified Graphics object.
     *
     * @param g The Graphics object on which to draw the Circle.
     */
    public void draw(Graphics g) {
        g.setColor(Color.ORANGE);
        g.fillOval(GameCourt.getORX(), GameCourt.getORY(), 20, 20);
    }

    public static Orange parseOrange(String s) {
        String[] parts = s.split(",");
        int x = Integer.parseInt(parts[0]);
        int y = Integer.parseInt(parts[1]);
        Color color = Color.ORANGE; // default color if not provided
        if (parts.length > 2) {
            color = new Color(Integer.parseInt(parts[2]));
        }
        return new Orange(x, y, color);
    }


    @Override
    public String toString() {
        return String.format("%d,%d", GameCourt.getORX(), GameCourt.getORY());
    }


    public void changeDirection(Direction oldDirection) {
        final long endTime = System.currentTimeMillis() + 4 * 1000L;
        // Calculate the end time of the change
        System.out.println("changeDirection is called");
        System.out.println("Direction bchange" + oldDirection);


        while (System.currentTimeMillis() < endTime) {
            int[] head = Snake.getBody().get(0);
            int[] newHead = new int[]{head[0], head[1]};

            switch (oldDirection) {
                case UP:
                    newHead[1]--;
                    break;
                case RIGHT:
                    newHead[0]--;

                    break;
                case DOWN:
                    newHead[1]++;
                    break;
                case LEFT:
                    newHead[0]++;
                    break;
                case NONE:
                    return; // If no direction is set, do not move
                default:
                    throw new IllegalArgumentException("Unexpected value: " + oldDirection);
            }


            Snake.getBody().remove(Snake.getBody().size() - 1);
            Snake.getBody().add(0, newHead);

        }


    }

}

