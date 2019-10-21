package com.github.sisyphsu.nakedata.node;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author sulin
 * @since 2019-09-21 14:10:57
 */
public class NodeMapperTest {

    //    @Test
    void test() {
        List<Bean> beans = new ArrayList<>();
        beans.add(new Bean(1, "hello"));
        beans.add(new Bean(2, "world"));
    }

    @Data
    public static class Bean {

        private int    id;
        private String name;
        private Date   timestamp;

        public Bean(int id, String name) {
            this.id = id;
            this.name = name;
            this.timestamp = new Date();
        }
    }

}
