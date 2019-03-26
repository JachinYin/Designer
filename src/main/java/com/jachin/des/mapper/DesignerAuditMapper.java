package com.jachin.des.mapper;

import com.jachin.des.entity.DesignerAudit;
import com.jachin.des.entity.SearchArg;
import com.jachin.des.mapper.provider.DesignerAuditSql;
import org.apache.ibatis.annotations.*;

import java.util.List;

// 在每个 mapper 上添加这个注解，或者在启动类添加@MapperScan("com.xxx.mapper")
@Mapper
public interface DesignerAuditMapper {

    // 查改增删

    @SelectProvider(type = DesignerAuditSql.class, method = "getDesignerAudit")
    public DesignerAudit getDesignerAudit(SearchArg searchArg);

    @SelectProvider(type = DesignerAuditSql.class, method = "getDesignerAuditList")
    public List<DesignerAudit> getDesignerAuditList(SearchArg searchArg);


    @UpdateProvider(type = DesignerAuditSql.class, method = "setDesignerAudit")
    public int setDesignerAudit(DesignerAudit designerAudit);

    @InsertProvider(type = DesignerAuditSql.class, method = "addDesignerAudit")
    public int addDesignerAudit(DesignerAudit designerAudit);

    @DeleteProvider(type = DesignerAuditSql.class, method = "delDesignerAudit")
    public int delDesignerAudit(SearchArg searchArg);
}
