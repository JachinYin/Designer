package com.jachin.des.mapper;

import com.jachin.des.entity.SearchArg;
import com.jachin.des.entity.TemplateAudit;
import com.jachin.des.mapper.provider.TemplateAuditSql;
import org.apache.ibatis.annotations.*;

import java.util.List;

// 在每个 mapper 上添加这个注解，或者在启动类添加@MapperScan("com.xxx.mapper")
@Mapper
public interface TemplateAuditMapper {

    // 获取指定模板的所有审核记录，按时间排序
    @Select("SELECT * FROM `templateAudit` where tempId = #{tempId} ORDER BY time desc;")
    public List<TemplateAudit> getTempAuditById(@Param("tempId")int tempId);

    @InsertProvider(type = TemplateAuditSql.class, method = "addTempAudit")
    public int addTemplate(TemplateAudit templateAudit);


    // =====基础查改增删=====
    @SelectProvider(type = TemplateAuditSql.class, method = "getTemplateAudit")
    public TemplateAudit getTemplateAudit(SearchArg searchArg);

    @SelectProvider(type = TemplateAuditSql.class, method = "getTemplateAuditList")
    public List<TemplateAudit> getTemplateAuditList(SearchArg searchArg);

    @UpdateProvider(type = TemplateAuditSql.class, method = "setTemplateAudit")
    public int setTemplateAudit(TemplateAudit templateAudit);

    @InsertProvider(type = TemplateAuditSql.class, method = "addTemplateAudit")
    public int addTemplateAudit(TemplateAudit templateAudit);

    @DeleteProvider(type = TemplateAuditSql.class, method = "delTemplateAudit")
    public int delTemplateAudit(SearchArg searchArg);

}
