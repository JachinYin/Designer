package com.jachin.des.mapper.provider;

import com.jachin.des.entity.DataDef;
import com.jachin.des.entity.Designer;
import com.jachin.des.entity.SearchArg;
import com.jachin.des.util.CommTool;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author Jachin
 * @since 2019/3/16 15:03
 */
public class DesignerSql extends SQL {

    // =======基础查改增删=======

    public String getDesigner(SearchArg searchArg) {
        return String.format("SELECT * FROM `%s` WHERE aid=%d", TableDef.DESIGNER, searchArg.getAid());
    }

    // 获取查询designer的Sql
    public String getDesignerList(SearchArg searchArg) {
        String sql;
        boolean distinct = searchArg.isDistinct();
        if(distinct) {
            sql = String.format("SELECT * FROM `%s` AS x " +
                    "WHERE time IN " +
                    "(SELECT MAX(time) FROM `%s` AS y " +
                    "WHERE x.aid = y.aid)", TableDef.DESIGNER_AUDIT, TableDef.DESIGNER_AUDIT);
        }else {
            sql = String.format("SELECT * FROM `%s` WHERE aid>0", TableDef.DESIGNER_AUDIT);
        }

        int aid = searchArg.getAid();
        int status = searchArg.getStatus();
        String nickName = searchArg.getNickName();
        String begTime = searchArg.getBegTime();
        String endTime = searchArg.getEndTime();
        String columns = searchArg.getColumns();
        boolean comp = searchArg.isComp();

        if(aid>0) sql = String.format("%s AND aid=%d", sql, aid);
        if(status>0) sql = String.format("%s AND status=%d", sql, status);
        if(CommTool.isNotBlank(begTime)) sql = String.format("%s AND time>'%s'", sql, begTime);
        if(CommTool.isNotBlank(endTime)) sql = String.format("%s AND time<'%s'", sql, endTime);
        if(CommTool.isNotBlank(nickName)) sql += " AND nickName LIKE '%"+ nickName +"%'";
        if(CommTool.isNotBlank(columns)){
            if(comp) sql = String.format("%s ORDER BY %s DESC;", sql, columns);
            else sql = String.format("%s ORDER BY %s ASC;", sql, columns);
        }
        return sql;
    }

    public String setDesigner(Designer designer) {
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

        return new SQL() {{
            UPDATE(TableDef.DESIGNER);

            SET("aid=#{aid}");  // 保证 update 语句至少拥有一个set项

            if(status > 0) SET("status=#{status}");
            if(balance > 0) SET("balance=#{balance}");
            if(totalPrice > 0) SET("totalPrice=#{totalPrice}");
            if(balance < 0) {
                designer.setBalance(0);
                SET("balance=#{balance}");
            }

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
    }

    public String addDesigner(Designer designer) {
        return new SQL(){{
            INSERT_INTO(TableDef.DESIGNER);
            VALUES("aid", "#{aid}");
        }}.toString();
    }

    public String delDesigner(SearchArg searchArg) {
        int aid = searchArg.getAid();
        Designer designer = new Designer();
        designer.setAid(aid);
        // 删除设计师，就是把设计师的状态设置为【已删除】
        designer.setStatus(DataDef.DesignerStatus.DEL);
        return setDesigner(designer);
    }

}
