/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package put.ai.snort.fission;

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.powermock.reflect.Whitebox;
import put.ai.snort.game.Move;
import put.ai.snort.game.Player;

public class FissionBoardTest {

    private FissionBoard b;

    @Before
    public void before() {
        b = new FissionBoard(5);
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
        assertEquals(3, moves.size());
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
        assertEquals(7, moves.size());
        moves = b.getMovesFor(Player.Color.PLAYER2);
        assertEquals(7, moves.size());
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
        assertEquals(7, moves.size());
        moves = b.getMovesFor(Player.Color.PLAYER2);
        assertEquals(7, moves.size());
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
        assertEquals(14, moves.size());
        moves = b.getMovesFor(Player.Color.PLAYER2);
        assertEquals(0, moves.size());
    }

    @Test
    public void generator6() {
        Player.Color[][] state = new Player.Color[][]{
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.PLAYER2, Player.Color.PLAYER2, Player.Color.PLAYER2, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.PLAYER2, Player.Color.PLAYER1, Player.Color.PLAYER2, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.PLAYER2, Player.Color.PLAYER2, Player.Color.PLAYER2, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},};
        Whitebox.setInternalState(b, state);
        List<Move> moves;
        moves = b.getMovesFor(Player.Color.PLAYER1);
        assertEquals(0, moves.size());
    }

    @Test
    public void generator7() {
        Player.Color[][] state = new Player.Color[][]{
            {Player.Color.PLAYER2, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.PLAYER2},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.PLAYER2, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.PLAYER2, Player.Color.PLAYER1, Player.Color.PLAYER2, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.PLAYER2, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.PLAYER2, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.PLAYER2},};
        Whitebox.setInternalState(b, state);
        List<Move> moves;
        moves = b.getMovesFor(Player.Color.PLAYER1);
        assertEquals(4, moves.size());
    }

    @Test
    public void wall1() {
        Player.Color[][] state = new Player.Color[][]{
            {Player.Color.EMPTY, Player.Color.PLAYER1, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.PLAYER2},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},};
        Whitebox.setInternalState(b, state);
        b.doMove(new FissionMove(Player.Color.PLAYER1, 0, 1, 0, 3));
        List<Move> moves;
        moves = b.getMovesFor(Player.Color.PLAYER1);
        assertTrue(moves.isEmpty());
        moves = b.getMovesFor(Player.Color.PLAYER2);
        assertTrue(moves.isEmpty());
    }

    @Test
    public void wall2() {
        Player.Color[][] state = new Player.Color[][]{
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.PLAYER2},
            {Player.Color.EMPTY, Player.Color.PLAYER1, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},};
        Whitebox.setInternalState(b, state);
        b.doMove(new FissionMove(Player.Color.PLAYER1, 1, 1, 1, 4));
        List<Move> moves;
        moves = b.getMovesFor(Player.Color.PLAYER1);
        assertFalse(moves.isEmpty());
        moves = b.getMovesFor(Player.Color.PLAYER2);
        assertFalse(moves.isEmpty());
    }
}
