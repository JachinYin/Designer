package com.jachin.des.mapper.provider;

import com.jachin.des.entity.SearchArg;
import com.jachin.des.entity.TemplateAudit;
import com.jachin.des.util.CommTool;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author Jachin
 * @since 2019/3/12 15:25
 */
public class TemplateAuditSql extends SQL {

    private static final Logger log = LoggerFactory.getLogger(TemplateAuditSql.class);

    private final String TABLE_TEMPLATE_AUDIT = "templateAudit";
    private final String TABLE_TEMPLATE = "template";

    // 设计师前台提交审核记录的SQL
    public String addTempAudit(TemplateAudit templateAudit){
        int aid = templateAudit.getAid();
        int tempId = templateAudit.getTempId();
        String title = templateAudit.getTitle();
        String designer = templateAudit.getDesigner();

        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ").append(TABLE_TEMPLATE_AUDIT);
        sb.append(" (aid, tempId, title, designer)"); // 列名
        sb.append(String.format(" VALUES (%d, %d, %s, %s);"
                , aid, tempId, title, designer));
        return sb.toString();
    }

    // ===========
    public String getTemplateAudit(SearchArg searchArg){
        int id = searchArg.getId();
        return String.format("SELECT * FROM `%s` WHERE id=%d ;", TABLE_TEMPLATE_AUDIT, id);
    }


    public String getTemplateAuditList(SearchArg searchArg){

        String sql = "";

        boolean distinct = searchArg.isDistinct();
        if(distinct){
            sql = String.format("SELECT * FROM `%s` AS x " +
                    "WHERE time IN " +
                    "(SELECT MAX(time) FROM `%s` AS y " +
                    "WHERE x.tempId = y.tempId)", TABLE_TEMPLATE_AUDIT, TABLE_TEMPLATE_AUDIT);
        }else{
            sql = String.format("SELECT * FROM `%s` WHERE id>0", TABLE_TEMPLATE_AUDIT);
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
        if(CommTool.isNotBlank(begTime)) sql = String.format("%s AND time>%s", sql, begTime);
        if(CommTool.isNotBlank(endTime)) sql = String.format("%s AND time<%s", sql, endTime);
        if(CommTool.isNotBlank(title)) sql += " AND title LIKE '%"+ title +"%'";
        if(CommTool.isNotBlank(designer)) sql += " AND designer LIKE '%"+ designer +"%'";
        if(CommTool.isNotBlank(columns)){
            if(comp) sql = String.format("%s ORDER BY %s DESC;", sql, columns);
            else sql = String.format("%s ORDER BY %s ASC;", sql, columns);
        }

        return sql;
    }
    public String setTemplateAudit(TemplateAudit templateAudit){
        String sql = "";

        return sql;
    }
    public String addTemplateAudit(TemplateAudit templateAudit){

        int aid = templateAudit.getAid();
        int tempId = templateAudit.getTempId();
        int price = templateAudit.getPrice();
        String designer = templateAudit.getDesigner();
        String reason = templateAudit.getReason();
        String title = templateAudit.getTitle();
        int status = templateAudit.getStatus();
//        templateAudit.getTime();

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        templateAudit.setTime(sf.format(Calendar.getInstance().getTime()));

        return new SQL(){{
            INSERT_INTO(TABLE_TEMPLATE_AUDIT);
            VALUES("aid", "#{aid}");
            VALUES("tempId", "#{tempId}");
            VALUES("price", "#{price}");
            VALUES("time", "#{time}");
            VALUES("status", "#{status}");
            if(CommTool.isNotBlank(designer)) VALUES("designer", "#{designer}");
            if(CommTool.isNotBlank(reason)) VALUES("reason", "#{reason}");
            if(CommTool.isNotBlank(title)) VALUES("title", "#{title}");
        }}.toString();


    }
    public String delTemplateAudit(SearchArg searchArg){
        String sql = "";

        return sql;
    }









    /*获取模板审核展示数据（分页）*/
    /*筛选模板审核展示数据（分页）*/
    /*导出筛选的模板数据（前端分页的话就不需要了）*/
    /*修改模板审核记录*/
    /*获取模板统计数据*/
    /*获取模板统计数据*/
    /*查看单个模板具体信息*/
    /*获取单个模板审核记录*/
}
