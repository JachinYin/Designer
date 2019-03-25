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

    public String getTemplate(SearchArg searchArg) {
        int tempId = searchArg.getTempId();
        return String.format("SELECT * FROM `%s` WHERE tempId=%d;", TableDef.TEMPLATE, tempId);
    }

    public String getTemplateList(SearchArg searchArg) {
        int aid = searchArg.getAid();
        boolean comp = searchArg.isComp();
        String columns = searchArg.getColumns();

        String sql = String.format("SELECT * FROM `%s` WHERE aid=%d", TableDef.TEMPLATE, aid);

        if (comp) {
            sql = sql + "ORDER BY " + columns;
        }
        return sql;
    }

    public String setTemplate(Template template) {
        int tempId = template.getTempId();
        String title = template.getTitle();
        String keyWd = template.getKeyWd();
        String info = template.getInfo();
        String imgUrl = template.getImgUrl();
        String content = template.getContent();

        String sql = String.format("UPDATE `%s` SET time='%s' ", TableDef.TEMPLATE, CommTool.getNowTime());
        if (CommTool.isNotBlank(title)) {
            sql = String.format("%s , title='%s'", sql, title);
        }
        if (CommTool.isNotBlank(keyWd)) {
            sql = String.format("%s , keyWd='%s'", sql, keyWd);
        }
        if (CommTool.isNotBlank(info)) {
            sql = String.format("%s , info='%s'", sql, info);
        }
        if (CommTool.isNotBlank(imgUrl)) {
            sql = String.format("%s , imgUrl='%s'", sql, imgUrl);
        }
        if (CommTool.isNotBlank(content)) {
            sql = String.format("%s , content='%s'", sql, content);
        }
        sql = sql + " WHERE tempId=" + tempId + ";";
        return sql;
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
