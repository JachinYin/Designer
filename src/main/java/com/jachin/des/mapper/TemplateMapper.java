package com.jachin.des.mapper;

import com.jachin.des.entity.SearchArg;
import com.jachin.des.entity.Template;
import com.jachin.des.mapper.provider.TemplateSql;
import org.apache.ibatis.annotations.*;

import java.util.List;

// 在每个 mapper 上添加这个注解，或者在启动类添加@MapperScan("com.xxx.mapper")
@Mapper
public interface TemplateMapper {

    // =====基础查改增删=====
    @SelectProvider(type = TemplateSql.class, method = "getTemplate")
    public Template getTemplate(SearchArg searchArg);

    @SelectProvider(type = TemplateSql.class, method = "getTemplateList")
    public List<Template> getTemplateList(SearchArg searchArg);

    @UpdateProvider(type = TemplateSql.class, method = "setTemplate")
    public int setTemplate(Template template);

    @InsertProvider(type = TemplateSql.class, method = "addTemplate")
    public int addTemplate(Template template);

    @DeleteProvider(type = TemplateSql.class, method = "delTemplate")
    public int delTemplate(int tempId);
}
