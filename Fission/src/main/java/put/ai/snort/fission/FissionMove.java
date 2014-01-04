/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package put.ai.snort.fission;

import java.util.ArrayList;
import java.util.List;
import put.ai.snort.game.Player.Color;
import put.ai.snort.game.moves.MoveMove;

class Piece {

    private Color color;
    private int x;
    private int y;

    public Piece(Color color, int x, int y) {
        this.color = color;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }
}

public class FissionMove implements MoveMove {

    private Color color;
    private int srcX, srcY, dstX, dstY;
    private List<Piece> removed = new ArrayList<>();

    public FissionMove(Color color, int srcX, int srcY, int dstX, int dstY) {
        this.color = color;
        this.srcX = srcX;
        this.srcY = srcY;
        this.dstX = dstX;
        this.dstY = dstY;
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

    /*package*/ boolean isHorizontal() {
        return srcY == dstY;
    }

    /*package*/ boolean isVertical() {
        return srcX == dstX;
    }

    /*package*/ boolean isDiagonal() {
        return !isHorizontal() && !isVertical();
    }

    /*package*/ List<Piece> getRemoved() {
        return removed;
    }

    @Override
    public String toString() {
        return String.format("(%d,%d)->(%d,%d)", srcX, srcY, dstX, dstY);
    }
}
