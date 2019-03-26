package com.jachin.des;

import com.jachin.des.entity.Designer;
import com.jachin.des.entity.SearchArg;
import com.jachin.des.mapper.provider.DesignerSql;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DesApplicationTests {

    @Test
    public void contextLoads() {
        SearchArg searchArg = new SearchArg();
        searchArg.setAid(1);

        Designer designer = new Designer();
        designer.setAid(1);

        DesignerSql designerSql = new DesignerSql();
        System.out.println(designerSql.addDesigner(designer));
    }

}
