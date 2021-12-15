package game;

import state.BoardSettings;
import state.Piece;
import state.Position;
import state.action.Ability;
import state.action.TileTarget;
import state.action.effect.HPEffect;
import ui.UIEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainRunner {
    public static void main(String[] args) {
        Ability mageAbility = new Ability(2, new HPEffect(-5), new TileTarget(2, TileTarget.Shape.CIRCLE), "Fireblast");

        Map<Position, Piece> pieces = new HashMap<>();
        Piece piece1 = new Piece(14, 2, 4, false, new ArrayList<>(), "warrior");
        Piece piece2 = new Piece(15, 3, 2, true, new ArrayList<>(), "commander");
        Piece piece3 = new Piece(8, 2, 0, false, new ArrayList<>(), "mage");
        piece3.getAbilities().add(mageAbility);

        pieces.put(new Position(0, 0), piece1);
        pieces.put(new Position(0, 2), piece2);
        pieces.put(new Position(0, 4), piece3);

        BoardSettings settings = new BoardSettings(5, pieces);



        GameRunner runner = new GameRunner(new HumanPlayer(), new HumanPlayer(), settings);
        UIEngine.setInstance(new UIEngine(settings.getBoardSize()));


        runner.runGame();
    }
}
