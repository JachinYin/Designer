package com.jachin.des.mapper;

import com.jachin.des.entity.Designer;
import com.jachin.des.entity.DesignerAudit;
import com.jachin.des.entity.SearchArg;
import com.jachin.des.mapper.provider.DesignerAuditSql;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

// 在每个 mapper 上添加这个注解，或者在启动类添加@MapperScan("com.xxx.mapper")
@Mapper
public interface DesignerAuditMapper {

    // 查改增删

    @SelectProvider(type = DesignerAuditSql.class, method = "getDesignerAuditList")
    public Designer getDesigner(SearchArg searchArg);

    @SelectProvider(type = DesignerAuditSql.class, method = "getDesignerAuditList")
    public List<DesignerAudit> getDesignerAuditList(SearchArg searchArg);

    @Select("select * from designerAudit where aid = #{aid};")
    public List<DesignerAudit> getDesignerAuditList(int aid);


}
