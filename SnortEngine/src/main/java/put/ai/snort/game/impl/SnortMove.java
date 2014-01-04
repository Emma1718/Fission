/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package put.ai.snort.game.impl;

import put.ai.snort.game.moves.PlaceMove;
import put.ai.snort.game.Move;
import put.ai.snort.game.Player.Color;

public class SnortMove implements PlaceMove {

    private int x;
    private int y;
    private Color c;

    public SnortMove(int x, int y, Color c) {
        this.x = x;
        this.y = y;
        this.c = c;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public Color getColor() {
        return c;
    }

    @Override
    public String toString() {
        return String.format("%s@(%d,%d)", c, x, y);
    }
}
