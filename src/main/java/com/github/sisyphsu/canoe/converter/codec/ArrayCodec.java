package com.github.sisyphsu.canoe.converter.codec;

import com.github.sisyphsu.canoe.converter.Codec;
import com.github.sisyphsu.canoe.converter.Converter;
import com.github.sisyphsu.canoe.reflect.XType;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;

/**
 * Convert everything to Object[] if could
 *
 * @author sulin
 * @since 2019-05-13 18:53:04
 */
public final class ArrayCodec extends Codec {

    /**
     * Convert Object[] to T[] based on specified T Type.
     * For beter performance, some array shouldn't use this way, like byte[]...
     */
    @Converter(extensible = true)
    public Object[] toArray(Object[] arr, XType<?> type) {
        if (type.isPure()) {
            Class<?> srcArrClass = arr.getClass();
            Class<?> tgtArrClass = type.getRawType();
            if (tgtArrClass.isAssignableFrom(srcArrClass)) {
                return arr; // can assign arr to be type.
            }
        }
        return this.toArray(Arrays.asList(arr), type);
    }

    /**
     * Convert Collection to T[] based on specified T Type.
     */
    @Converter
    public Object[] toArray(Collection list, XType<?> type) {
        XType<?> itemType = type.getComponentType();
        if (itemType == null) {
            itemType = toXType(type.getRawType().getComponentType());
        }
        Object[] result = (Object[]) Array.newInstance(itemType.getRawType(), list.size());
        int i = 0;
        for (Object item : list) {
            if (item == null || (itemType.isPure() && item.getClass() == itemType.getRawType())) {
                result[i] = item; // copy directly
            } else {
                result[i] = convert(item, itemType); // need convert
            }
            i++;
        }
        return result;
    }

    /**
     * Convert boolean[] to Boolean[]
     */
    @Converter
    public Boolean[] toBooleanArray(boolean[] arr) {
        Boolean[] result = new Boolean[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = arr[i];
        }
        return result;
    }

    /**
     * Convert Boolean[] to boolean[]
     */
    @Converter
    public boolean[] toBoolArray(Boolean[] arr) {
        boolean[] result = new boolean[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = arr[i]; // could NPE
        }
        return result;
    }

    /**
     * Convert byte[] to Byte[]
     */
    @Converter
    public Byte[] toByteArray(byte[] arr) {
        Byte[] result = new Byte[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = arr[i];
        }
        return result;
    }

    /**
     * Convert Byte[] to byte[]
     */
    @Converter
    public byte[] toByteArray(Byte[] arr) {
        byte[] result = new byte[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = arr[i];
        }
        return result;
    }

    /**
     * Convert char[] to Character[]
     */
    @Converter
    public Character[] toCharacterArray(char[] arr) {
        Character[] result = new Character[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = arr[i];
        }
        return result;
    }

    /**
     * Convert Character[] to char[]
     */
    @Converter
    public char[] toCharArray(Character[] arr) {
        char[] result = new char[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = arr[i];
        }
        return result;
    }

    /**
     * Convert double[] to Double[]
     */
    @Converter
    public Double[] toDoubleArray(double[] arr) {
        Double[] result = new Double[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = arr[i];
        }
        return result;
    }

    /**
     * Convert Double[] to double[]
     */
    @Converter
    public double[] toDoubleArray(Double[] arr) {
        double[] result = new double[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = arr[i];
        }
        return result;
    }

    /**
     * Convert float[] to Float[]
     */
    @Converter
    public Float[] toFloatArray(float[] arr) {
        Float[] result = new Float[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = arr[i];
        }
        return result;
    }

    /**
     * Convert Float[] to float[]
     */
    @Converter
    public float[] toFloatArray(Float[] arr) {
        float[] result = new float[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = arr[i];
        }
        return result;
    }

    /**
     * Convert Integer[] to int[]
     */
    @Converter
    public Integer[] toIntegerArray(int[] arr) {
        Integer[] result = new Integer[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = arr[i];
        }
        return result;
    }

    /**
     * Convert Integer[] to int[]
     */
    @Converter
    public int[] toIntArray(Integer[] arr) {
        int[] result = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = arr[i];
        }
        return result;
    }

    /**
     * Convert Long[] to long[]
     */
    @Converter
    public Long[] toLongArray(long[] arr) {
        Long[] result = new Long[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = arr[i];
        }
        return result;
    }

    /**
     * Convert Long[] to long[]
     */
    @Converter
    public long[] toLongArray(Long[] arr) {
        long[] result = new long[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = arr[i];
        }
        return result;
    }

    /**
     * Convert Short[] to short[]
     */
    @Converter
    public Short[] toShortArray(short[] arr) {
        Short[] result = new Short[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = arr[i];
        }
        return result;
    }

    /**
     * Convert Short[] to short[]
     */
    @Converter
    public short[] toShortArray(Short[] arr) {
        short[] result = new short[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = arr[i];
        }
        return result;
    }

}