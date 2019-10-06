package com.github.sisyphsu.nakedata.node;

import com.github.sisyphsu.nakedata.convertor.Codec;
import com.github.sisyphsu.nakedata.convertor.Converter;
import com.github.sisyphsu.nakedata.node.std.ObjectNode;
import net.sf.cglib.beans.BeanMap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Object's Codec
 *
 * @author sulin
 * @since 2019-06-05 20:29:40
 */
public final class BeanNodeCodec extends Codec {

    private static final Map<Class, ObjectNode.Key> FIELDS_MAP = new ConcurrentHashMap<>();

    /**
     * encode map to ObjectNode, pojo should be encoded as map first.
     *
     * @param map Map
     * @return ObjectNode
     */
    @Converter
    public Node toNode(Map<?, ?> map) {
        if (map.isEmpty()) {
            return ObjectNode.EMPTY;
        }
        final HashMap<String, Node> fields = new HashMap<>();
        for (Object item : map.entrySet()) {
            Map.Entry entry = (Map.Entry) item;
            String key;
            if (entry.getKey() instanceof String) {
                key = (String) entry.getKey();
            } else {
                key = convert(entry.getKey(), String.class);
            }
            fields.put(key, convert(entry.getValue(), Node.class));
        }
        ObjectNode.Key objectKey;
        if (map instanceof BeanMap) {
            objectKey = parseBeanMapKey((BeanMap) map);
        } else {
            String[] fieldNames = fields.keySet().toArray(new String[0]);
            objectKey = new ObjectNode.Key(false, fieldNames);
        }
        return ObjectNode.valueOf(objectKey, fields);
    }

    /**
     * decode ObjectNode to map, expose fields directly.
     *
     * @param node ObjectNode
     * @return Map
     */
    @Converter
    public Map toMap(ObjectNode node) {
        if (node == ObjectNode.NULL) {
            return null;
        }
        if (node == ObjectNode.EMPTY) {
            return new HashMap();
        }
        return node.getData();
    }

    /**
     * Get the sorted names from the specified Map, which maybe BeanMap
     *
     * @param map Map
     * @return names as array
     */
    public static ObjectNode.Key parseBeanMapKey(BeanMap map) {
        Class beanCls = map.getBean().getClass();
        ObjectNode.Key objectKey = FIELDS_MAP.get(beanCls);
        if (objectKey == null) {
            String[] fieldNames = new String[map.size()];
            int i = 0;
            for (Object key : map.keySet()) {
                String fieldName = String.valueOf(key);
                fieldNames[i++] = fieldName;
            }
            Arrays.sort(fieldNames);
            objectKey = new ObjectNode.Key(true, fieldNames);
            FIELDS_MAP.put(beanCls, objectKey);
        }
        return objectKey;
    }

}
