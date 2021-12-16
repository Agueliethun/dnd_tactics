package state.action;

import state.GameState;
import state.Piece;

import java.util.ArrayList;
import java.util.List;

public class AttackAction extends Action {
    public AttackAction(int range) {
        super(Phase.ATTACK, range);
    }

    @Override
    public String getDesc() {
        return "Attack";
    }

    @Override
    public Action copy() {
        return new AttackAction(getRange());
    }

    @Override
    public boolean apply(ActionInstance actionInstance, GameState gameState) {
        Piece targetPiece = getTargets(actionInstance, gameState).get(0);
        if (actionInstance.getTargetPostion().getDistance(actionInstance.getActor().getPosition()) <= actionInstance.getAction().getRange()) {
            targetPiece.addHp(-1 * actionInstance.getActor().getAttack());
            return true;
        }

        return false;
    }

    @Override
    public List<Piece> getTargets(ActionInstance actionInstance, GameState gameState) {
        Piece piece = gameState.getPieceAtPosition(actionInstance.getTargetPostion());
        if (piece == null) {
            return new ArrayList<>();
        }
        return List.of(piece);
    }
}
