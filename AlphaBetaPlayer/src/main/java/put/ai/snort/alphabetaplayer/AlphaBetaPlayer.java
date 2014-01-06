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
		int maximum = -500, chosen = 0, ocena;
		Move move = moves.get(1);
		Board board = b.clone();
		for (Move m : moves) {
			board.doMove(m);
			int rate = alphaBeta(board, 3, -500, 500, Type.MIN);

			board.undoMove(m);
			if (rate > maximum) {
				maximum = rate;
				move = m;
				chosen = i;
			}
			i++;
		}

		// System.out.println("FRom: " + ((FissionMove)move).getSrcX() +" " +
		// ((FissionMove)move).getSrcY() );
		// System.out.println("FRom: " + ((FissionMove)move).getDstX() +" " +
		// ((FissionMove)move).getDstY() );

		Board b2 = board.clone();
		b2.doMove(move);
		System.out.println("---------");
		ocena = rateMove(b2);
		System.out.println("Move no. " + chosen + " ocena: " + ocena);
		b2.undoMove(move);
		System.out.println("Wybrany ruch: " + chosen + " Ocena: " + maximum);
		return move;
	}

	public int rateMove(Board b) {
		int rate1, rate2, rate;
		int theSameCol = 0, otherCol = 0;
		// FissionBoard fb = (FissionBoard) b.clone();
		// FissionMove move = (FissionMove) m;
		int xi = getStonesAmount(getColor(), b);

		int yi = getStonesAmount(Player.getOpponent(getColor()), b);
		rate1 = xi - yi;
		if (yi == 0)
			rate1++;
		if (rate1 == 0) {
			int zi = getNeighbours(getColor(), b);
			// System.out.println("Neighbour1 " + zi);
			int vi = getNeighbours(getOpponent(getColor()), b);
			// System.out.println("Neighbour2 " + vi);
			rate2 = vi - zi;
			rate1 = rate2;
		}
		/*
		 * rate1 *= 2; int vi = getNeighbours(getOpponent(getColor()), b); rate2
		 * = vi - zi;
		 * 
		 * rate = rate1 + rate2;
		 */
		return rate1;

	}

	private boolean isTerminalState(Board b) {
		if (getStonesAmount(getColor(), b) > 0
				&& getStonesAmount(Player.getOpponent(getColor()), b) == 0)
			return true;
		if (getStonesAmount(getColor(), b) == 1
				&& getStonesAmount(getColor(), b) == getStonesAmount(
						getOpponent(getColor()), b))

			return true;

		return false;
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

	private int countDanger(Color c, Board b) {
		int opp = 0, emp = 0, same = 0;
		for (int i = 0; i < b.getSize(); i++) {
			for (int j = 0; j < b.getSize(); j++) {
				if (b.getState(i, j) == c) {
					// System.out.println("Coords of player: "+ c.toString() +
					// " " +i +" " + j);
					for (int z = -1; z <= 1; ++z) {
						for (int v = -1; v <= 1; ++v) {
							if (b.getState(i + z, j + v) == c) {
								same++;
							} else if (b.getState(i + z, j + v) == getOpponent(c))
								opp++;
							else if(i+z >=0 && i+z<b.getSize() && j+v >=0 && j+v < b.getSize())
								emp++;
						}
					}
				}
			}
		}
		if(opp == same-1) return emp;
		else return opp-same+1;
	}

	private int getNeighbours(Color c, Board b) {
		int pairs1 = 0, pairs2 = 0, threats = 0;
		for (int i = 0; i < b.getSize(); i++) {
			for (int j = 0; j < b.getSize(); j++) {
				if (b.getState(i, j) == c) {
					// System.out.println("Coords of player: "+ c.toString() +
					// " " +i +" " + j);
					for (int z = -1; z <= 1; ++z) {
						for (int v = -1; v <= 1; ++v) {
							if ((b.getState(i + z, j + v) == Color.EMPTY)
									&& (i + z >= 0 && i + z < b.getSize()
											&& j + v >= 0 && j + v < b
											.getSize())) {
								for (int k = -1; k <= 1; ++k) {
									for (int l = -1; l <= 1; ++l) {
										if ((b.getState(i + z + k, j + v + l) == c))
											pairs1++;
										if ((b.getState(i + z + k, j + v + l) == getOpponent(c)))
											pairs2++;
									}
								}
								pairs1--;
								if (pairs1 - pairs2 > 0)
									threats += ((pairs1 - pairs2) * canBeCrash(
											i + z, j + v, b, c));
								pairs1 = pairs2 = 0;
							}
						}
					}
				}
			}
		}
		return threats;
	}

	private int canBeCrash(int x, int y, Board b, Color c) {

		int threatsDiag1 = 0, threatsDiag2 = 0, threatsHor = 0, threatsVer = 0;
		int z;
		int v;

		// 1
		if (b.getState(x - 1, y - 1) != Color.EMPTY) {
			z = x;
			v = y;
			while (++z < b.getSize() && ++v < b.getSize()) {
				if (b.getState(z, v) == getOpponent(c)) {
					threatsDiag1++;
					break;
				} else if (b.getState(z, v) == c)
					break;
			}
		}
		// 2
		if (b.getState(x, y - 1) != Color.EMPTY) {
			z = x;
			v = y;
			while (++v < b.getSize()) {
				if (b.getState(z, v) == getOpponent(c)) {
					threatsVer++;
					break;
				} else if (b.getState(z, v) == c)
					break;
			}
		}
		// 3
		if (b.getState(x + 1, y - 1) != Color.EMPTY) {
			z = x;
			v = y;
			while (--z >= 0 && ++v < b.getSize()) {
				if (b.getState(z, v) == getOpponent(c)) {
					threatsDiag2++;
					break;
				} else if (b.getState(z, v) == c)
					break;
			}
		}
		// 4
		if (b.getState(x + 1, y) != Color.EMPTY) {
			z = x;
			v = y;
			while (--z >= 0) {
				if (b.getState(z, v) == getOpponent(c)) {
					threatsVer++;
					break;
				} else if (b.getState(z, v) == c)
					break;
			}
		}
		// 5
		if (threatsDiag1 == 0 && b.getState(x + 1, y + 1) != Color.EMPTY) {
			z = x;
			v = y;
			while (--z >= 0 && --v >= 0) {
				if (b.getState(z, v) == getOpponent(c)) {
					threatsDiag1++;
					break;
				} else if (b.getState(z, v) == c)
					break;
			}
		}
		// 6
		if (threatsVer == 0 && b.getState(x, y + 1) != Color.EMPTY) {
			z = x;
			v = y;
			while (--v >= 0) {
				if (b.getState(z, v) == getOpponent(c)) {
					threatsVer++;
					break;
				} else if (b.getState(z, v) == c)
					break;
			}
		}

		// 7
		if (threatsDiag2 == 0 && b.getState(x - 1, y + 1) != Color.EMPTY) {
			z = x;
			v = y;
			while (++z < b.getSize() && --v >= 0) {
				if (b.getState(z, v) == getOpponent(c)) {
					threatsDiag2++;
					break;
				} else if (b.getState(z, v) == c)
					break;
			}
		}
		// 8
		if (threatsHor == 0 && b.getState(x - 1, y) != Color.EMPTY) {
			z = x;
			v = y;
			while (++z < b.getSize()) {
				if (b.getState(z, v) == getOpponent(c)) {
					threatsHor++;
					break;
				} else if (b.getState(z, v) == c)
					break;
			}
		}
		// System.out.println("number of threats: " + threats
		// + " coords of empty: " + x + " " + y);
		return (threatsDiag1 + threatsDiag2 + threatsHor + threatsVer);
	}

	private int alphaBeta(Board board, int depth, int alpha, int beta, Type type) {

		if (depth == 0 || isTerminalState(board))
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

	private int alphaBetaNMax(Board board, int depth, int alpha, int beta) {

		if (depth == 0)
			return (-rateMove(board));

		for (Move m : board.getMovesFor(getColor())) {
			board.doMove(m);
			int val = -alphaBetaNMax(board, depth - 1, -beta, -alpha);
			board.undoMove(m);
			// alpha = Math.max(val, alpha);
			if (val > alpha)
				alpha = val;
			if (alpha >= beta)
				return beta; // cutoff
		}
		return alpha;

	}

	public static void main(String args[]) {

	}
}
