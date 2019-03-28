package com.jachin.des.mapper;

import com.jachin.des.entity.SearchArg;
import com.jachin.des.entity.User;
import com.jachin.des.mapper.provider.UserSql;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    // =====基础查改增删=====
    @SelectProvider(type = UserSql.class, method = "getUser")
    public User getUser(SearchArg searchArg);

    @SelectProvider(type = UserSql.class, method = "getUserList")
    public List<User> getUserList(SearchArg searchArg);

    @UpdateProvider(type = UserSql.class, method = "setUser")
    public int setUser(User user);

    @InsertProvider(type = UserSql.class, method = "addUser")
    public int addUser(User user);

    @DeleteProvider(type = UserSql.class, method = "delUser")
    public int delUser(int tempId);
}
