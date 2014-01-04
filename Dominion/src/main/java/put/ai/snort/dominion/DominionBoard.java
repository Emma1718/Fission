/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package put.ai.snort.dominion;

import java.util.ArrayList;
import java.util.List;
import put.ai.snort.game.Move;
import put.ai.snort.game.Player;
import put.ai.snort.game.Player.Color;
import put.ai.snort.game.TypicalBoard;
import put.ai.snort.game.moves.MoveMove;
import put.ai.snort.game.moves.PlaceMove;
import put.ai.snort.game.moves.SkipMove;

abstract class SwappingMove implements Move {

    private List<int[]> swappings = new ArrayList<>();
    private Color myColor;

    public SwappingMove(Color myColor) {
        this.myColor = myColor;
    }

    public List<int[]> getSwappings() {
        return swappings;
    }

    @Override
    public Color getColor() {
        return myColor;
    }

    public abstract int getX();

    public abstract int getY();
}

class DominionPlaceMove extends SwappingMove implements PlaceMove {

    private int x, y;

    public DominionPlaceMove(Color myColor, int x, int y) {
        super(myColor);
        this.x = x;
        this.y = y;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }
}

class DominionSkipMove implements SkipMove {

    private Color color;

    public DominionSkipMove(Color color) {
        this.color = color;
    }

    @Override
    public Color getColor() {
        return color;
    }
}

class DominionMoveMove extends SwappingMove implements MoveMove {

    private int srcX, srcY, dstX, dstY;

    public DominionMoveMove(Color myColor, int srcX, int srcY, int dstX, int dstY) {
        super(myColor);
        this.srcX = srcX;
        this.srcY = srcY;
        this.dstX = dstX;
        this.dstY = dstY;
    }

    @Override
    public int getDstX() {
        return dstX;
    }

    @Override
    public int getDstY() {
        return dstY;
    }

    @Override
    public int getSrcX() {
        return srcX;
    }

    @Override
    public int getSrcY() {
        return srcY;
    }

    @Override
    public int getX() {
        return dstX;
    }

    @Override
    public int getY() {
        return dstY;
    }
}

public class DominionBoard extends TypicalBoard {

    public DominionBoard() {
        super(8);
        state[0][0] = Color.PLAYER1;
        state[3][3] = Color.PLAYER1;
        state[4][4] = Color.PLAYER1;
        state[7][7] = Color.PLAYER1;
        state[7][0] = Color.PLAYER2;
        state[4][3] = Color.PLAYER2;
        state[3][4] = Color.PLAYER2;
        state[0][7] = Color.PLAYER2;
    }

    protected DominionBoard(DominionBoard other) {
        super(other);
    }

    @Override
    public DominionBoard clone() {
        return new DominionBoard(this);
    }

    @Override
    public boolean canMove(Color color) {
        return hasCell(Color.EMPTY) && hasCell(color);
    }

    private void doPlace(DominionPlaceMove move) {
        if (!isValid(move.getX(), move.getY())) {
            throw new IllegalArgumentException("Move outside the board");
        }
        if (state[move.getX()][move.getY()] != Color.EMPTY) {
            throw new IllegalArgumentException("Cell is not empty");
        }
        state[move.getX()][move.getY()] = move.getColor();
        doSwap(move);
    }

    private void undoPlace(DominionPlaceMove move) {
        if (!isValid(move.getX(), move.getY())) {
            throw new IllegalArgumentException("Move outside the board");
        }
        if (state[move.getX()][move.getY()] != move.getColor()) {
            throw new IllegalArgumentException("Cell is empty");
        }
        state[move.getX()][move.getY()] = Color.EMPTY;
        undoSwap(move);
    }

    private void doJump(DominionMoveMove move) {
        if (!isValid(move.getSrcX(), move.getSrcY()) || !isValid(move.getDstX(), move.getDstY())) {
            throw new IllegalArgumentException("Move outside the board");
        }
        if (state[move.getSrcX()][move.getSrcY()] != move.getColor()) {
            throw new IllegalArgumentException("Cell does not belong to the player");
        }
        if (state[move.getDstX()][move.getDstY()] != Color.EMPTY) {
            throw new IllegalArgumentException("Cell is not empty");
        }
        state[move.getDstX()][move.getDstY()] = state[move.getSrcX()][move.getSrcY()];
        state[move.getSrcX()][move.getSrcY()] = Color.EMPTY;
        doSwap(move);
    }

