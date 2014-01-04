/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package put.ai.snort.cleave;

import java.util.ArrayList;
import java.util.List;
import put.ai.snort.game.Move;
import put.ai.snort.game.Player;
import put.ai.snort.game.Player.Color;
import put.ai.snort.game.TypicalBoard;

public class CleaveBoard extends TypicalBoard {

    private CleaveBoard(CleaveBoard other) {
        super(other);
    }

    public CleaveBoard(int size) {
        super(size);
        if (size < 7) {
            throw new IllegalArgumentException("Board can not be smaller than 7x7");
        }
        for (int i = 0; i < size; ++i) {
            state[i][0] = Color.PLAYER2;
            state[i][2] = Color.PLAYER2;
            state[i][size - 1] = Color.PLAYER1;
            state[i][size - 3] = Color.PLAYER1;
        }
    }

    @Override
    public boolean canMove(Color color) {
        int n = 0;
        for (int x = 0; x < state.length; ++x) {
            for (int y = 0; y < state.length; ++y) {
                if (state[x][y] == color) {
                    ++n;
                    if (n > 2) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean isOwnedBy(int x, int y, Color def) {
        if (!isValid(x, y)) {
            return true;
        }
        return state[x][y] == def;
    }

    private void capture(int x, int y, CleaveMove m) {
        if (x >= 0 && y >= 0 && x < state.length && y < state.length) {
            m.getCaptured().add(new Piece(state[x][y], x, y));
            state[x][y] = Color.EMPTY;
        }
    }

    @Override
    public void doMove(Move move) {
        CleaveMove m = (CleaveMove) move;
        if (state[m.getSrcX()][m.getSrcY()] != m.getColor()) {
            throw new IllegalArgumentException("Source cell does not contain player's piece");
        }
        if (state[m.getDstX()][m.getDstY()] != Color.EMPTY) {
            throw new IllegalArgumentException("Destination cell is not empty");
        }
        if (m.getSrcX() != m.getDstX() && m.getSrcY() != m.getDstY()) {
            throw new IllegalArgumentException("Diagonal moves are forbidden");
        }
        m.getCaptured().clear();
        state[m.getSrcX()][m.getSrcY()] = Color.EMPTY;
        state[m.getDstX()][m.getDstY()] = m.getColor();
        Color opp = Player.getOpponent(m.getColor());
        int[][] tab = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        //Intervention
        for (int i = 0; i < tab.length / 2; ++i) {
            int ax = m.getDstX() + tab[2 * i][0];
            int ay = m.getDstY() + tab[2 * i][1];
            int bx = m.getDstX() + tab[2 * i + 1][0];
            int by = m.getDstY() + tab[2 * i + 1][1];
            if (isOwnedBy(ax, ay, opp) && isOwnedBy(bx, by, opp)) {
                capture(ax, ay, m);
                capture(bx, by, m);
            }
        }
        //Custodian
        for (int[] d : tab) {
            int x = m.getDstX() + d[0];
            int y = m.getDstY() + d[1];
            if (getState(x, y) == opp && isOwnedBy(m.getDstX() + 2 * d[0], m.getDstY() + 2 * d[1], m.getColor())) {
                capture(x, y, m);
            }
        }
    }

    @Override
    public void undoMove(Move move) {
        CleaveMove m = (CleaveMove) move;
        if (state[m.getDstX()][m.getDstY()] != m.getColor()) {
            throw new IllegalArgumentException("Source cell does not contain player's piece");
        }
        if (state[m.getSrcX()][m.getSrcY()] != Color.EMPTY) {
            throw new IllegalArgumentException("Destination cell is not empty");
        }
        state[m.getSrcX()][m.getSrcY()] = m.getColor();
        state[m.getDstX()][m.getDstY()] = Color.EMPTY;
        for (Piece p : m.getCaptured()) {
            state[p.getX()][p.getY()] = p.getColor();
        }
        m.getCaptured().clear();
    }

    @Override
    public List<Move> getMovesFor(Color color) {
        List<Move> result = new ArrayList<>();
        for (int x = 0; x < state.length; ++x) {
            for (int y = 0; y < state.length; ++y) {
                if (state[x][y] != color) {
                    continue;
                }
                for (int i = x + 1; i < state.length; ++i) {
                    if (state[i][y] == Color.EMPTY) {
                        result.add(new CleaveMove(color, x, y, i, y));
                    } else {
                        break;
                    }
                }
                for (int i = x - 1; i >= 0; --i) {
                    if (state[i][y] == Color.EMPTY) {
                        result.add(new CleaveMove(color, x, y, i, y));
                    } else {
                        break;
                    }
                }
                for (int i = y + 1; i < state.length; ++i) {
                    if (state[x][i] == Color.EMPTY) {
                        result.add(new CleaveMove(color, x, y, x, i));
                    } else {
                        break;
                    }
                }
                for (int i = y - 1; i >= 0; --i) {
                    if (state[x][i] == Color.EMPTY) {
                        result.add(new CleaveMove(color, x, y, x, i));
                    } else {
                        break;
                    }
                }
            }
        }
        return result;
    }

    @Override
    public CleaveBoard clone() {
        return new CleaveBoard(this);
    }
}
