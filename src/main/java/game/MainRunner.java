package game;

import state.BoardSettings;
import state.Piece;
import state.Position;
import ui.UIEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainRunner {
    public static void main(String[] args) {
        Map<Position, Piece> pieces = new HashMap<>();
        Piece piece1 = new Piece(5, 2, 5, true, new ArrayList<>(), 'X');
        pieces.put(new Position(0, 0), piece1);

        BoardSettings settings = new BoardSettings(5, pieces);

        GameRunner runner = new GameRunner(new HumanPlayer(), new HumanPlayer(), settings);

        UIEngine.setInstance(new UIEngine(runner.getGameState()));

        runner.runGame();
    }
}
