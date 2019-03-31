package com.jachin.des.mapper.provider;

import org.springframework.stereotype.Service;

/**
 * @author Jachin
 * @since 2019/3/30 10:01
 */
@Service
public class SqlUtils {
    public static final CashFlowSql cashFlowSql = new CashFlowSql();
    public static final DesignerSql designerSql = new DesignerSql();
    public static final DesignerAuditSql designerAuditSql = new DesignerAuditSql();
    public static final TemplateSql templateSql = new TemplateSql();
    public static final TemplateAuditSql templateAuditSql = new TemplateAuditSql();
    public static final UserSql userSql = new UserSql();
}
