package com.ewa.test;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Date;

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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.web.context.WebApplicationContext;

import com.ewa.model.Event;
import com.ewa.model.Location;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Rule;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EventControllerTest {
	@Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();
	
	@Autowired
    private WebApplicationContext context;
	
    private MockMvc mockMvc;
    
    private String sessionId = "";
    private String picture = "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";

    @Before
    public void setUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();
    }

    @Test
    public void testPost() throws Exception {
    	ObjectMapper mapper = new ObjectMapper();
        Event event = new Event();
        
        event.setDate(new Date());
        event.setDescription("A description");
        event.setName("An event name");
        event.setAddress(new Location());
        event.getAddress().setLatitud("40.4412031");
        event.getAddress().setLongitud("-3.6953329");
        
        String obj_ev = mapper.writeValueAsString(event);
        
        MockMultipartFile mpicture = new MockMultipartFile("picture", "sample.png", "image/png", picture.getBytes());
        MockMultipartFile mevent = new MockMultipartFile("event", "", "application/json", obj_ev.getBytes());
        
        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/ewa/event")
        		.file(mpicture)
        		.file(mevent)
        		.header("Authorization", sessionId)
        		.header("Content-Type", "application/json"))
            .andExpect(status().isOk())
            .andDo(document("ewa/event/get",
            		responseFields(fieldWithPath("id").description("Generated Event ID"),
            				fieldWithPath("name").description("Event Name"),
            				fieldWithPath("description").description("Event Description"),
            				fieldWithPath("picture").description("Event Picture"),
            				fieldWithPath("ownerId").description("Event Owner ID"),
            				fieldWithPath("date").description("Event Date"),
            				fieldWithPath("status").description("Event Status"),
            				fieldWithPath("address").description("Event Address"))
            		));
    }
}

