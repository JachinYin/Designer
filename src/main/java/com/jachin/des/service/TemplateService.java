package com.jachin.des.service;

import com.jachin.des.def.DataDef;
import com.jachin.des.entity.SearchArg;
import com.jachin.des.entity.Template;
import com.jachin.des.entity.TemplateAudit;
import com.jachin.des.mapper.TemplateAuditMapper;
import com.jachin.des.mapper.TemplateMapper;
import com.jachin.des.mapper.provider.SqlUtils;
import com.jachin.des.mapper.provider.TemplateSql;
import com.jachin.des.util.CommTool;
import com.jachin.des.util.CurrentUser;
import com.jachin.des.util.ResParam;
import com.jachin.des.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

/**
 * @author Jachin
 * @since 2019/3/22 20:50
 */
@Service
public class TemplateService {

    private static final Logger log = LoggerFactory.getLogger(TemplateService.class);

    @Resource
    private TemplateMapper templateMapper;

    @Resource
    private TemplateAuditMapper templateAuditMapper;

    // for debug
    TemplateSql sql = new TemplateSql();

    // 业务逻辑代码
    public Response uploadImg(MultipartFile file, int imgType){
        try {
            // 根据上传的图片类型，存放到不同的文件夹下
            String folder = DataDef.getFolderType(imgType);
            if (folder.isEmpty()) return new Response(false, "指定的图片类型错误。");
            //资源文件的存放路径
            String path = CommTool.imgUrl + folder;
            //获取文件名称
            String fileName = file.getOriginalFilename();
            //获取文件名后缀
            String suffix = fileName.substring(file.getOriginalFilename().lastIndexOf("."));
            suffix = suffix.toLowerCase();  // 后缀统一转小写
            // 如果不是图片
            if(!CommTool.isPicture(suffix)) return new Response(false, "图片格式有误，请上传.jpg、.png、.jpeg格式的文件");

            // 通过MD5获取文件名
            String fileMD5String = CommTool.getFileMD5String(file);
            if(fileMD5String.isEmpty()) return new Response(false, "文件转码失败。");
            fileName = fileMD5String + suffix;

            File targetFile = new File(path, fileName);
            if(targetFile.exists()){    // 通过MD5获取的文件名唯一，所以如果要创建的文件存在的话，说明文件重复，直接返回文件名即可。
                Response response = new Response(true, "上传成功");
                // 返回存放到数据库的图片URL
                String fileUrl= folder + fileName;
                response.setData(new ResParam("fileUrl", fileUrl));
                return response;
            }

            if(!targetFile.getParentFile().exists()){    //注意，判断父级路径是否存在
                if(!targetFile.getParentFile().mkdirs())
                    return new Response(false, "文件上传失败。");
            }
            //如果文件不重复，则保存
            try {
                file.transferTo(targetFile);
            } catch (Exception e) {
                log.error(e.getMessage());
                return new Response(false, "上传失败！");
            }
            // 返回存放到数据库的图片URL
            String fileUrl= folder + fileName;

            Response response = new Response(true, "上传成功");
            response.setData(new ResParam("fileUrl", fileUrl));

            return response;

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
        int aid = CurrentUser.getCurrentAid();
        if(aid < 1){
            return new Response(false, "获取模板列表失败，aid错误");
        }
        Response response = new Response(true, "获取列表成功");
        searchArg.setAid(aid);
        List<Template> templateList = templateMapper.getTemplateList(searchArg);
        ResParam resParam = new ResParam();
        resParam.put("list",templateList);
        resParam.put("sql",SqlUtils.templateSql.getTemplateList(searchArg));
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

        // 在每一次执行更新操作前，都需要保证该模板是可以更新的（即状态为 打回、待提交）
        SearchArg searchArg = new SearchArg();
        searchArg.setTempId(template.getTempId());
        searchArg.setCompColumns("time", true);
        List<TemplateAudit> templateAuditList = templateAuditMapper.getTemplateAuditList(searchArg);
        if(!templateAuditList.isEmpty()) {
            TemplateAudit templateAudit = templateAuditList.get(0);
            if (templateAudit != null) {
                // 如果为 通过 或 待审核状态，则说明不能修改
                if (templateAudit.getStatus() == DataDef.TemplateStatus.PASS) {
                    return new Response(false, "模板已通过审核，不能再进行修改");
                }
                if (templateAudit.getStatus() == DataDef.TemplateStatus.WAIT) {
                    return new Response(false, "模板已提交审核，请耐心等待结果");
                }
            }
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
        int aid = CurrentUser.getCurrentAid();
        if(aid < 1) return new Response(false, "创建失败，账户ID错误");

        template.setAid(aid);
        int rt = templateMapper.addTemplate(template);
        if(rt  == 0) return new Response(false, "创建失败(TemplateService error)");

        Response response = new Response(true, "创建成功");
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
