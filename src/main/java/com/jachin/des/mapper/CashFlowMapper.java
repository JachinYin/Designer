package com.jachin.des.mapper;

import com.jachin.des.entity.CashFlow;
import com.jachin.des.entity.CashFlowWithTitle;
import com.jachin.des.entity.SearchArg;
import com.jachin.des.mapper.provider.CashFlowSql;
import org.apache.ibatis.annotations.*;

import java.util.List;

// 在每个 mapper 上添加这个注解，或者在启动类添加@MapperScan("com.xxx.mapper")
@Mapper
public interface CashFlowMapper {

    // 后台获取模板收入记录
    @SelectProvider(type = CashFlowSql.class, method = "getCashFlowWithTempTitle")
    public List<CashFlowWithTitle> getCashFlowWithTempTitle(SearchArg searchArg);

    // 统计总金额
    @SelectProvider(type = CashFlowSql.class, method = "getSumWithdraw")
//    @Select("SELECT SUM(price) FROM `cashFlow` where aid=${aid} and type={$type};")
    public Integer getSumWithdraw(SearchArg searchArg);

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
