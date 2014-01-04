/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package put.ai.snort.game.moves;

import put.ai.snort.game.Move;

public interface MoveMove extends Move {

    int getDstX();

    int getDstY();

    int getSrcX();

    int getSrcY();

}
