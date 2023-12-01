package cn.ilovecry.cloudpractice.controller;

import cn.ilovecry.cloudpractice.common.R;
import cn.ilovecry.cloudpractice.security.SecurityUserInfo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * DemoController
 *
 * @author yangyi
 * @version 1.0
 * @date 2023/11/30 20:01
 */
@RestController
@RequestMapping("/demo")
public class DemoController {
    @GetMapping(path = "/hello")
    public R hello() {
        return R.success("hello,");
    }

    @GetMapping("/getInfo")
    public R getInfo() {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof SecurityUserInfo userInfo) {
            userInfo.setPassword(null);
            return R.success(userInfo);
        }
        return R.error(R.UNAUTHORIZED_CODE, "获取用户信息失败");
    }
}
