/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package put.ai.snort.alphabetaTTplayer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import put.ai.snort.game.Board;
import put.ai.snort.game.Move;
import put.ai.snort.game.Player;
import put.ai.snort.game.Player.Color;
import put.ai.snort.fission.*;

enum Bound {
	LOWER, UPPER, ACCURATE;
}

class TTEntry {
	public String b_hash;
	public int heur_val;
	public int alpha;
	public int beta;
	public int depth;
	public Bound bound;
	public Color color;

	public Move best_move;

	public TTEntry(int h, int al, int be, int d, Move bM) {
		//b_hash = b;
		heur_val = h;
		alpha = al;
		beta = be;
		depth = d;
		best_move = bM;
	}
	/*
	 * public Board getBoard() {
	 * 
	 * return board; }
	 */
}

class TTable extends ArrayList<TTEntry> {
	public TTEntry TTLookup(String b, Color c) {
		for (TTEntry tt : this) {
			if (tt.b_hash.equals(b) && tt.color.equals(c))
				return tt;
		}
		return null;
	}

	public void saveTT(TTEntry tt, Color c) {
		if (tt.heur_val <= tt.alpha)
			tt.bound = Bound.UPPER;
		else if (tt.heur_val >= tt.beta)
			tt.bound = Bound.LOWER;
		else
			tt.bound = Bound.ACCURATE;

		tt.color = c;
		this.add(tt);
	}

	public String getHash(Board b, Color c) {
		String hash = "";
		for (int i = 0; i < b.getSize(); i++) {
			for (int j = 0; j < b.getSize(); j++) {
				if (b.getState(i, j) == c)
					hash += "1";
				else if (b.getState(i, j) == Player.getOpponent(c))
					hash += "2";
				else
					hash += "0";
			}
		}
		// System.out.println(hash);
		return hash;
	}
}

public class AlphaBetaTTPlayer extends Player {

	enum Type {
		MAX, MIN
	};

	TTable ttable = new TTable();
	HashMap<String, TTEntry> htable = new HashMap<String, TTEntry>();

	@Override
	public String getName() {
		return "AlphaBetaTTPlayer 45645";
	}

