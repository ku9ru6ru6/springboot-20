package me.cai.demo.springboot20.mybatis.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * me.cai.demo.springboot20.mybatis.model
 *
 * @author caiguangzheng
 * date: 2019-07-09
 * mail: caiguangzheng@terminus.io
 * description:
 */
@Data
@NoArgsConstructor
public class City {
    private Long id;
    private String name;
    private String state;
    private String country;

    public City(String name, String state, String country) {
        this.name = name;
        this.state = state;
        this.country = country;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", state='" + state + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
