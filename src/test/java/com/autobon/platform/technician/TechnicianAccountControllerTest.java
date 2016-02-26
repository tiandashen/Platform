package com.autobon.platform.technician;

import com.autobon.platform.Application;
import com.autobon.technician.entity.Technician;
import com.autobon.technician.service.TechnicianService;
import org.junit.Assert;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
/**
 * Created by dave on 16/2/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
public class TechnicianAccountControllerTest {
    @Autowired WebApplicationContext wac;
    @Autowired TechnicianService technicianService;
    @Autowired FilterChainProxy springFilterChain;
    @Value("${com.autobon.test.token}")
    String token;
    String phoneT = "18812345678";

    MockMvc mockMvc;
    MockMvc mockMvcS;
    String phone = "18827075300";
    String password = "123456";

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        mockMvcS = MockMvcBuilders.webAppContextSetup(wac).addFilter(springFilterChain).build();
    }

    @Test
    public void sendVerifySms() throws Exception {
        mockMvc.perform(get("/api/mobile/verifySms").param("phone", phone))
            .andExpect(status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(jsonPath("$.result", is(true)));
    }

    @Test
    public void register() throws Exception {
        mockMvc.perform(get("/api/mobile/verifySms").param("phone", phone))
                .andExpect(status().isOk());

        mockMvc.perform(post("/api/mobile/technician/register")
                .param("phone", phone)
                .param("password", password)
                .param("verifySms", "123456"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(jsonPath("$.data.phone", is(phone)));

        Technician technician = technicianService.getByPhone(phone);
        Assert.assertNotNull(technician);

        mockMvc.perform(post("/api/mobile/technician/register")
                .param("phone", phone)
                .param("password", password)
                .param("verifySms", "123456"))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(jsonPath("$.error", is("OCCUPIED_ID")));
    }

    @Test
    public void response403() throws Exception {
        mockMvcS.perform(post("/api/mobile/technician/changePassword")
                .param("password", "123456"))
            .andExpect(status().is(403))
            .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void changeAndResetPassword() throws Exception {
        mockMvcS.perform(post("/api/mobile/technician/changePassword")
                .param("oldPassword", "123456")
                .param("newPassword", "221234")
                .cookie(new Cookie("autoken", token)))
            .andDo(MockMvcResultHandlers.print())
            .andExpect(jsonPath("$.result", is(true)));

        mockMvc.perform(post("/api/mobile/technician/login")
                .param("phone", phoneT)
                .param("password", "221234"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.result", is(true)));

        mockMvc.perform(get("/api/mobile/verifySms").param("phone", phoneT));
        mockMvc.perform(post("/api/mobile/technician/resetPassword")
                .param("phone", phoneT)
                .param("password", "123456")
                .param("verifySms", "123456"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.result", is(true)));
    }


}