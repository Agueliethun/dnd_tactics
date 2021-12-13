package state.action;

import state.GameState;
import state.Piece;
import state.Position;

import java.util.ArrayList;
import java.util.List;

public class TileTarget {

    public enum Shape {
        CIRCLE
    }

    private int size;
    private Shape shape;

    public TileTarget(int size, Shape shape) {
        this.size = size;
        this.shape = shape;
    }

    public List<Position> getPositions(Position targetPosition, int worldSize) {
        if (shape == Shape.CIRCLE) {
            return getCircle(targetPosition, size, worldSize);
        }

        return new ArrayList<>();
    }

    private List<Position> getCircle(Position currentPosition, int currentSize, int worldSize) {
        return getCircle(new ArrayList<>(), currentPosition, currentSize, worldSize);
    }

    private List<Position> getCircle(List<Position> positions, Position currentPosition, int currentSize, int worldSize) {
        if (currentSize > 0) {
            if (!positions.contains(currentPosition)) {
                positions.add(currentPosition);

                for (Position adjacent : currentPosition.getAdjacent(worldSize)) {
                    positions.addAll(getCircle(positions, adjacent, currentSize - 1, worldSize));
                }
            }
        }

        return positions;
    }

    public TileTarget copy() {
        return new TileTarget(size, shape);
    }
}
