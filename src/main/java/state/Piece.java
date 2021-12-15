package state;

import game.Player;
import state.action.*;

import java.util.ArrayList;
import java.util.List;

public class Piece {
    private String visual;

    private int hp;
    private int move;
    private int attack;

    private boolean required;

    private List<Ability> abilities;

    private Player owningPlayer;
    private Position position;

    public Piece(int hp, int move, int attack, boolean required, List<Ability> abilities, String visual) {
        this.hp = hp;
        this.move = move;
        this.attack = attack;
        this.required = required;
        this.abilities = abilities;
        this.visual = visual;
    }

    public Player getOwningPlayer() {
        return owningPlayer;
    }

    public void setOwningPlayer(Player owningPlayer) {
        this.owningPlayer = owningPlayer;
    }

    public Piece copy() {
        List<Ability> copyAbilities = new ArrayList<>();

        for (Ability ability : abilities) {
            copyAbilities.add(ability.copy());
        }

        Piece retPiece = new Piece(hp, move, attack, required, copyAbilities, visual);
        retPiece.setOwningPlayer(owningPlayer);
        retPiece.setPosition(position);

        return retPiece;
    }

    public List<Action> getAllActions() {
        List<Action> actions = new ArrayList<>();

        if (move > 0 ) actions.add(new MoveAction(move));
        if (attack > 0 ) actions.add(new AttackAction(1));
        actions.add(new DoNothingAction(Action.Phase.MOVE));
        actions.add(new DoNothingAction(Action.Phase.ATTACK));

        actions.addAll(abilities);

        return actions;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public void addHp(int add) {
        this.hp += add;
    }

    public int getMove() {
        return move;
    }

    public void setMove(int move) {
        this.move = move;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public void setAbilities(List<Ability> abilities) {
        this.abilities = abilities;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getVisual() {
        return visual;
    }

    public void setVisual(String visual) {
        this.visual = visual;
    }
}
