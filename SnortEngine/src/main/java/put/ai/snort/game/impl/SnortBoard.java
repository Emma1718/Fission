/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package put.ai.snort.game.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import put.ai.snort.game.Move;
import put.ai.snort.game.Player;
import put.ai.snort.game.Player.Color;
import put.ai.snort.game.TypicalBoard;

public class SnortBoard extends TypicalBoard {

    public SnortBoard(int size) {
        super(size);
    }

    private SnortBoard(SnortBoard base) {
        super(base);
    }

    @Override
    public void doMove(Move _m) {
        SnortMove m = (SnortMove) _m;
        if (state[m.getX()][m.getY()] != Color.EMPTY) {
            throw new IllegalArgumentException("Move on non-empty cell");
        }
        state[m.getX()][m.getY()] = m.getColor();
    }

    @Override
    public void undoMove(Move _m) {
        SnortMove m = (SnortMove) _m;
        if (state[m.getX()][m.getY()] != m.getColor()) {
            throw new IllegalArgumentException("Undo on invalid cell");
        }
        state[m.getX()][m.getY()] = Color.EMPTY;
    }

    @Override
    public SnortBoard clone() {
        return new SnortBoard(this);
    }

    @Override
    public List<Move> getMovesFor(Color c) {
        if (c == Color.EMPTY) {
            throw new IllegalArgumentException("Color for getMovesFor must be well defined");
        }
        Color opp = Player.getOpponent(c);
        ArrayList<Move> result = new ArrayList<>();
        for (int i = 0; i < getSize(); ++i) {
            for (int j = 0; j < getSize(); ++j) {
                if (canMove(opp, i, j)) {
                    result.add(new SnortMove(i, j, c));
                }
            }
        }
        return result;
    }

    private boolean canMove(Color opp, int i, int j) {
        return getState(i, j) == Color.EMPTY && getState(i - 1, j) != opp && getState(i + 1, j) != opp && getState(i, j - 1) != opp && getState(i, j + 1) != opp;
    }

    @Override
    public boolean canMove(Color c) {
        c = Player.getOpponent(c);
        for (int i = 0; i < getSize(); ++i) {
            for (int j = 0; j < getSize(); ++j) {
                if (canMove(c, i, j)) {
                    return true;
                }
            }
        }
        return false;
    }

	@Override
	public int rateMove(Color c, Move m) {
		return 0;
		// TODO Auto-generated method stub
		
	}
}
