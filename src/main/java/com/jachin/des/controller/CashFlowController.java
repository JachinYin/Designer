package com.jachin.des.controller;

import com.jachin.des.entity.CashFlow;
import com.jachin.des.entity.SearchArg;
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

    @GetMapping("/withDraw")
    public Response withdrawCash(CashFlow cashFlow){
        return cashFlowService.addCashFlow(cashFlow);
    }

    @GetMapping("/getCashFlowShowList")
    public Response getCashFlowShowList(SearchArg searchArg){
        return cashFlowService.getCashFlowShowList(searchArg);
    }

    // ========

    @GetMapping("/getCashFlowList")
    public Response getCashFlowList(SearchArg searchArg){
        return cashFlowService.getCashFlowList(searchArg);
    }
}
