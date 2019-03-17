package com.jachin.des.mapper.provider;

import com.jachin.des.entity.TemplateAudit;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jachin
 * @since 2019/3/12 15:25
 */
public class TemplateAuditSql extends SQL {

    private static final Logger log = LoggerFactory.getLogger(TemplateAuditSql.class);

    private final String TABLE_AUDIT = "templateAudit";
    private final String TABLE_TEMP = "template";
    class Info{
        public static final String id = "id";
        public static final String aid = "aid";
        public static final String tempId = "tempId";
        public static final String title = "title";
        public static final String designer = "designer";
        public static final String time = "time";
        public static final String status = "status";
        public static final String price = "price";
        public static final String type = "type";
        public static final String reason = "reason";
    }
    class Info_Key{
        public static final String id = "#{id}";
        public static final String aid = "#{aid}";
        public static final String tempId = "#{tempId}";
        public static final String title = "#{title}";
        public static final String designer = "#{designer}";
        public static final String time = "#{time}";
        public static final String status = "#{status}";
        public static final String price = "#{price}";
        public static final String type = "#{type}";
        public static final String reason = "#{reason}";
    }
    class Info_EQ{
        public static final String id = " id = #{ id }";
        public static final String aid = " aid = #{ aid }";
        public static final String tempId = " tempId = #{ tempId }";
        public static final String title = " title = #{ title }";
        public static final String designer = " designer = #{ designer }";
        public static final String time = " time = #{ time }";
        public static final String status = " status = #{ status }";
        public static final String price = " price = #{ price }";
        public static final String type = "type = #{type}";
        public static final String reason = "reason = #{reason}";
    }
    
    public String addTempAudit(){
        return new SQL()
                .INSERT_INTO(TABLE_AUDIT)
                .VALUES(Info.aid, Info_Key.aid)
                .VALUES(Info.tempId, Info_Key.tempId)
                .VALUES(Info.title, Info_Key.title)
                .VALUES(Info.designer, Info_Key.designer)
                .VALUES(Info.time, Info_Key.time)
                .VALUES(Info.status, Info_Key.status)
                .VALUES(Info.price, Info_Key.price)
                .VALUES(Info.type, Info_Key.type)
                .VALUES(Info.reason, Info_Key.reason)
                .toString();
    }

    public String getShowList(TemplateAudit templateAudit){

        StringBuilder sb = new StringBuilder();
               // "ORDER BY time desc;";
        sb.append("select * from `templateAudit` as x where time in ");
        sb.append("(select max(time) from `templateAudit` as y ");
        sb.append("where x.tempId = y.tempId) ");

        // 拼接多条件
//        int aid = templateAudit.getAid();
        int tempId = templateAudit.getTempId();
        String designer = templateAudit.getDesigner();
        String title = templateAudit.getTitle();
        String time = templateAudit.getTime();
        int status = templateAudit.getStatus();

        if(tempId > 0) sb.append(" and tempId = " + tempId);
        if(designer != null && !designer.isEmpty()) sb.append(" and designer like '%" + designer + "%'");
        if(title  != null && !title.isEmpty()) sb.append(" and title like '%" + title + "%'");
        if(time != null && !time.isEmpty()) sb.append(" and time > '" + time + "' ");
        if(status > 0) sb.append(" and status = " + status);


        sb.append(" ORDER BY time desc;");
        return sb.toString();
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
