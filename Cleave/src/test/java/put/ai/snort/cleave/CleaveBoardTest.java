/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package put.ai.snort.cleave;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.powermock.reflect.Whitebox;
import put.ai.snort.game.Move;
import put.ai.snort.game.Player;

public class CleaveBoardTest {

    private CleaveBoard b;

    @Before
    public void before() {
        b = new CleaveBoard(10);
    }

    @Test
    public void generator1() {
        Player.Color[][] state = new Player.Color[][]{
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.PLAYER1, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},};
        Whitebox.setInternalState(b, state);
        List<Move> moves = b.getMovesFor(Player.Color.PLAYER1);
        assertEquals(8, moves.size());
    }

    @Test
    public void generator2() {
        Player.Color[][] state = new Player.Color[][]{
            {Player.Color.PLAYER1, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},};
        Whitebox.setInternalState(b, state);
        List<Move> moves = b.getMovesFor(Player.Color.PLAYER1);
        assertEquals(8, moves.size());
    }

    @Test
    public void generator3() {
        Player.Color[][] state = new Player.Color[][]{
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.PLAYER1, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.PLAYER2, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},};
        Whitebox.setInternalState(b, state);
        List<Move> moves;
        moves = b.getMovesFor(Player.Color.PLAYER1);
        assertEquals(8, moves.size());
        moves = b.getMovesFor(Player.Color.PLAYER2);
        assertEquals(8, moves.size());
    }

    @Test
    public void generator4() {
        Player.Color[][] state = new Player.Color[][]{
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.PLAYER1, Player.Color.PLAYER2, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},};
        Whitebox.setInternalState(b, state);
        List<Move> moves;
        moves = b.getMovesFor(Player.Color.PLAYER1);
        assertEquals(6, moves.size());
        moves = b.getMovesFor(Player.Color.PLAYER2);
        assertEquals(5, moves.size());
    }

    @Test
    public void generator5() {
        Player.Color[][] state = new Player.Color[][]{
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.PLAYER1, Player.Color.PLAYER1, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},};
        Whitebox.setInternalState(b, state);
        List<Move> moves;
        moves = b.getMovesFor(Player.Color.PLAYER1);
        assertEquals(11, moves.size());
        moves = b.getMovesFor(Player.Color.PLAYER2);
        assertEquals(0, moves.size());
    }

    @Test
    public void custodian() {
        Player.Color[][] state = new Player.Color[][]{
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.PLAYER1, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.PLAYER2, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.PLAYER1, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},};
        Whitebox.setInternalState(b, state);
        CleaveMove m = new CleaveMove(Player.Color.PLAYER1, 1, 1, 1, 2);
        b.doMove(m);
        assertEquals(Player.Color.EMPTY, b.getState(1, 1));
        assertEquals(Player.Color.PLAYER1, b.getState(1, 2));
        assertEquals(Player.Color.PLAYER1, b.getState(3, 2));
        assertEquals(Player.Color.EMPTY, b.getState(2, 2));
        b.undoMove(m);
        assertEquals(Player.Color.PLAYER1, b.getState(1, 1));
        assertEquals(Player.Color.EMPTY, b.getState(1, 2));
        assertEquals(Player.Color.PLAYER1, b.getState(3, 2));
        assertEquals(Player.Color.PLAYER2, b.getState(2, 2));
    }

    @Test
    public void intervention() {
        Player.Color[][] state = new Player.Color[][]{
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.PLAYER1, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.PLAYER2, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.PLAYER1, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},};
        Whitebox.setInternalState(b, state);
        CleaveMove m = new CleaveMove(Player.Color.PLAYER2, 2, 1, 2, 2);
        b.doMove(m);
        assertEquals(Player.Color.EMPTY, b.getState(2, 1));
        assertEquals(Player.Color.PLAYER2, b.getState(2, 2));
        assertEquals(Player.Color.EMPTY, b.getState(1, 2));
        assertEquals(Player.Color.EMPTY, b.getState(3, 2));
        b.undoMove(m);
        assertEquals(Player.Color.PLAYER2, b.getState(2, 1));
        assertEquals(Player.Color.EMPTY, b.getState(2, 2));
        assertEquals(Player.Color.PLAYER1, b.getState(1, 2));
        assertEquals(Player.Color.PLAYER1, b.getState(3, 2));
    }

    @Test
    public void edgeIntervention() {
        Player.Color[][] state = new Player.Color[][]{
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.PLAYER1, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.PLAYER2, Player.Color.PLAYER2, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},};
        Whitebox.setInternalState(b, state);
        CleaveMove m = new CleaveMove(Player.Color.PLAYER1, 2, 0, 3, 0);
        System.err.println(b);
        b.doMove(m);
        System.err.println(b);
        assertEquals(Player.Color.PLAYER1, b.getState(3, 0));
        assertEquals(Player.Color.EMPTY, b.getState(3, 1));
        assertEquals(Player.Color.PLAYER2, b.getState(3, 2));
    }
}
