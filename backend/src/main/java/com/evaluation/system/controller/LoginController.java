package com.evaluation.system.controller;


import com.evaluation.system.interceptor.WebSecurityConfig;
import com.evaluation.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/")
    public String index(@SessionAttribute(WebSecurityConfig.SESSION_KEY) String account, Model model) {

        return "index";
    }

    /*登录验证*/
    @PostMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/loginVerify")
    public String loginVerify(@RequestParam("user_id") String id, @RequestParam("password") String password,
                              HttpSession session) {
        boolean verify = userService.loginCheck(id, password);
        if (verify) {
            session.setAttribute(WebSecurityConfig.SESSION_KEY, id);
            return "index";
        } else {
            return "redirect:/login";
        }
    }

    /*退出登录*/
    @PostMapping("/exit")
    public String exit(HttpSession session) {
        session.removeAttribute(WebSecurityConfig.SESSION_KEY);
        return "redirect:/login";
    }
}
