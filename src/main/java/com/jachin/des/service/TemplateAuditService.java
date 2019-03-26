package com.jachin.des.service;

import com.jachin.des.entity.*;
import com.jachin.des.mapper.CashFlowMapper;
import com.jachin.des.mapper.DesignerMapper;
import com.jachin.des.mapper.TemplateAuditMapper;
import com.jachin.des.mapper.TemplateMapper;
import com.jachin.des.mapper.provider.DesignerSql;
import com.jachin.des.mapper.provider.TemplateAuditSql;
import com.jachin.des.util.CommTool;
import com.jachin.des.util.ResParam;
import com.jachin.des.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * @author Jachin
 * @since 2019/3/15 20:03
 */
@Service
public class TemplateAuditService {
    private static final Logger log = LoggerFactory.getLogger(TemplateAuditService.class);

    @Autowired
    TemplateAuditMapper templateAuditMapper;
    @Autowired
    TemplateMapper templateMapper;
    @Autowired
    DesignerMapper designerMapper;
    @Autowired
    CashFlowMapper cashFlowMapper;

    private TemplateAuditSql templateAuditSql = new TemplateAuditSql();
    private DesignerSql designerSql = new DesignerSql();

    /**
     * 【模板审核】- 查看详情 done
     * @param searchArg 通用参数类
     * @return 返回类
     */
    public Response getAuditShowData(SearchArg searchArg){
        int tempId = searchArg.getTempId();
        if(tempId == 0){
            return new Response(false, "模板Id错误");
        }
        Template template = templateMapper.getTemplate(searchArg);
        searchArg.setCompColumns("time", true);
        // 获取该模板的所有审核记录 & 最新的一条记录
        List<TemplateAudit> tempAuditList = templateAuditMapper.getTemplateAuditList(searchArg);
        TemplateAudit lastTempAudit = tempAuditList.get(0);
        searchArg.setAid(template.getAid());
        // 获取设计师信息
        Designer designer = designerMapper.getDesigner(searchArg);
        // 构造返回数据类
        Response response = new Response(true, "获取模板详情信息");

        ResParam resParam = new ResParam();
        // 拼接数据
        int rt = CommTool.mergeResParam(resParam, template);
        int rt2 = CommTool.mergeResParam(resParam, lastTempAudit);

        if(rt != 0 || rt2 != 0) response.setMsg("合并Param出错");

        resParam.put("phone", designer.getPhone());
        resParam.put("realName", designer.getRealName());
        resParam.put("list", tempAuditList);
        response.setData(resParam);
        return response;
    }

    public Response tempPass(int tempId, int price) {
        if(tempId == 0){
            return new Response(false, "模板Id错误");
        }
        if(price < 0){
            return new Response(false, "价格错误");
        }
        // 通过的逻辑分为：
        /*
         * 1.模板审核表新增通过记录
         * 2.收入表添加该设计师收入记录，包括
         *   - 在收入表新插入一条记录
         *   - 修改设计师表的历史总收入
         *   - 修改设计师表的可提现余额
         * */

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = sf.format(Calendar.getInstance().getTimeInMillis());

        TemplateAudit lastTempAudit = templateAuditMapper.getTempAuditById(tempId).get(0);
        lastTempAudit.setPrice(price);
        lastTempAudit.setStatus(DataDef.TemplateStatus.PASS);
        lastTempAudit.setTime(now);
        templateAuditMapper.addTemplateAudit(lastTempAudit);

        return new Response(true, "模板通过");
    }

    public Response tempRefuse(int tempId, String reason) {
        Response response;
        if(tempId == 0){
            response = new Response(false, "模板Id错误");
            return response;
        }
        if(reason.isEmpty()){
            return new Response(false,"请填写打回原因。");
        }

        // 打回的逻辑分为：
        /*
         * 1.模板审核表新增打回记录
         * 2.从数据库获取该模板的最新一条审核记录
         *
         * 2.收入表添加该设计师收入记录，包括
         *   - 在收入表新插入一条记录
         *   - 修改设计师表的历史总收入
         *   - 修改设计师表的可提现余额
         * */
        ;
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now = sf.format(Calendar.getInstance().getTimeInMillis());

        TemplateAudit lastTempAudit = templateAuditMapper.getTempAuditById(tempId).get(0);
        int price = lastTempAudit.getPrice();
        price = price > 0 ? -price : 0;     // 价格大于0.说明是通过后再打回的
        lastTempAudit.setPrice(price);
        lastTempAudit.setStatus(DataDef.TemplateStatus.BACK);
        lastTempAudit.setTime(now);
        int rt = templateAuditMapper.addTemplateAudit(lastTempAudit); // 在模板审核表插入打回记录

        // 如有必要，修改设计师收入情况
        if(price != 0){
//            designerMapper
        }

        if(rt == 0) return new Response(false, "系统错误，打回失败！");

        response = new Response(true, "模板打回");
        return response;
    }

