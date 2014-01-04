/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package put.ai.snort.game.exceptions;

import put.ai.snort.game.Player.Color;

public class NoMoveException extends RuleViolationException {

    public NoMoveException(Color guilty) {
        super(guilty, "Nie wygenerował ruchu");
    }
}
