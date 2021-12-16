package state.action;

import state.GameState;
import state.Piece;
import state.Position;
import state.action.effect.Effect;

import java.util.ArrayList;
import java.util.List;

public class Ability extends Action {
    private Effect effect;
    private TileTarget tileTarget;
    private String desc;

    public Ability(int range, Effect effect, TileTarget tileTarget, String desc) {
        super(Phase.ATTACK, range);
        this.effect = effect;
        this.tileTarget = tileTarget;
        this.desc = desc;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    @Override
    public Ability copy() {
        return new Ability(getRange(), effect.copy(), tileTarget.copy(), desc);
    }

    @Override
    public boolean apply(ActionInstance actionInstance, GameState gameState) {
        for (Piece piece : getTargets(actionInstance, gameState)) {
            effect.apply(piece);
        }

        return true;
    }

    @Override
    public List<Piece> getTargets(ActionInstance actionInstance, GameState gameState) {
        List<Position> positions = tileTarget.getPositions(actionInstance.getTargetPostion(), gameState.getSettings().getBoardSize());
        List<Piece> affectedPieces = new ArrayList<>();

        for (Position position : positions) {
            Piece targetPiece = gameState.getPieceAtPosition(position);
            if (targetPiece != null) {
                affectedPieces.add(targetPiece);
            }
        }

        return affectedPieces;
    }
}
