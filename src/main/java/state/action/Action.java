package state.action;

import state.GameState;

public abstract class Action {

    public enum Phase {
        MOVE,
        ATTACK
    }

    private Phase phase;
    private int range;

    public Action(Phase phase, int range) {
        this.phase = phase;
        this.range = range;
    }

    public abstract String getDesc();

    public abstract Action copy();

    public abstract boolean apply(ActionInstance actionInstance, GameState gameState);

    public Phase getPhase() {
        return phase;
    }

    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }
}
