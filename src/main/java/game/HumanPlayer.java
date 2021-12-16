package game;

import state.GameState;
import state.Piece;
import state.Position;
import state.action.Action;
import state.action.ActionInstance;
import state.action.DoNothingAction;
import ui.UIEngine;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class HumanPlayer implements Player {
    @Override
    public ActionInstance getAction(GameState state) {
        Piece piece = null;
        if (state.getValidTurnPieces().size() == 1) {
            piece = state.getValidTurnPieces().get(0);
        }
        while (piece == null || !state.getValidTurnPieces().contains(piece)) {
            piece = state.getPieceAtPosition(getPositionFromUser());
        }

        List<Action> actions = piece.getAllActions();
        actions.removeIf(a -> a.getPhase() != state.getTurnPhase());
        Action action = getListSelectionFromUser(actions);
        if (action instanceof DoNothingAction) {
            return new ActionInstance(action, piece, null);
        }

        Position target = null;
        while (target == null || !target.isValid(state.getSettings().getBoardSize()) || target.getDistance(piece.getPosition()) > action.getRange()) {
            target = getPositionFromUser();
        }

        return new ActionInstance(action, piece, target);
    }

    private Position getPositionFromUser() {
        Position pos = null;
        while (pos == null) {
            pos = UIEngine.getInstance().getPosition();
        }

        return pos;
    }

    private Action getListSelectionFromUser(List<Action> actions) {
        Action action = null;
        while (action == null) {
            action = UIEngine.getInstance().getAction(actions);
        }
        return action;
    }
}
