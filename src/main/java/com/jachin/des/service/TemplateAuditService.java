package com.jachin.des.service;

import com.jachin.des.entity.*;
import com.jachin.des.mapper.DesignerMapper;
import com.jachin.des.mapper.TemplateAuditMapper;
import com.jachin.des.mapper.TemplateMapper;
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

    private TemplateAuditSql sql = new TemplateAuditSql();

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
        // 获取设计师信息
        Designer designer = designerMapper.getDesigner(lastTempAudit.getAid());
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
        templateAuditMapper.addTemplate(lastTempAudit);

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
        int rt = templateAuditMapper.addTemplate(lastTempAudit); // 在模板审核表插入打回记录

        // 如有必要，修改设计师收入情况
        if(price != 0){
//            designerMapper
        }

        if(rt == 0) return new Response(false, "系统错误，打回失败！");

        response = new Response(true, "模板打回");
        return response;
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
        resParam.put("sql", sql.getTemplateAuditList(searchArg));
        response.setData(resParam);

        return response;
    }

    /**
     * 根据模板Id更新模板信息
     */
    public Response setTemplateAudit(TemplateAudit templateAudit, int type){

        if(type == DataDef.TemplateStatus.BACK){

            SearchArg searchArg = new SearchArg();
            searchArg.setTempId(templateAudit.getTempId());
            searchArg.setCompColumns("time", true);
            List<TemplateAudit> templateAuditList = templateAuditMapper.getTemplateAuditList(searchArg);
            if(templateAuditList.isEmpty()){
                log.error("getTemplateAuditList error; sql Search error;from TemplateAuditService");
                return new Response(false, "系统错误");
            }
            TemplateAudit lastTemplateAudit = templateAuditList.get(0);

            int price = lastTemplateAudit.getPrice();
            price = price > 0 ? -price : 0;
            lastTemplateAudit.setPrice(price);
            lastTemplateAudit.setStatus(DataDef.TemplateStatus.BACK);
            lastTemplateAudit.setTime(CommTool.getNowTime());

            Response response = new Response(true, "打回成功");
            response.setData(new ResParam("type", type));
            return response;
        }

        if(templateAudit.getTempId() < 1) return new Response(false, "更新失败，模板ID错误");
        if(templateAudit.getAid() < 1) return new Response(false, "更新失败，账户ID错误");

//        int rt = templateAuditMapper.setTemplateAudit(templateAudit);
//        if(rt == 0) return new Response(false, "更新失败！");

        Response response = new Response(true, "更新成功");
        response.setData(new ResParam("sql", sql.setTemplateAudit(templateAudit)));
        return response;
    }

    /**
     * 添加模板审核记录
     */
    public Response addTemplateAudit(TemplateAudit templateAudit){
        if(templateAudit.getTempId() < 1) return new Response(false, "操作失败，模板ID错误");
        if(templateAudit.getAid() < 1) return new Response(false, "操作失败，账户ID错误");

        if(!CommTool.isNotBlank(templateAudit.getDesigner())){
            // 如果设计师名字为空，则要根据 aid 去拿名字，然后存入templateAudit对象中
            String nickName = designerMapper.getDesigner(templateAudit.getAid()).getNickName();
            templateAudit.setDesigner(nickName);
        }

        Response response = new Response(true, "操作成功");
        int rt = templateAuditMapper.addTemplateAudit(templateAudit);
        if(rt == 0) return new Response(false, "操作失败");

        response.setData(new ResParam("sql", sql.addTempAudit(templateAudit)));
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
        response.setData(new ResParam("sql", sql.delTemplateAudit(searchArg)));

        return response;
    }

}
