package com.example.mebatis.v2.session;


import com.example.mebatis.v2.Executor;

public class DefaultSqlSession {
    private Configuration configuration;
    private Executor executor;

    public DefaultSqlSession(Configuration configuration) {
        this.configuration = configuration;
        this.executor = this.configuration.createExecutor();
    }

    public <T> T getMapper(Class<T> clazz) {
        return configuration.getMapper(clazz, this);
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public Object selectOne(String statementId, Object[] args, Class<?> resultType) {
        String sql = getConfiguration().getMappedStatement(statementId);
        return executor.query(sql, args, resultType);
    }
}
