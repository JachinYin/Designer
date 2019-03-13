package com.jachin.des.mapper.provider;

import com.jachin.des.entity.TemplateAudit;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Jachin
 * @since 2019/3/12 15:25
 */
public class TemplateSql extends SQL {

    private static final Logger log = LoggerFactory.getLogger(TemplateSql.class);

    private final String TABLE_AUDIT = "templateAudit";
    private final String TABLE_TEMP = "template";
    class Info{
        public static final String id = "id";
        public static final String aid = "aid";
        public static final String tempId = "tempId";
        public static final String name = "name";
        public static final String designer = "designer";
        public static final String time = "time";
        public static final String status = "status";
        public static final String price = "price";
    }
    class Info_Key{
        public static final String id = "#{id}";
        public static final String aid = "#{aid}";
        public static final String tempId = "#{tempId}";
        public static final String name = "#{name}";
        public static final String designer = "#{designer}";
        public static final String time = "#{time}";
        public static final String status = "#{status}";
        public static final String price = "#{price}";
    }
    class Info_EQ{
        public static final String id = " id = #{ id }";
        public static final String aid = " aid = #{ aid }";
        public static final String tempId = " tempId = #{ tempId }";
        public static final String name = " name = #{ name }";
        public static final String designer = " designer = #{ designer }";
        public static final String time = " time = #{ time }";
        public static final String status = " status = #{ status }";
        public static final String price = " price = #{ price }";
    }
    class Info_EQ_Param{
        public static final String id = " id = #{ templateAudit.id }";
        public static final String aid = " aid = #{ templateAudit.aid }";
        public static final String tempId = " tempId = #{ templateAudit.tempId }";
        public static final String name = " name = #{ templateAudit.name }";
        public static final String designer = " designer = #{ templateAudit.designer }";
        public static final String time = " time = #{ templateAudit.time }";
        public static final String status = " status = #{ templateAudit.status }";
        public static final String price = " price = #{ templateAudit.price }";
    }

    public String list() {
        return new SQL(){{
            SELECT("*");
            FROM(TABLE_AUDIT);
        }}.toString();
    }

    public String get() {
        return new SQL()
                .SELECT("*")
                .FROM(TABLE_AUDIT)
                .WHERE(Info_EQ.id)
                .toString();
    }

    public String add(){
        return new SQL()
                .INSERT_INTO(TABLE_AUDIT)
                .VALUES(Info.aid, Info_Key.aid)
                .VALUES(Info.tempId, Info_Key.tempId)
                .VALUES(Info.name, Info_Key.name)
                .VALUES(Info.designer, Info_Key.designer)
                .VALUES(Info.time, Info_Key.time)
                .VALUES(Info.status, Info_Key.status)
                .VALUES(Info.price, Info_Key.price)
                .toString();
    }

    /**
     模板审核记录更新
     对于没有的字段，则不个更新
     必须要传入 aid 和 templateId,否则报错
    */
    public String update(@Param("templateAudit") TemplateAudit templateAudit) throws Exception {
        if(templateAudit.getAid() == 0){
            log.error("{rt=-2}; arg error; aid=0");
            throw new Exception();
        }
        if(templateAudit.getTempId() == 0){
            log.error("{rt=-2}; arg error; tempId=0");
            throw new Exception();
        }
        return new SQL(){
            {
                UPDATE(TABLE_AUDIT);
                if(templateAudit.getDesigner() != null && !templateAudit.getDesigner().isEmpty())
                    SET(Info_EQ_Param.designer);
                if(templateAudit.getTime() != null && !templateAudit.getTime().isEmpty())
                    SET(Info_EQ_Param.time);
                if(templateAudit.getStatus() != 0)
                    SET(Info_EQ_Param.status);
                if(templateAudit.getPrice() != 0)
                    SET(Info_EQ_Param.price);
                WHERE(Info_EQ_Param.aid);
                WHERE(Info_EQ_Param.tempId);
            }
        }.toString();
    }

    public String delete(){
        return new SQL()
                .DELETE_FROM(TABLE_AUDIT)
                .WHERE(Info_EQ.id)
                .toString();
    }


    /*获取模板审核展示数据（分页）*/
    public String getShowList(){
        return new SQL(){{
            SELECT("t1.title 'name'");
            SELECT("t2.*");
            FROM(TABLE_TEMP + " t1");
            FROM(TABLE_AUDIT + " t2");
            INNER_JOIN(TABLE_TEMP);
            INNER_JOIN(TABLE_AUDIT + " on template.tempId = templateAudit.tempId");
            ORDER_BY(Info_Key.time);
        }}.toString();
    }


    /*筛选模板审核展示数据（分页）*/
    /*导出筛选的模板数据（前端分页的话就不需要了）*/
    /*修改模板审核记录*/
    /*获取模板统计数据*/
    /*获取模板统计数据*/
    /*查看单个模板具体信息*/
    /*获取单个模板审核记录*/
}
