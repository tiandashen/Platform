package com.autobon.test.common;

import com.autobon.test.MvcTest;
import org.junit.Test;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by yuh on 2016/2/17.
 */
public class PubControllerTest extends MvcTest {

    @Test
    public void getSkills() throws Exception {
        this.mockMvc.perform(get("/api/pub/technician/skills"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.result", is(true)));
    }

    @Test
    public void getOrderTypes() throws Exception {
        this.mockMvc.perform(get("/api/pub/orderTypes"))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.result", is(true)));
    }

    @Test
    public void getWorkItems() throws Exception {
        this.mockMvc.perform(get("/api/pub/technician/workItems")
                .param("orderType", "1"))
            .andExpect(status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(jsonPath("$.result", is(true)));
    }

    @Test
    public void getSmsCode() throws Exception {
        this.mockMvc.perform(get("/api/pub/verifySms")
                .param("phone", "18827075338"))
            .andExpect(status().isOk())
            .andDo(MockMvcResultHandlers.print())
            .andExpect(jsonPath("$.result", is(true)));
    }


}
