package kr.ac.kopo.sns.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

import javax.sql.DataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:config/spring/spring-mvc.xml"})
public class MyBatisTest {

    @Autowired
    private DataSource ds;

    @Test
    public void DB접속테스트() throws Exception {
        System.out.println(ds);
       
        
        assertNotNull(ds);
    }
}