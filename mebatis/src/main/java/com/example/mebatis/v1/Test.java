package com.example.mebatis.v1;


import com.example.mebatis.v1.mapper.BlogMapper;

public class Test {
    public static void main(String[] args) {
        GPConfiguration configuration = new GPConfiguration();
        GPSqlSession sqlSession = new GPSqlSession(configuration, new GPExecutor(configuration));
        BlogMapper blogMapper = sqlSession.getMapper(BlogMapper.class);
        blogMapper.selectBlogById(1);
    }
}
