package com.autobon.platform.coop;

import com.autobon.cooperators.entity.Cooperator;
import com.autobon.platform.Application;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.http.Cookie;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by yuh on 2016/3/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class CoopAccountControllerTest {
    @Value("${com.autobon.test.coopToken}")
    String token;

    @Autowired
    WebApplicationContext wac;

    @Autowired
    FilterChainProxy springFilterChain;

    MockMvc mockMvc;
    MockMvc mockMvcS;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        mockMvcS = MockMvcBuilders.webAppContextSetup(wac).addFilter(springFilterChain).build();
    }


    @Test
    public void register() throws Exception {
        //System.out.println(Cooperator.makeToken(1));

        mockMvc.perform(get("/api/mobile/verifySms").param("phone", "13072705335"))
                .andExpect(status().isOk());

        mockMvcS.perform(post("/api/mobile/coop/register")
                .param("shortname","tomcat")
                .param("phone", "13072705335")
                .param("password","123456")
                .param("verifySms","123456"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.result", is(true)));
    }

}
