package com.example.mebatis.v1.mapper;

import com.example.mebatis.v2.annotation.Entity;
import com.example.mebatis.v2.annotation.Select;

@Entity(Blog.class)
public interface BlogMapper {
    /**
     * 根据主键查询文章
     *
     * @param bid
     * @return
     */
    @Select("select * from blog where bid = %d")
    public Blog selectBlogById(Integer bid);

}
