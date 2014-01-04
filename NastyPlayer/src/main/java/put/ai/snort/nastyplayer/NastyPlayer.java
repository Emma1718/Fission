/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package put.ai.snort.nastyplayer;

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

public class NastyPlayer extends Player {

    @Override
    public String getName() {
        return "Gracz Wredny 84868";
    }

    @Override
    public Move nextMove(Board b) {        
        PrintStream p = null;
        try {
            p = new PrintStream("/tmp/plik.txt");
            p.println(Calendar.getInstance().getTime());
            p.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NastyPlayer.class.getName()).log(Level.SEVERE, null, ex);
            throw new RuntimeException(ex);
        } finally {
            if (p != null) {
                p.close();
            }
        }
        List<Move> moves = b.getMovesFor(getColor());
        return moves.get(moves.size() - 1);
    }
    
    public static void main(String args[]) {
    
    }
}
