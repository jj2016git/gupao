package com.example.mebatis.v1;

import lombok.Cleanup;
import lombok.Data;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

@Data
public class GPConfiguration {
    private String jdbcDriver;
    private String jdbcUrl;
    private String jdbcUsername;
    private String jdbcPassword;
    private Map<String, String> mappedStatements = new HashMap<>();

    public GPConfiguration() {
        Properties properties = null;
        try {
            properties = parseConfig("/mybatis.properties");
            parseSql("/sql.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.jdbcDriver = properties.getProperty("jdbc.driver");
        this.jdbcUrl = properties.getProperty("jdbc.url");
        this.jdbcUsername = properties.getProperty("jdbc.username");
        this.jdbcPassword = properties.getProperty("jdbc.password");
    }

    private Properties parseConfig(String configPath) throws IOException {
        @Cleanup InputStream inputStream = this.getClass().getResourceAsStream(configPath);
        Properties properties = new Properties();
        properties.load(inputStream);

        return properties;
    }

    private void parseSql(String mapperPath) throws IOException {
        @Cleanup InputStream inputStream = this.getClass().getResourceAsStream(mapperPath);
        Properties properties = new Properties();
        properties.load(inputStream);

        properties.forEach((key, val) -> {
            this.mappedStatements.put(key.toString(), val.toString());
        });
    }

    public <T> T getMapper(Class<T> clazz, GPSqlSession sqlSession) {
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class[]{clazz}, new GPMapperProxy(sqlSession));
    }
}
