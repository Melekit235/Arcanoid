import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Settings extends JFrame{
    public static JPanel settingsPanel;
    public static final String[] COMPLEXITY_LABELS = {"Легко", "Легко+", "Средне", "Средне+", "Сложно"};
    public static final String[] SCREEN_RESOLUTION_LABELS = { "FullScreen", "800x600", "1200x800", "1366x768", "1200x600"};
    private static int currentIndexComplexityLabels = 0;
    private static int currentIndexScreenResolutionLabels = 0;
    public static int previousHeight = Game.HEIGHT;
    public static int previousWidth = Game.WIDTH;
    public static ArrayList<JButton> settingsItems;
    public static float speedRatio;
    public static int buttonWidth;
    public static int buttonHeight;
    public Settings() {
        settingsItems = new ArrayList<>();

        settingsPanel = new JPanel();
        settingsPanel.setLayout(new GridBagLayout());
        settingsPanel.setOpaque(false);
        speedRatio = 0.004f;

        JButton complexityButton = new JButton(COMPLEXITY_LABELS[currentIndexComplexityLabels]);
        JButton screenResolutionButton =  new JButton(SCREEN_RESOLUTION_LABELS[currentIndexScreenResolutionLabels]);
        JButton backToMenuButton = new JButton("Назад в меню");

        settingsItems.add(complexityButton);
        settingsItems.add(screenResolutionButton);
        settingsItems.add(backToMenuButton);

        repaintSettings();

        settingsPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    Game.game.remove(Game.gameField.settings.settingsPanel);
                    Game.game.add(Game.gameField.menu.menuPanel, BorderLayout.CENTER);
                    Game.gameField.menu.menuPanel.requestFocus();
                    Game.gameField.menu.menuPanel.setVisible(true);
                    Game.game.revalidate();
                    Game.game.repaint();
                    Game.frame.revalidate();
                    Game.frame.repaint();
                }
            }
        });

        complexityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentIndexComplexityLabels = (currentIndexComplexityLabels + 1) % COMPLEXITY_LABELS.length;
                complexityButton.setText(COMPLEXITY_LABELS[currentIndexComplexityLabels]);
                switch (complexityButton.getText()) {
                    case "Легко" :
                        speedRatio = 0.004f;
                        break;
                    case "Легко+" :
                        speedRatio = 0.0065f;
                        break;
                    case "Средне" :
                        speedRatio = 0.0093f;
                        break;
                    case "Средне+" :
                        speedRatio = 0.0121f;
                        break;
                    case "Сложно" :
                        speedRatio = 0.016f;
                        break;
                }
                Balls.balls.get(0).speed = (int)(Game.HEIGHT * speedRatio);
                Platforms.platforms.get(0).speed = (int)(Game.HEIGHT * speedRatio) + 2;;
                settingsPanel.requestFocus();

            }
        });


        screenResolutionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentIndexScreenResolutionLabels = (currentIndexScreenResolutionLabels + 1) % SCREEN_RESOLUTION_LABELS.length;
                screenResolutionButton.setText(SCREEN_RESOLUTION_LABELS[currentIndexScreenResolutionLabels]);
                switch (screenResolutionButton.getText()) {
                    case "800x600" :
                        Game.WIDTH = 800;
                        Game.HEIGHT = 600;
                        Game.frame.dispose();
                        Game.frame.setUndecorated(false);
                        break;
                    case "1200x800":
                        Game.WIDTH = 1200;
                        Game.HEIGHT = 800;
                        Game.frame.dispose();
                        Game.frame.setUndecorated(false);
                        break;
                    case "1366x768" :
                        Game.WIDTH = 1366;
                        Game.HEIGHT = 768;
                        Game.frame.dispose();
                        Game.frame.setUndecorated(false);
                        break;
                    case "1200x600" :
                        Game.WIDTH = 1200;
                        Game.HEIGHT = 600;
                        Game.frame.dispose();
                        Game.frame.setUndecorated(false);
                        break;
                    case "FullScreen" :
                        Game.WIDTH = 1920;
                        Game.HEIGHT = 1080;
                        Game.frame.dispose();
                        Game.frame.setUndecorated(true);
                        break;
                }

                Game.game.setPreferredSize(new Dimension(Game.WIDTH, Game.HEIGHT));
                Game.frame.setResizable(false);
                Game.frame.pack();
                Game.frame.setLocationRelativeTo(null);
                Game.frame.setVisible(true);
                Bricks.repaintBricks();
                Platforms.repaintPlatform();
                Balls.repaintBall();
                Menu.repaintMenu();
                repaintSettings();
                Game.frame.revalidate();
                Game.frame.repaint();
                settingsPanel.requestFocus();
            }
        });

        backToMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.game.remove(Game.gameField.settings.settingsPanel);
                Game.game.add(Game.gameField.menu.menuPanel, BorderLayout.CENTER);
                Game.gameField.menu.menuPanel.setVisible(true);
                Game.game.revalidate();
                Game.game.repaint();
                Game.frame.revalidate();
                Game.frame.repaint();
            }
        });
    }

    public static void repaintSettings() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0);

        buttonWidth = (int)(Game.WIDTH * 0.375);
        buttonHeight = (int)(Game.HEIGHT * 0.06667);

        for (int i = 0; i < settingsItems.size(); i++) {
            settingsItems.get(i).setPreferredSize(new Dimension(buttonWidth, buttonHeight));
            settingsItems.get(i).setHorizontalAlignment(SwingConstants.CENTER);
            settingsPanel.add(settingsItems.get(i), gbc);
            gbc.gridy++;
        }
    }

}
