package com.jachin.des.mapper.provider;

import com.jachin.des.entity.SearchArg;
import com.jachin.des.entity.Template;
import com.jachin.des.util.CommTool;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author Jachin
 * @since 2019/3/12 15:25
 */
public class TemplateSql {


    //=======基础查改增删=======
    public String getTemplate(SearchArg searchArg) {
        return String.format("SELECT * FROM `%s` WHERE tempId=%d;", TableDef.TEMPLATE, searchArg.getTempId());
    }

    public String getTemplateList(SearchArg searchArg) {
        int tempId = searchArg.getTempId();
        int aid = searchArg.getAid();
        int status = searchArg.getStatus();
        String title = searchArg.getTitle();
        String begTime = searchArg.getBegTime();
        String endTime = searchArg.getEndTime();

        boolean comp = searchArg.isComp();
        String columns = searchArg.getColumns();

        String sql = String.format("SELECT * FROM `%s` WHERE tempId>0", TableDef.TEMPLATE);

        if(aid>0) sql = String.format("%s AND aid=%d", sql, aid);
        if(tempId>0) sql = String.format("%s AND tempId=%d", sql, tempId);
        if(status>0) sql = String.format("%s AND status=%d", sql, status);
        if(CommTool.isNotBlank(begTime)) sql = String.format("%s AND time>'%s'", sql, begTime);
        if(CommTool.isNotBlank(endTime)) sql = String.format("%s AND time<'%s'", sql, endTime);
        if(CommTool.isNotBlank(title)) sql += " AND title LIKE '%"+ title +"%'";
        if(CommTool.isNotBlank(columns)){
            if(comp) sql = String.format("%s ORDER BY %s DESC;", sql, columns);
            else sql = String.format("%s ORDER BY %s ASC;", sql, columns);
        }
        return sql;
    }

    public String setTemplate(Template template) {
        int tempId = template.getTempId(); // key
        int status = template.getStatus();
        int aid = template.getAid();
        String title = template.getTitle();
        String keyWd = template.getKeyWd();
        String info = template.getInfo();
        String imgUrl = template.getImgUrl();
        String content = template.getContent();

        return new SQL() {{
            UPDATE(TableDef.TEMPLATE);

            SET("time=#{time}");  // 保证 update 语句至少拥有一个set项

            if(aid > 0) SET("aid=#{aid}");
            if(status > 0) SET("status=#{status}");

            if (CommTool.isNotBlank(title)) SET("title=#{title}");
            if (CommTool.isNotBlank(keyWd)) SET("keyWd=#{keyWd}");
            if (CommTool.isNotBlank(info)) SET("info=#{info}");
            if (CommTool.isNotBlank(imgUrl)) SET("imgUrl=#{imgUrl}");
            if (CommTool.isNotBlank(content)) SET("content=#{content}");

            WHERE("tempId=#{tempId}");
        }}.toString();
    }

    public String addTemplate(Template template) {
        int aid = template.getAid();
        String title = template.getTitle();
        String keyWd = template.getKeyWd();
        String info = template.getInfo();
        String imgUrl = template.getImgUrl();
        String content = template.getContent();

        template.setTime(CommTool.getNowTime());

        return new SQL() {{
            INSERT_INTO(TableDef.TEMPLATE);
            VALUES("aid", "#{aid}");
            VALUES("time", "#{time}");
            if (CommTool.isNotBlank(title)) VALUES("title", "#{title}");
            if (CommTool.isNotBlank(keyWd)) VALUES("keyWd", "#{keyWd}");
            if (CommTool.isNotBlank(info)) VALUES("info", "#{info}");
            if (CommTool.isNotBlank(content)) VALUES("content", "#{content}");
            if (CommTool.isNotBlank(imgUrl)) VALUES("imgUrl", "#{imgUrl}");
        }}.toString();
    }

    public String delTemplate(int tempId) {
        return String.format("DELETE FROM `%s` WHERE tempId=%d;", TableDef.TEMPLATE, tempId);
    }

}
