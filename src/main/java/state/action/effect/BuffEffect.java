package state.action.effect;

import state.Piece;
import state.action.Buff;

public class BuffEffect implements Effect {
    Buff buff;

    public BuffEffect(Buff buff) {
        this.buff = buff;
    }

    public Buff getBuff() {
        return buff;
    }

    public void setBuff(Buff buff) {
        this.buff = buff;
    }

    @Override
    public void apply(Piece targetPiece) {
        targetPiece.getBuffs().add(buff.copy());
    }

    @Override
    public Effect copy() {
        return new BuffEffect(buff.copy());
    }
}
