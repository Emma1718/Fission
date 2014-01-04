/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package put.ai.snort.game.impl;

import put.ai.snort.game.impl.SnortBoard;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.reflect.Whitebox;
import put.ai.snort.game.Move;
import put.ai.snort.game.Player.Color;

@RunWith(MockitoJUnitRunner.class)
public class BoardTest {

    private SnortBoard b;
    @Mock
    private SnortMove m1;
    @Mock
    private SnortMove m2;

    @Before
    public void setup() {
        b = new SnortBoard(5);
        when(m1.getX()).thenReturn(2);
        when(m1.getY()).thenReturn(3);
        when(m1.getColor()).thenReturn(Color.PLAYER1);
        when(m2.getX()).thenReturn(2);
        when(m2.getY()).thenReturn(3);
        when(m2.getColor()).thenReturn(Color.PLAYER2);
    }

    private static boolean isBoardEmpty(SnortBoard b) {
        for (int i = 0; i < b.getSize(); ++i) {
            for (int j = 0; j < b.getSize(); ++j) {
                if (b.getState(i, j) != Color.EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    @Test
    public void create() {
        assertEquals(5, b.getSize());
        assertTrue(isBoardEmpty(b));
    }

    @Test
    public void cloneTest() {
        SnortBoard c = b.clone();
        assertNotSame(b, c);
        assertEquals(b.getSize(), c.getSize());
        Color[][] bstate = Whitebox.getInternalState(b, Color[][].class);
        Color[][] cstate = Whitebox.getInternalState(c, Color[][].class);
        assertNotSame(bstate, cstate);
        for (int i = 0; i < b.getSize(); ++i) {
            assertArrayEquals(bstate[i], cstate[i]);
        }
    }

    @Test
    public void move1() {
        b.doMove(m1);
        assertEquals(Color.PLAYER1, b.getState(2, 3));
        for (int i = 0; i < b.getSize(); ++i) {
            for (int j = 0; j < b.getSize(); ++j) {
                if (i != 2 && j != 3) {
                    assertEquals(Color.EMPTY, b.getState(i, j));
                }
            }
        }
    }

    @Test
    public void undo1() {
        b.doMove(m1);
        b.undoMove(m1);
        assertTrue(isBoardEmpty(b));
    }

    @Test
    public void undo2() {
        b.doMove(m2);
        assertFalse(isBoardEmpty(b));
        b.undoMove(m2);
        assertTrue(isBoardEmpty(b));
    }

    @Test
    public void move2() {
        b.doMove(m2);
        assertEquals(Color.PLAYER2, b.getState(2, 3));
        for (int i = 0; i < b.getSize(); ++i) {
            for (int j = 0; j < b.getSize(); ++j) {
                if (i != 2 && j != 3) {
                    assertEquals(Color.EMPTY, b.getState(i, j));
                }
            }
        }
    }

    @Test(expected = RuntimeException.class)
    public void move_undo() {
        b.doMove(m1);
        b.undoMove(m2);
    }

    @Test
    public void equals() {
        SnortBoard c = b.clone();
        assertTrue(c.equals(b));
        assertTrue(b.equals(c));
        assertEquals(b.hashCode(), c.hashCode());
        b.doMove(m1);
        assertFalse(c.equals(b));
        assertFalse(b.equals(c));
        c.doMove(m1);
        assertTrue(c.equals(b));
        assertTrue(b.equals(c));
        assertEquals(b.hashCode(), c.hashCode());
    }

    @Test
    public void move_move() {
        b.doMove(m1);
        assertFalse(isBoardEmpty(b));
        b.undoMove(m1);
        assertTrue(isBoardEmpty(b));
        b.doMove(m2);
        assertFalse(isBoardEmpty(b));
        b.undoMove(m2);
        assertTrue(isBoardEmpty(b));
    }

    @Test
    public void can_move() {
        Color[][] state = new Color[][]{
            {Color.PLAYER1, Color.EMPTY, Color.PLAYER1, Color.EMPTY, Color.PLAYER1},
            {Color.EMPTY, Color.PLAYER1, Color.EMPTY, Color.PLAYER1, Color.EMPTY},
            {Color.PLAYER1, Color.EMPTY, Color.PLAYER1, Color.EMPTY, Color.PLAYER1},
            {Color.EMPTY, Color.PLAYER1, Color.EMPTY, Color.PLAYER1, Color.EMPTY},
            {Color.PLAYER1, Color.EMPTY, Color.PLAYER1, Color.EMPTY, Color.PLAYER1}
        };
        Whitebox.setInternalState(b, state);
        assertFalse(b.canMove(Color.PLAYER2));
        assertTrue(b.canMove(Color.PLAYER1));
    }

    @Test
    public void generator1() {
        List<Move> result = b.getMovesFor(Color.PLAYER1);
        assertEquals(25, result.size());
    }

    @Test
    public void generator2() {
        b.doMove(m1);
        List<Move> result = b.getMovesFor(Color.PLAYER2);
        assertEquals(20, result.size());
    }
}
