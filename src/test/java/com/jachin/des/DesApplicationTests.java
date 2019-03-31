package com.jachin.des;

import com.jachin.des.entity.SearchArg;
import com.jachin.des.mapper.provider.SqlUtils;
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

        String user = SqlUtils.userSql.getUser(searchArg);
        System.out.println(user);
    }

}
