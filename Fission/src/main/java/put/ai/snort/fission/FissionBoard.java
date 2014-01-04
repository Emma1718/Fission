package put.ai.snort.fission;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import put.ai.snort.game.Move;
import put.ai.snort.game.Player;
import put.ai.snort.game.Player.Color;
import put.ai.snort.game.TypicalBoard;

public class FissionBoard extends TypicalBoard {

	public FissionBoard(int size) {
		super(size);
		init();
	}

	public FissionBoard(FissionBoard other) {
		super(other);
	}

	@Override
	public TypicalBoard clone() {
		return new FissionBoard(this);
	}

	@Override
	public boolean canMove(Color color) {
		int myStones = countStones(color);
		int oppStones = countStones(Player.getOpponent(color));
		if (myStones == oppStones && (myStones == 0 || myStones == 1)) {
			return false;
		}
		return !getMovesFor(color).isEmpty();
	}

	private int countStones(Color c) {
		int n = 0;
		for (int i = 0; i < getSize(); ++i) {
			for (int j = 0; j < getSize(); ++j) {
				if (getState(i, j) == c) {
					++n;
				}
			}
		}
		return n;
	}

	private void init() {
		for (int y = 0; y < getSize(); ++y) {
			int n, d;
			if (y < getSize() / 2) {
				n = y;
				d = 0;
			} else {
				n = getSize() - y - 1;
				d = 1;
			}
			for (int x = getSize() / 2 - n; x < getSize() / 2 + n; x += 2) {
				state[x + d][y] = Color.PLAYER1;
				state[x + (1 - d)][y] = Color.PLAYER2;
			}
		}
	}

	private boolean stoppedByWall(FissionMove m) {
		int x = m.getDstX();
		int y = m.getDstY();
		if (m.isHorizontal() && (x == 0 || x == getSize() - 1)) {
			return true;
		}
		if (m.isVertical() && (y == 0 || y == getSize() - 1)) {
			return true;
		}
		if (m.isDiagonal()
				&& (x == 0 || y == 0 || x == getSize() - 1 || y == getSize() - 1)) {
			return true;
		}
		return false;
	}

	@Override
	public void doMove(Move move) {

		FissionMove m = (FissionMove) move;
		if (state[m.getSrcX()][m.getSrcY()] != m.getColor()) {
			throw new IllegalArgumentException(
					"Source cell does not contain player's piece");
		}
		if (state[m.getDstX()][m.getDstY()] != Color.EMPTY) {
			throw new IllegalArgumentException("Destination cell is not empty");
		}
		state[m.getSrcX()][m.getSrcY()] = Color.EMPTY;
		state[m.getDstX()][m.getDstY()] = m.getColor();
		m.getRemoved().clear();
		if (!stoppedByWall(m)) {
			for (int i = -1; i <= 1; ++i) {
				for (int j = -1; j <= 1; ++j) {
					int x = m.getDstX() + i;
					int y = m.getDstY() + j;
					Color c = getState(x, y);
					//System.out.println("x: " + x + " y: " + y);
					if (c != Color.EMPTY) {
						m.getRemoved().add(new Piece(c, x, y));
						state[x][y] = Color.EMPTY;

					}
				}
			}
		}
	}

	private static class Vector {

		public int x, y;

		public Vector(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public static Vector sum(Vector a, int n, Vector b) {
			return new Vector(a.x + n * b.x, a.y + n * b.y);
		}
	}

	private static List<Vector> vectors = Arrays.asList(new Vector(0, 1),
			new Vector(1, 0), new Vector(0, -1), new Vector(-1, 0), new Vector(
					1, 1), new Vector(1, -1), new Vector(-1, 1), new Vector(-1,
					-1));

	private void getMovesFor(Color color, int x, int y, List<Move> result) {
		Vector base = new Vector(x, y);
		for (Vector v : vectors) {
			for (int i = 1;; ++i) {
				Vector n = Vector.sum(base, i, v);
				if (!isValid(n.x, n.y) || getState(n.x, n.y) != Color.EMPTY) {
					// można się ruszyć do pola base+(i-1)*v o ile i>1
					if (i > 1) {
						n = Vector.sum(base, i - 1, v);
						FissionMove m = new FissionMove(color, x, y, n.x, n.y);
						result.add(m);
					}
					break;
				}
			}
		}
	}

	public void rateMove(FissionMove m) {
		System.out.println(m.getRemoved().size());
	}

	@Override
	public List<Move> getMovesFor(Color color) {
		List<Move> result = new ArrayList<>();
		for (int x = 0; x < getSize(); ++x) {
			for (int y = 0; y < getSize(); ++y) {
				if (state[x][y] == color) {
					getMovesFor(color, x, y, result);
				}
			}

		}
		return result;
	}

	@Override
	public void undoMove(Move move) {
		FissionMove m = (FissionMove) move;
		// if (state[m.getSrcX()][m.getSrcY()] != Color.EMPTY) {
		// throw new
		// IllegalArgumentException("Source cell does not contain player's piece");
		// }
		// if (state[m.getDstX()][m.getDstY()] != m.getColor()) {
		// throw new IllegalArgumentException("Destination cell is not empty");
		// }
		for (Piece p : m.getRemoved()) {
			state[p.getX()][p.getY()] = p.getColor();
		}
		state[m.getDstX()][m.getDstY()] = Color.EMPTY;
		state[m.getSrcX()][m.getSrcY()] = m.getColor();
	}

	@Override
	public int rateMove(Color col, Move m) {
		int rate;
		int theSameCol = 0, otherCol = 0;
		FissionMove move = (FissionMove) m;
		int xi = countStones(col);
		int yi = countStones(Player.getOpponent(col));
		rate = xi - yi;
		rate*=2;
		//System.out.println("Fission: " + (xi - yi));//
		//System.out
		//		.println("Usunięte: " + ((FissionMove) m).getRemoved().size());
		//System.out.println("Polozenie: " + ((FissionMove) m).getDstX() + "  "
		//		+ ((FissionMove) m).getDstY());
		if (stoppedByWall(move)) {
			for (int i = -1; i <= 1; ++i) {
				for (int j = -1; j <= 1; ++j) {
					int x = move.getDstX() + i;
					int y = move.getDstY() + j;
					Color c = getState(x, y);
					if (c == col) {
						theSameCol++;
			//			System.out
			//					.println("Ten sam kolor x: " + x + " y: " + y);
					} else if(c==Player.getOpponent(col)) {
						otherCol++;
					}
			

				}
			}
			if ((move.getDstX() == 0 && move.getDstY() == 0)
					|| (move.getDstX() == 0 && move.getDstY() == this.getSize() - 1)
					|| (move.getDstX() == this.getSize() - 1 && move.getDstY() == 0)
					|| (move.getDstX() == this.getSize() - 1 && move.getDstY() == this
							.getSize() - 1)) {
				rate=-1;

			}

		}
		
		if(theSameCol > 1) rate-=1;
		if(otherCol > 0) rate+=1;
		return rate;
		
		
	}

	public void takeStats(Move m) {

	}
}
