package com.example.demospringcours;

import com.example.demospringcours.entites.Personne;
import com.example.demospringcours.metier.ServicePersonne;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.isA;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class DemoSpringCoursApplicationTests {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ServicePersonne servicePersonne;
    @BeforeEach
    void setUp() {
        assertNotNull(servicePersonne);
        servicePersonne.ajouterPersonne(new Personne(1L, "Dupond", "Jean", 30));
    }
    @Test
    void testAjout() throws Exception {
        mockMvc.perform(post("/api/personnes")
                        .contentType(MediaType.valueOf("application/json;charset=UTF-8"))
                        .content("{\"nom\" : \"Test\", \"prenom\":\"Jean\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", isA(Integer.class)))
                .andExpect(jsonPath("$.prenom", is("Jean")))
                .andExpect(jsonPath("$.nom", is("Test")));
    }

    @Test
    void testRecup() throws Exception {
        mockMvc.perform(get("/api/personnes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", isA(Integer.class)))
                .andExpect(jsonPath("$.prenom", is("Jean")))
                .andExpect(jsonPath("$.nom", is("Dupond")));
    }

    @Test
    void testRecupTous() throws Exception {
        mockMvc.perform(get("/api/personnes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasItems()))
                .andExpect(jsonPath("$[0].id", isA(Integer.class)))
                .andExpect(jsonPath("$[0].nom", isA(String.class)));
    }

}
