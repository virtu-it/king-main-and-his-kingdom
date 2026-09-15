package com.bnp.kingmainandhiskingdom.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class HelloRestControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testWelcomeDefault() throws Exception {
        mockMvc.perform(get("/welcome"))
                .andExpect(status().isOk())
                .andExpect(content().string("Welcome to the Kingdom, Guest!"));
    }

    @Test
    void testWelcomeWithCustomName() throws Exception {
        mockMvc.perform(get("/welcome").param("name", "Arthur"))
                .andExpect(status().isOk())
                .andExpect(content().string("Welcome to the Kingdom, Arthur!"));
    }

    @Test
    void testWelcomePerson() throws Exception {
        mockMvc.perform(get("/welcomePerson").param("name", "Lancelot").param("age", "28"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Lancelot"))
                .andExpect(jsonPath("$.age").value(28));
    }
}
