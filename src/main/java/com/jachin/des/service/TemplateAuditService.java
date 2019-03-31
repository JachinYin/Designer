package com.jachin.des.service;

import com.jachin.des.entity.*;
import com.jachin.des.mapper.CashFlowMapper;
import com.jachin.des.mapper.DesignerMapper;
import com.jachin.des.mapper.TemplateAuditMapper;
import com.jachin.des.mapper.TemplateMapper;
import com.jachin.des.mapper.provider.CashFlowSql;
import com.jachin.des.mapper.provider.DesignerSql;
import com.jachin.des.mapper.provider.TemplateAuditSql;
import com.jachin.des.util.CommTool;
import com.jachin.des.util.ResParam;
import com.jachin.des.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private CashFlowSql cashFlowSql = new CashFlowSql();

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

    // 执行审核逻辑的服务
    public Response doTemplateAudit(TemplateAudit templateAudit, String typeName){
        int type = DataDef.getTemplateStatus(typeName);
        if(type == 0) return new Response(false, "请求参数错误");

        int tempId = templateAudit.getTempId();
        if(tempId < 1) return new Response(false, "操作失败，模板ID错误。");
//        if(aid < 1) return new Response(false, "操作失败，账户ID错误。");

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


        int aid = lastTemplateAudit.getAid();
        // 获取设计师信息
        searchArg = new SearchArg();
        searchArg.setAid(aid);
        Designer designer = designerMapper.getDesigner(searchArg);
        if(designer == null) return new Response(false, "系统错误，请联系管理员。");

        if(type == DataDef.TemplateStatus.BACK) {
            // 1.插入审核记录，2.修改模板状态，3.修改设计师收入，4.添加现金流记录
            int price = lastTemplateAudit.getPrice(); // 价格大于 0 说明是在【通过态】进行的操作
            price = price > 0 ? -price : 0;
            lastTemplateAudit.setPrice(price);
            lastTemplateAudit.setStatus(DataDef.TemplateStatus.BACK);
            try {
                // 如果拿不到理由，则直接置空
                String reason = templateAudit.getReason().trim();
                if(reason.endsWith(",")) reason = reason.substring(0, reason.length()-2);
                lastTemplateAudit.setReason(reason);
            } catch (Exception ignore) {}
            // 1.
            int rt = templateAuditMapper.addTemplateAudit(lastTemplateAudit);
            if(rt == 0) return new Response(false); // 如果插入失败，就不执行设计师表的修改

            //2.
            Template template = new Template();
            template.setTempId(tempId);
            template.setStatus(DataDef.TemplateStatus.BACK);
            rt = templateMapper.setTemplate(template);
            if(rt == 0) return new Response(false, "打回成功，请联系管理员。");

            if(price < 0){
                Designer designerData = new Designer();
                designerData.setAid(aid);
                designerData.setBalance(designer.getBalance() + price); // 打回时只需要扣除设计师余额
                if(designerData.getBalance() == 0) designerData.setBalance(-1); // 变成负数，便可以写数据库
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
        }
        else if(type == DataDef.TemplateStatus.PASS){
            // 1.插入审核记录，2.更新模板状态， 3.更新设计师余额，4.插入现金流记录
            int price = templateAudit.getPrice(); // 设置通过的采购价
            if(price < 1) return new Response(false, "模板价格错误");

            lastTemplateAudit.setPrice(price);
            lastTemplateAudit.setStatus(DataDef.TemplateStatus.PASS);

            // 1.
//            log.error("Jacin1==="+templateAuditSql.addTemplateAudit(lastTemplateAudit));
            int rt = templateAuditMapper.addTemplateAudit(lastTemplateAudit);
            if(rt == 0) return new Response(false);

            //2.
            Template template = new Template();
            template.setTempId(tempId);
            template.setStatus(DataDef.TemplateStatus.BACK);
            rt = templateMapper.setTemplate(template);
            if(rt == 0) return new Response(false, "打回成功，请联系管理员。");

            Designer designerData = new Designer();
            designerData.setAid(aid);
            designerData.setBalance(designer.getBalance() + price); // 设计师余额
            designerData.setTotalPrice(designer.getTotalPrice() + price); // 设计师总收入，通过会一直累加
//            log.error("Jacin2==="+ designerSql.setDesigner(designerData) + "====" + designerData);
            // 3.
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
//            log.error("Jacin3==="+cashFlowSql.addCashFlow(cashFlow));
            // 4.
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
     * 前台添加模板审核记录
     */
    public Response addTemplateAudit(TemplateAudit templateAudit){
        if(templateAudit.getTempId() < 1) return new Response(false, "操作失败，模板ID错误");
        if(templateAudit.getAid() < 1) return new Response(false, "操作失败，账户ID错误");

        if(!CommTool.isNotBlank(templateAudit.getDesigner())){
            SearchArg searchArg = new SearchArg();
            searchArg.setAid(templateAudit.getAid());
            // 如果设计师名字为空，则要根据 aid 去拿名字，然后存入templateAudit对象中
            Designer designer = designerMapper.getDesigner(searchArg);
            if(designer == null) return new Response(false,"目前不可提交审核。");
            if(designer.getStatus() != DataDef.DesignerStatus.PASS)
                return new Response(false,"目前不可提交审核。");
            String nickName = designer.getNickName();
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
