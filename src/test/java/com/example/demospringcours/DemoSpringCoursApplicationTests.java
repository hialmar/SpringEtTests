package com.example.demospringcours;

import com.example.demospringcours.modeles.Commentaire;
import com.example.demospringcours.modeles.Personne;
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
        servicePersonne.ajouterPersonne(new Personne(2L, "Dupond", "Edouard", 35));
        servicePersonne.ajouterCommentaire(servicePersonne.recupererPersonne(1L), new Commentaire("1", "Contenu 1", 1L));
        servicePersonne.ajouterCommentaire(servicePersonne.recupererPersonne(2L), new Commentaire("2", "Contenu 2", 2L));
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

    @Test
    void recupererCommentaires() throws Exception {
        mockMvc.perform(get("/api/personnes/1/commentaires"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasItems()))
                .andExpect(jsonPath("$[0].id", isA(String.class)))
                .andExpect(jsonPath("$[0].contenu", isA(String.class)))
                .andExpect(jsonPath("$[0].idAuteur", isA(Integer.class)));
    }

    @Test
    void ajouterCommentaire() throws Exception {
        mockMvc.perform(post("/api/personnes/1/commentaires")
                        .contentType(MediaType.valueOf("application/json;charset=UTF-8"))
                        .content("{\"contenu\" : \"Contenu 1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", isA(String.class)))
                .andExpect(jsonPath("$.contenu", isA(String.class)))
                .andExpect(jsonPath("$.idAuteur", isA(Integer.class)));
    }
}
