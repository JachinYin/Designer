package com.jachin.des.mapper.provider;

import com.jachin.des.entity.CashFlow;
import com.jachin.des.entity.SearchArg;

/**
 * @author Jachin
 * @since 2019/3/25 21:09
 */
public class CashFlowSql {



    public String getDesignerAudit(SearchArg searchArg) {
        return "select * from designer where aid=${aid};";
    }

    public String getDesignerAuditList(SearchArg searchArg) {
        return "select * from designerAudit where aid = " + searchArg.getAid();
    }

    public String setDesignerAudit(CashFlow designerAudit, SearchArg searchArg) {
        return "select * from designer where aid=${aid};";
    }

    public String addDesignerAudit(CashFlow designerAudit) {
        return "select * from designer where aid=${aid};";
    }

    public String delDesignerAudit(SearchArg searchArg) {
        return "select * from designer where aid=${aid};";
    }
}

