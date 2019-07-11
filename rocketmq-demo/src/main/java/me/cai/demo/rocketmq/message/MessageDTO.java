package me.cai.demo.rocketmq.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * me.cai.demo.rocketmq.message
 *
 * @author caiguangzheng
 * date: 2019-07-10
 * mail: caiguangzheng@terminus.io
 * description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO implements Serializable {

    private static final long serialVersionUID = 1290588432625549779L;

    private Integer id;

    private String name;
}
