/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package put.ai.snort.dominion;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import put.ai.snort.engine.BoardFactory;
import put.ai.snort.engine.parameters.Parameter;
import put.ai.snort.game.Board;

public class DominionBoardFactory implements BoardFactory {

    @Override
    public Board create() {
        return new DominionBoard();
    }

    @Override
    public List<? extends Parameter<?>> getConfigurationOptions() {
        return Collections.EMPTY_LIST;
    }

    @Override
    public void configure(Map<String, Object> configuration) {
    }
}
