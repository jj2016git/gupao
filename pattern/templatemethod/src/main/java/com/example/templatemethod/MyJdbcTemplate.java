package com.example.templatemethod;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MyJdbcTemplate {

    private DataSource dataSource;

    public MyJdbcTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public <T> List<T> query(String sql, RowMapper<T> rowMapper) throws DataAccessException {
        try {
            // step 1: get connection
            Connection conn = getConnection();

            // step 2: create preparedStatement
            PreparedStatement pstmt = createStatement(conn, sql);

            // step 3: 执行
            ResultSet rs = pstmt.executeQuery();

            // step 4: handleResult
            List<T> results = handleResult(rs, rowMapper);

            // step 5: 关闭、回收资源
            recycleResource(conn, pstmt, rs);

            return results;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private void recycleResource(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            closeConnection(conn);
            pstmt.close();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void closeConnection(Connection conn) {
        try {
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected <T> List<T> handleResult(ResultSet rs, RowMapper<T> rowMapper) {
        try {
            int rowCount = 0;
            List<T> results = new ArrayList<>();
            while (rs.next()) {
                T obj = rowMapper.mapRow(rs, rowCount++);
                results.add(obj);
            }

            return results;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    private PreparedStatement createStatement(Connection connection, String sql) {
        try {
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


}
