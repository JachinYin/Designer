package com.jachin.des.service;

import com.jachin.des.entity.SearchArg;
import com.jachin.des.entity.Template;
import com.jachin.des.mapper.TemplateMapper;
import com.jachin.des.mapper.provider.TemplateSql;
import com.jachin.des.util.CommTool;
import com.jachin.des.util.ResParam;
import com.jachin.des.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.UUID;

/**
 * @author Jachin
 * @since 2019/3/22 20:50
 */
@Service
public class TemplateService {

    private static final Logger log = LoggerFactory.getLogger(TemplateService.class);

    @Autowired
    TemplateMapper templateMapper;

    // for debug
    TemplateSql sql = new TemplateSql();

    // 业务逻辑代码
    public Response uploadImg(MultipartFile file, HttpServletRequest request){
        try {
            //目前这里是写死的本地硬盘路径
            String path = CommTool.imgUrl + "/Template";
            //获取文件名称
            String fileName = file.getOriginalFilename();
            //获取文件名后缀
            String suffix = fileName.substring(file.getOriginalFilename().lastIndexOf("."));
            suffix = suffix.toLowerCase();
            if(suffix.equals(".jpg") || suffix.equals(".jpeg") || suffix.equals(".png") || suffix.equals(".gif")){
                fileName = UUID.randomUUID().toString().replace("-","")+suffix;
                File targetFile = new File(path, fileName);
                if(!targetFile.getParentFile().exists()){    //注意，判断父级路径是否存在
                    targetFile.getParentFile().mkdirs();
                }
                //保存
                try {
                    file.transferTo(targetFile);
                } catch (Exception e) {
                    log.error(e.getMessage());
                    return new Response(false, "上传失败！");
                }
                //项目url，这里可以使用常量或者去数据字典获取相应的url前缀；
                String fileUrl="http://localhost:8088";
                //文件获取路径
                fileUrl = fileUrl + request.getContextPath() + "/img/" + fileName;
                Response response = new Response(true, "上传成功");
                ResParam resParam = new ResParam("fileUrl", fileUrl);

                response.setData(resParam);

                return response;
            }else{
                return new Response(false, "图片格式有误，请上传.jpg、.png、.jpeg格式的文件");
            }
        } catch (Exception e) {
            return new Response(false,"上传失败");
        }
    }



    // =====基础查改增删=====

    /**
     * 根据模板Id获取模板信息
     */
    public Response getTemplate(SearchArg searchArg){
        int tempId = searchArg.getTempId();
        if(tempId == 0) return new Response(false, "模板Id错误！");
        Response response = new Response(true, "获取模板数据");
        Template template = templateMapper.getTemplate(searchArg);
        ResParam resParam = new ResParam();
        resParam.put("tempData", template);
        response.setData(resParam);
        return response;
    }

    /**
     * 根据条件获取模板列表
     */
    public Response getTemplateList(SearchArg searchArg){
        int aid = searchArg.getAid();
        if(aid < 1){
            return new Response(false, "获取模板列表失败，aid错误");
        }
        Response response = new Response(true, "获取列表成功");
        List<Template> templateList = templateMapper.getTemplateList(searchArg);
        ResParam resParam = new ResParam();
        resParam.put("list",templateList);
        response.setData(resParam);
        return response;
    }


    /**
     * 根据模板Id更新模板信息
     */
    public Response setTemplate(Template template){

        if(template.getTempId() < 1){
            log.error(template.toString());
            return new Response(false, "更新失败，模板ID错误");
        }

        Response response = new Response(true, "更新成功");

        int rt = templateMapper.setTemplate(template);
        if(rt == 0) return new Response(false, "更新失败(TemplateService Error)");


        ResParam resParam = new ResParam();
        resParam.put("sql", sql.setTemplate(template));
        response.setData(resParam);

        return response;
    }

    /**
     * 添加模板
     */
    public Response addTemplate(Template template){
        Response response = new Response(true, "创建成功");

        if(template.getAid() < 1) return new Response(false, "创建失败，账户ID错误");
        int rt = templateMapper.addTemplate(template);

        if(rt  == 0) return new Response(false, "创建失败(TemplateService error)");

        ResParam resParam = new ResParam();
        resParam.put("sql", sql.addTemplate(template));
        response.setData(resParam);

        return response;
    }

    /**
     * 根据模板Id删除模板
     */
    public Response delTemplate(int tempId){
        if(tempId < 1) return new Response(false, "删除失败，模板Id错误");

        Response response = new Response(true, "删除成功");

        int rt = templateMapper.delTemplate(tempId);
        if(rt == 0) return new Response(false, "删除失败, TemplateService Error");

        ResParam resParam = new ResParam();
        resParam.put("sql", sql.delTemplate(tempId));
        response.setData(resParam);

        return response;
    }

}