package game;

import state.BoardSettings;
import state.GameState;

public class GameRunner {
    private final GameState gameState;

    public GameRunner(Player player1, Player player2, BoardSettings settings) {
        this.gameState = new GameState(settings, player1, player2);
    }

    public void runGame() {
        while (gameState.getState().equals(GameState.State.IN_PROGRESS)) {
            gameState.doTurn();
        }
        System.out.println(gameState.getState());
    }

    public GameState getGameState() {
        return gameState;
    }
}
