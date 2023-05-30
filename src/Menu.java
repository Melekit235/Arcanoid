import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Menu extends JFrame{

    public static JPanel menuPanel;
    public static int buttonWidth;
    public static int buttonHeight;
    public static ArrayList<JButton> menuItems;
    public Menu() {
        menuItems = new ArrayList<>();
        menuPanel = new JPanel();
        menuPanel.setLayout(new GridBagLayout());
        menuPanel.setOpaque(false);

        buttonWidth = (int)(Game.WIDTH * 0.375);
        buttonHeight = (int)(Game.HEIGHT * 0.06667);

        JButton resumeGameButton = new JButton("Продолжить игру");
        JButton newGameButton = new JButton("Новая игра");
        JButton saveGameButton = new JButton("Сохранить игру");
        JButton loadGameButtonJSON = new JButton("Загрузить игру JSON");
        JButton loadGameButtonTXT = new JButton("Загрузить игру TXT");
        JButton settingsButton = new JButton("Настройки");
        JButton exitButton = new JButton("Выход");

        menuItems.add(resumeGameButton);
        menuItems.add(newGameButton);
        menuItems.add(saveGameButton);
        menuItems.add(loadGameButtonJSON);
        menuItems.add(loadGameButtonTXT);
        menuItems.add(settingsButton);
        menuItems.add(exitButton);
        
        repaintMenu();

        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Audio.playSoundThread(Audio.CLICK_SOUND);
                Game.newGame();
                Game.resume();
                dispose();
            }
        });

        resumeGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Game.player.statistics.lives > 0) {
                    Game.resume();
                    dispose();
                }
            }
        });

        saveGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.save();
                dispose();
            }
        });

        loadGameButtonJSON.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.loadJSON();
            }
        });

        loadGameButtonTXT.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.loadTXT();
            }
        });


        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game.gameField.menu.menuPanel.setVisible(false);
                Game.game.remove(Game.gameField.menu.menuPanel);
                Game.game.add(Game.gameField.settings.settingsPanel, BorderLayout.CENTER);
                Game.gameField.settings.settingsPanel.requestFocus();
                Game.game.revalidate();
                Game.game.repaint();
                Game.frame.revalidate();
                Game.frame.repaint();
                dispose();
            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    public static void repaintMenu() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 10, 0);

        for (int i = 0; i < menuItems.size(); i++) {
            JButton menuItem = menuItems.get(i);
            buttonWidth = (int)(Game.WIDTH * 0.375);
            buttonHeight = (int)(Game.HEIGHT * 0.06667);
            menuItem.setPreferredSize(new Dimension(buttonWidth, buttonHeight));
            menuItem.setHorizontalAlignment(SwingConstants.CENTER);
            menuPanel.add(menuItem, gbc);
            gbc.gridy++;
        }
    }
}
