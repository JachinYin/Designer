package com.jachin.des.mapper;

import com.jachin.des.entity.Designer;
import com.jachin.des.entity.DesignerAudit;
import com.jachin.des.mapper.provider.DesignerSql;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

// 在每个 mapper 上添加这个注解，或者在启动类添加@MapperScan("com.xxx.mapper")
@Mapper
public interface DesignerMapper {

    @Select("select * from designer where aid=${aid};")
    public Designer getDesignerById(@Param("aid") int aid);

    @SelectProvider(type = DesignerSql.class, method = "getDesignerList")
    public List<DesignerAudit> getDesignerList(DesignerAudit designerAudit);

    @Select("select * from designerAudit where aid = #{aid};")
    public List<DesignerAudit> getDesignerAuditList(int aid);
}
