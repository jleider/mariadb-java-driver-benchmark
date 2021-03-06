package org.perf.jdbc;

import org.openjdk.jmh.annotations.Benchmark;
import org.perf.jdbc.common.BenchmarkInit;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BenchmarkSelect1000Rows extends BenchmarkInit {
    private String request = "select * from seq_1_to_1000"; //using the <a href="https://mariadb.com/kb/en/mariadb/sequence/">sequence storage engine</a>

    @Benchmark
    public ResultSet mysql(MyState state) throws Throwable {
        return select1000Row(state.mysqlConnectionText);
    }

    @Benchmark
    public ResultSet mariadb(MyState state) throws Throwable {
        return select1000Row(state.mariadbConnectionText);
    }

    @Benchmark
    public ResultSet drizzle(MyState state) throws Throwable {
        return select1000Row(state.drizzleConnectionText);
    }

    private ResultSet select1000Row(Connection connection) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            try (ResultSet rs = statement.executeQuery(request)) {
                while (rs.next()) {
                    rs.getString(1);
                }
                return rs;
            }
        }
    }
}
