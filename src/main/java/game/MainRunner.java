package game;

import game.ai.AIPlayer;
import state.BoardSettings;
import state.Piece;
import state.Position;
import state.action.Ability;
import state.action.Buff;
import state.action.Modifier;
import state.action.TileTarget;
import state.action.effect.BuffEffect;
import state.action.effect.HPEffect;
import ui.UIEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainRunner {
    public static void main(String[] args) {
        Ability mageAbility = new Ability(2, new HPEffect(-3), new TileTarget(2, TileTarget.Shape.CIRCLE), "Fireblast");
        Ability commanderAbility = new Ability(2, new BuffEffect(new Buff(2, new Modifier(2, 0), new Modifier(1, 1), new ArrayList<>())),
                new TileTarget(1, TileTarget.Shape.CIRCLE), "Buff");
        Ability warriorAbility = new Ability(1, new BuffEffect(new Buff(3, null, new Modifier(0, 0), new ArrayList<>())),
                new TileTarget(1, TileTarget.Shape.CIRCLE), "Cripple");

        Map<Position, Piece> pieces = new HashMap<>();
        Piece piece1 = new Piece(14, 2, 4, false, new ArrayList<>(), "warrior");
        Piece piece2 = new Piece(15, 3, 2, true, new ArrayList<>(), "commander");
        Piece piece3 = new Piece(8, 2, 0, false, new ArrayList<>(), "mage");
        piece3.addAbility(mageAbility);
        piece2.addAbility(commanderAbility);
        piece1.addAbility(warriorAbility);

        pieces.put(new Position(0, 1), piece1);
        pieces.put(new Position(0, 3), piece2);
        pieces.put(new Position(0, 5), piece3);

        BoardSettings settings = new BoardSettings(7, pieces);

        GameRunner runner = new GameRunner(new HumanPlayer(), new AIPlayer(4), settings);
        UIEngine.setInstance(new UIEngine(settings.getBoardSize()));

        runner.runGame();
    }
}
