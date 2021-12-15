package ui;

import state.GameState;
import state.Piece;
import state.Position;
import state.action.Action;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class UIEngine {
    private static UIEngine instance;

    private JFrame frame;
    private Map<Position, JButton> buttons;

    private CountDownLatch positionInputLatch;
    private Position selectedPosition;

    private CountDownLatch actionInputLatch;
    private Action selectedAction;
    private JDialog actionDialog;

    public UIEngine(int size) {
        buttons = new HashMap<>();
        javax.swing.SwingUtilities.invokeLater(() -> initUI(size));
    }

    private void initUI(int size) {
        frame = new JFrame();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(size, size));

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                JButton button = new JButton();
                final Position pos = new Position(x, y);
                buttons.put(pos, button);

                button.setPreferredSize(new Dimension(130, 130));
                button.addActionListener(e -> {
                    selectedPosition = pos;
                    positionInputLatch.countDown();
                });
                mainPanel.add(button);
            }
        }
        frame.getContentPane().add(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public void update(GameState state) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            for (Map.Entry<Position, JButton> entry : buttons.entrySet()) {
                Piece piece = state.getPieceAtPosition(entry.getKey());
                if (piece != null && piece.getHp() > 0) {
                    try {
                        entry.getValue().setIcon(new ImageIcon(ImageIO.read(new File("./img/" + piece.getVisual() + ".png"))));
                        entry.getValue().setBackground(piece.getOwningPlayer() == state.getPlayer1() ? Color.RED : Color.BLUE);
                        entry.getValue().setText("" + piece.getHp());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    entry.getValue().setIcon(null);
                    entry.getValue().setBackground(null);
                    entry.getValue().setText(null);
                }
            }
        });
    }

    public static void setInstance(UIEngine engine) {
        instance = engine;
    }

    public static UIEngine getInstance() {
        return instance;
    }

    public Position getPosition() {
        positionInputLatch = new CountDownLatch(1);

        try {
            positionInputLatch.await();

            return selectedPosition;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Action getAction(List<Action> actions) {
        actionInputLatch = new CountDownLatch(1);

        javax.swing.SwingUtilities.invokeLater(() -> {
            actionDialog = new JDialog();

            JPanel listPanel = new JPanel();
            listPanel.setLayout(new GridLayout(0, 1));

            for (Action action : actions) {
                JButton button = new JButton();
                button.setText(action.getDesc());
                button.addActionListener(e -> {
                    selectedAction = action;
                    actionInputLatch.countDown();
                });
                listPanel.add(button);
            }

            actionDialog.getContentPane().add(listPanel);

            actionDialog.pack();
            actionDialog.setLocationRelativeTo(null);
            actionDialog.setVisible(true);
            actionDialog.toFront();
        });

        try {
            actionInputLatch.await();
            if (actionDialog != null) {
                actionDialog.setVisible(false);
                actionDialog.dispose();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return selectedAction;
    }
}
