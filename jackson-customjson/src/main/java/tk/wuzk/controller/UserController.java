package tk.wuzk.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import tk.wuzk.annotation.CustomJson;
import tk.wuzk.bean.UserInfo;

import java.util.Collections;
import java.util.List;

@Controller
public class UserController {
    @CustomJson(targetClass = UserInfo.class, includes = "name")
    @GetMapping("/v1/users")
    public List<UserInfo> list_v1() {
        UserInfo userInfo = new UserInfo().setName("hello").setPassword("password");
        return Collections.singletonList(userInfo);
    }

    @CustomJson(targetClass = UserInfo.class, excludes = "password")
    @GetMapping("/v2/users")
    public List<UserInfo> list_v2() {
        UserInfo userInfo = new UserInfo().setName("hello").setPassword("password");
        return Collections.singletonList(userInfo);
    }
}
