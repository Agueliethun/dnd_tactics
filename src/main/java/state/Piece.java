package state;

import game.Player;
import state.action.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Piece {
    private String visual;

    private int hp;
    private int move;
    private int attack;

    private boolean required;

    private List<Ability> abilities;
    private List<Buff> buffs;

    private Player owningPlayer;
    private Position position;

    public Piece(int hp, int move, int attack, boolean required, List<Ability> abilities, String visual) {
        this.hp = hp;
        this.move = move;
        this.attack = attack;
        this.required = required;
        this.abilities = abilities;
        this.buffs = new ArrayList<>();
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

        List<Buff> copyBuffs = new ArrayList<>();
        for (Buff buff : buffs) {
            copyBuffs.add(buff.copy());
        }

        Piece retPiece = new Piece(hp, move, attack, required, copyAbilities, visual);
        if (owningPlayer != null) {
            retPiece.setOwningPlayer(owningPlayer);
        }
        if (position != null) {
            retPiece.setPosition(position.copy());
        }
        retPiece.setBuffs(copyBuffs);

        return retPiece;
    }

    public List<Action> getAllActions() {
        List<Action> actions = new ArrayList<>();

        if (getMove() > 0 ) actions.add(new MoveAction(getMove()));
        if (getAttack() > 0 ) actions.add(new AttackAction(1));
        actions.add(new DoNothingAction(Action.Phase.MOVE));
        actions.add(new DoNothingAction(Action.Phase.ATTACK));

        actions.addAll(getAbilities());

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
        Modifier moveModifier = new Modifier(1, 0);
        for (Buff buff : buffs) {
            Modifier buffMoveMod = buff.getMovementMod();
            if (buffMoveMod != null) {
                moveModifier = moveModifier.addModifier(buffMoveMod);
            }
        }
        return moveModifier.getResult(move);
    }

    public void setMove(int move) {
        this.move = move;
    }

    public int getAttack() {
        Modifier attackModifier = new Modifier(1, 0);
        for (Buff buff : buffs) {
            Modifier buffAttackMod = buff.getAttackMod();
            if (buffAttackMod != null) {
                attackModifier = attackModifier.addModifier(buffAttackMod);
            }
        }
        return attackModifier.getResult(attack);
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public List<Ability> getAbilities() {
        List<Ability> allAbilities = new ArrayList<>(abilities);
        for (Buff buff : buffs) {
            allAbilities.addAll(buff.getNewAbilities());
        }
        return allAbilities;
    }

    public void setAbilities(List<Ability> abilities) {
        this.abilities = abilities;
    }

    public void addAbility(Ability newAbility) {
        abilities.add(newAbility);
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

    public List<Buff> getBuffs() {
        return buffs;
    }

    public void setBuffs(List<Buff> buffs) {
        this.buffs = buffs;
    }

    public void tick() {
        buffs.forEach(buff -> buff.setDuration(buff.getDuration() - 1));
        buffs.removeIf(buff -> buff.getDuration() <= 0);
    }
}
