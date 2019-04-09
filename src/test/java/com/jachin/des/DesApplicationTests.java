package com.jachin.des;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DesApplicationTests {

    @Test
    public void contextLoads() {
        for(int i = 0; i < 10; i++){
            if(i==1) continue;
            System.out.println(i);
        }
    }

}
