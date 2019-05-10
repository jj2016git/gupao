package com.example.mebatis.v1;

import com.example.mebatis.v1.mapper.Blog;
import lombok.Cleanup;

import java.sql.*;

public class GPExecutor {
    private GPConfiguration configuration;

    public GPExecutor(GPConfiguration configuration) {
        this.configuration = configuration;
    }

    public <T> T query(String sql, Object paramater) {
        Blog blog = new Blog();

        try {
            // 注册 JDBC 驱动
            Class.forName(configuration.getJdbcDriver());

            // 打开连接
            @Cleanup Connection conn = DriverManager.getConnection(configuration.getJdbcUrl(), configuration.getJdbcUsername(), configuration.getJdbcPassword());

            // 执行查询
            @Cleanup Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery(String.format(sql, paramater));

            // 获取结果集
            while (rs.next()) {
                Integer bid = rs.getInt("bid");
                String name = rs.getString("name");
                Integer authorId = rs.getInt("author_id");
                blog.setAuthorId(authorId);
                blog.setBid(bid);
                blog.setName(name);
            }
            System.out.println(blog);

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (T)blog;
    }
}
