package state;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Position {
    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDistance(Position otherPosition) {
        return Math.abs(otherPosition.x - x) + Math.abs(otherPosition.y - y);
    }

    public List<Position> getAdjacent(int worldSize) {
        List<Position> adjacent = new ArrayList<>();

        Position up = new Position(x, y - 1);
        Position left = new Position(x - 1, y);
        Position right = new Position(x + 1, y);
        Position down = new Position(x, y + 1);

        if (up.isValid(worldSize)) {
            adjacent.add(up);
        }
        if (left.isValid(worldSize)) {
            adjacent.add(left);
        }
        if (right.isValid(worldSize)) {
            adjacent.add(right);
        }
        if (down.isValid(worldSize)) {
            adjacent.add(down);
        }

        return adjacent;
    }

    public boolean isValid(int worldSize) {
        return x >= 0 && y >= 0 && x < worldSize && y < worldSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public Position flip(int worldSize) {
        return new Position(worldSize - 1 - x, worldSize - 1 - y);
    }

    public Position copy() {
        return new Position(x, y);
    }
}
