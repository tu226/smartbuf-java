package com.github.sisyphsu.nakedata.node.codec;

import com.github.sisyphsu.nakedata.convertor.codec.Codec;
import com.github.sisyphsu.nakedata.node.Node;
import com.github.sisyphsu.nakedata.node.std.ObjectNode;

import java.util.Map;
import java.util.TreeMap;

/**
 * Map's Codec
 *
 * @author sulin
 * @since 2019-06-05 20:29:40
 */
public class MapCodec extends Codec<Map> {

    public Node toNode(Map map) {
        if (map == null) {
            return ObjectNode.NULL;
        }
        TreeMap<String, Node> fields = new TreeMap<>();
        for (Object item : map.entrySet()) {
            Map.Entry entry = (Map.Entry) item;
            String key;
            if (entry.getKey() instanceof String) {
                key = (String) entry.getKey();
            } else {
                key = convert(entry.getKey(), String.class);
            }
            Node value = convert(entry.getValue(), Node.class);

            fields.put(key, value);
        }
        return ObjectNode.valueOf(fields);
    }

}