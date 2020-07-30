package me.cai.demo.valid.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@ToString
public class Person {

    @NotNull
    private String name;
    @NotNull
    @Positive
    private Integer age;

    @Valid // 让InnerChild的属性也参与校验
    @NotNull
    private InnerChild child;

    @Getter
    @Setter
    @ToString
    public static class InnerChild {
        @NotNull
        private String name;
        @NotNull
        @Positive
        private Integer age;
    }

}
