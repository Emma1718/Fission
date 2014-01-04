/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package put.ai.snort.linesofaction;

import org.junit.Test;
import static org.junit.Assert.*;
import put.ai.snort.game.Player;

public class LoAMoveTest {

    @Test
    public void test1a() {
        LoAMove m = new LoAMove(Player.Color.EMPTY, 1, 2, 1, 4);
        assertEquals(2, m.length());
    }

    @Test
    public void test1b() {
        LoAMove m = new LoAMove(Player.Color.EMPTY, 1, 4, 1, 2);
        assertEquals(2, m.length());
    }

    @Test
    public void test2a() {
        LoAMove m = new LoAMove(Player.Color.EMPTY, 2, 1, 4, 1);
        assertEquals(2, m.length());
    }

    @Test
    public void test2b() {
        LoAMove m = new LoAMove(Player.Color.EMPTY, 4, 1, 2, 1);
        assertEquals(2, m.length());
    }

    @Test
    public void test3a() {
        LoAMove m = new LoAMove(Player.Color.EMPTY, 2, 1, 4, 3);
        assertEquals(2, m.length());
    }

    @Test
    public void test3b() {
        LoAMove m = new LoAMove(Player.Color.EMPTY, 4, 3, 2, 1);
        assertEquals(2, m.length());
    }
}
