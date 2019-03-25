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

    public String getDesignerAudit(SearchArg searchArg) {
        return "select * from designer where aid=${aid};";
    }

    public String getDesignerAuditList(SearchArg searchArg) {
        return "select * from designerAudit where aid = " + searchArg.getAid();
    }

    public String setDesignerAudit(DesignerAudit designerAudit) {
        int aid = designerAudit.getAid();
        String nickName = designerAudit.getNickName();
        int status = designerAudit.getStatus();
        String reason = designerAudit.getReason();

        String sql = String.format("UPDATE `%s` SET time='%s' ", TableDef.DESIGNER_AUDIT, CommTool.getNowTime());
        sql = String.format("$s , status=%d", sql, status);
        if (CommTool.isNotBlank(nickName)) {
            sql = String.format("%s , nickName='%s'", sql, nickName);
        }
        if (CommTool.isNotBlank(reason)) {
            sql = String.format("%s , reason='%s'", sql, reason);
        }
        sql = sql + " WHERE aid=" + aid + ";";

        return sql;
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
        return "select * from designer;";
    }
}

