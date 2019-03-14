package com.jachin.des.mapper;

import com.jachin.des.entity.TemplateAudit;
import com.jachin.des.mapper.provider.TemplateAuditSql;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

// 在每个 mapper 上添加这个注解，或者在启动类添加@MapperScan("com.xxx.mapper")
@Mapper
public interface TemplateAuditMapper {
    //@SelectProvider(type=TemplateAuditSql.class, method = "getShowList")
    @Select("select * from `templateAudit` as x where time in " +
                "(select max(time) from `templateAudit` as y " +
                "where x.tempId = y.tempId) " +
            "ORDER BY time desc;")
    public List<TemplateAudit> getShowListTemp();

    // 获取指定模板的所有审核记录，按时间排序
    @Select("SELECT * FROM `templateAudit` where tempId = #{tempId} ORDER BY time desc;")
    public List<TemplateAudit> getTempAuditById(@Param("tempId")int tempId);

    @InsertProvider(type = TemplateAuditSql.class, method = "addTempAudit")
    public void addTemplate(TemplateAudit templateAudit);


//    public List<TemplateAudit> getAllListById();
}