    // 执行审核逻辑的服务
    public Response doTemplateAudit(TemplateAudit templateAudit, int type){
        if(type == 0) return new Response(false, "请求参数错误");

        int tempId = templateAudit.getTempId();
        int aid = templateAudit.getAid();
        if(tempId < 1) return new Response(false, "操作失败，模板ID错误。");
        if(aid < 1) return new Response(false, "操作失败，账户ID错误。");

        SearchArg searchArg = new SearchArg();
        searchArg.setTempId(tempId);
        searchArg.setCompColumns("time", true);
        List<TemplateAudit> templateAuditList = templateAuditMapper.getTemplateAuditList(searchArg);

        TemplateAudit lastTemplateAudit; // 取最新的一条记录
        try {
            lastTemplateAudit = templateAuditList.get(0);
        } catch (Exception e) {
            log.error("getTemplateAuditList error; templateAuditSql Search error;from TemplateAuditService");
            // 能发起该请求，说明一定在数据库中存在数据，如果拿到的数据集为空，说明是拿数据的过程出错了
            return new Response(false, "系统错误，请联系管理员。");
        }

        // 获取设计师信息
        searchArg = new SearchArg();
        searchArg.setAid(aid);
        Designer designer = designerMapper.getDesigner(searchArg);
        if(designer == null) return new Response(false, "系统错误，请联系管理员。");

        if(type == DataDef.TemplateStatus.BACK) {
            int price = lastTemplateAudit.getPrice(); // 价格大于 0 说明是在【通过态】进行的操作
            price = price > 0 ? -price : 0;
            lastTemplateAudit.setPrice(price);
            lastTemplateAudit.setStatus(DataDef.TemplateStatus.BACK);

            int rt = templateAuditMapper.addTemplateAudit(lastTemplateAudit);
            if(rt == 0) return new Response(false); // 如果插入失败，就不执行设计师表的修改

            if(price < 0){
                Designer designerData = new Designer();
                designerData.setAid(aid);
                designerData.setBalance(designer.getBalance() + price); // 打回时只需要扣除设计师余额
                rt = designerMapper.setDesigner(designerData);
                if(rt == 0) { // 这个错误涉及到事务了，需要手动帮客户改表
                    log.error(String.format("Temp refused,but designer table don't update" +
                                    ".{aid=%d},{tempId=%d}"
                            , aid, tempId));
                    return new Response(true, "打回成功，请联系管理员。");
                }
                CashFlow cashFlow = new CashFlow();
                cashFlow.setAid(aid);
                cashFlow.setTempId(tempId);
                cashFlow.setPrice(price);
                cashFlow.setType(DataDef.CashFlag.DELTA_PRICE);
                cashFlow.setBalance(designerData.getBalance());
                rt = cashFlowMapper.addCashFlow(cashFlow);
                if(rt == 0) { // 这个错误涉及到事务了，需要手动帮客户改表
                    log.error(String.format("Temp refused,but cashFlow table don't update" +
                                    ".{aid=%d},{tempId=%d}"
                            , aid, tempId));
                    return new Response(true, "打回成功，请联系管理员。");
                }
            }
            return new Response(true, "打回成功");
        }else if(type == DataDef.TemplateStatus.PASS){
            int price = templateAudit.getPrice(); // 设置通过的采购价
            if(price < 1) return new Response(false, "模板价格错误");

            lastTemplateAudit.setPrice(price);
            lastTemplateAudit.setStatus(DataDef.TemplateStatus.PASS);

            int rt = templateAuditMapper.addTemplateAudit(lastTemplateAudit);
            if(rt == 0) return new Response(false);

            Designer designerData = new Designer();
            designerData.setAid(aid);
            designerData.setBalance(designer.getBalance() + price); // 设计师余额
            designerData.setBalance(designer.getTotalPrice() + price); // 设计师总收入，通过会一直累加
            rt = designerMapper.setDesigner(designerData);
            if(rt == 0) { // 这个错误涉及到事务了，需要手动帮客户改表
                log.error(String.format("Temp refused,but designer table don't update" +
                                ".{aid=%d},{tempId=%d}"
                        , aid, tempId));
                return new Response(true, "打回成功，请联系管理员。");
            }
            CashFlow cashFlow = new CashFlow();
            cashFlow.setAid(aid);
            cashFlow.setTempId(tempId);
            cashFlow.setPrice(price);
            cashFlow.setType(DataDef.CashFlag.INCOME);
            cashFlow.setBalance(designerData.getBalance());
            rt = cashFlowMapper.addCashFlow(cashFlow);
            if(rt == 0) { // 这个错误涉及到事务了，需要手动帮客户改表
                log.error(String.format("Temp refused,but cashFlow table don't update" +
                                ".{aid=%d},{tempId=%d}"
                        , aid, tempId));
                return new Response(true, "打回成功，请联系管理员。");
            }
            return new Response(true);
        }
        else{
            return new Response(false);
        }
    }

