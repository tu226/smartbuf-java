package com.github.smartbuf.node.array;

import com.github.smartbuf.node.Node;
import com.github.smartbuf.node.NodeType;

/**
 * BooleanArrayNode represents boolean[]
 *
 * @author sulin
 * @since 2019-11-03 14:46:34
 */
public final class BooleanArrayNode extends Node {

    private final boolean[] data;

    public BooleanArrayNode(boolean[] data) {
        this.data = data;
    }

    @Override
    public Object value() {
        return data;
    }

    @Override
    public NodeType type() {
        return NodeType.ARRAY_BOOLEAN;
    }
}
