package Main;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MainCore {
    public static void main(String[] args) throws IOException {
        JFrame frame = new JFrame();

        frame.setTitle("Game"); // Game Title

        frame.setSize(1280, 720);

        frame.setMinimumSize(new Dimension(1280, 720)); // Min Frame size

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel); // add game panel to frame
        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);

        gamePanel.setUpGame();
        gamePanel.startGameThread();
    }
}
