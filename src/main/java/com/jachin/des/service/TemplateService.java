package com.jachin.des.service;

import com.jachin.des.entity.Designer;
import com.jachin.des.entity.Template;
import com.jachin.des.entity.TemplateAudit;
import com.jachin.des.entity.DataDef;
import com.jachin.des.mapper.DesignerMapper;
import com.jachin.des.mapper.TemplateAuditMapper;
import com.jachin.des.mapper.TemplateMapper;
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
public class TemplateService {
    private static final Logger log = LoggerFactory.getLogger(TemplateService.class);

    @Autowired
    TemplateAuditMapper templateAuditMapper;
    @Autowired
    TemplateMapper templateMapper;
    @Autowired
    DesignerMapper designerMapper;

    /**
     * 【模板审核】- 查看详情
     * @param tempId 模板Id
     * @return 返回类
     */
    public Response getTempById(int tempId){
        if(tempId == 0){
            return new Response(false, "模板Id错误");
        }
        Template template = templateMapper.findById(tempId);
        // 获取该模板的所有审核记录
        List<TemplateAudit> tempAuditList = templateAuditMapper.getTempAuditById(tempId);
        // 获取最新的一条记录
        TemplateAudit lastTempAudit = tempAuditList.get(0);
        // 获取设计师信息
        Designer designer = designerMapper.getDesignerById(lastTempAudit.getAid());
        // 构造返回数据类
        ResParam resParam = new ResParam();
        Response response = new Response();
        // 拼接数据
        try {
            CommTool.mergeResParam(resParam, template);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMsg("合并Param出错");
        }
        resParam.put("aid", lastTempAudit.getAid());
        resParam.put("status", lastTempAudit.getStatus());
        resParam.put("price", lastTempAudit.getPrice());
        resParam.put("designer", lastTempAudit.getDesigner());
        resParam.put("phone", designer.getPhone());
        resParam.put("realName", designer.getRealName());
        resParam.put("list", tempAuditList);

        response = new Response(true, "获取模板详情信息");
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
        price = price > 0 ? -price : 0;
        lastTempAudit.setPrice(price);
        lastTempAudit.setStatus(DataDef.TemplateStatus.BACK);
        lastTempAudit.setTime(now);
        templateAuditMapper.addTemplate(lastTempAudit);

        response = new Response(true, "模板打回");
        return response;
    }

    public Response getTempList(TemplateAudit form) {
        if(form == null){
            form = new TemplateAudit();
        }

        Response response = new Response(true, "获取模板审核记录");
        List<TemplateAudit> res = templateAuditMapper.getShowTempListForProvider(form);
        ResParam resParam = new ResParam();
        resParam.put("list", res);
        resParam.put("form", form);
        response.setData(resParam);
        return response;
    }

    // ======设计师前台====
    //
    // 获取模板列表
    public Response getTempList(int aid) {
        if(aid < 1){
            return new Response(false, "获取模板列表失败，aid错误");
        }
        Response response = new Response(true, "获取列表成功");
        List<Template> templateList = templateMapper.getTemplateList(aid);
        ResParam resParam = new ResParam();
        resParam.put("list",templateList);
        response.setData(resParam);
        return response;
    }

    // 获取指定模板 ID 的模板数据
    public Response getTempById_Des(int tempId){
        if(tempId == 0) return new Response(false, "模板Id错误！");
        Response response = new Response(true, "获取模板数据");
        Template template = templateMapper.findById(tempId);
        ResParam resParam = new ResParam();
        resParam.put("tempData", template);
        response.setData(resParam);
        return response;
    }
}
