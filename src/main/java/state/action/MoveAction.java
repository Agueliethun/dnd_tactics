package state.action;

import state.GameState;
import state.Piece;

import java.util.ArrayList;
import java.util.List;

public class MoveAction extends Action {
    public MoveAction(int range) {
        super(Phase.MOVE, range);
    }

    @Override
    public String getDesc() {
        return "Move";
    }

    @Override
    public Action copy() {
        return new MoveAction(getRange());
    }

    @Override
    public boolean apply(ActionInstance actionInstance, GameState gameState) {
        if (gameState.isPositionFree(actionInstance.getTargetPostion())) {
            if (actionInstance.getTargetPostion().getDistance(actionInstance.getActor().getPosition()) <= actionInstance.getAction().getRange()) {
                actionInstance.getActor().setPosition(actionInstance.getTargetPostion());
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Piece> getTargets(ActionInstance actionInstance, GameState gameState) {
        Piece piece = actionInstance.getActor();
        if (piece == null) {
            return new ArrayList<>();
        }
        return List.of(piece);
    }
}