    // =====基础查改增删=====

    /**
     * 根据模板Id获取模板信息
     */
    public Response getTemplateAudit(SearchArg searchArg){
        Response response = new Response(true, "获取成功");
        TemplateAudit templateAudit = templateAuditMapper.getTemplateAudit(searchArg);
        response.setData(new ResParam("tempAuditData",  templateAudit));
        return response;
    }

    /**
     * 根据条件获取模板列表
     */
    public Response getTemplateAuditList(SearchArg searchArg){
        Response response = new Response(true, "获取成功");

        searchArg.setCompColumns("time", true);

        List<TemplateAudit> list = templateAuditMapper.getTemplateAuditList(searchArg);
        TemplateAuditSql sql = new TemplateAuditSql();


        ResParam resParam = new ResParam();
        resParam.put("list", list);
        resParam.put("templateAuditSql", sql.getTemplateAuditList(searchArg));
        response.setData(resParam);

        return response;
    }

    /**
     * 根据模板Id更新模板信息
     * 对于当前的业务，只有两种可能：
     * 1.基于通过业务的修改
     * 2.基于打回业务的修改
     * 然而最终，都是对审核记录表的新增而不是修改
     */
    public Response setTemplateAudit(TemplateAudit templateAudit){
        if(templateAudit.getId() == 0) return new Response(false, "操作失败，ID错误。");
        int rt = templateAuditMapper.setTemplateAudit(templateAudit);
        if(rt == 0) return new Response(false, "操作失败，系统错误。");
        return new Response(true, "操作成功~");
    }

    /**
     * 添加模板审核记录
     */
    public Response addTemplateAudit(TemplateAudit templateAudit){
        if(templateAudit.getTempId() < 1) return new Response(false, "操作失败，模板ID错误");
        if(templateAudit.getAid() < 1) return new Response(false, "操作失败，账户ID错误");

        if(!CommTool.isNotBlank(templateAudit.getDesigner())){
            SearchArg searchArg = new SearchArg();
            searchArg.setAid(templateAudit.getAid());
            // 如果设计师名字为空，则要根据 aid 去拿名字，然后存入templateAudit对象中
            String nickName = designerMapper.getDesigner(searchArg).getNickName();
            templateAudit.setDesigner(nickName);
        }

        Response response = new Response(true, "操作成功");
        int rt = templateAuditMapper.addTemplateAudit(templateAudit);
        if(rt == 0) return new Response(false, "操作失败");

        response.setData(new ResParam("templateAuditSql", templateAuditSql.addTemplateAudit(templateAudit)));
        return response;
    }

    /**
     * 根据模板Id删除模板
     */
    public Response delTemplateAudit(SearchArg searchArg){

        if(searchArg.getTempId() < 1) return new Response(false, "删除失败，模板ID错误");
        if(searchArg.getAid() < 1) return new Response(false, "删除失败，账户ID错误");

        int rt = templateAuditMapper.delTemplateAudit(searchArg);
        if(rt == 0) return new Response(false, "--删除失败--");

        Response response = new Response(true, "删除成功");
        response.setData(new ResParam("templateAuditSql", templateAuditSql.delTemplateAudit(searchArg)));

        return response;
    }

}
