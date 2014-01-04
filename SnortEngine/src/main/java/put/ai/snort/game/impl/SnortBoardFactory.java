/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package put.ai.snort.game.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import put.ai.snort.engine.BoardFactory;
import put.ai.snort.engine.parameters.IntegerParameter;
import put.ai.snort.engine.parameters.Parameter;
import put.ai.snort.game.Board;

public class SnortBoardFactory implements BoardFactory {

    private static final List<? extends Parameter<?>> PARAMS = Collections.unmodifiableList(Arrays.asList(new IntegerParameter(BOARD_SIZE, 10, 4, 50)));
    private int boardSize;

    @Override
    public Board create() {
        return new SnortBoard(boardSize);
    }

    @Override
    public List<? extends Parameter<?>> getConfigurationOptions() {
        return PARAMS;
    }

    @Override
    public void configure(Map<String, Object> configuration) {
        boardSize = Parameter.<Integer>get(BOARD_SIZE, PARAMS, configuration);
    }
}
