package com.example.mebatis.v2.session;

public class SqlSessionFactory {
    private Configuration configuration;

    /**
     * 解析配置
     *
     * @return
     */
    public SqlSessionFactory build() {
         configuration = new Configuration();
        return this;
    }

    public DefaultSqlSession openSqlSession() {
        return new DefaultSqlSession(configuration);
    }
}
