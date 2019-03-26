package com.jachin.des.mapper.provider;

import com.jachin.des.entity.CashFlow;
import com.jachin.des.entity.SearchArg;
import com.jachin.des.util.CommTool;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author Jachin
 * @since 2019/3/25 21:09
 */
public class CashFlowSql {

    // =====基础查改增删=====
    public String getCashFlow(SearchArg searchArg) {
        return String.format("SELECT * FROM `%s` WHERE id=%d", TableDef.CASH_FLOW, searchArg.getId());
    }

    public String getCashFlowList(SearchArg searchArg) {

        int aid = searchArg.getAid();
        int tempId = searchArg.getTempId();
        int type = searchArg.getType();
        String begTime = searchArg.getBegTime();
        String endTime = searchArg.getEndTime();

        String columns = searchArg.getColumns();    // 排序的列
        boolean comp = searchArg.isComp();          // 是否降序排序

        String sql = String.format("SELECT * FROM `%s` WHERE id>0 ", TableDef.CASH_FLOW);

        if(aid>0) sql = String.format("%s AND aid=%d", sql, aid);
        if(tempId>0) sql = String.format("%s AND tempId=%d", sql, tempId);
        if(type>0) sql = String.format("%s AND type=%d", sql, type);

        if(CommTool.isNotBlank(begTime)) sql = String.format("%s AND time>%s", sql, begTime);
        if(CommTool.isNotBlank(endTime)) sql = String.format("%s AND time<%s", sql, endTime);
        if(CommTool.isNotBlank(columns)){
            if(comp) sql = String.format("%s ORDER BY %s DESC;", sql, columns);
            else sql = String.format("%s ORDER BY %s ASC;", sql, columns);
        }
        return sql;
    }

    public String setCashFlow(CashFlow cashFlow) {
        int aid = cashFlow.getAid();
        int tempId = cashFlow.getTempId();
        String time = cashFlow.getTime();
        int price = cashFlow.getPrice();
        int balance = cashFlow.getBalance();
        int type = cashFlow.getType();
        return new SQL() {{
            UPDATE(TableDef.CASH_FLOW);
            SET("id=#{id}");          // 保证 update 语句至少拥有一个set项
            if(aid > 0) SET("aid=#{aid}");
            if(tempId > 0) SET("tempId=#{tempId}");
            if(price != 0) SET("price=#{price}");
            if(balance > 0) SET("balance=#{balance}");
            if(type > 0) SET("type=#{type}");
            if (CommTool.isNotBlank(time)) SET("time=#{time}");
            WHERE("id=#{id}");
        }}.toString();
    }

    public String addCashFlow(CashFlow cashFlow) {

        int aid = cashFlow.getAid();
        int type = cashFlow.getType();
        int balance = cashFlow.getBalance();
        int price = cashFlow.getPrice();
        int tempId = cashFlow.getTempId();
        cashFlow.setTime(CommTool.getNowTime());

        return new SQL() {{
            INSERT_INTO(TableDef.CASH_FLOW);
            VALUES("aid", "#{aid}");
            VALUES("time", "#{time}");
            VALUES("type", "#{type}");
            if(balance > 0) VALUES("balance", "#{balance}");
            if(price != 0) VALUES("price", "#{price}");
            if(tempId > 0) VALUES("tempId", "#{tempId}");
        }}.toString();
    }

    public String delCashFlow(SearchArg searchArg) {
        return String.format("DELETE FROM `%s` WHERE id=%d", TableDef.CASH_FLOW, searchArg.getId());
    }
}

