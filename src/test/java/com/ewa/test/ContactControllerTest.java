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
public class ContactControllerTest {
	@Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();
	
	@Autowired
    private WebApplicationContext context;
	
    private MockMvc mockMvc;
    
    private String sessionId = "";
    private String contactId = "";
    private String q = "";
    private String order = "";
    private String direction = "ASC";
	
    @Before
    public void setUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();
    }
    
    @Test
    public void testGetList() throws Exception {
        this.mockMvc.perform(get("/ewa/contact/current").header("Authorization", sessionId))
            .andExpect(status().isOk())
            .andDo(document("contact/current/get",
            		responseFields(
            				fieldWithPath("[].id").description("Contact Id"),
            				fieldWithPath("[].fullName").description("Full Name").optional(),
            		                fieldWithPath("[].picture").description("Avatar Picture").optional(),
            		                fieldWithPath("[].email").description("Email"),
            		                fieldWithPath("[].knowLocation").description("Last Known Location").optional())
            		));
    }
    
    @Test
    public void testDelete() throws Exception {
        this.mockMvc.perform(delete("/ewa/contact/" + contactId).header("Authorization", sessionId))
            .andExpect(status().isOk())
            .andDo(document("contact/delete"));
    }

    @Test
    public void testGet() throws Exception {
        this.mockMvc.perform(post("/ewa/contact").header("Authorization", sessionId)
        		.param("q", q)
        		.param("order", order)
        		.param("direction", direction))
            .andExpect(status().isOk())
            .andDo(document("session/post",
            		responseFields(
            				fieldWithPath("[].id").description("Contact Id"),
            				fieldWithPath("[].fullName").description("Full Name").optional(),
            		                fieldWithPath("[].picture").description("Avatar Picture").optional(),
            		                fieldWithPath("[].email").description("Email"),
            		                fieldWithPath("[].knowLocation").description("Last Known Location").optional())
            		));
    }
}

