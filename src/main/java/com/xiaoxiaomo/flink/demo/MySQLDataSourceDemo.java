package com.xiaoxiaomo.flink.demo;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class MySQLDataSourceDemo {

    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        String sql = "select id, name from user";
        DataStream<User> dataStream = env.addSource(new MySQLDataSource(sql));
        dataStream.print();

        env.execute("Customize DataSource demo : MySQLDataSourceDemo");
    }
}

