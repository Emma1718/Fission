/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package put.ai.snort.engine;

import put.ai.snort.game.Player;
import put.ai.snort.game.Player.Color;
import put.ai.snort.game.exceptions.RuleViolationException;

public interface GameEngine {

    void addPlayer(Player p);

    Color play(Callback cb) throws RuleViolationException;

    void setTimeout(int timeout);
}
