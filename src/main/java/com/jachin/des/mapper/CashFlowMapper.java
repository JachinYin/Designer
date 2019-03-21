package com.jachin.des.mapper;

import com.jachin.des.entity.CashFlow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

// 在每个 mapper 上添加这个注解，或者在启动类添加@MapperScan("com.xxx.mapper")
@Mapper
public interface CashFlowMapper {

    @Select("SELECT * FROM cashFlow ORDER BY time DESC;")
    public List<CashFlow> getAllList();
}
