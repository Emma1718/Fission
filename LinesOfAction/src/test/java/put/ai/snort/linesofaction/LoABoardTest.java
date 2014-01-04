/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package put.ai.snort.linesofaction;

import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.powermock.reflect.Whitebox;
import put.ai.snort.game.Move;
import put.ai.snort.game.Player;

public class LoABoardTest {

    private LoABoard b;

    @Before
    public void before() {
        b = new LoABoard(5);
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
        assertEquals(6, moves.size());
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
        assertEquals(6, moves.size());
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
        assertEquals(15, moves.size());
        int[] n = new int[6];
        for (Move m : moves) {
            LoAMove lm = (LoAMove) m;
            n[lm.length()]++;
        }
        assertEquals(12, n[1]);
        assertEquals(3, n[2]);
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
        assertEquals(0, moves.size());
    }

    @Test
    public void generator8() {
        Player.Color[][] state = new Player.Color[][]{
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.PLAYER1, Player.Color.PLAYER1, Player.Color.PLAYER1, Player.Color.EMPTY, Player.Color.PLAYER2},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},};
        Whitebox.setInternalState(b, state);
        List<Move> moves;
        moves = b.getMovesFor(Player.Color.PLAYER1);
        assertEquals(17, moves.size());
        int[] n = new int[6];
        for (Move m : moves) {
            LoAMove lm = (LoAMove) m;
            n[lm.length()]++;
        }
        assertEquals(16, n[1]);
        assertEquals(1, n[4]);
        moves = b.getMovesFor(Player.Color.PLAYER2);
        assertEquals(4, moves.size());
    }

    @Test
    public void generator9() {
        Player.Color[][] state = new Player.Color[][]{
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.PLAYER1, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.PLAYER1, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},};
        Whitebox.setInternalState(b, state);
        List<Move> moves;
        moves = b.getMovesFor(Player.Color.PLAYER1);
        assertEquals(12, moves.size());
        int[] n = new int[6];
        for (Move m : moves) {
            LoAMove lm = (LoAMove) m;
            n[lm.length()]++;
        }
        assertEquals(4 + 6, n[1]);
        assertEquals(2, n[2]);
    }

    @Test
    public void generator10() {
        Player.Color[][] state = new Player.Color[][]{
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.PLAYER1, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.PLAYER1, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.PLAYER2, Player.Color.EMPTY},};
        Whitebox.setInternalState(b, state);
        List<Move> moves;
        moves = b.getMovesFor(Player.Color.PLAYER1);
        assertEquals(11, moves.size());
        int[] n = new int[6];
        for (Move m : moves) {
            LoAMove lm = (LoAMove) m;
            n[lm.length()]++;
        }
        assertEquals(4 + 6, n[1]);
        assertEquals(0, n[2]);
        assertEquals(1, n[3]);
    }

    @Test
    public void connected1() {
        Player.Color[][] state = new Player.Color[][]{
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.PLAYER2},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.PLAYER1, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.PLAYER2},};
        Whitebox.setInternalState(b, state);
        assertFalse(b.canMove(Player.Color.PLAYER2));
    }

    @Test
    public void connected2() {
        Player.Color[][] state = new Player.Color[][]{
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.PLAYER2},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.PLAYER1, Player.Color.PLAYER1, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.PLAYER2},};
        Whitebox.setInternalState(b, state);
        assertFalse(b.canMove(Player.Color.PLAYER2));
    }

    @Test
    public void connected3() {
        Player.Color[][] state = new Player.Color[][]{
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.PLAYER2},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.PLAYER1, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.PLAYER1, Player.Color.PLAYER1, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.PLAYER2},};
        Whitebox.setInternalState(b, state);
        assertFalse(b.canMove(Player.Color.PLAYER2));
    }

    @Test
    public void connected4() {
        Player.Color[][] state = new Player.Color[][]{
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.PLAYER2},
            {Player.Color.EMPTY, Player.Color.PLAYER1, Player.Color.PLAYER1, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.PLAYER1, Player.Color.PLAYER1, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.PLAYER2},};
        Whitebox.setInternalState(b, state);
        assertFalse(b.canMove(Player.Color.PLAYER2));
    }

    @Test
    public void connected5() {
        Player.Color[][] state = new Player.Color[][]{
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.PLAYER2},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.PLAYER1, Player.Color.EMPTY, Player.Color.PLAYER1, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY},
            {Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.EMPTY, Player.Color.PLAYER2},};
        Whitebox.setInternalState(b, state);
        assertTrue(b.canMove(Player.Color.PLAYER2));
    }
}
