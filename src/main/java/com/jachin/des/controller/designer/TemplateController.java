package com.jachin.des.controller.designer;

import com.jachin.des.service.TemplateService;
import com.jachin.des.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Jachin
 * @since 2019/3/18 23:07
 */
@RestController
public class TemplateController {

    @Autowired
    TemplateService templateService;

    @GetMapping("/getTemplateList")
    public Response getTemplateList(int aid){
        return templateService.getTempList(aid);
    }

    @GetMapping("/getTempById")
    public Response gerTemplateById(int tempId){
        return templateService.getTempById_Des(tempId);
    }
}

