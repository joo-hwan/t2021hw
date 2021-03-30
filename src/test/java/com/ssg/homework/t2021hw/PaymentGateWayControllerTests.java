package com.ssg.homework.t2021hw;

import com.ssg.homework.t2021hw.domain.member.model.Member;
import com.ssg.homework.t2021hw.domain.member.repository.MemberRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class PaymentGateWayControllerTests {

    @Autowired
    MockMvc mvc;

    @Autowired
    private WebApplicationContext ctx;

    /*
    한글 깨짐 처리
     */
    @BeforeEach
    void mvcSetUp() {
        this.mvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

    @Disabled
    @Test
    void approveTest() throws Exception {
        mvc.perform(post("/api/pg/approve")
                .param("mbrId", "0000000010")
                .param("pmtCode", "P0001")
                .param("pmtType", "")
                .param("pmtAmt", "1000"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
