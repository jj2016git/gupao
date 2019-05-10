package com.example.mebatis.v2.session;

import com.example.mebatis.v2.Executor;
import com.example.mebatis.v2.binding.MapperRegistry;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * 解析全局配置及mapper配置
 */
@Data
@Slf4j
public class Configuration {
    private static final ResourceBundle GLOBAL_CONFIG = ResourceBundle.getBundle("mybatis");
    private static final ResourceBundle MAPPERS = ResourceBundle.getBundle("sql2");
    private static final MapperRegistry MAPPER_REGISTRY = new MapperRegistry();
    /**
     * statementId -> sql
     */
    private static final Map<String, String>  mappedStatements = new HashMap<>();

    public Configuration() {
        try {
            // 1. 解析mappers配置文件
            for (String key : MAPPERS.keySet()) {
                String value = MAPPERS.getString(key);
                String sql = value.split("--")[0];
                String resultType = value.split("--")[1];
                String mappedInterfaceName = key.substring(0, key.lastIndexOf('.'));
                MAPPER_REGISTRY.addMapper(Class.forName(mappedInterfaceName), Class.forName(resultType));
                mappedStatements.putIfAbsent(key, sql);
            }

            // 2. 扫mapperPath, 根据注解进行注册
            String mapperPath = GLOBAL_CONFIG.getString("mapper.path");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public <T> T getMapper(Class<T> clazz, DefaultSqlSession defaultSqlSession) {
        return MAPPER_REGISTRY.getMapper(clazz, defaultSqlSession);
    }

    public boolean hasStatement(String statementId) {
        return false;
    }

    public Executor createExecutor() {
        return null;
    }

    public String getMappedStatement(String statementId) {
        return mappedStatements.get(statementId);
    }
}
