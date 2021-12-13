package game;

import state.GameState;
import state.action.ActionInstance;

public interface Player {
    ActionInstance getAction(GameState state);
}
