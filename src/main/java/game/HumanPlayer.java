package game;

import state.GameState;
import state.Piece;
import state.Position;
import state.action.Action;
import state.action.ActionInstance;
import state.action.DoNothingAction;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class HumanPlayer implements Player {
    @Override
    public ActionInstance getAction(GameState state) {
        Scanner scanner = new Scanner(System.in);

        Piece piece = null;
        while (piece == null || piece.getOwningPlayer() != state.getPlayerTurn()) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();

            piece = state.getPieceAtPosition(new Position(x, y));
        }

        List<Action> actions = piece.getAllActions();
        actions.removeIf(a -> a.getPhase() != state.getTurnPhase());
        int num = 0;
        for (Action action : actions) {
            System.out.println(num + ": " + action.getDesc());
            num++;
        }

        int index = -1;
        while (index < 0 || index >= actions.size()) {
            index = scanner.nextInt();
        }

        Action action = actions.get(index);
        if (action instanceof DoNothingAction) {
            return new ActionInstance(action, piece, null);
        }

        Position target = null;
        while (target == null || !target.isValid(state.getSettings().getBoardSize()) || target.getDistance(piece.getPosition()) > action.getRange()) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();

            target = new Position(x, y);
        }

        return new ActionInstance(action, piece, target);
    }
}
