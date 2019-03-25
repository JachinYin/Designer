package com.jachin.des.service;

import com.jachin.des.entity.Designer;
import com.jachin.des.entity.DesignerAudit;
import com.jachin.des.entity.SearchArg;
import com.jachin.des.mapper.DesignerAuditMapper;
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

    @Autowired
    DesignerAuditMapper designerAuditMapper;

    public Response getDesignerList(SearchArg searchArg){

        // 获取设计师表的记录
        List<DesignerAudit> designerList = designerMapper.getDesignerList(searchArg);

        ResParam resParam = new ResParam();
        resParam.put("list", designerList);

        Response response = new Response(true, "获取设计师列表");
        response.setData(resParam);


        return response;
    }

    public Response getDesignerByAid(SearchArg searchArg) {
        int aid = searchArg.getAid();
        if(aid < 1){
            return new Response(false, "Aid错误");
        }
        Designer designer = designerMapper.getDesigner(searchArg);


        // 获取设计师审核表的记录
        List<DesignerAudit> list = designerAuditMapper.getDesignerAuditList(aid);

        Response response = new Response(true, "获取设计师信息");

        ResParam resParam = new ResParam();
         int rt = CommTool.mergeResParam(resParam, designer);
         if(rt != 0) response.setMsg("合并Param出错");
        resParam.put("list", list);
        response.setData(resParam);
        return response;
    }
}
