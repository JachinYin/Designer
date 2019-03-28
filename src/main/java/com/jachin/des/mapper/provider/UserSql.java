package com.jachin.des.mapper.provider;

import com.jachin.des.entity.SearchArg;
import com.jachin.des.entity.User;
import com.jachin.des.util.CommTool;
import org.apache.ibatis.jdbc.SQL;

/**
 * @author Jachin
 * @since 2019/3/28 21:15
 */
public class UserSql {

    public String getUserList(SearchArg searchArg){
        return "";
    }

    public String getUser(SearchArg searchArg){
        int aid = searchArg.getAid();
        return String.format("SELECT * FROM `%s` WHERE aid=%d", TableDef.USER, aid);
    }

    public String setUser(User user){
        //int aid = user.getAid(); //AID不可改
        String userName = user.getUserName();
        String password = user.getPassword();
        String token = user.getToken();
        String info = user.getInfo();
        user.setTime(CommTool.getNowTime());

        return new SQL() {{
            UPDATE(TableDef.USER);
            SET("time=#{time}");  // 保证 update 语句至少拥有一个set项
            if (CommTool.isNotBlank(userName)) SET("userName=#{userName}");
            if (CommTool.isNotBlank(password)) SET("password=#{password}");
            if (CommTool.isNotBlank(token)) SET("token=#{token}");
            if (CommTool.isNotBlank(info)) SET("info=#{info}");
            WHERE("aid=#{aid}");
        }}.toString();
    }

    public String addUser(User user){
        int aid = user.getAid(); //AID在插入后只读
        String userName = user.getUserName();
        String password = user.getPassword();
        String token = user.getToken();
        String info = user.getInfo();
        user.setTime(CommTool.getNowTime());

        return new SQL() {{
            INSERT_INTO(TableDef.USER);
            SET("aid=#{aid}");
            SET("time=#{time}");
            if (CommTool.isNotBlank(userName)) VALUES("userName", "#{userName}");
            if (CommTool.isNotBlank(password)) VALUES("password", "#{password}");
            if (CommTool.isNotBlank(token)) VALUES("token", "#{token}");
            if (CommTool.isNotBlank(info)) VALUES("info", "#{info}");
        }}.toString();
    }

    public String delUser(SearchArg searchArg){
        return String.format("DELETE FROM `%s` WHERE aid=%d", TableDef.USER, searchArg.getAid());
    }

}
