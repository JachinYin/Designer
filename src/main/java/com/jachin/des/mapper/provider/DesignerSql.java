package com.jachin.des.mapper.provider;

import com.jachin.des.entity.Designer;
import com.jachin.des.entity.SearchArg;
import com.jachin.des.util.CommTool;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author Jachin
 * @since 2019/3/16 15:03
 */
public class DesignerSql extends SQL {

    public String getDesigner(SearchArg searchArg) {
        return "select * from designer where aid=${aid};";
    }

    // 获取查询designer的Sql
    public String getDesignerList(SearchArg searchArg) {
        String sql = "";
        boolean distinct = searchArg.isDistinct();
        if(distinct) {
            sql = String.format("SELECT * FROM `%s` AS x " +
                    "WHERE time IN " +
                    "(SELECT MAX(time) FROM `%s` AS y " +
                    "WHERE x.aid = y.aid)", TableDef.DESIGNER_AUDIT, TableDef.DESIGNER_AUDIT);
        }else {
            sql = String.format("SELECT * FROM `%s` WHERE aid>0", TableDef.DESIGNER_AUDIT);
        }
            // 拼接多条件
//        int aid = templateAudit.getAid();
        int aid = searchArg.getAid();
        int status = searchArg.getStatus();
        String nickName = searchArg.getNickName();
        String begTime = searchArg.getBegTime();
        String endTime = searchArg.getEndTime();
        String columns = searchArg.getColumns();
        boolean comp = searchArg.isComp();

        if(aid>0) sql = String.format("%s AND aid=%d", sql, aid);
        if(status>0) sql = String.format("%s AND status=%d", sql, status);
        if(CommTool.isNotBlank(begTime)) sql = String.format("%s AND time>%s", sql, begTime);
        if(CommTool.isNotBlank(endTime)) sql = String.format("%s AND time<%s", sql, endTime);
        if(CommTool.isNotBlank(nickName)) sql += " AND nickName LIKE '%"+ nickName +"%'";
        if(CommTool.isNotBlank(columns)){
            if(comp) sql = String.format("%s ORDER BY %s DESC;", sql, columns);
            else sql = String.format("%s ORDER BY %s ASC;", sql, columns);
        }
        return sql;
    }

    public String setDesigner(Designer designer, SearchArg searchArg) {

        int aid = designer.getAid();
        int status = designer.getStatus();
        int balance = designer.getBalance();
        int totalPrice = designer.getTotalPrice();
        String nickName = designer.getNickName();
        String intro = designer.getIntro();
        String idNum = designer.getIdNum();
        String realName = designer.getRealName();
        String country = designer.getCountry();
        String province = designer.getProvince();
        String city = designer.getCity();
        String phone = designer.getPhone();
        String email = designer.getEmail();
        String weChat = designer.getWeChat();
        String qq = designer.getQq();
        String address = designer.getAddress();
        String bankAcct = designer.getBankAcct();
        String openBank = designer.getOpenBank();
        String cardHolder = designer.getCardHolder();
        String photoImg = designer.getPhotoImg();
        String frontImg = designer.getFrontImg();
        String reverseImg = designer.getReverseImg();

        String sql = new SQL() {{
            UPDATE(TableDef.DESIGNER);

            if(status > 0) SET("status=#{status}");
            if(balance > 0) SET("balance=#{balance}");
            if(totalPrice > 0) SET("totalPrice=#{totalPrice}");

            if (CommTool.isNotBlank(nickName)) SET("nickName=#{nickName}");
            if (CommTool.isNotBlank(intro)) SET("intro=#{intro}");
            if (CommTool.isNotBlank(idNum)) SET("idNum=#{idNum}");
            if (CommTool.isNotBlank(realName)) SET("realName=#{realName}");
            if (CommTool.isNotBlank(country)) SET("country=#{country}");
            if (CommTool.isNotBlank(province)) SET("province=#{province}");
            if (CommTool.isNotBlank(city)) SET("city=#{city}");
            if (CommTool.isNotBlank(phone)) SET("phone=#{phone}");
            if (CommTool.isNotBlank(email)) SET("email=#{email}");
            if (CommTool.isNotBlank(weChat)) SET("weChat=#{weChat}");
            if (CommTool.isNotBlank(qq)) SET("qq=#{qq}");
            if (CommTool.isNotBlank(address)) SET("address=#{address}");
            if (CommTool.isNotBlank(bankAcct)) SET("bankAcct=#{bankAcct}");
            if (CommTool.isNotBlank(openBank)) SET("openBank=#{openBank}");
            if (CommTool.isNotBlank(cardHolder)) SET("cardHolder=#{cardHolder}");
            if (CommTool.isNotBlank(photoImg)) SET("photoImg=#{photoImg}");
            if (CommTool.isNotBlank(frontImg)) SET("frontImg=#{frontImg}");
            if (CommTool.isNotBlank(reverseImg)) SET("reverseImg=#{reverseImg}");

            WHERE("aid=#{aid}");
        }}.toString();

        return sql;
    }

    public String addDesigner(Designer designer) {

        int aid = designer.getAid();
        designer.setStatus(0);

        return new SQL(){{
            INSERT_INTO(TableDef.DESIGNER);
            VALUES("aid", "#{aid}");
            VALUES("status", "#{status}");
        }}.toString();
    }

    public String delDesigner(SearchArg searchArg) {
        return "select * from designer where aid=${aid};";
    }

}
