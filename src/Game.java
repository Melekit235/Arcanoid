import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;

public class Game extends JPanel {
    public static Player player;

    public static Game game;
    public static GameField gameField;
    public static long delay = 7;
    public static boolean isPaused;
    public static int WIDTH = 1920;
    public static int HEIGHT = 1080;
    public static Timer timer;
    public static JFrame frame;
    public static TableRecords tableRecords;

    public Game() {

        player = new Player();
        gameField = new GameField();
        isPaused = false;


        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    if (!isPaused) {
                        pause();
                    } else if (player.getLives() > 0){
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

    public void startTimer() {

        timer = new Timer();
        timer.scheduleAtFixedRate(new GameTask(), 2000, delay);
    }


    private class GameTask extends TimerTask {
        @Override
        public void run() {
            if (!isPaused) {
                gameField.allObjects.moveObjects();
                try {
                    gameField.allObjects.checkCollisions();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                repaint();
            }
            if (Player.isGameFailed) {
                timer.cancel();
                startTimer();
                Player.isGameFailed = false;
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
        timer.cancel();
        gameField.menu.menuPanel.setVisible(true);

    }

    public static void resume() {
        isPaused = false;
        game.startTimer();
        gameField.menu.menuPanel.setVisible(false);
    }

    public static void save() {
        // Код сохранения игры
    }

    public static void loadJSON() {

    }

    public static void loadTXT() {

    }

    public static void newGame() {
        player = new Player();
        gameField.platforms = new Platforms();
        gameField.balls = new Balls();
        gameField.bricks = new Bricks();
        gameField.bonuses = new Bonuses();
        gameField.allObjects = new DisplayAll(gameField.balls, gameField.platforms, gameField.bricks, gameField.bonuses);
        TableRecords.update();
    }

    public void start() {
        frame = new JFrame("Арканоид");
        frame.setUndecorated(true);
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(new Color(11, 22, 26));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(this);
        tableRecords = new TableRecords();
        frame.pack();
        frame.setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        add(tableRecords, BorderLayout.NORTH);
        add(gameField.menu.menuPanel, BorderLayout.CENTER);
        gameField.menu.menuPanel.setVisible(false);
        frame.setVisible(true);
        setFocusable(true);
        requestFocusInWindow();
    }

    public static void main(String[] args) {
        game = new Game();
        game.start();
        game.startTimer();

    }

}
