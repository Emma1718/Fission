/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package put.ai.snort.linesofaction;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import put.ai.snort.game.Move;
import put.ai.snort.game.Player;
import put.ai.snort.game.Player.Color;
import put.ai.snort.game.TypicalBoard;

public class LoABoard extends TypicalBoard {

    public LoABoard(int boardSize) {
        super(boardSize);
        init();
    }

    public LoABoard(LoABoard other) {
        super(other);
    }

    private void init() {
        for (int i = 1; i <= getSize() - 2; ++i) {
            state[i][0] = Color.PLAYER1;
            state[i][getSize() - 1] = Color.PLAYER1;
            state[0][i] = Color.PLAYER2;
            state[getSize() - 1][i] = Color.PLAYER2;
        }
    }

    @Override
    public TypicalBoard clone() {
        return new LoABoard(this);
    }

    private static class Point {

        public int[] p = new int[2];

        public Point(int x, int y) {
            this.p[0] = x;
            this.p[1] = y;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Point)) {
                return false;
            }
            Point other = (Point) obj;
            return p[0] == other.p[0] && p[1] == other.p[1];
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(p);
        }

        public int getX() {
            return p[0];
        }

        public int getY() {
            return p[1];
        }

        @Override
        public String toString() {
            return String.format("(%d,%d)", getX(), getY());
        }
    }

    private void findNeighbours(Point current, Set<Point> visited, Deque<Point> queue) {
        for (int dx = -1; dx <= 1; ++dx) {
            for (int dy = -1; dy <= 1; ++dy) {
                if (dx == 0 && dy == 0) {
                    continue;
                }
                Point p = new Point(current.getX() + dx, current.getY() + dy);
                if (!isValid(p.getX(), p.getY())) {
                    continue;
                }
                if (!visited.contains(p)) {
                    queue.add(p);
                }
            }
        }
    }

    private int countReachable(int x, int y) {
        Set<Point> visited = new HashSet<>();
        Deque<Point> queue = new ArrayDeque<>();
        queue.add(new Point(x, y));
        Color c = getState(x, y);
        Point current;
        int n = 0;
        while ((current = queue.pollFirst()) != null) {
            if (visited.contains(current)) {
                continue;
            }
            visited.add(current);
            if (state[current.getX()][current.getY()] == c) {
                ++n;
                findNeighbours(current, visited, queue);
            }
        }
        return n;
    }

    private int countInColor(Color c) {
        int n = 0;
        for (int x = 0; x < getSize(); ++x) {
            for (int y = 0; y < getSize(); ++y) {
                if (state[x][y] == c) {
                    ++n;
                }
            }
        }
        return n;
    }

    private boolean allConnected(Color c) {
        int all = countInColor(c);
        if (all <= 1) {
            return true;
        }
        for (int x = 0; x < getSize(); ++x) {
            for (int y = 0; y < getSize(); ++y) {
                if (state[x][y] == c) {
                    return countReachable(x, y) == all;
                }
            }
        }
        //unreachable code not detected
        return true;
    }

    @Override
    public boolean canMove(Color color) {
        //false, jeżeli przeciwnik wszystko połączył
        return !allConnected(Player.getOpponent(color));
    }

    @Override
    public void doMove(Move move) {
        LoAMove m = (LoAMove) move;
        if (state[m.getSrcX()][m.getSrcY()] != m.getColor()) {
            throw new IllegalArgumentException("Source cell does not contain player's piece");
        }
        state[m.getSrcX()][m.getSrcY()] = Color.EMPTY;
        m.setPrevious(state[m.getDstX()][m.getDstY()]);
        state[m.getDstX()][m.getDstY()] = m.getColor();

    }

    private int countInLine(int x, int y, int dx, int dy) {
        int n = 0;
        while (isValid(x, y)) {
            if (getState(x, y) != Color.EMPTY) {
                ++n;
            }
            x += dx;
            y += dy;
        }
        return n;
    }

    private int canMoveInLine(Color opponent, int x, int y, int dx, int dy) {
        int n = countInLine(x, y, dx, dy) + countInLine(x, y, -dx, -dy) - 1;
        for (int i = 1; i <= n; ++i) {
            int lx = x + i * dx;
            int ly = y + i * dy;
            if (!isValid(lx, ly)) {
                return 0;
            }
            if (i < n && getState(lx, ly) == opponent) {
                return 0;
            }
        }
        return n;
    }

    private void getMovesFor(Color c, int x, int y, List<Move> moves) {
        Color opponent = Player.getOpponent(c);
        for (int dx = -1; dx <= 1; ++dx) {
            for (int dy = -1; dy <= 1; ++dy) {
                if (dx == 0 && dy == 0) {
                    continue;
                }
                int n = canMoveInLine(opponent, x, y, dx, dy);
                if (n > 0) {
                    moves.add(new LoAMove(c, x, y, x + n * dx, y + n * dy));
                }
            }
        }
    }

    @Override
    public List<Move> getMovesFor(Color color) {
        List<Move> moves = new ArrayList<>();
        for (int x = 0; x < getSize(); ++x) {
            for (int y = 0; y < getSize(); ++y) {
                if (getState(x, y) == color) {
                    getMovesFor(color, x, y, moves);
                }
            }
        }
        return moves;
    }

    @Override
    public void undoMove(Move move) {
        LoAMove m = (LoAMove) move;
        state[m.getDstX()][m.getDstY()] = m.getPrevious();
        m.resetPrevious();
        state[m.getSrcX()][m.getSrcY()] = m.getColor();
    }
}
