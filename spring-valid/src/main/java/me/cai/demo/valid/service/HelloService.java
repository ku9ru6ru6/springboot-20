package me.cai.demo.valid.service;

import me.cai.demo.valid.model.Person;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public interface HelloService {

    Object testCallSelf(@NotNull @Min(10) Integer id, @NotNull String name);

    @NotEmpty String cascade(@NotNull @Valid Person father, @NotNull Person mother);
}
