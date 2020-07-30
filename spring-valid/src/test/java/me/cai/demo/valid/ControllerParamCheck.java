package me.cai.demo.valid;

import lombok.extern.slf4j.Slf4j;
import me.cai.demo.valid.service.HelloService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * @author : caiguangzheng
 * @date : 2020-07-30
 */

@Slf4j
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = SpringValidStarter.class)
public class ControllerParamCheck {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private HelloService helloService;

    @Test
    public void testAddUserM1() throws Exception {

        Assert.assertNotNull(helloService);

        MockHttpServletRequestBuilder post = MockMvcRequestBuilders.post("/api/user/addUser/m2")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}");

        mockMvc.perform(post).andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

}
