package com.jachin.des.mapper;

import com.jachin.des.pojo.Template;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TemplateMapper {

    @Select("select * from template")
    public List<Template> findAll();

    @Insert("insert into " +
            "template(aid,tempId,name,time,status,price) " +
            "values(#{aid},#{tempId},#{name},#{time},#{status},#{price})")
    public void addTemplate(Template template);
}
