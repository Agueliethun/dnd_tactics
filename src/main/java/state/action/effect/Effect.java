package state.action.effect;

import state.Piece;

public interface Effect {
    void apply(Piece targetPiece);
    Effect copy();
}
