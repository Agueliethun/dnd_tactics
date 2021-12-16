package state.action;

public class Modifier {
    private double mult;
    private int add;

    public Modifier() {
        this.mult = 1;
        this.add = 0;
    }

    public Modifier(double mult, int add) {
        this.mult = mult;
        this.add = add;
    }

    public int getResult(int base) {
        return (int)Math.round(base * mult) + add;
    }

    public Modifier addModifier(Modifier other) {
        return new Modifier(other.mult * mult, other.add + add);
    }

    public double getMult() {
        return mult;
    }

    public void setMult(double mult) {
        this.mult = mult;
    }

    public int getAdd() {
        return add;
    }

    public void setAdd(int add) {
        this.add = add;
    }

    public Modifier copy() {
        return new Modifier(mult, add);
    }
}
