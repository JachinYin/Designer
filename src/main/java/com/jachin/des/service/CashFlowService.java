package com.jachin.des.service;

import com.jachin.des.entity.CashFlow;
import com.jachin.des.mapper.CashFlowMapper;
import com.jachin.des.util.ResParam;
import com.jachin.des.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CashFlow 表的服务层
 *
 * @author Jachin
 * @since 2019/3/21 22:13
 */
@Service
public class CashFlowService {

    @Autowired
    CashFlowMapper cashFlowMapper;

    public Response getAllList() {
        Response response = new Response(true, "获取收入/提现表记录");
        List<CashFlow> list = cashFlowMapper.getAllList();
        ResParam resParam = new ResParam();
        resParam.put("list", list);
        response.setData(resParam);
        return response;
    }
}
