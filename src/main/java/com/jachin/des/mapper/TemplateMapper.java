package com.jachin.des.mapper;

import com.jachin.des.mapper.provider.TemplateSql;
import com.jachin.des.pojo.Template;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TemplateMapper {

    @SelectProvider(type=TemplateSql.class, method = "list")
    public List<Template> findAll();

    @UpdateProvider(type=TemplateSql.class, method = "update")
    public void updateName(@Param("template")Template template);

    @DeleteProvider(type = TemplateSql.class, method = "delete")
    public void deleteById(Template template);

    @InsertProvider(type = TemplateSql.class, method = "add")
    public void addTemplate(Template template);
}
