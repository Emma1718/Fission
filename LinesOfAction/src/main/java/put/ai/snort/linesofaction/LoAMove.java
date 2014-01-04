/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package put.ai.snort.linesofaction;

import put.ai.snort.game.Player.Color;
import put.ai.snort.game.moves.MoveMove;

public class LoAMove implements MoveMove {

    private Color color;
    private int srcX, srcY, dstX, dstY;
    private Color previous = Color.EMPTY;

    public LoAMove(Color color, int srcX, int srcY, int dstX, int dstY) {
        this.color = color;
        this.srcX = srcX;
        this.srcY = srcY;
        this.dstX = dstX;
        this.dstY = dstY;
        if (!(srcX - dstX == 0 || srcY - dstY == 0 || Math.abs(srcX - dstX) == Math.abs(srcY - dstY))) {
            throw new IllegalArgumentException("Move can be only horizontal, vertical or diagonal");
        }
    }

    @Override
    public int getDstX() {
        return dstX;
    }

    @Override
    public int getDstY() {
        return dstY;
    }

    @Override
    public int getSrcX() {
        return srcX;
    }

    @Override
    public int getSrcY() {
        return srcY;
    }

    @Override
    public Color getColor() {
        return color;
    }

    /*package*/ Color getPrevious() {
        return previous;
    }

    /*package*/ void setPrevious(Color previous) {
        this.previous = previous;
    }

    /*package*/ void resetPrevious() {
        this.previous = Color.EMPTY;
    }

    /*package*/ int length() {
        if (srcX == dstX) {
            return Math.abs(srcY - dstY);
        } else {
            return Math.abs(srcX - dstX);
        }
    }

    @Override
    public String toString() {
        return String.format("(%d,%d)->(%d,%d)", srcX, srcY, dstX, dstY);
    }
}
