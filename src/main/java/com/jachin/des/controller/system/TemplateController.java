package com.jachin.des.controller.system;

import com.jachin.des.entity.Designer;
import com.jachin.des.entity.Template;
import com.jachin.des.entity.TemplateAudit;
import com.jachin.des.entity.def.TemplateDef;
import com.jachin.des.mapper.DesignerMapper;
import com.jachin.des.mapper.TemplateAuditMapper;
import com.jachin.des.mapper.TemplateMapper;
import com.jachin.des.util.CommTool;
import com.jachin.des.util.ResParam;
import com.jachin.des.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * @author Jachin
 * @since 2019/3/8 15:06
 */
@RestController
public class TemplateController {
    private static final Logger log = LoggerFactory.getLogger(TemplateController.class);

    @Autowired
    TemplateAuditMapper templateAuditMapper;
    @Autowired
    TemplateMapper templateMapper;
    @Autowired
    DesignerMapper designerMapper;



    @GetMapping("/getTemplateList")
    public Response getTemplateListBySearch(){
        List<TemplateAudit> templateAuditList = templateAuditMapper.getShowListTemp();
        List<TemplateAudit> resAuditList = new ArrayList<>();

        Response response = new Response(true, "获取模板数据");
        ResParam resParam = new ResParam();
        resParam.put("list", templateAuditList);
        response.setData(resParam);
        return response;
    }

    /**
     * 用于模板审核表。点击查看时，返回展示数据信息
     * 根据模板ID来获取数据，包括设计师信息，该模板所有审核记录，以及模板信息
     * @param tempId 模板Id
     * @return ResParam
     */
    @GetMapping("/getTempById")
    public Response getTempById(@RequestParam(value = "tempId", required = false, defaultValue = "0")int tempId){
        Response response;
        if(tempId == 0){
            response = new Response(false, "模板Id错误");
            return response;
        }

        Template template = templateMapper.findById(tempId);
        // 获取该模板的所有审核记录
        List<TemplateAudit> tempAuditList = templateAuditMapper.getTempAuditById(tempId);
        TemplateAudit lastTempAudit = tempAuditList.get(0);

        Designer designer = designerMapper.getDesignerById(lastTempAudit.getAid());
        response = new Response(true, "获取模板详情信息");

        ResParam resParam = new ResParam();

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

        resParam.put("list", tempAuditList);

        response.setData(resParam);
        return response;
    }

    @GetMapping("/tempPass")
    public Response tempPass(@RequestParam(value = "tempId", required = false, defaultValue = "0")int tempId,
                             @RequestParam(value = "price", required = false, defaultValue = "-1")int price){
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
        lastTempAudit.setStatus(TemplateDef.Status.PASS);
        lastTempAudit.setTime(now);
        templateAuditMapper.addTemplate(lastTempAudit);

        Response response = new Response(true, "模板通过");
        return response;
    }
    @GetMapping("/tempRefuse")
    public Response tempRefuse(@RequestParam(value = "tempId", required = false, defaultValue = "0")int tempId,
                               @RequestParam(value = "reason", required = false, defaultValue = "")String reason){
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
        lastTempAudit.setStatus(TemplateDef.Status.BACK);
        lastTempAudit.setTime(now);
        templateAuditMapper.addTemplate(lastTempAudit);

        response = new Response(true, "模板打回");
        return response;
    }




    @GetMapping("/addTemplate")
    //@RequestParam("template")String template
    public Response addTemplate(TemplateAudit templateAudit){
        Response response;
        try {
//            templateAuditMapper.addTemplate(templateAudit1);
        }catch (Exception e){
            response = new Response(false, "新增失败");
            System.out.println(e.getMessage());
            return response;
        }
        ResParam resParam = new ResParam();
        resParam.put("tempAudit",templateAudit);
        response = new Response(true, "插入成功");
        response.setData(resParam);
        return response;
    }
}
