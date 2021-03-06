package state;

import game.Player;
import state.action.Action;
import state.action.ActionInstance;
import ui.UIEngine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameState {

    public enum State {
        IN_PROGRESS,
        PLAYER_1_WIN,
        PLAYER_2_WIN
    }

    private BoardSettings settings;
    private Map<Player, List<Piece>> playerPieces;

    private Player player1;
    private Player player2;

    private Player playerTurn;
    private Action.Phase turnPhase;
    private State state;

    private Piece currentTurnPiece;

    public GameState(BoardSettings settings, Player player1, Player player2) {
        this.settings = settings;
        this.player1 = player1;
        this.player2 = player2;

        playerPieces = new HashMap<>();
        playerPieces.put(player1, new ArrayList<>());
        playerPieces.put(player2, new ArrayList<>());

        playerTurn = player1;
        turnPhase = Action.Phase.MOVE;
        state = State.IN_PROGRESS;

        Map<Position, Piece> startingPositions = settings.getStartPositions();
        for (Map.Entry<Position, Piece> entry : startingPositions.entrySet()) {
            Piece player1Piece = entry.getValue().copy();
            player1Piece.setOwningPlayer(player1);
            player1Piece.setPosition(entry.getKey());
            playerPieces.get(player1).add(player1Piece);

            Piece player2Piece = entry.getValue().copy();
            player2Piece.setOwningPlayer(player2);
            player2Piece.setPosition(entry.getKey().flip(settings.getBoardSize()));
            playerPieces.get(player2).add(player2Piece);
        }
    }

    public void doTurn() {
        currentTurnPiece = null;
        UIEngine.getInstance().update(this);
        ActionInstance moveAction = playerTurn.getAction(this);
        currentTurnPiece = moveAction.getActor();
        applyAction(moveAction);  //move
        UIEngine.getInstance().update(this);
        applyAction(playerTurn.getAction(this));  //action
        UIEngine.getInstance().update(this);
        tick();
        recalculateState();
    }

    private void tick() {
        for (Piece piece : getAllPieces()) {
            if (piece.getOwningPlayer() == playerTurn) {
                piece.tick();
            }
        }
    }

    private void recalculateState() {
        for (Map.Entry<Player, List<Piece>> entry : playerPieces.entrySet()) {
            for (Piece piece : entry.getValue()) {
                if (piece.getHp() <= 0 && piece.isRequired()) {
                    setState(entry.getKey() == player1 ? State.PLAYER_2_WIN : State.PLAYER_1_WIN);
                    return;
                }
            }
        }
    }

    public void applyAction(ActionInstance action) {
        action.apply(this);
        if (turnPhase == Action.Phase.MOVE) {
            setTurnPhase(Action.Phase.ATTACK);
        } else if (turnPhase == Action.Phase.ATTACK) {
            setTurnPhase(Action.Phase.MOVE);
            setPlayerTurn((playerTurn == player1) ? player2 : player1);
        }
    }

    public Piece getPieceAtPosition(Position targetPostion) {
        for (Piece piece : getAllPieces()) {
            if (piece.getPosition().equals(targetPostion) && piece.getHp() >= 0) {
                return piece;
            }
        }
        return null;
    }

    public List<Piece> getValidTurnPieces() {
        if (currentTurnPiece != null) {
            return List.of(currentTurnPiece);
        } else {
            List<Piece> playerPieces = getPlayerPieces().get(playerTurn);
            playerPieces.removeIf(piece -> piece.getHp() <= 0);
            return playerPieces;
        }
    }

    public boolean isPositionFree(Position targetPostion) {
        for (Piece piece : getAllPieces()) {
            if (piece.getPosition().equals(targetPostion) && piece.getHp() > 0) {
                return false;
            }
        }
        return true;
    }

    public List<Piece> getAllPieces() {
        List<Piece> allPieces = new ArrayList<>();
        for (Map.Entry<Player, List<Piece>> entry : playerPieces.entrySet()) {
            allPieces.addAll(entry.getValue());
        }

        return allPieces;
    }

    public State getState() {
        return state;
    }

    public GameState(BoardSettings settings, Map<Player, List<Piece>> playerPieces) {
        this.settings = settings;
        this.playerPieces = playerPieces;
    }

    public BoardSettings getSettings() {
        return settings;
    }

    public void setSettings(BoardSettings settings) {
        this.settings = settings;
    }

    public Map<Player, List<Piece>> getPlayerPieces() {
        return playerPieces;
    }

    public void setPlayerPieces(Map<Player, List<Piece>> playerPieces) {
        this.playerPieces = playerPieces;
    }

    public Player getPlayerTurn() {
        return playerTurn;
    }

    public void setPlayerTurn(Player playerTurn) {
        this.playerTurn = playerTurn;
    }

    public Action.Phase getTurnPhase() {
        return turnPhase;
    }

    public void setTurnPhase(Action.Phase turnPhase) {
        this.turnPhase = turnPhase;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public GameState copy() {
        Map<Player, List<Piece>> playerPiecesCopy = new HashMap<>();
        for (Map.Entry<Player, List<Piece>> entry : playerPieces.entrySet()) {
            List<Piece> pieceCopy = new ArrayList<>();
            for (Piece piece : entry.getValue()) {
                pieceCopy.add(piece.copy());
            }

            playerPiecesCopy.put(entry.getKey(), pieceCopy);
        }

        GameState copy = new GameState(settings, playerPiecesCopy);
        copy.setPlayerTurn(playerTurn);
        copy.setTurnPhase(turnPhase);
        copy.setState(state);
        copy.setPlayer1(player1);
        copy.setPlayer2(player2);

        return copy;
    }
}
