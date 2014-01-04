/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package put.ai.snort.engine;

import java.util.List;
import java.util.Map;
import put.ai.snort.engine.parameters.Parameter;
import put.ai.snort.game.Board;

public interface BoardFactory {

    static final String BOARD_SIZE = "Board size";

    List<? extends Parameter<?>> getConfigurationOptions();

    void configure(Map<String, Object> configuration);

    Board create();
}
