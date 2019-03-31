package com.jachin.des.controller;

import com.jachin.des.entity.SearchArg;
import com.jachin.des.entity.User;
import com.jachin.des.mapper.TemplateAuditMapper;
import com.jachin.des.mapper.provider.SqlUtils;
import com.jachin.des.service.TemplateAuditService;
import com.jachin.des.service.UserService;
import com.jachin.des.util.ResParam;
import com.jachin.des.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jachin
 * @since 2019/3/14 9:41
 */
@RestController
public class TestController {

    @Autowired
    TemplateAuditMapper templateAuditMapper;

    @Autowired
    TemplateAuditService templateAuditService;

    @Autowired
    UserService userService;

    @GetMapping("getTest")
    public Response getTest(SearchArg searchArg){
        User user = userService.getUser(searchArg);
        Response response = new Response(true);
        ResParam resParam = new ResParam();
        resParam.put("user", user);
        resParam.put("sql", SqlUtils.userSql.getUser(searchArg));
        response.setData(resParam);
        return response;
    }
    @GetMapping("getTest2")
    public Response getTest2(SearchArg searchArg){
        return userService.userLogin(searchArg);
    }
}
