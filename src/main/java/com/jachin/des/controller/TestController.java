package com.jachin.des.controller;

import com.jachin.des.entity.SearchArg;
import com.jachin.des.mapper.TemplateAuditMapper;
import com.jachin.des.service.TemplateAuditService;
import com.jachin.des.service.UserService;
import com.jachin.des.util.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Jachin
 * @since 2019/3/14 9:41
 */
@RestController
public class TestController {

    @Resource
    private TemplateAuditMapper templateAuditMapper;

    @Resource
    private TemplateAuditService templateAuditService;

    @Resource
    private UserService userService;

    @GetMapping("getTest")
    public Response getTest(SearchArg searchArg){
        return new Response(true);
    }
}
