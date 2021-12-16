package game;

import state.GameState;
import state.Piece;
import state.action.ActionInstance;

public interface Player {
    ActionInstance getAction(GameState state);
}
