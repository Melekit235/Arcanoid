import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

public class Game extends JPanel {
    public static GameField gameField;
    private final int DELAY = 3;
    public static boolean isPaused;
    public static final int WIDTH = 840;
    public static final int HEIGHT = 650;
    private Timer timer;

    public Game() {
        gameField = new GameField();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(new Color(11, 22, 26));
        setLayout(new BorderLayout());

        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    if (!isPaused) {
                        pause();
                    } else {
                        resume();
                    }
                } else if (!isPaused) {
                    if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
                        gameField.platforms.platforms.get(0).moveLeft = true;
                    } else if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
                        gameField.platforms.platforms.get(0).moveRight = true;
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (!isPaused) {
                    if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT) {
                        gameField.platforms.platforms.get(0).moveLeft = false;
                    } else if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT) {
                        gameField.platforms.platforms.get(0).moveRight = false;
                    }
                }
            }
        });
    }

    public void start() {
        JFrame frame = new JFrame("Арканоид");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        setFocusable(true);
        requestFocus();
        startTimer();
    }

    private void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new LoopTask(), 1000, DELAY);
    }

    private class LoopTask extends TimerTask {
        @Override
        public void run() {
            if (!isPaused) {
                gameField.allObjects.checkCollisions();
                gameField.allObjects.moveObjects();
                repaint();
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        gameField.allObjects.drawObjects(g);
    }

    public static void pause() {
        isPaused = true;
    }

    public static void resume() {
        isPaused = false;
    }


    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }
}
