/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package put.ai.snort.cleave;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import put.ai.snort.engine.BoardFactory;
import put.ai.snort.engine.parameters.IntegerParameter;
import put.ai.snort.engine.parameters.Parameter;

public class CleaveBoardFactory implements BoardFactory {

    private static final List<? extends Parameter<?>> PARAMS = Collections.unmodifiableList(Arrays.asList(new IntegerParameter(BOARD_SIZE, 8, 8, 20)));
    private int size = (Integer) PARAMS.get(0).getDefaultValue();

    @Override
    public CleaveBoard create() {
        return new CleaveBoard(size);
    }

    @Override
    public List<? extends Parameter<?>> getConfigurationOptions() {
        return PARAMS;
    }

    @Override
    public void configure(Map<String, Object> configuration) {
        size = Parameter.<Integer>get(BOARD_SIZE, PARAMS, configuration);
    }
}
