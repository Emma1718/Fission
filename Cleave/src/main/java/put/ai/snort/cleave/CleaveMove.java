/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package put.ai.snort.cleave;

import put.ai.snort.game.moves.MoveMove;
import java.util.ArrayList;
import java.util.List;
import put.ai.snort.game.Move;
import put.ai.snort.game.Player.Color;

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

public class CleaveMove implements MoveMove {

    private Color color;
    private int srcX, srcY, dstX, dstY;
    private List<Piece> captured = new ArrayList<>();

    public CleaveMove(Color color, int srcX, int srcY, int dstX, int dstY) {
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

    /*package*/ List<Piece> getCaptured() {
        return captured;
    }
}
