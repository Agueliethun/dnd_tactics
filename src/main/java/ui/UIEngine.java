package ui;

import state.GameState;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class UIEngine {
    private static UIEngine instance;

    private GameState gameState;
    private JFrame frame;

    public UIEngine(GameState state) {
        this.gameState = state;

        int size = state.getSettings().getBoardSize();

        frame = new JFrame();

        JPanel mainPanel = new JPanel();
        mainPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(e);
            }
        });
        mainPanel.setLayout(new GridLayout(size, size));

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                JLayeredPane layerPane = new JLayeredPane();
                layerPane.add(new JLabel(new ImageIcon(ImageIO.read(new File("../")))));
            }
        }
    }

    public static void setInstance(UIEngine engine) {
        instance = engine;
    }

    public static UIEngine getInstance() {
        return instance;
    }
}
