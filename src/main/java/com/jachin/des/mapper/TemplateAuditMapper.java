package com.jachin.des.mapper;

import com.jachin.des.mapper.provider.TemplateSql;
import com.jachin.des.entity.TemplateAudit;
import org.apache.ibatis.annotations.*;

import java.util.List;

// 在每个 mapper 上添加这个注解，或者在启动类添加@MapperScan("com.xxx.mapper")
@Mapper
public interface TemplateAuditMapper {

    @SelectProvider(type=TemplateSql.class, method = "list")
    public List<TemplateAudit> findAll();

//    @SelectProvider(type=TemplateSql.class, method = "getShowList")
    @Select("SELECT template.title 'name' , templateAudit.* FROM `template` INNER JOIN `templateAudit` on template.tempId = templateAudit.tempId ORDER BY time desc;")
    public List<TemplateAudit> getShowListTemp();

    //    @SelectProvider(type=TemplateSql.class, method = "getShowList")
    @Select("SELECT * FROM `templateAudit` where tempId = #{tempId} ORDER BY time desc;")
    public List<TemplateAudit> getTempAuditById(@Param("tempId")int tempId);

    @UpdateProvider(type=TemplateSql.class, method = "update")
    public void updateName(@Param("templateAudit") TemplateAudit templateAudit);

    @DeleteProvider(type = TemplateSql.class, method = "delete")
    public void deleteById(TemplateAudit templateAudit);

    @InsertProvider(type = TemplateSql.class, method = "add")
    public void addTemplate(TemplateAudit templateAudit);
}
