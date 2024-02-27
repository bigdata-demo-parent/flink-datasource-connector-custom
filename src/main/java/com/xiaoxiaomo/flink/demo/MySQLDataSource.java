package com.xiaoxiaomo.flink.demo;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.source.RichSourceFunction;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MySQLDataSource extends RichSourceFunction<User> {

    private Connection connection = null;

    private PreparedStatement preparedStatement = null;

    private volatile boolean isRunning = true;

    String sql ;

    public MySQLDataSource(String sql) {
        this.sql = sql;
    }

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);

        if (null == connection) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                    "jdbc:mysql://192.168.13.34:3306/test?useUnicode=true&characterEncoding=UTF-8",
                    "root",
                    "iXnetCOM88");
        }

        if (null == preparedStatement) {
            preparedStatement = connection.prepareStatement(sql);
        }
    }

    /**
     * 释放资源
     *
     * @throws Exception
     */
    @Override
    public void close() throws Exception {
        super.close();

        if (null != preparedStatement) {
            try {
                preparedStatement.close();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        if (null != connection) {
            connection.close();
        }
    }

    @Override
    public void run(SourceContext<User> ctx) throws Exception {
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next() && isRunning) {
            User user = new User();
            user.setId(resultSet.getInt("id"));
            user.setName(resultSet.getString("name"));
            ctx.collect(user);
        }
    }

    @Override
    public void cancel() {
        isRunning = false;
    }
}

