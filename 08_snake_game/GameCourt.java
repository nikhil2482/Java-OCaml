package org.cis1200.snake;



import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;


/**
 * A game object representing the snake.
 */
public class GameCourt extends JPanel {
    private Timer gameTimer;
    // the state of the game logic
    private static Snake currsnake; // the Black Square, keyboard control

    public static Snake getSnake() {
        return currsnake;
    }


    private static Apple currapple; // the Golden Snitch, bounces

    public static Apple getApple() {
        return currapple;
    }

    public static void setApple(Apple apple) {
        currapple = apple;
    }

    private static Orange currorange; // the Poison Mushroom, doesn't move

    public static Orange getOrange() {
        return currorange;
    }

    public static void setOrange(Orange orange) {
        currorange = orange;
    }

    private static Green currgreen; // the Poison Mushroom, doesn't move

    public static Green getGreen() {
        return currgreen;
    }

    public static void setGreen(Green green) {
        currgreen = green;
    }

    private static int[][] board = new int[17][17];

    public static int[][] getBoard() {
        return board;
    }



    private static boolean playing = false; // whether the game is running

    public static boolean getPlaying() {
        return playing;
    }

    public static void setPlaying(boolean value) {
        playing = value;
    }

    private static JLabel status; // Current status text, i.e. "Running..."

    public static JLabel getStatus() {
        return status;
    }

    public static void setStatus(JLabel value) {
        status = value;
    }

    private static JLabel scoreLabel; // Current status text, i.e. "Running..."

    public static JLabel getScoreLabel() {
        /* if (scoreLabel == null) {
            scoreLabel = "Score"
        } */
        return scoreLabel;
    }

    public static void setScoreLabel(JLabel value) {
        status = value;
    }

    // Game constants
    public static final int COURT_WIDTH = 340;
    public static final int COURT_HEIGHT = 340;
    public static final int SQUARE_VELOCITY = 1;

    private static int orx;

    public static int getORX() {
        return orx;
    }

    public static void setORX(int value) {
        orx = value;
    }
    private static int ory;

    public static int getORY() {
        return ory;
    }

    public static void setORY(int value) {
        ory = value;
    }
    private static int apx;

    public static int getAPX() {
        return apx;
    }

    public static void setAPX(int value) {
        apx = value;
    }
    private static int apy;

    public static int getAPY() {
        return apy;
    }

    public static void setAPY(int value) {
        apy = value;
    }
    private static int grx;

    public static int getGRX() {
        return grx;
    }

    public static void setGRX(int value) {
        grx = value;
    }
    private static int gry;

    public static int getGRY() {
        return gry;
    }

    public static void setGRY(int value) {
        gry = value;
    }

    // Update interval for timer, in milliseconds
    private final int interval = 200;



    private static Direction direction;

    public static Direction getDirection() {
        return direction;
    }

    public static void setDirection(Direction value) {
        direction = value;
    }

