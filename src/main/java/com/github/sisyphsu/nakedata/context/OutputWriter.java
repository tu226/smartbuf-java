package com.github.sisyphsu.nakedata.context;

import com.github.sisyphsu.nakedata.ArrayType;
import com.github.sisyphsu.nakedata.utils.NumberUtils;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import static com.github.sisyphsu.nakedata.context.Proto.BODY_FLAG_ARRAY;

/**
 * @author sulin
 * @since 2019-04-27 13:02:49
 */
public final class OutputWriter {

    private final OutputStream stream;

    public OutputWriter(OutputStream stream) {
        this.stream = stream;
    }

    public void writeByte(byte b) throws IOException {
        stream.write(b);
    }

    public void writeVarInt(long n) throws IOException {
        this.writeVarUint(NumberUtils.intToUint(n));
    }

    public void writeVarUint(long n) throws IOException {
        do {
            if ((n & 0xFFFFFFFFFFFFFF80L) == 0) {
                stream.write((byte) n);
            } else {
                stream.write((byte) ((n | 0x80) & 0xFF));
            }
            n >>>= 7;
        } while (n != 0);
    }

    public void writeFloat(float f) throws IOException {
        int bits = NumberUtils.floatToBits(f);
        for (int i = 0; i < 4; i++) {
            stream.write((byte) (bits & 0xFF));
            bits >>>= 8;
        }
    }

    public void writeDouble(double d) throws IOException {
        long bits = NumberUtils.doubleToBits(d);
        for (int i = 0; i < 8; i++) {
            stream.write((byte) (bits & 0xFF));
            bits >>>= 8;
        }
    }

    public void writeString(String str) throws IOException {
        byte[] bytes = str.getBytes();
        this.writeVarUint(bytes.length);
        for (byte b : bytes) {
            stream.write(b);
        }
    }

    public void writeBooleanArray(boolean[] booleans) throws IOException {
        int len = booleans.length;
        this.writeSliceHead(len, ArrayType.BOOL, false);
        int off;
        for (int i = 0; i < len; i += 8) {
            byte b = 0;
            for (int j = 0; j < 8; j++) {
                if ((off = i * 8 + j) >= len) {
                    break;
                }
                if (booleans[off]) {
                    b |= 1 << j;
                }
            }
            stream.write(b);
        }
    }

    public void writeByteArray(byte[] bytes) throws IOException {
        int len = bytes.length;
        this.writeSliceHead(len, ArrayType.BYTE, false);
        for (byte b : bytes) {
            stream.write(b);
        }
    }

    public void writeShortArray(short[] shorts) throws IOException {
        int len = shorts.length;
        this.writeSliceHead(len, ArrayType.SHORT, false);
        for (short s : shorts) {
            stream.write((byte) (s >> 8));
            stream.write((byte) s);
        }
    }

    public void writeIntArray(int[] ints) throws IOException {
        int len = ints.length;
        this.writeSliceHead(len, ArrayType.INT, false);
        for (int i : ints) {
            writeVarInt(i);
        }
    }

    public void writeLongArray(long[] longs) throws IOException {
        int len = longs.length;
        this.writeSliceHead(len, ArrayType.LONG, false);
        for (long l : longs) {
            writeVarInt(l);
        }
    }

    public void writeFloatArray(float[] floats) throws IOException {
        int len = floats.length;
        this.writeSliceHead(len, ArrayType.FLOAT, false);
        for (float f : floats) {
            writeFloat(f);
        }
    }

    public void writeDoubleArray(double[] doubles) throws IOException {
        int len = doubles.length;
        this.writeSliceHead(len, ArrayType.DOUBLE, false);
        for (double d : doubles) {
            writeDouble(d);
        }
    }

    public void writeBooleanSlice(List<Boolean> booleans, boolean hasMore) throws IOException {
        int len = booleans.size();
        this.writeSliceHead(len, ArrayType.BOOL, hasMore);
        int off;
        for (int i = 0; i < len; i += 8) {
            byte b = 0;
            for (int j = 0; j < 8; j++) {
                if ((off = i * 8 + j) >= len) {
                    break;
                }
                if (booleans.get(off)) {
                    b |= 1 << j;
                }
            }
            stream.write(b);
        }
    }

    public void writeByteSlice(List<Byte> bytes, boolean hasMore) throws IOException {
        int len = bytes.size();
        this.writeSliceHead(len, ArrayType.BYTE, hasMore);
        for (byte b : bytes) {
            stream.write(b);
        }
    }

    public void writeShortSlice(List<Short> shorts, boolean hasMore) throws IOException {
        int len = shorts.size();
        this.writeSliceHead(len, ArrayType.SHORT, hasMore);
        for (short s : shorts) {
            stream.write((byte) (s >> 8));
            stream.write((byte) s);
        }
    }

    public void writeIntSlice(List<Integer> ints, boolean hasMore) throws IOException {
        int len = ints.size();
        this.writeSliceHead(len, ArrayType.INT, hasMore);
        for (int i : ints) {
            writeVarInt(i);
        }
    }

    public void writeLongSlice(List<Long> longs, boolean hasMore) throws IOException {
        int len = longs.size();
        this.writeSliceHead(len, ArrayType.LONG, hasMore);
        for (long l : longs) {
            writeVarInt(l);
        }
    }

    public void writeFloatSlice(List<Float> floats, boolean hasMore) throws IOException {
        int len = floats.size();
        this.writeSliceHead(len, ArrayType.FLOAT, hasMore);
        for (float f : floats) {
            writeFloat(f);
        }
    }

    public void writeDoubleSlice(List<Double> doubles, boolean hasMore) throws IOException {
        int len = doubles.size();
        this.writeSliceHead(len, ArrayType.DOUBLE, hasMore);
        for (double d : doubles) {
            writeDouble(d);
        }
    }

    public void writeSliceHead(int size, ArrayType elType, boolean hasMore) throws IOException {
        this.writeVarUint((size << 7) | (elType.getCode() << 3) | (BODY_FLAG_ARRAY << 1) | (hasMore ? 1 : 0));
    }

}
