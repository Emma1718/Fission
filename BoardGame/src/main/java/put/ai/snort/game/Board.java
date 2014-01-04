/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package put.ai.snort.game;

import java.util.List;
import put.ai.snort.game.Player.Color;

/**
 * Stan planszy
 */
public interface Board extends Cloneable {

    /**
     * Czy gracz koloru color może wykonać jescze jakiś ruch
     */
    boolean canMove(Color color);

    /**
     * Zrób ruch move
     */
    void doMove(Move move);

    /**
     * Zwraca listę wszystkich ruchów dopuszczalnych w danym układzie planszy
     * dla gracza koloru color.
     * Dla wszystkich tych ruchów zachodzi: <code>move.getColor()==color</code>.
     */
    List<Move> getMovesFor(Color color);

    /**
     * Zwraca rozmiar planszy
     */
    int getSize();
    
    int rateMove(Color p, Move m);

    /**
     * Zwraca kolor pionka zajmującego pole o współrzędnych (x,y). Dla
     * niepoprawnych współrzędnych zwracana jest wartość odpowiadająca pustemu
     * polu
     */
    Color getState(int x, int y);

    /**
     * Wycofuje ruch move, przywracając planszę do stanu sprzed tego ruchu
     */
    void undoMove(Move move);
    
    /**
     * Tworzy dokładną, głęboką kopię tej planszy, która nie współdzieli z nią
     * żadnych obiektów
     */
    Board clone();
}
