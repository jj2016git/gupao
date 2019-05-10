package com.example.mebatis.v1;

public class GPSqlSession {
    private GPConfiguration configuration;
    private GPExecutor executor;

    public GPSqlSession(GPConfiguration gpConfiguration, GPExecutor gpExecutor) {
        configuration = gpConfiguration;
        executor = gpExecutor;
    }

    public <T> T getMapper(Class<T> clazz) {

        return configuration.getMapper(clazz, this);
    }

    public Object selectOne(String statementId, Object arg) {
        String sql = configuration.getMappedStatements().get(statementId);
        if (sql != null) {
            return executor.query(sql, arg);
        }

        return null;
    }
}
