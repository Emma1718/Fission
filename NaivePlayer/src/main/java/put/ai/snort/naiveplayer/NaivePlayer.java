/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package put.ai.snort.naiveplayer;

import java.util.List;
import java.util.Random;
import put.ai.snort.game.Board;
import put.ai.snort.game.Move;
import put.ai.snort.game.Player;

public class NaivePlayer extends Player {

    private Random random=new Random(0xdeadbeef);

    @Override
    public String getName() {
        return "Gracz Naiwny 84868";
    }

    @Override
    public Move nextMove(Board b) {
        List<Move> moves = b.getMovesFor(getColor());
        return moves.get(random.nextInt(moves.size()));
    }
    
    public static void main(String args[]) {
    	
    }
}
