package state.action.effect;

import state.Piece;

public class HPEffect implements Effect {
    int amount;

    public HPEffect(int amount) {
        this.amount = amount;
    }

    @Override
    public void apply(Piece targetPiece) {
        targetPiece.addHp(amount);
    }

    @Override
    public Effect copy() {
        return new HPEffect(amount);
    }
}
