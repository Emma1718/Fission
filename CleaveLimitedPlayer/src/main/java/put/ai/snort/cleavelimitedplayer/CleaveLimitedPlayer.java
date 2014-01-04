/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package put.ai.snort.cleavelimitedplayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import put.ai.snort.cleave.CleaveMove;
import put.ai.snort.game.Board;
import put.ai.snort.game.Move;
import put.ai.snort.game.Player;

public class CleaveLimitedPlayer extends Player {

    private Random random = new Random(0xdeadbeef);

    @Override
    public String getName() {
        return "Ograniczony Gracz dla Cleave 84868";
    }

    private List<CleaveMove> getMovesFor(Board b, Color color) {
        List<CleaveMove> result = new ArrayList<>();
        for (int x = 0; x < b.getSize(); ++x) {
            for (int y = 0; y < b.getSize(); ++y) {
                if (b.getState(x, y) != color) {
                    continue;
                }
                for (int j = y + 1; j < b.getSize(); ++j) {
                    if (b.getState(x, j) == Color.EMPTY) {
                        result.add(new CleaveMove(color, x, y, x, j));
                    } else {
                        break;
                    }
                }
                for (int j = 0; j < y; ++j) {
                    if (b.getState(x, j) == Color.EMPTY) {
                        result.add(new CleaveMove(color, x, y, x, j));
                    } else {
                        break;
                    }
                }
            }
        }
        return result;
    }

    @Override
    public Move nextMove(Board b) {
        List<CleaveMove> moves = getMovesFor(b, getColor());        
        return moves.get(random.nextInt(moves.size()));
    }
}
