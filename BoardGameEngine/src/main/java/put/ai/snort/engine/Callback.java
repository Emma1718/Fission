/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package put.ai.snort.engine;

import put.ai.snort.game.Board;
import put.ai.snort.game.Move;
import put.ai.snort.game.Player.Color;

public interface Callback {

    public void update(Color nextPlayer, Board currentState, Move lastMove);
}
