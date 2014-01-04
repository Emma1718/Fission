/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package put.ai.snort.alphabetaplayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import put.ai.snort.game.Board;
import put.ai.snort.game.Move;
import put.ai.snort.game.Player;
import put.ai.snort.game.Player.Color;
import put.ai.snort.fission.*;

public class AlphaBetaPlayer extends Player {

	enum Type {
		MAX, MIN
	};

	@Override
	public String getName() {
		return "AlphaBetaPlayer 12345";
	}

	@Override
	public Move nextMove(Board b) {
		PrintStream p = null;
		try {
			p = new PrintStream("/tmp/plik.txt");
			p.println(Calendar.getInstance().getTime());
			p.close();
		} catch (FileNotFoundException ex) {
			Logger.getLogger(AlphaBetaPlayer.class.getName()).log(Level.SEVERE,
					null, ex);
			throw new RuntimeException(ex);
		} finally {
			if (p != null) {
				p.close();
			}
		}

		List<Move> moves = b.getMovesFor(getColor());
		int i = 0;
		int maximum = -15, chosen = 0;
		Move move = moves.get(1);
		for (Move m : moves) {
			b.doMove(m);
			int rate = alphaBeta(b, 3, -500, 500, Type.MIN);
			b.undoMove(m);
			if (rate > maximum) {
				maximum = rate;
				move = m;
				chosen = i;
			}
			i++;
		}

		System.out.println("Wybrany ruch: " + chosen + " Ocena: " + maximum);
		return move;
	}

	public int rateMove(Board b) {
		int rate1, rate2, rate;
		int theSameCol = 0, otherCol = 0;
		FissionBoard fb = (FissionBoard) b.clone();
		// FissionMove move = (FissionMove) m;
		int xi = getStonesAmount(getColor(), b);

		int yi = getStonesAmount(Player.getOpponent(getColor()), b);
		rate1 = xi - yi;
		rate1 *= 2;
		int zi = getNeighbours(getColor(), b);
		int vi = getNeighbours(getOpponent(getColor()), b);
		rate2 = vi - zi;
		
		rate = rate1 + rate2;
		

		// System.out.println("Fission: " + (xi - yi));//
		// System.out
		// .println("Usunięte: " + ((FissionMove) m).getRemoved().size());
		// System.out.println("Polozenie: " + ((FissionMove) m).getDstX() + "  "
		// + ((FissionMove) m).getDstY());
		/*
		 * if (isNextToWall(move, fb)) { for (int i = -1; i <= 1; ++i) { for
		 * (int j = -1; j <= 1; ++j) { int x = move.getDstX() + i; int y =
		 * move.getDstY() + j; Color c = b.getState(x, y); if (c == getColor())
		 * { theSameCol++; // System.out // .println("Ten sam kolor x: " + x +
		 * " y: " + y); } else if (c == Player.getOpponent(getColor())) {
		 * otherCol++; }
		 * 
		 * } } if ((move.getDstX() == 0 && move.getDstY() == 0) ||
		 * (move.getDstX() == 0 && move.getDstY() == b.getSize() - 1) ||
		 * (move.getDstX() == b.getSize() - 1 && move.getDstY() == 0) ||
		 * (move.getDstX() == b.getSize() - 1 && move.getDstY() == b .getSize()
		 * - 1)) { rate = -1;
		 * 
		 * }
		 * 
		 * }
		 */

		/*
		 * if (theSameCol > 1) rate -= 1; if (otherCol > 0) rate += 1;
		 */
		return rate;

	}

	private int getStonesAmount(Color c, Board b) {
		int n = 0;
		for (int i = 0; i < b.getSize(); ++i) {
			for (int j = 0; j < b.getSize(); ++j) {
				if (b.getState(i, j) == c) {
					++n;
				}
			}
		}
		return n;
	}

	private int getNeighbours(Color c, Board b) {
		int pairs = 0;
		for (int i = 0; i < b.getSize(); ++i) {
			for (int j = 0; j < b.getSize(); ++j) {
				if (b.getState(i, j) == c) {
					for (int z = -1; z <= 1; ++z) {
						for (int v = -1; v <= 1; ++v) {
							if ((b.getState(i + z, j + v) == c) && ((z != 0)
									|| (v != 0))) {
								pairs++;
							}
						}
					}
				}
			}
		}
		return pairs;
	}

	private boolean isNextToWall(FissionMove m, Board b) {
		int x = m.getDstX();
		int y = m.getDstY();
		if ((m.getSrcY() == m.getDstY()) && (x == 0 || x == b.getSize() - 1)) {
			return true;
		}
		if ((m.getSrcX() == m.getDstX()) && (y == 0 || y == b.getSize() - 1)) {
			return true;
		}
		if ((!(m.getSrcY() == m.getDstY()) && !(m.getSrcX() == m.getDstX()))
				&& (x == 0 || y == 0 || x == b.getSize() - 1 || y == b
						.getSize() - 1)) {
			return true;
		}
		return false;
	}

	private int alphaBeta(Board board, int depth, int alpha, int beta, Type type) {

		if (depth == 0)
			return (rateMove(board));
		if (type == Type.MAX) {
			for (Move m : board.getMovesFor(getColor())) {
				board.doMove(m);
				int val = alphaBeta(board, depth - 1, alpha, beta, Type.MIN);
				board.undoMove(m);
				alpha = Math.max(val, alpha);
				if (alpha >= beta)
					return beta; // cutoff
			}
			return alpha;
		} else { // type == MIN
			for (Move m : board.getMovesFor(getOpponent(getColor()))) {
				board.doMove(m);
				int val = alphaBeta(board, depth - 1, alpha, beta, Type.MAX);
				board.undoMove(m);
				beta = Math.min(val, beta);
				if (alpha >= beta)
					return alpha; // cutoff
			} // endfor
			return beta;

		}

	}

	public static void main(String args[]) {

	}
}
