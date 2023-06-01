
import com.fasterxml.jackson.databind.JsonNode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

public class Settings implements Serializable {
    public int currentIndexComplexityLabels = 0;
    public int currentIndexScreenResolutionLabels = 0;
    public static int previousHeight = Game.HEIGHT;
    public static int previousWidth = Game.WIDTH;
    public static float speedRatio;
    public static int buttonWidth;
    public static int buttonHeight;

    public void saveComponentData(String filename) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename, true))) {
            writer.println(getClass().getName());
            writer.println(currentIndexComplexityLabels + "," + currentIndexScreenResolutionLabels + "," + previousHeight + ","
                    + previousWidth + "," + speedRatio + "," + buttonWidth + "," + buttonHeight);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readComponentData(String dataComponent) {
        String[] dataArray = dataComponent.split(",");
        currentIndexComplexityLabels = Integer.parseInt(dataArray[0]);
        currentIndexScreenResolutionLabels = Integer.parseInt(dataArray[1]);
        previousHeight = Integer.parseInt(dataArray[2]);
        previousWidth = Integer.parseInt(dataArray[3]);
        speedRatio = Float.parseFloat(dataArray[4]);
        buttonWidth = Integer.parseInt(dataArray[5]);
        buttonHeight = Integer.parseInt(dataArray[6]);
    }

    public void readComponentDataFromJSON (JsonNode rootNode) {
        JsonNode settingsNode = rootNode.get("settings");
        currentIndexComplexityLabels = settingsNode.get("currentIndexComplexityLabels").asInt();
        currentIndexScreenResolutionLabels = settingsNode.get("currentIndexScreenResolutionLabels").asInt();

    }

    public Settings() {
        SettingsComponents.settingsItems = new ArrayList<>();

        SettingsComponents.settingsPanel = new JPanel();
        SettingsComponents.settingsPanel.setLayout(new GridBagLayout());
        SettingsComponents.settingsPanel.setOpaque(false);
        speedRatio = 0.004f;

        JButton complexityButton = new JButton(SettingsComponents.COMPLEXITY_LABELS[currentIndexComplexityLabels]);
        JButton screenResolutionButton = new JButton(SettingsComponents.SCREEN_RESOLUTION_LABELS[currentIndexScreenResolutionLabels]);
        JButton backToMenuButton = new JButton("Назад в меню");

        SettingsComponents.settingsItems.add(complexityButton);
        SettingsComponents.settingsItems.add(screenResolutionButton);
        SettingsComponents.settingsItems.add(backToMenuButton);

        repaintSettings();

        SettingsComponents.settingsPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                    Game.game.remove(SettingsComponents.settingsPanel);
                    Game.game.add(Menu.menuPanel, BorderLayout.CENTER);
                    Menu.menuPanel.requestFocus();
                    Menu.menuPanel.setVisible(true);
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
                currentIndexComplexityLabels = (currentIndexComplexityLabels + 1) % SettingsComponents.COMPLEXITY_LABELS.length;
                complexityButton.setText(SettingsComponents.COMPLEXITY_LABELS[currentIndexComplexityLabels]);
                Player.statistics.complexity = complexityButton.getText();
                switch (complexityButton.getText()) {
                    case "Легко":
                        speedRatio = 0.004f;
                        break;
                    case "Легко+":
                        speedRatio = 0.0065f;
                        break;
                    case "Средне":
                        speedRatio = 0.0093f;
                        break;
                    case "Средне+":
                        speedRatio = 0.0121f;
                        break;
                    case "Сложно":
                        speedRatio = 0.017f;
                        break;
                }
                TableRecords.update();
                Balls.balls.get(0).speed = (int) (Game.HEIGHT * speedRatio);
                Platforms.platforms.get(0).speed = (int) (Game.HEIGHT * speedRatio) + 2;

                SettingsComponents.settingsPanel.requestFocus();

            }
        });

        screenResolutionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentIndexScreenResolutionLabels = (currentIndexScreenResolutionLabels + 1) % SettingsComponents.SCREEN_RESOLUTION_LABELS.length;
                screenResolutionButton.setText(SettingsComponents.SCREEN_RESOLUTION_LABELS[currentIndexScreenResolutionLabels]);
                switch (screenResolutionButton.getText()) {
                    case "800x600":
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
                    case "1366x768":
                        Game.WIDTH = 1366;
                        Game.HEIGHT = 768;
                        Game.frame.dispose();
                        Game.frame.setUndecorated(false);
                        break;
                    case "1200x600":
                        Game.WIDTH = 1200;
                        Game.HEIGHT = 600;
                        Game.frame.dispose();
                        Game.frame.setUndecorated(false);
                        break;
                    case "FullScreen":
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
                Bonuses.repaintBonuses();
                repaintSettings();
                Game.frame.revalidate();
                Game.frame.repaint();
                SettingsComponents.settingsPanel.requestFocus();
            }
        });

        backToMenuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.game.remove(SettingsComponents.settingsPanel);
                Game.game.add(Menu.menuPanel, BorderLayout.CENTER);
                Menu.menuPanel.setVisible(true);
                Game.game.revalidate();
                Game.game.repaint();
                Game.frame.revalidate();
                Game.frame.repaint();
            }
        });
    }

    public void update() {
        SettingsComponents.settingsItems.get(1).setText(SettingsComponents.SCREEN_RESOLUTION_LABELS[currentIndexScreenResolutionLabels]);
        switch (SettingsComponents.settingsItems.get(1).getText()) {
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

        SettingsComponents.settingsItems.get(0).setText(SettingsComponents.COMPLEXITY_LABELS[currentIndexComplexityLabels]);
        switch (SettingsComponents.settingsItems.get(0).getText()) {
            case "Легко":
                speedRatio = 0.004f;
                break;
            case "Легко+":
                speedRatio = 0.0065f;
                break;
            case "Средне":
                speedRatio = 0.0093f;
                break;
            case "Средне+":
                speedRatio = 0.0121f;
                break;
            case "Сложно":
                speedRatio = 0.017f;
                break;
        }
        System.out.println(speedRatio);

        SettingsComponents.settingsItems.get(0).setText(SettingsComponents.COMPLEXITY_LABELS[currentIndexComplexityLabels]);

        Balls.balls.get(0).speed = (int) (Game.HEIGHT * speedRatio);
        Platforms.platforms.get(0).speed = (int) (Game.HEIGHT * speedRatio) + 2;
        TableRecords.update();
        Game.game.setPreferredSize(new Dimension(Game.WIDTH, Game.HEIGHT));
        Game.frame.setResizable(false);
        Game.frame.pack();
        Game.frame.setLocationRelativeTo(null);
        Game.frame.setVisible(true);
        Menu.repaintMenu();
        repaintSettings();
        Game.frame.revalidate();
        Game.frame.repaint();
        SettingsComponents.settingsPanel.requestFocus();
    }

    public static void repaintSettings() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0);

        buttonWidth = (int)(Game.WIDTH * 0.375);
        buttonHeight = (int)(Game.HEIGHT * 0.06667);

        for (int i = 0; i < SettingsComponents.settingsItems.size(); i++) {
            SettingsComponents.settingsItems.get(i).setPreferredSize(new Dimension(buttonWidth, buttonHeight));
            SettingsComponents.settingsItems.get(i).setHorizontalAlignment(SwingConstants.CENTER);
            SettingsComponents.settingsPanel.add(SettingsComponents.settingsItems.get(i), gbc);
            gbc.gridy++;
        }
    }



}
