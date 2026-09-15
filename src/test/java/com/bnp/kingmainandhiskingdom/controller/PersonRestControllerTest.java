package com.bnp.kingmainandhiskingdom.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class PersonRestControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void testGetAllPersonsContainsPreFilled() throws Exception {
        mockMvc.perform(get("/api/persons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[?(@.name == 'Arthur')].age").value(35));
    }

    @Test
    void testCreateAndGetPerson() throws Exception {
        String jsonPayload = """
                {
                    "name": "Percival",
                    "age": 25
                }
                """;

        // POST /api/persons -> 201 Created
        mockMvc.perform(post("/api/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Percival"))
                .andExpect(jsonPath("$.age").value(25));

        // GET /api/persons/Percival -> 200 OK
        mockMvc.perform(get("/api/persons/Percival"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Percival"))
                .andExpect(jsonPath("$.age").value(25));

        // GET /api/persons/query?name=Percival -> 200 OK
        mockMvc.perform(get("/api/persons/query").param("name", "Percival"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Percival"))
                .andExpect(jsonPath("$.age").value(25));
    }

    @Test
    void testCreatePersonWithInvalidAgeThrowsBadRequest() throws Exception {
        String jsonPayload = """
                {
                    "name": "YoungKnight",
                    "age": 18
                }
                """;

        mockMvc.perform(post("/api/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.title").value("Invalid Request"))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.description").value("Age must be greater than 20"));
    }

    @Test
    void testGetNonExistentPersonThrowsNotFound() throws Exception {
        mockMvc.perform(get("/api/persons/UnknownKnight"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.title").value("Person Not Found"))
                .andExpect(jsonPath("$.status").value(404));
    }

    @Test
    void testUpdatePerson() throws Exception {
        // Train first
        String createPayload = """
                {
                    "name": "Gawain",
                    "age": 24
                }
                """;
        mockMvc.perform(post("/api/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createPayload))
                .andExpect(status().isCreated());

        // Update age
        String updatePayload = """
                {
                    "name": "Gawain",
                    "age": 32
                }
                """;
        mockMvc.perform(put("/api/persons/Gawain")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatePayload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Gawain"))
                .andExpect(jsonPath("$.age").value(32));
    }

    @Test
    void testDeletePerson() throws Exception {
        // Train first
        String createPayload = """
                {
                    "name": "Galahad",
                    "age": 27
                }
                """;
        mockMvc.perform(post("/api/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(createPayload))
                .andExpect(status().isCreated());

        // Delete
        mockMvc.perform(delete("/api/persons/Galahad"))
                .andExpect(status().isNoContent());

        // Verify deleted
        mockMvc.perform(get("/api/persons/Galahad"))
                .andExpect(status().isNotFound());
    }
}
