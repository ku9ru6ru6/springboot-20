package me.cai.demo.valid.model;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * me.cai.demo.valid.model
 *
 * @author caiguangzheng
 * date: 2020/5/7
 * mail: caiguangzheng@terminus.io
 * description:
 */
@Data
public class User {

    private Long id;

    @NotNull(message = "user.account.not.null")
    @Size(min = 6, max = 11, message = "user.account.size.not.match")
    private String account;

    @NotNull(message = "user.password.not.null")
    @Size(min = 6, max = 11, message = "user.password.size.not.match")
    private String password;

    @NotNull(message = "user.email.not.null")
    @Email(message = "user.email.not.match")
    private String email;
}
