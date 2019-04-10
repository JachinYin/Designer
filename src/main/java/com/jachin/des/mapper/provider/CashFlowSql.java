package com.jachin.des.mapper.provider;

import com.jachin.des.def.DataDef;
import com.jachin.des.entity.CashFlow;
import com.jachin.des.entity.SearchArg;
import com.jachin.des.util.CommTool;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author Jachin
 * @since 2019/3/25 21:09
 */
public class CashFlowSql {

    // 获取设计师收入(财务打款)
    public String getTotalWithdraw(SearchArg searchArg) {
        int year = searchArg.getYear();
        int month = searchArg.getMonth();
        String sql = "SELECT s.aid, SUM(s.price) as price,d.bankAcct, d.cardHolder, d.openBank, d.phone, d.realName";
        sql += " from cashFlow s join designer d on d.aid=s.aid";
        sql += " WHERE type=" + DataDef.CashFlag.WITHDRAW;
        if (year > 0) sql += " AND YEAR(time)=" + year;
        if (month > 0) sql += " AND MONTH(time)=" + month;
        sql += " GROUP BY aid";
        sql += " ORDER BY price DESC";
        return sql;
    }


    // 获取指定aid的模板收入记录(后台分佣管理使用)
    public String getCashFlowWithTempTitle(SearchArg searchArg){
        int aid = searchArg.getAid();
        String sql = String.format("SELECT c.tempId,c.time,c.price,t.title FROM %s AS c JOIN %s AS t", TableDef.CASH_FLOW, TableDef.TEMPLATE);
        sql = String.format("%s ON c.tempId = t.tempId", sql);
        sql = String.format("%s WHERE type!=%d", sql, DataDef.CashFlag.WITHDRAW);
        sql = String.format("%s AND c.aid=%d", sql, aid);
        sql = String.format("%s ORDER BY c.time DESC;", sql);

        return sql;
    }

    // 获取提现总额
    public String getSumWithdraw(SearchArg searchArg){
        int aid = searchArg.getAid();
        int type = searchArg.getType();

        String sql = String.format("SELECT SUM(price) FROM %s WHERE price!=0", TableDef.CASH_FLOW);
        if(aid > 0) sql = String.format("%s AND aid=%d", sql, aid);
        if(type > 0) sql = String.format("%s AND type=%d", sql, type);
        return sql;
    }

    // =====基础查改增删=====
    public String getCashFlow(SearchArg searchArg) {
        return String.format("SELECT * FROM `%s` WHERE id=%d", TableDef.CASH_FLOW, searchArg.getId());
    }

    public String getCashFlowList(SearchArg searchArg) {

        int aid = searchArg.getAid();
        int tempId = searchArg.getTempId();
        int type = searchArg.getType();
        String extra = searchArg.getExtra();
        String begTime = searchArg.getBegTime();
        String endTime = searchArg.getEndTime();

        String columns = searchArg.getColumns();    // 排序的列
        boolean comp = searchArg.isComp();          // 是否降序排序

        String sql = String.format("SELECT * FROM `%s` WHERE id>0 ", TableDef.CASH_FLOW);

        if(aid>0) sql = String.format("%s AND aid=%d", sql, aid);
        if(tempId>0) sql = String.format("%s AND tempId=%d", sql, tempId);
        if(type>0) sql = String.format("%s AND type=%d", sql, type);
        if(CommTool.isNotBlank(begTime)) sql = String.format("%s AND time>'%s'", sql, begTime);
        if(CommTool.isNotBlank(endTime)) sql = String.format("%s AND time<'%s'", sql, endTime);
        if(CommTool.isNotBlank(extra)) sql = String.format("%s %s", sql, extra);
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

