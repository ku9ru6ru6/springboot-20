package me.cai.demo.valid.controller;

import me.cai.demo.valid.model.User;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * me.cai.demo.valid.controller
 *
 * @author caiguangzheng
 * date: 2020/5/7
 * mail: caiguangzheng@terminus.io
 * description:
 */

@RestController
@RequestMapping("/api/user")
public class UserController {

    @PostMapping("/addUser/m1")
    public String addUserM1(@RequestBody @Valid User user, BindingResult bindingResult) {
        // 如果有参数校验失败，会将错误信息封装成对象组装在BindingResult里
        for (ObjectError error : bindingResult.getAllErrors()) {
            return error.getDefaultMessage();
        }
        return "success";
    }

    @PostMapping("/addUser/m2")
    public String addUserM2(@RequestBody @Valid User user) {
        return "success";
    }
}
