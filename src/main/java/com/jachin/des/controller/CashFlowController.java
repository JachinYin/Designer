package com.jachin.des.controller;

import com.jachin.des.entity.CashFlow;
import com.jachin.des.entity.SearchArg;
import com.jachin.des.service.CashFlowService;
import com.jachin.des.util.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author Jachin
 * @since 2019/3/21 22:17
 */
@RestController
public class CashFlowController {

    @Resource
    private CashFlowService cashFlowService;

    // 获取设计师提现总数
    @GetMapping("/getTotalWithdraw")
    public Response getTotalWithdraw(SearchArg searchArg){
        return cashFlowService.getTotalWithdraw(searchArg);
    }

    // 提现
    @GetMapping("/withdraw")
    public Response withdrawCash(CashFlow cashFlow){
        return cashFlowService.withdraw(cashFlow);
    }

    // 后台获取现金流列表
    @GetMapping("/getCashFlowShowList")
    public Response getCashFlowShowList(SearchArg searchArg){
        return cashFlowService.getCashFlowShowList(searchArg);
    }

    // 后台获取分佣管理详细数据
    @GetMapping("/getCashDetail")
    public Response getCashDetail(SearchArg searchArg){
        return cashFlowService.getCashDetail(searchArg);
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
