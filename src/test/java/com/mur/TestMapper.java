package com.mur;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mur.domain.security.User;
import com.mur.service.security.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestMapper {
    
    @Autowired
    UserService userService;
    
    
    
    @Test
    public void testFindUsers() {
        User user = new User();
        user.setAccounts("admin2");
        user.setPassword("admin2");
        user.setCreatedTime(new Date());
        user.setName("admin");
        user.setCreator("admin");
//        userService.saveOrUpdateUser(user, "admin");
//        System.out.println(user.getId());
    }
}
