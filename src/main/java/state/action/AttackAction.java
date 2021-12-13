package state.action;

import state.GameState;
import state.Piece;

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
        Piece targetPiece = gameState.getPieceAtPosition(actionInstance.getTargetPostion());
        if (targetPiece != null) {
            if (actionInstance.getTargetPostion().getDistance(actionInstance.getActor().getPosition()) <= actionInstance.getAction().getRange()) {
                targetPiece.addHp(-1 * actionInstance.getActor().getAttack());
                return true;
            }
        }

        return false;
    }
}
