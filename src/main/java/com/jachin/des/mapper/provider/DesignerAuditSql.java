package com.jachin.des.mapper.provider;

import com.jachin.des.entity.DesignerAudit;
import com.jachin.des.entity.SearchArg;
import com.jachin.des.util.CommTool;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author Jachin
 * @since 2019/3/25 21:09
 */
public class DesignerAuditSql {


    // =====基础查改增删=====
    public String getDesignerAudit(SearchArg searchArg) {
        return String.format("SELECT * FROM %s WHERE id=%d", TableDef.DESIGNER_AUDIT, searchArg.getId());
    }

    // 设定 distinct 来启用不同的查询模式
    public String getDesignerAuditList(SearchArg searchArg) {
        String sql = "";
        boolean distinct = searchArg.isDistinct();
        if(distinct) {
            sql = String.format("SELECT * FROM `%s` AS x " +
                    "WHERE time IN " +
                    "(SELECT MAX(time) FROM `%s` AS y " +
                    "WHERE x.aid = y.aid)", TableDef.DESIGNER_AUDIT, TableDef.DESIGNER_AUDIT);
        }else {
            sql = String.format("SELECT * FROM `%s` WHERE aid>0", TableDef.DESIGNER_AUDIT);
        }
        // 拼接多条件
        int aid = searchArg.getAid();
        int status = searchArg.getStatus();
        String nickName = searchArg.getNickName();
        String begTime = searchArg.getBegTime();
        String endTime = searchArg.getEndTime();
        String columns = searchArg.getColumns();    // 排序的列
        boolean comp = searchArg.isComp();          // 是否降序排序

        if(aid>0) sql = String.format("%s AND aid=%d", sql, aid);
        if(status>0) sql = String.format("%s AND status=%d", sql, status);
        if(CommTool.isNotBlank(begTime)) sql = String.format("%s AND time>%s", sql, begTime);
        if(CommTool.isNotBlank(endTime)) sql = String.format("%s AND time<%s", sql, endTime);
        if(CommTool.isNotBlank(nickName)) sql += " AND nickName LIKE '%"+ nickName +"%'";
        if(CommTool.isNotBlank(columns)){
            if(comp) sql = String.format("%s ORDER BY %s DESC;", sql, columns);
            else sql = String.format("%s ORDER BY %s ASC;", sql, columns);
        }
        return sql;
    }

    public String setDesignerAudit(DesignerAudit designerAudit) {
        int aid = designerAudit.getAid();
        int status = designerAudit.getStatus();
        String nickName = designerAudit.getNickName();
        String reason = designerAudit.getReason();
        String time = designerAudit.getTime();

        return new SQL() {{
            UPDATE(TableDef.DESIGNER_AUDIT);
            SET("aid=#{aid}");          // 保证 update 语句至少拥有一个set项
            if(status > 0) SET("status=#{status}");
            if (CommTool.isNotBlank(nickName)) SET("nickName=#{nickName}");
            if (CommTool.isNotBlank(reason)) SET("reason=#{reason}");
            if (CommTool.isNotBlank(time)) SET("time=#{time}");
            WHERE("id=#{id}");
        }}.toString();
    }

    public String addDesignerAudit(DesignerAudit designerAudit) {
        int aid = designerAudit.getAid();
        int status = designerAudit.getStatus();
        designerAudit.setTime(CommTool.getNowTime());
        String nickName = designerAudit.getNickName();
        String reason = designerAudit.getReason();

        return new SQL() {{
            INSERT_INTO(TableDef.DESIGNER_AUDIT);
            VALUES("aid", "#{aid}");
            VALUES("time", "#{time}");
            VALUES("status", "#{status}");
            if (CommTool.isNotBlank(nickName)) VALUES("nickName", "#{nickName}");
            if (CommTool.isNotBlank(reason)) VALUES("reason", "#{reason}");
        }}.toString();
    }

    public String delDesignerAudit(SearchArg searchArg) {
        return String.format("DELETE from `%s` where id=%d", TableDef.DESIGNER_AUDIT, searchArg.getId());
    }
}