	@Override
	public Move nextMove(Board b) {
		PrintStream p = null;
		try {
			p = new PrintStream("/tmp/plik.txt");
			p.println(Calendar.getInstance().getTime());
			p.close();
		} catch (FileNotFoundException ex) {
			Logger.getLogger(AlphaBetaTTPlayer.class.getName()).log(
					Level.SEVERE, null, ex);
			throw new RuntimeException(ex);
		} finally {
			if (p != null) {
				p.close();
			}
		}

		List<Move> moves = b.getMovesFor(getColor());
		int i = 0;
		int maximum = -500, chosen = 0;
		Move move = moves.get(1);
		Board board = b.clone();
		for (Move m : moves) {
			board.doMove(m);
			int rate = alphaBetaTT(board, 5, -10, 10, Type.MIN);
			board.undoMove(m);
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

		int xi = getStonesAmount(getColor(), b);

		int yi = getStonesAmount(Player.getOpponent(getColor()), b);
		rate1 = xi - yi;
		/*
		 * rate1 *= 2; int zi = getNeighbours(getColor(), b); int vi =
		 * getNeighbours(getOpponent(getColor()), b); rate2 = vi - zi;
		 * 
		 * rate = rate1 + rate2;
		 */
		return rate1;

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
							if ((b.getState(i + z, j + v) == c)
									&& ((z != 0) || (v != 0))) {
								pairs++;
							}
						}
					}
				}
			}
		}
		return pairs;
	}

	private int alphaBetaTT(Board board, int depth, int alpha, int beta,
			Type type) {

		int result;
		int prevalpha = alpha;
		int prevbeta = beta;
		Move best_move = null;

		if (depth == 0)
			return (rateMove(board));

		int best;
		if (type == Type.MAX) {

			// TTEntry ptr = ttable.TTLookup(ttable.getHash(board, getColor()),
			// getColor());
			TTEntry ptr = htable.get(ttable.getHash(board, getColor()));
			if (ptr != null && ptr.depth >= depth) {
				if (ptr.bound == Bound.LOWER)
					alpha = Math.max(alpha, ptr.heur_val);
				if (ptr.bound == Bound.UPPER)
					beta = Math.min(beta, ptr.heur_val);
				if (ptr.bound == Bound.ACCURATE) {
					alpha = ptr.heur_val;
					beta = alpha;
				}
				if (alpha >= beta) {
					// System.out.println("FOUND");

					return ptr.heur_val;
				}
			}
			//System.out.println("max");
			List<Move> moves = board.getMovesFor(getColor());
			if (ptr != null && containsMove(moves, ptr.best_move)) {
				moves.remove(ptr.best_move);
			//	System.out.println("remove1");
				moves.add(0, ptr.best_move);
			}
			//System.out.println("max2");

			best = -500;
			for (Move m : moves) {
				board.doMove(m);
				int val = alphaBetaTT(board, depth - 1, alpha, beta, Type.MIN);
				board.undoMove(m);
				if (val > best) {
					best = val;
					best_move = m;
				}
				if (best >= beta)
					break;
				if (best > alpha)
					alpha = best;

				// alpha = Math.max(val, alpha);
				// if (alpha >= beta)
				// return beta; // cutoff
			}
			result = best;
			
			TTEntry entry = new TTEntry(rateMove(board), prevalpha, prevbeta,
					depth, best_move);
			if (entry.heur_val <= entry.alpha)
				entry.bound = Bound.UPPER;
			else if (entry.heur_val >= entry.beta)
				entry.bound = Bound.LOWER;
			else
				entry.bound = Bound.ACCURATE;

			entry.color = getColor();
			htable.put(ttable.getHash(board, getColor()), entry);
			// ttable.saveTT(entry, getColor());
			// return alpha;
			//System.out.println("maxkoniec");
			moves.clear();

		} else { // type == MIN
			//System.out.println("min1");

			// TTEntry ptr = ttable.TTLookup(ttable.getHash(board, getColor()),
			// getOpponent(getColor()));
			TTEntry ptr = htable.get(ttable.getHash(board, getColor()));
			if (ptr != null && ptr.depth >= depth) {
				if (ptr.bound == Bound.LOWER)
					alpha = Math.max(alpha, ptr.heur_val);
				if (ptr.bound == Bound.UPPER)
					beta = Math.min(beta, ptr.heur_val);
				if (ptr.bound == Bound.ACCURATE) {
					alpha = ptr.heur_val;
					beta = alpha;
				}
				if (alpha >= beta) {
					// System.out.println("FOUND");

					return ptr.heur_val;
				}
			}
			//System.out.println("min");
			List<Move> moves = board.getMovesFor(getOpponent(getColor()));
			if (ptr != null && containsMove(moves, ptr.best_move)) {
				moves.remove(ptr.best_move);
			//	System.out.println("remove");
				moves.add(0, ptr.best_move);
			}
			best = 500;
			for (Move m : moves) {
				board.doMove(m);
				int val = alphaBetaTT(board, depth - 1, alpha, beta, Type.MAX);
				board.undoMove(m);

				if (val < best) {
					best = val;
					best_move = m;
				}
				// beta = Math.min(val, beta);
				// if (alpha >= beta)
				// return alpha; // cutoff
				if (best <= alpha)
					break;
				if (best < beta)
					beta = best;

			} // endfor
				// return beta;

			result = best;
			TTEntry entry = new TTEntry(rateMove(board), prevalpha, prevbeta, depth, best_move);
			if (entry.heur_val <= entry.alpha)
				entry.bound = Bound.UPPER;
			else if (entry.heur_val >= entry.beta)
				entry.bound = Bound.LOWER;
			else
				entry.bound = Bound.ACCURATE;

			entry.color = getOpponent(getColor());
			htable.put(ttable.getHash(board, getColor()), entry);
			ttable.saveTT(entry, getOpponent(getColor()));
			moves.clear();

		}

		return result;

	}

	public boolean containsMove(List<Move> moves, Move m) {
		FissionMove fm = (FissionMove) m;
		for (Move move : moves) {
			FissionMove fm2 = (FissionMove) move;
			if (fm2.getColor() == fm.getColor()
					&& fm2.getSrcX() == fm.getSrcX()
					&& fm2.getSrcY() == fm.getSrcY()
					&& fm2.getDstX() == fm.getDstX()
					&& fm2.getDstY() == fm.getDstY())
				return true;
		}
		return false;
	}

	public static void main(String args[]) {

	}
}
