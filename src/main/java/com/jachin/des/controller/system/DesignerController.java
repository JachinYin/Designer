package com.jachin.des.controller.system;

import com.jachin.des.entity.DesignerAudit;
import com.jachin.des.entity.SearchArg;
import com.jachin.des.service.DesignerService;
import com.jachin.des.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jachin
 * @since 2019/3/16 14:58
 */
@RestController
public class DesignerController {

    @Autowired
    DesignerService designerService;

    // 获取设计师审核列表
    @GetMapping("/getDesignerList")
    public Response getDesignerList(DesignerAudit designerAudit){
        return designerService.getDesignerList(designerAudit);
    }

    // 根据 aid 获取设计师信息【用于设计师审核详情】
    @GetMapping("/getDesByAid")
    public Response getDesignerByAid(SearchArg searchArg){
        return designerService.getDesignerByAid(searchArg.getAid());
    }
}
