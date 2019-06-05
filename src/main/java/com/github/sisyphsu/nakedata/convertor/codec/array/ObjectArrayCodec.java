package com.github.sisyphsu.nakedata.convertor.codec.array;

import com.github.sisyphsu.nakedata.convertor.codec.Codec;
import com.github.sisyphsu.nakedata.node.Node;
import com.github.sisyphsu.nakedata.node.container.MixArrayNode;

/**
 * Codec for Object[]
 *
 * @author sulin
 * @since 2019-05-13 18:53:04
 */
public class ObjectArrayCodec extends Codec<Object[]> {

    @Override
    public Node toNode(Object[] objects) {
//        MixArrayNode arrayNode = MixArrayNode.valueOf(null);
        // TODO ARRAY handle
        return null;
    }

}
