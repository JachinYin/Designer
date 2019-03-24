package com.jachin.des.mapper.provider;

import com.jachin.des.entity.SearchArg;
import com.jachin.des.entity.Template;
import com.jachin.des.util.CommTool;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Jachin
 * @since 2019/3/12 15:25
 */
public class TemplateSql{

    private static final Logger log = LoggerFactory.getLogger(TemplateSql.class);

    private final String TABLE_TEMPLATE_AUDIT = "templateAudit";
    private final String TABLE_TEMPLATE = "template";

    public String getTemplate(SearchArg searchArg){
        int tempId = searchArg.getTempId();
        return String.format("SELECT * FROM `%s` WHERE tempId=%d;", TABLE_TEMPLATE, tempId );
}

    public String getTemplateList(SearchArg searchArg){
        int aid = searchArg.getAid();
        boolean comp = searchArg.isComp();
        String columns = searchArg.getColumns();

        String sql = String.format("SELECT * FROM `%s` WHERE aid=%d", TABLE_TEMPLATE, aid);

        if (comp){
            sql = sql + "ORDER BY " + columns;
        }
        return sql;
    }

    public String setTemplate(Template template){
        int tempId = template.getTempId();
        String title = template.getTitle();
        String keyWd = template.getKeyWd();
        String info = template.getInfo();
        String imgUrl = template.getImgUrl();
        String content = template.getContent();

        Date time = Calendar.getInstance().getTime();
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = sf.format(time);

        String sql = String.format("UPDATE `%s` SET time='%s' ",TABLE_TEMPLATE, now);
        if(CommTool.isNotBlank(title)) {
            sql = sql + ", title='" + title + "'";
        }
        if(CommTool.isNotBlank(keyWd)) {
            sql = sql + ", keyWd='" + keyWd + "'";
        }
        if(CommTool.isNotBlank(info)) {
            sql = sql + ", info='" + info + "'";
        }
        if(CommTool.isNotBlank(imgUrl)) {
            sql = sql + ", imgUrl='" + imgUrl + "'";
        }
        if(CommTool.isNotBlank(content)) {
            sql = sql + ", content='" + content + "'";
        }
        sql = sql + " WHERE tempId=" + tempId + ";";
        return sql;
    }

    public String addTemplate(Template template){
        int aid = template.getAid();
        String title = template.getTitle();
        String keyWd = template.getKeyWd();
        String info = template.getInfo();
        String imgUrl = template.getImgUrl();
        String content = template.getContent();

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        template.setTime(sf.format(Calendar.getInstance().getTime()));

        return new SQL(){{
                 INSERT_INTO(TABLE_TEMPLATE);
                 VALUES("aid", "#{aid}");
                 VALUES("time", "#{time}");
                 if(CommTool.isNotBlank(title)) VALUES("title", "#{title}");
                 if(CommTool.isNotBlank(keyWd)) VALUES("keyWd", "#{keyWd}");
                 if(CommTool.isNotBlank(info)) VALUES("info", "#{info}");
                 if(CommTool.isNotBlank(content)) VALUES("content", "#{content}");
                 if(CommTool.isNotBlank(imgUrl)) VALUES("imgUrl", "#{imgUrl}");
              }}.toString();
    }

    public String delTemplate(int tempId){
        return String.format("DELETE FROM `%s` WHERE tempId=%d;", TABLE_TEMPLATE, tempId);
    }

}
