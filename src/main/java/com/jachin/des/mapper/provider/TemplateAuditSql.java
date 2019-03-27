package com.jachin.des.mapper.provider;

import com.jachin.des.entity.SearchArg;
import com.jachin.des.entity.TemplateAudit;
import com.jachin.des.util.CommTool;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author Jachin
 * @since 2019/3/12 15:25
 */
public class TemplateAuditSql extends SQL {

    // ======基础查改增删=====
    public String getTemplateAudit(SearchArg searchArg){
        int id = searchArg.getId();
        return String.format("SELECT * FROM `%s` WHERE id=%d ;", TableDef.TEMPLATE_AUDIT, id);
    }

    // 通过 distinct 来指定是否需要去重（该去重是结合业务逻辑的）
    public String getTemplateAuditList(SearchArg searchArg){
        String sql;
        boolean distinct = searchArg.isDistinct();
        if(distinct){
            sql = String.format("SELECT * FROM `%s` AS x " +
                    "WHERE time IN " +
                    "(SELECT MAX(time) FROM `%s` AS y " +
                    "WHERE x.tempId = y.tempId)", TableDef.TEMPLATE_AUDIT, TableDef.TEMPLATE_AUDIT);
        }else{
            sql = String.format("SELECT * FROM `%s` WHERE id>0", TableDef.TEMPLATE_AUDIT);
        }

        int aid = searchArg.getAid();
        int tempId = searchArg.getTempId();
        String title = searchArg.getTitle();
        String designer = searchArg.getDesigner();
        String begTime = searchArg.getBegTime();
        String endTime = searchArg.getEndTime();
        int status = searchArg.getStatus();
        String columns = searchArg.getColumns();
        boolean comp = searchArg.isComp();

        if(aid>0) sql = String.format("%s AND aid=%d", sql, aid);
        if(tempId>0) sql = String.format("%s AND tempId=%d", sql, tempId);
        if(status>0) sql = String.format("%s AND status=%d", sql, status);
        if(CommTool.isNotBlank(begTime)) sql = String.format("%s AND time>'%s'", sql, begTime);
        if(CommTool.isNotBlank(endTime)) sql = String.format("%s AND time<'%s'", sql, endTime);
        if(CommTool.isNotBlank(title)) sql += " AND title LIKE '%"+ title +"%'";
        if(CommTool.isNotBlank(designer)) sql += " AND designer LIKE '%"+ designer +"%'";
        if(CommTool.isNotBlank(columns)){
            if(comp) sql = String.format("%s ORDER BY %s DESC;", sql, columns);
            else sql = String.format("%s ORDER BY %s ASC;", sql, columns);
        }

        return sql;
    }

    public String setTemplateAudit(TemplateAudit templateAudit){
        int aid = templateAudit.getAid();
        int tempId = templateAudit.getTempId();
        int status = templateAudit.getStatus();
        int price = templateAudit.getPrice();
        String title = templateAudit.getTitle();
        String designer = templateAudit.getDesigner();
        String reason = templateAudit.getReason();
        String time = templateAudit.getTime();

        return new SQL() {{
            UPDATE(TableDef.DESIGNER_AUDIT);
            SET("id=#{id}");          // 保证 update 语句至少拥有一个set项
            if(aid > 0) SET("aid=#{aid}");
            if(tempId > 0) SET("tempId=#{tempId}");
            if(status > 0) SET("status=#{status}");
            if(price > 0) SET("price=#{price}");
            if (CommTool.isNotBlank(title)) SET("title=#{title}");
            if (CommTool.isNotBlank(designer)) SET("designer=#{designer}");
            if (CommTool.isNotBlank(reason)) SET("reason=#{reason}");
            if (CommTool.isNotBlank(time)) SET("time=#{time}");
            WHERE("id=#{id}");
        }}.toString();
    }

    public String addTemplateAudit(TemplateAudit templateAudit){
        int aid = templateAudit.getAid();
        int tempId = templateAudit.getTempId();
        int price = templateAudit.getPrice();
        int status = templateAudit.getStatus();
        templateAudit.setTime(CommTool.getNowTime());
        String designer = templateAudit.getDesigner();
        String reason = templateAudit.getReason();
        String title = templateAudit.getTitle();

        return new SQL(){{
            INSERT_INTO(TableDef.TEMPLATE_AUDIT);
            VALUES("time", "#{time}");

            if(aid > 0) VALUES("aid", "#{aid}");
            if(tempId > 0) VALUES("tempId", "#{tempId}");
            if(price > 0) VALUES("price", "#{price}");
            if(status > 0) VALUES("status", "#{status}");
            if(CommTool.isNotBlank(designer)) VALUES("designer", "#{designer}");
            if(CommTool.isNotBlank(reason)) VALUES("reason", "#{reason}");
            if(CommTool.isNotBlank(title)) VALUES("title", "#{title}");
        }}.toString();
    }

    public String delTemplateAudit(SearchArg searchArg){
        return String.format("DELETE from `%s` where id=%d", TableDef.TEMPLATE_AUDIT, searchArg.getId());
    }
}
