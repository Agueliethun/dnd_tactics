package state.action;

import state.GameState;
import state.Piece;

import java.util.ArrayList;
import java.util.List;

public class DoNothingAction extends Action {
    public DoNothingAction(Phase phase) {
        super(phase, 0);
    }

    @Override
    public String getDesc() {
        return "Do Nothing";
    }

    @Override
    public Action copy() {
        return new DoNothingAction(getPhase());
    }

    @Override
    public boolean apply(ActionInstance actionInstance, GameState gameState) {
        return true;
    }

    @Override
    public List<Piece> getTargets(ActionInstance actionInstance, GameState gameState) {
        return new ArrayList<>();
    }
}
