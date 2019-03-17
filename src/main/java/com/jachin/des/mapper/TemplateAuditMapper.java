package com.jachin.des.mapper;

import com.jachin.des.entity.TemplateAudit;
import com.jachin.des.mapper.provider.TemplateAuditSql;
import org.apache.ibatis.annotations.*;

import java.util.List;

// 在每个 mapper 上添加这个注解，或者在启动类添加@MapperScan("com.xxx.mapper")
@Mapper
public interface TemplateAuditMapper {

    @SelectProvider(type = TemplateAuditSql.class, method = "getShowList")
    public List<TemplateAudit> getShowTempListForProvider(TemplateAudit templateAudit);

    // 获取指定模板的所有审核记录，按时间排序
    @Select("SELECT * FROM `templateAudit` where tempId = #{tempId} ORDER BY time desc;")
    public List<TemplateAudit> getTempAuditById(@Param("tempId")int tempId);

    @InsertProvider(type = TemplateAuditSql.class, method = "addTempAudit")
    public void addTemplate(TemplateAudit templateAudit);


//    public List<TemplateAudit> getAllListById();
}
