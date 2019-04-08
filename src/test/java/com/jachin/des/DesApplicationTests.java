package com.jachin.des;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DesApplicationTests {

    @Test
    public void contextLoads() {
        int sum = 0;
        long start = Calendar.getInstance().getTimeInMillis();
        for(int i=0; i < 10000*10000; i++){
            sum++;
        }
        long end = Calendar.getInstance().getTimeInMillis();
        System.out.println(end-start);
    }

}
