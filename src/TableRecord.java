import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TableRecord extends JPanel {
    public static JLabel statistics;
    public static JButton menuButton;

    public TableRecord(){
        statistics = new JLabel(Player.getPlayerStatistic());
        menuButton = new JButton("Menu");
        add(statistics, BorderLayout.WEST);
        add(menuButton, BorderLayout.EAST);
        menuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                transferFocusDownCycle();
                if (!Game.isPaused) {
                    Game.pause();
                }
            }
        });
    }
    public static void update(){
        statistics.setText(Game.player.getPlayerStatistic());
    }

}
