package com.autobon.test.coop;

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
import javax.transaction.Transactional;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Created by yuh on 2016/2/22.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
public class OrderControllerTest {
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
    public void comment() throws Exception {
        mockMvcS.perform(post("/api/mobile/coop/order/comment")
                .param("orderId","2")
                .param("star", "5")
                .param("arriveOnTime","true")
                .param("completeOnTime","true")
                .param("professional","true")
                .param("dressNeatly","true")
                .param("carProtect","true")
                .param("goodAttitude","true")
                .param("advice","贴膜技术不错")
                .cookie(new Cookie("autoken", token)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.result", is(true)));
    }


    @Test
    public void createOrder() throws Exception {
        mockMvcS.perform(post("/api/mobile/coop/order/createOrder")
                .param("photo", "a/a.jpg")
                .param("remark", "remark is here")
                .param("orderTime", "2016-03-01 12:02")
                .param("orderType", "1")
                .cookie(new Cookie("autoken", token)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.result", is(true)));
    }

    @Test
    public void listUnfinished() throws Exception {
        mockMvcS.perform(post("/api/mobile/coop/order/listUnfinished")
                .cookie(new Cookie("autoken", token)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.result", is(true)));
    }

    @Test
    public void listFinished() throws Exception {
        mockMvcS.perform(post("/api/mobile/coop/order/listFinished")
                .cookie(new Cookie("autoken", token)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.result", is(true)));
    }

    @Test
    public void listUncomment() throws Exception {
        mockMvcS.perform(post("/api/mobile/coop/order/listUncomment")
                .cookie(new Cookie("autoken", token)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.result", is(true)));
    }


    @Test
    public void orderCount() throws Exception {
        mockMvcS.perform(post("/api/mobile/coop/order/orderCount")
                .cookie(new Cookie("autoken", token)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.result", is(true)));
    }

    //测试订单带指定技师
    @Test   //主技师未通过审核
    public void createOrderAndAppoint1()throws Exception {
        mockMvcS.perform(post("/api/mobile/coop/order/createOrderAndAppoint")
                .param("photo","photo")
                .param("remark","订单注释")
                .param("orderTime","2016-02-10 09:23")
                .param("orderType","1")
                .param("mainTechId","2")
                .cookie(new Cookie("autoken", token)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.result", is(false)));
    }

    @Test   //日期时间不正确
    public void createOrderAndAppoint2()throws Exception {
        mockMvcS.perform(post("/api/mobile/coop/order/createOrderAndAppoint")
                .param("photo","photo")
                .param("remark","订单注释")
                .param("orderTime","2016-02-1009:23")
                .param("orderType","1")
                .param("mainTechId","1")
                .cookie(new Cookie("autoken", token)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.result", is(false)));
    }

    @Test   //主技师无此技能
    public void createOrderAndAppoint3()throws Exception {
        mockMvcS.perform(post("/api/mobile/coop/order/createOrderAndAppoint")
                .param("photo","photo")
                .param("remark","订单注释")
                .param("orderTime","2016-02-10 09:23")
                .param("orderType","2")
                .param("mainTechId","1")
                .cookie(new Cookie("autoken", token)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.result", is(false)));
    }

    @Test   //商户未通过验证
    public void createOrderAndAppoint4()throws Exception {
        mockMvcS.perform(post("/api/mobile/coop/order/createOrderAndAppoint")
                .param("photo","photo")
                .param("remark","订单注释")
                .param("orderTime","2016-02-10 09:23")
                .param("orderType","1")
                .param("mainTechId","1")
                .cookie(new Cookie("autoken", token)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.result", is(false)));
    }

    @Test   //订单创建并指定技师
    public void createOrderAndAppoint5()throws Exception {
        mockMvcS.perform(post("/api/mobile/coop/order/createOrderAndAppoint")
                .param("photo","photo")
                .param("remark","订单注释")
                .param("orderTime","2016-02-10 09:23")
                .param("orderType","1")
                .param("mainTechId","1")
                .cookie(new Cookie("autoken", token)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.result", is(true)));
    }

}
