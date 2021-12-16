package state.action;

import java.util.ArrayList;
import java.util.List;

public class Buff {
    private int duration;

    private Modifier attackMod;
    private Modifier movementMod;
    private List<Ability> newAbilities;

    public Buff(int duration, Modifier attackMod, Modifier movementMod, List<Ability> newAbilities) {
        this.duration = duration;
        this.attackMod = attackMod;
        this.movementMod = movementMod;
        this.newAbilities = newAbilities;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public Modifier getAttackMod() {
        return attackMod;
    }

    public void setAttackMod(Modifier attackMod) {
        this.attackMod = attackMod;
    }

    public Modifier getMovementMod() {
        return movementMod;
    }

    public void setMovementMod(Modifier movementMod) {
        this.movementMod = movementMod;
    }

    public List<Ability> getNewAbilities() {
        return newAbilities;
    }

    public void setNewAbilities(List<Ability> newAbilities) {
        this.newAbilities = newAbilities;
    }

    public Buff copy() {
        List<Ability> copyAbilities = new ArrayList<>();
        for (Ability ability : newAbilities) {
            copyAbilities.add(ability.copy());
        }

        Modifier attackCopy = attackMod == null ? null : attackMod.copy();
        Modifier movementCopy = movementMod == null ? null : movementMod.copy();
        return new Buff(duration, attackCopy, movementCopy, copyAbilities);
    }
}
