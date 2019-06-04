package com.github.sisyphsu.nakedata.node.array;

import com.github.sisyphsu.nakedata.node.Node;
import com.github.sisyphsu.nakedata.type.DataType;

/**
 * @author sulin
 * @since 2019-06-04 19:51:03
 */
public class VarintArrayNode extends Node {
    @Override
    public DataType getDataType() {
        return DataType.ARRAY;
    }

    @Override
    public boolean isNull() {
        return false;
    }
}
