package com.jachin.des.mapper.provider;

import com.jachin.des.pojo.Template;
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

    private final String TABLE = "template";
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
    class Info_EQ_Param{
        public static final String id = " id = #{ template.id }";
        public static final String aid = " aid = #{ template.aid }";
        public static final String tempId = " tempId = #{ template.tempId }";
        public static final String name = " name = #{ template.name }";
        public static final String designer = " designer = #{ template.designer }";
        public static final String time = " time = #{ template.time }";
        public static final String status = " status = #{ template.status }";
        public static final String price = " price = #{ template.price }";
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

    public String list() {
        return new SQL()
                .SELECT("*")
                .FROM(TABLE)
                .toString();

    }

    public String get() {
        return new SQL()
                .SELECT("*")
                .FROM(TABLE)
                .WHERE(Info_EQ.id)
                .toString();
    }

    public String add(){
        return new SQL()
                .INSERT_INTO(TABLE)
                .VALUES(Info.aid, Info_Key.aid)
                .VALUES(Info.tempId, Info_Key.tempId)
                .VALUES(Info.name, Info_Key.name)
                .VALUES(Info.designer, Info_Key.designer)
                .VALUES(Info.time, Info_Key.time)
                .VALUES(Info.status, Info_Key.status)
                .VALUES(Info.price, Info_Key.price)
                .toString();
    }

    public String update(@Param("template")Template template) throws Exception {
        if(template.getAid() == 0){
            log.error("{rt=-2}; arg error; aid=0");
            throw new Exception();
        }
        if(template.getTempId() == 0){
            log.error("{rt=-2}; arg error; tempId=0");
            throw new Exception();
        }
        log.info(template.toString() + template.getName());
        return new SQL(){
            {
                UPDATE(TABLE);
                if(template.getDesigner() != null && !template.getDesigner().isEmpty())
                    SET(Info_EQ_Param.designer);
                if(template.getName() != null && !template.getName().isEmpty())
                    SET(Info_EQ_Param.name);
                if(template.getTime() != null && !template.getTime().isEmpty())
                    SET(Info_EQ_Param.time);
                if(template.getStatus() != 0)
                    SET(Info_EQ_Param.status);
                if(template.getPrice() != 0)
                    SET(Info_EQ_Param.price);
                WHERE(Info_EQ_Param.aid);
                WHERE(Info_EQ_Param.tempId);
            }
        }.toString();
    }

    public String delete(){
        return new SQL()
                .DELETE_FROM(TABLE)
                .WHERE(Info_EQ.id)
                .toString();
    }

}
