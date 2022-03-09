package net.gentledot.myservices.userservice.user.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserServiceController {

    @GetMapping("/info")
    public String info(@Value("${server.port}") String port) {
        return String.format("UserService 서비스의 기본 동작 Port: %s", port);
    }

}
