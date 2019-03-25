package com.jachin.des.mapper;

import com.jachin.des.entity.Designer;
import com.jachin.des.entity.DesignerAudit;
import com.jachin.des.entity.SearchArg;
import com.jachin.des.mapper.provider.DesignerSql;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;

// 在每个 mapper 上添加这个注解，或者在启动类添加@MapperScan("com.xxx.mapper")
@Mapper
public interface DesignerMapper {

    // 查改增删
    @SelectProvider(type = DesignerSql.class, method = "getDesigner")
    public Designer getDesigner(SearchArg searchArg);

    @SelectProvider(type = DesignerSql.class, method = "getDesignerList")
    public List<DesignerAudit> getDesignerList(SearchArg searchArg);

    @UpdateProvider(type = DesignerSql.class, method = "setDesigner")
    public int addDesigner(Designer designer);

    @UpdateProvider(type = DesignerSql.class, method = "addDesigner")
    public int setDesigner(Designer designer, SearchArg searchArg);

    @UpdateProvider(type = DesignerSql.class, method = "delDesigner")
    public int delDesigner(SearchArg searchArg);

}
