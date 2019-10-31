package com.github.sisyphsu.canoe.benchmark.small;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.sisyphsu.canoe.Canoe;
import com.github.sisyphsu.canoe.CanoePacket;
import com.github.sisyphsu.canoe.CanoeStream;
import com.github.sisyphsu.canoe.node.BeanNodeCodec;
import com.github.sisyphsu.canoe.node.Node;
import org.openjdk.jmh.annotations.*;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Benchmark            Mode  Cnt     Score    Error  Units
 * PBenchmark.json      avgt    6   727.796 ± 63.093  ns/op
 * PBenchmark.packet    avgt    6  1392.294 ± 27.676  ns/op
 * PBenchmark.protobuf  avgt    6   211.894 ± 13.259  ns/op
 * PBenchmark.stream    avgt    6   701.970 ± 10.048  ns/op
 * <p>
 * Need more works to do to improve performace~
 * <p>
 * 190ns for Output#scan
 * 500ns for Output#doWrite and others
 *
 * @author sulin
 * @since 2019-10-28 17:32:33
 */
@Warmup(iterations = 2, time = 2)
@Fork(2)
@Measurement(iterations = 3, time = 3)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class PBenchmark {

    static final Date date = new Date();

    private static final BeanNodeCodec beanNodeCodec = new BeanNodeCodec();
    private static final ObjectMapper  OBJECT_MAPPER = new ObjectMapper();
    private static final UserModel     USER          = UserModel.random();
    private static final CanoeStream   STREAM        = new CanoeStream();

    static {
        Canoe.CODEC.installCodec(beanNodeCodec);
        beanNodeCodec.setFactory(Canoe.CODEC);
    }

    @Benchmark
    public void json() throws JsonProcessingException {
        OBJECT_MAPPER.writeValueAsString(USER.toModel());
    }

    @Benchmark
    public void packet() throws IOException {
        CanoePacket.serialize(USER.toModel());
    }

    @Benchmark
    public void stream() throws IOException {
        STREAM.serialize(USER.toModel());
    }

    @Benchmark
    public void protobuf() {
        USER.toPB().toByteArray();
    }

    @Benchmark
    public void toNode() {
//        USER.toModel(); // 27ns

//        Canoe.CODEC.getPipeline(UserModel.class, Node.class); // 11ns -> 5ns

        // 441ns, CodecContext cost 20ns
        // 123ns, 48ns if not convert value, 75ns if no Date
        // [date -> node] cost 100ns???
//        USER.setCreateTime(null);
//        beanNodeCodec.toNode(USER);
//        CodecContext.reset();

        // 123ns
//        beanNodeCodec.toNode(USER);
//        CodecContext.reset();

//        Canoe.CODEC.convert(date, Node.class); // 60ns -> 17ns

        // 380ns, Convert all fields into Node
//        BeanHelper helper = BeanHelper.valueOf(USER.getClass());
//        String[] names = helper.getNames();
//        Object[] values = helper.getValues(USER);
//        for (int i = 0, len = values.length; i < len; i++) {
//            values[i] = values[i];
//        }
//        new ObjectNode(true, names, values);

        // 263ns = 27ns(toModel) + 11ns(getPipeline) + 178ns(BeanNodeCodec.toNode)
        // Pipeline.convert cost 50ns ???
        // use ASM optimize ConverterPipeline, 263ns -> 155ns
        Canoe.CODEC.convert(USER.toModel(), Node.class);

        // 169ns = toModel[27ns] + beanNodeCodec#toNode[123ns] + getPipeline[5ns] + [14ns]
        // CodecContext/ThreadLocal may cost 20~30ns
    }

}