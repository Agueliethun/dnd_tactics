package game;

import state.GameState;
import state.Piece;
import state.action.ActionInstance;

public class AIPlayer implements Player {
    @Override
    public ActionInstance getAction(GameState state) {
        return null;
    }

    @Override
    public ActionInstance getAction(GameState state, Piece selectedPiece) {
        return null;
    }
}
