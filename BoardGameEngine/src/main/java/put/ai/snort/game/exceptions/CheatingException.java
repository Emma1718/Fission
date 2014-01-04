/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package put.ai.snort.game.exceptions;

import put.ai.snort.game.Player.Color;

public class CheatingException extends RuleViolationException {

    public CheatingException(Color guilty) {
        super(guilty, "Oszust!");
    }
}
