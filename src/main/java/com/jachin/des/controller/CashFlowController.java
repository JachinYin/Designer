package com.jachin.des.controller;

import com.jachin.des.service.CashFlowService;
import com.jachin.des.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jachin
 * @since 2019/3/21 22:17
 */
@RestController
public class CashFlowController {

    @Autowired
    CashFlowService cashFlowService;

    @GetMapping("/getAllCashFlowList")
    public Response getAllList(){
        return cashFlowService.getAllList();
    }
}
