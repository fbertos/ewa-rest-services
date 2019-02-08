package com.ewa.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.web.context.WebApplicationContext;

import org.junit.Before;
import org.junit.Rule;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SessionControllerTest {
	@Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();
	
	@Autowired
    private WebApplicationContext context;
	
    private MockMvc mockMvc;
    
    private String sessionId = "";
	
    @Before
    public void setUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();
    }
    
    @Test
    public void testPut() throws Exception {
        this.mockMvc.perform(put("/ewa/session"))
            .andExpect(status().isOk())
            .andDo(document("session/put",
            		responseFields(fieldWithPath("id").description("Generated Session Token ID"),
            				fieldWithPath("userId").description("User ID"),
            				fieldWithPath("dateTime").description("Creation Date"))
            		));
    }
    
    @Test
    public void testGet() throws Exception {
        this.mockMvc.perform(post("/ewa/session/" + sessionId))
            .andExpect(status().isOk())
            .andDo(document("session/get",
            		responseFields(fieldWithPath("id").description("Generated Session Token ID"),
            				fieldWithPath("userId").description("User ID"),
            				fieldWithPath("dateTime").description("Creation Date"))
            		));
    }

    @Test
    public void testDelete() throws Exception {
        this.mockMvc.perform(delete("/ewa/session/" + sessionId))
            .andExpect(status().isOk())
            .andDo(document("session/get"));
    }
    
    @Test
    public void testPost() throws Exception {
        this.mockMvc.perform(post("/ewa/session/" + sessionId))
            .andExpect(status().isOk())
            .andDo(document("session/post",
            		responseFields(fieldWithPath("id").description("Generated Session Token ID"),
            				fieldWithPath("userId").description("User ID"),
            				fieldWithPath("dateTime").description("Creation Date"))
            		));
    }
}