    public void saveGame(String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            bw.write(currsnake.toString());
            bw.newLine();
            bw.write(currapple.toString());
            bw.newLine();
            bw.write(currorange.toString());
            bw.newLine();
            bw.write(currgreen.toString());
            bw.newLine();

            for (int i = 0; i < 17; i++) {
                for (int j = 0; j < 17; j++) {
                    bw.write(Integer.toString(board[i][j]));
                    if (j < 16) {
                        bw.write(",");
                    }
                }
                bw.newLine();
            }

            bw.write(Integer.toString(score));
            bw.newLine();
            System.out.println("Game is saved");
        } catch (IOException e) {
            System.out.println("Unable to save game");
            e.printStackTrace();
        }
    }

    public void loadGame(String fileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            currsnake = Snake.parseSnake(br.readLine());

            currapple = Apple.parseApple(br.readLine());
            currorange = Orange.parseOrange(br.readLine());
            currgreen = Green.parseGreen(br.readLine());

            int[][] newBoard = new int[17][17];

            for (int i = 0; i < 17; i++) {
                String line = br.readLine();
                String[] row = line.split(",");
                for (int j = 0; j < 17; j++) {
                    try {
                        newBoard[i][j] = Integer.parseInt(row[j]);
                    } catch (NumberFormatException e) {
                        System.out.println(
                                "Unable to parse integer at row "
                                        + i + ", column " + j + ": " + row[j]);
                        throw e;
                    }
                }
            }
            board = newBoard;
            score = Integer.parseInt(br.readLine()); // read score before closing BufferedReader
            br.close(); // close the BufferedReader after reading the score value
            System.out.println("Game is loaded");
            repaint();
        } catch (IOException e) {
            System.out.println("Unable to load the game: IOException");
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Unable to load the game: NumberFormatException");
            e.printStackTrace();
        }
    }



    // Game state

    private static int score;

    public static int getScore() {
        return score;
    }

    public static void setScoreLabel(Integer value) {
        score = value;
    }




    public static void incScore() {
        score++;
        GameCourt.getScoreLabel().setText("Score: " + score);
    }









    public GameCourt(JLabel status, JLabel scoreLabel) {
        // creates border around the court area, JComponent method
        // System.out.println("GameCourt constructor");
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // The timer is an object which triggers an action periodically with the
        // given INTERVAL. We register an ActionListener with this timer, whose
        // actionPerformed() method is called each time the timer triggers. We
        // define a helper method called tick() that actually does everything
        // that should be done in a single time step.
        gameTimer = new Timer(interval, e -> tick());
        // snake = new Snake(getWidth(), getHeight(), Color.GREEN);
        // this.snake = new Snake(COURT_WIDTH, COURT_HEIGHT, Color.GREEN);



        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        // This key listener allows the square to move as long as an arrow key
        // is pressed, by changing the square's velocity accordingly. (The tick
        // method below actually moves the square.)
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                // System.out.println("key listener in GameCourt called");
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    direction = Direction.LEFT;
                    currsnake.setVx(0);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    direction = Direction.RIGHT;
                    currsnake.setVx(0);
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    direction = Direction.DOWN;
                    currsnake.setVy(0);
                } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                    direction = Direction.UP;
                    currsnake.setVy(0);
                }
            }

            public void keyReleased(KeyEvent e) {

            }
        });

        this.status = status;
        this.scoreLabel = scoreLabel;

        gameTimer.start(); // MAKE SURE TO START THE TIMER!


        // currsnake = new Snake(COURT_WIDTH, COURT_HEIGHT, Color.BLACK);
        currapple = new Apple(COURT_WIDTH, COURT_HEIGHT, Color.RED);
        currorange = new Orange(COURT_WIDTH, COURT_HEIGHT, Color.ORANGE);
        currgreen = new Green(COURT_WIDTH, COURT_HEIGHT, Color.GREEN);
        System.out.println("End of GameCourt Constructor");

    }






    public void reset() {
        // Stop the timer
        (gameTimer).stop();
        System.out.println("Start of Reset:");


        // Reset the game state
        currsnake = new Snake(340, 340, Color.BLACK);

        currorange = new Orange(340, 340, Color.ORANGE);

        orx = currorange.getPx();
        ory = currorange.getPy();
        System.out.println("Orange:" + orx + "," + ory);
        currapple = new Apple(340, 340, Color.RED);

        apx = currapple.getPx();
        apy = currapple.getPy();
        currgreen = new Green(340, 340, Color.GREEN);
        System.out.println("Apple:" + apx + "," + apy);

        grx = currgreen.getPx();
        gry = currgreen.getPy();
        System.out.println("Green:" + grx + "," + gry);
        // board = new int[17][17];
        direction = Direction.NONE;



        playing = true;
        status.setText("Running...");

        // Start the timer again with the original interval
        (gameTimer).setDelay(interval);

        (gameTimer).start();




        repaint();

        requestFocusInWindow();
    }


    /**
     * This method is called every time the timer defined in the constructor
     * triggers.
     */
    void tick() {
        // System.out.println("Value address: " + this.currsnake);
        if (playing) {




            // snake.move();
            this.currsnake.move3(direction);




            // update the display
            repaint();
        }
    }





    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        currsnake.draw(g);
        currapple.draw(g);
        currorange.draw(g);
        currgreen.draw(g);


        // draw the grid lines
        g.setColor(Color.GRAY);
        for (int row = 0; row < 17; row++) {
            for (int col = 0; col < 18; col++) {
                int x = col * 20;
                int y = row * 20;
                g.drawLine(x, 0, x, COURT_HEIGHT);
                g.drawLine(0, y, COURT_WIDTH, y);
            }
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }

    public static void appleEat() {
        setApple(new Apple(COURT_WIDTH, COURT_HEIGHT, Color.RED));
        Apple apple = getApple();
        apx = currapple.getPx();
        apy = currapple.getPy();
        incScore();
    }

    public static void orangeEat() {
        setOrange(new Orange(COURT_WIDTH, COURT_HEIGHT, Color.ORANGE));
        Orange orange = getOrange();
        orx = currorange.getPx();
        ory = currorange.getPy();
    }

    public static void greenEat() {
        System.out.print("Green is eaten");
        setGreen(new Green(COURT_WIDTH, COURT_HEIGHT, Color.GREEN));
        Green green = getGreen();
        grx = currgreen.getPx();
        gry = currgreen.getPy();
    }


}
