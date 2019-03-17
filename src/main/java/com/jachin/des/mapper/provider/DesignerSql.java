package com.jachin.des.mapper.provider;

import com.jachin.des.entity.DesignerAudit;
import com.jachin.des.util.CommTool;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author Jachin
 * @since 2019/3/16 15:03
 */
public class DesignerSql extends SQL {


    // 获取查询designer的Sql
    public String getDesignerList(DesignerAudit designerAudit){

        StringBuilder sb = new StringBuilder();
        sb.append("select * from `designerAudit` as x where time in ");
        sb.append("(select max(time) from `designerAudit` as y ");
        sb.append("where x.aid = y.aid) ");

        // 拼接多条件
//        int aid = templateAudit.getAid();
        int aid = designerAudit.getAid();
        String nickName = designerAudit.getNickName();
        int status = designerAudit.getStatus();
        String time = designerAudit.getTime();

        if(aid > 0) sb.append(" and aid = " + aid);
        if(!CommTool.isBlank(nickName)) sb.append(" and nickName like '%" + nickName + "%'");
        if(status > 0) sb.append(" and status = " + status);
        if(!CommTool.isBlank(time)) sb.append(" and time > '" + time + "'");


        sb.append(" ORDER BY time desc;");
        return sb.toString();
    }


    public String  getDesignerAuditList(int aid){
        return "select * from designerAudit where aid = " + aid;
    }

}
