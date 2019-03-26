package com.jachin.des.mapper;

import com.jachin.des.entity.CashFlow;
import com.jachin.des.entity.SearchArg;
import com.jachin.des.mapper.provider.CashFlowSql;
import org.apache.ibatis.annotations.*;

import java.util.List;

// 在每个 mapper 上添加这个注解，或者在启动类添加@MapperScan("com.xxx.mapper")
@Mapper
public interface CashFlowMapper {

    // =====基础查改增删=====
    @SelectProvider(type = CashFlowSql.class, method = "getCashFlow")
    public CashFlow getCashFlow(SearchArg searchArg);

    @SelectProvider(type = CashFlowSql.class, method = "getCashFlowList")
    public List<CashFlow> getCashFlowList(SearchArg searchArg);

    @UpdateProvider(type = CashFlowSql.class, method = "setCashFlow")
    public int setCashFlow(CashFlow template);

    @InsertProvider(type = CashFlowSql.class, method = "addCashFlow")
    public int addCashFlow(CashFlow template);

    @DeleteProvider(type = CashFlowSql.class, method = "delCashFlow")
    public int delCashFlow(SearchArg searchArg);
}
