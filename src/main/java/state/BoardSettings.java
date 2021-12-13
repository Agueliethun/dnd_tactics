package state;

import java.util.Map;

public class BoardSettings {
    private int boardSize;
    private Map<Position, Piece> startPositions;

    public BoardSettings(int boardSize, Map<Position, Piece> startPositions) {
        this.boardSize = boardSize;
        this.startPositions = startPositions;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public Map<Position, Piece> getStartPositions() {
        return startPositions;
    }

    public void setStartPositions(Map<Position, Piece> startPositions) {
        this.startPositions = startPositions;
    }
}
