package state.action;

import state.GameState;
import state.Piece;
import state.Position;

public class ActionInstance {
    private Action action;
    private Piece actor;
    private Position targetPostion;

    public ActionInstance(Action action, Piece actor, Position targetPostion) {
        this.action = action;
        this.actor = actor;
        this.targetPostion = targetPostion;
    }

    public void apply(GameState gameState) {
        action.apply(this, gameState);
    }

    public Action getAction() {
        return action;
    }

    public void setAction(Action action) {
        this.action = action;
    }

    public Position getTargetPostion() {
        return targetPostion;
    }

    public void setTargetPostion(Position targetPostion) {
        this.targetPostion = targetPostion;
    }

    public Piece getActor() {
        return actor;
    }

    public void setActor(Piece actor) {
        this.actor = actor;
    }
}