    private void undoJump(DominionMoveMove move) {
        if (!isValid(move.getSrcX(), move.getSrcY()) || !isValid(move.getDstX(), move.getDstY())) {
            throw new IllegalArgumentException("Move outside the board");
        }
        if (state[move.getDstX()][move.getDstY()] != move.getColor()) {
            throw new IllegalArgumentException("Cell does not belong to the player");
        }
        if (state[move.getSrcX()][move.getSrcY()] != Color.EMPTY) {
            throw new IllegalArgumentException("Cell is not empty");
        }
        state[move.getSrcX()][move.getSrcY()] = state[move.getDstX()][move.getDstY()];
        state[move.getDstX()][move.getDstY()] = Color.EMPTY;
        undoSwap(move);
    }

    private void doSwap(SwappingMove move) {
        Color opp = Player.getOpponent(move.getColor());
        for (int[] d : ADJACENT) {
            int x = move.getX() + d[0];
            int y = move.getY() + d[1];
            if (!isValid(x, y)) {
                continue;
            }
            if (state[x][y] == opp) {
                state[x][y] = move.getColor();
                move.getSwappings().add(new int[]{x, y});
            }
        }
    }

    private void undoSwap(SwappingMove move) {
        Color opp = Player.getOpponent(move.getColor());
        for (int[] swapping : move.getSwappings()) {
            state[swapping[0]][swapping[1]] = opp;
        }
        move.getSwappings().clear();
    }

    @Override
    public void doMove(Move move) {
        if (move instanceof DominionPlaceMove) {
            doPlace((DominionPlaceMove) move);
        } else if (move instanceof DominionMoveMove) {
            doJump((DominionMoveMove) move);
        } else if (move instanceof DominionSkipMove) {
            //do nothing
        } else {
            throw new IllegalArgumentException("Unknown type of move");
        }
    }

    @Override
    public List<Move> getMovesFor(Color color) {
        List<Move> result = new ArrayList<>();
        for (int i = 0; i < getSize(); ++i) {
            for (int j = 0; j < getSize(); ++j) {
                if (getState(i, j) == color) {
                    addDrop(result, color, i, j);
                    addJump(result, color, i, j);
                }
            }
        }
        if (result.isEmpty()) {
            result.add(new DominionSkipMove(color));
        }
        return result;
    }

    @Override
    public void undoMove(Move move) {
        if (move instanceof DominionPlaceMove) {
            undoPlace((DominionPlaceMove) move);
        } else if (move instanceof DominionMoveMove) {
            undoJump((DominionMoveMove) move);
        } else if (move instanceof DominionSkipMove) {
            //do nothing
        } else {
            throw new IllegalArgumentException("Unknown type of move");
        }
    }
    private static final int[][] ADJACENT = new int[][]{
        {-1, -1}, {-1, 0}, {-1, 1},
        {0, -1}, {0, 1},
        {1, -1}, {1, 0}, {1, 1}
    };

    /**
     * Dodaje ruchy położenia kamienia na pustych polach około kamienia (x,y)
     */
    private void addDrop(List<Move> result, Color color, int x, int y) {
        assert isValid(x, y);
        assert getState(x, y) == color;
        for (int[] d : ADJACENT) {
            int i = x + d[0];
            int j = y + d[1];
            if (isValid(i, j) && getState(i, j) == Color.EMPTY) {
                result.add(new DominionPlaceMove(color, i, j));
            }
        }
    }
    private static final int[][] JUMP_RANGE = new int[][]{
        {-2, -2}, {-1, -2}, {0, -2}, {1, -2}, {2, -2},
        {2, -1}, {2, 0}, {2, 1}, {2, 2},
        {1, 2}, {0, 2}, {-1, 2}, {-2, 2},
        {-2, 1}, {-2, 0}, {-2, -1}
    };

    /**
     * Dodaje ruchy skoku kamienia (x,y) na puste pole w odległości dwóch
     * punktów kratowych
     */
    private void addJump(List<Move> result, Color color, int x, int y) {
        assert isValid(x, y);
        assert getState(x, y) == color;
        for (int[] d : JUMP_RANGE) {
            int i = x + d[0];
            int j = y + d[1];
            if (isValid(i, j) && getState(i, j) == Color.EMPTY) {
                result.add(new DominionMoveMove(color, x, y, i, j));
            }
        }
    }
}
