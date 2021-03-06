package com.github.smartbuf.node.array;

import com.github.smartbuf.node.Node;
import com.github.smartbuf.node.NodeType;

/**
 * DoubleArrayNode represents double[]
 *
 * @author sulin
 * @since 2019-11-03 14:47:29
 */
public final class DoubleArrayNode extends Node {

    private final double[] data;

    public DoubleArrayNode(double[] data) {
        this.data = data;
    }

    @Override
    public Object value() {
        return data;
    }

    @Override
    public NodeType type() {
        return NodeType.ARRAY_DOUBLE;
    }
}
