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

    // 提现
    @GetMapping("/withdraw")
    public Response withdrawCash(CashFlow cashFlow){
        return cashFlowService.withdraw(cashFlow);
    }

    // 获取现金流列表
    @GetMapping("/getCashFlowShowList")
    public Response getCashFlowShowList(SearchArg searchArg){
        return cashFlowService.getCashFlowShowList(searchArg);
    }


    // 设计师前台，获取账户明细数据
    @GetMapping("/getCashData")
    public Response getCashData(){
        return cashFlowService.getCashData();
    }

    // ========

    @GetMapping("/getCashFlowList")
    public Response getCashFlowList(SearchArg searchArg){
        return cashFlowService.getCashFlowList(searchArg);
    }
}
