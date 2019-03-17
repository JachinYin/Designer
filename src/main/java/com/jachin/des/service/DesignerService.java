package com.jachin.des.service;

import com.jachin.des.entity.Designer;
import com.jachin.des.entity.DesignerAudit;
import com.jachin.des.mapper.DesignerMapper;
import com.jachin.des.util.CommTool;
import com.jachin.des.util.ResParam;
import com.jachin.des.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jachin
 * @since 2019/3/16 15:00
 */
@Service
public class DesignerService {

    @Autowired
    DesignerMapper designerMapper;

    public Response getDesignerList(DesignerAudit designerAudit){

        // 获取设计师表的记录
        List<DesignerAudit> designerList = designerMapper.getDesignerList(designerAudit);

        ResParam resParam = new ResParam();
        resParam.put("list", designerList);

        Response response = new Response(true, "获取设计师列表");
        response.setData(resParam);


        return response;
    }

    public Response getDesignerByAid(int aid) {
        if(aid < 1){
            return new Response(false, "Aid错误");
        }
        Designer designer = designerMapper.getDesignerById(aid);


        // 获取设计师审核表的记录
        List<DesignerAudit> list = designerMapper.getDesignerAuditList(aid);

        Response response = new Response(true, "获取设计师信息");

        ResParam resParam = new ResParam();
        try {
            CommTool.mergeResParam(resParam, designer);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMsg("合并Param出错");
        }
        resParam.put("list", list);
        response.setData(resParam);
        return response;
    }
}
