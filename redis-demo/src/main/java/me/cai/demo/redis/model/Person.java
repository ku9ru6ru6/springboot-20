package me.cai.demo.redis.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * me.cai.demo.redis.model
 *
 * @author caiguangzheng
 * date: 2019-07-13
 * mail: caiguangzheng@terminus.io
 * description:
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Person {

    private Integer id;

    private String name;

    private String phoneNumber;

    private Integer age;
}
