package cn.ilovecry.cloudpractice;

import cn.ilovecry.cloudpractice.util.SecureUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class DemoApplicationTests {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {
    }

    @Test
    public void passwordTest() {
        String password = "super@123";
        String passwordSha1 = SecureUtil.sha1Hex(password);
        System.out.println(passwordSha1);
        System.out.println(passwordEncoder.encode(passwordSha1));
    }

}
