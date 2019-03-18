package com.jachin.des.mapper;

import com.jachin.des.entity.Template;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

// 在每个 mapper 上添加这个注解，或者在启动类添加@MapperScan("com.xxx.mapper")
@Mapper
public interface TemplateMapper {

    @Select("select * from template where tempId=${tempId};")
    public Template findById(@Param("tempId")int id);

    @Select("select * from template")
    public List<Template> getTemplateList(int aid);
}
