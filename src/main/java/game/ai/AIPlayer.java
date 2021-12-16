package game.ai;

import game.Player;
import state.GameState;
import state.Piece;
import state.Position;
import state.action.Action;
import state.action.ActionInstance;
import state.action.DoNothingAction;
import state.action.TileTarget;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class AIPlayer implements Player {
    private static long timeAvailable = TimeUnit.SECONDS.toNanos(3000);

    private int depth;
    private long startTime;

    public AIPlayer(int depth) {
        this.depth = depth;
    }

    public ActionInstance getAction(GameState state) {
        startTime = System.nanoTime();

        return calculateBestAction(state);
    }

    private ActionInstance calculateBestAction(GameState state) {
        ActionInstance bestAction = minimax(state, depth, true).getX();
        System.out.println("Total time used: " + (System.nanoTime() - startTime));
        return bestAction;
    }

    private Tuple<ActionInstance, Double> minimax(GameState state, int depth, boolean max) {
        if (System.nanoTime() - startTime >= timeAvailable) {
            if (state.getPlayerTurn() == this) {
                return new Tuple<>(null, Double.NEGATIVE_INFINITY);
            } else {
                return new Tuple<>(null, Double.POSITIVE_INFINITY);
            }
        }

        if (depth == 0) {
            double val = state.getPlayerTurn() == this ? 1 : -1;
            val = 1;
            return new Tuple<>(null, val * evaluateState(state));
        }

        List<ActionInstance> actions = getPotentialMoves(state, state.getValidTurnPieces());

//        if (state.getTurnPhase() == Action.Phase.ATTACK) {
//            System.out.println();
//        }

        Tuple<ActionInstance, Double> bestAction = (max) ? new Tuple<>(null, Double.NEGATIVE_INFINITY) : new Tuple<>(null, Double.POSITIVE_INFINITY);
        for (ActionInstance instance : actions) {
            GameState copyState = state.copy();
            ActionInstance copyInstance = getCopyInstanceFromInstance(instance, copyState);
            copyState.applyAction(copyInstance);

            Tuple<ActionInstance, Double> action = minimax(copyState, depth - 1, copyState.getPlayerTurn() == this);
            if (copyState.getPlayerTurn() != state.getPlayerTurn()) {
                action.setY(action.getY() * -1);
            }
            if (max) {
                if (action.getY() != null && action.getY() > bestAction.getY()) {
                    bestAction = new Tuple<>(instance, action.getY());
                }
            } else {
                if (action.getY() != null && action.getY() < bestAction.getY()) {
                    bestAction = new Tuple<>(instance, action.getY());
                }
            }
        }

        return bestAction;
    }

    private ActionInstance getCopyInstanceFromInstance(ActionInstance instance, GameState copyState) {
        Position oldPos = instance.getActor().getPosition();
        Piece newPiece = copyState.getPieceAtPosition(oldPos);

        String oldDesc = instance.getAction().getDesc();
        try {
            Action newAction = newPiece.getAllActions().stream().filter(a -> a.getDesc().equals(oldDesc)).findFirst().get();
            return new ActionInstance(newAction, newPiece, instance.getTargetPostion().copy());
        } catch (NullPointerException | NoSuchElementException npe) {
            npe.printStackTrace();
        }

        return null;
    }

    private double evaluateState(GameState state) {
        int hpVal = 0;
        for (Piece piece : state.getAllPieces()) {
            int isOurs = (piece.getOwningPlayer() == state.getPlayerTurn()) ? 1 : -1;
            hpVal += (piece.isRequired()) ? 10 * isOurs * piece.getHp() : isOurs * piece.getHp();
        }
        if (hpVal > 10) {
//            System.out.println("here with " + hpVal + " points");
        }

        return hpVal;
    }

    private List<ActionInstance> getPotentialMoves(GameState state, List<Piece> potentialPieces) {
        List<ActionInstance> instances = new ArrayList<>();

        for (Piece piece : potentialPieces) {
            for (Action action : piece.getAllActions()) {
                if (action.getPhase() == state.getTurnPhase()) {
                    if (action instanceof DoNothingAction) {
                        instances.add(new ActionInstance(action, piece, piece.getPosition()));
                    } else {
                        for (Position target : getPossibleTargets(piece, action, state)) {
                            ActionInstance instance = new ActionInstance(action, piece, target);
                            if (instance.getAction().getTargets(instance, state).size() > 0) {
                                instances.add(instance);
                            }
                        }
                    }
                }
            }
        }

        return instances;
    }

    private List<Position> getPossibleTargets(Piece piece, Action action, GameState state) {
        TileTarget shape = new TileTarget(action.getRange(), TileTarget.Shape.CIRCLE);
        return shape.getPositions(piece.getPosition(), state.getSettings().getBoardSize());
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }
}
