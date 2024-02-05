package com.example.demospringcours.exposition;

import com.example.demospringcours.modeles.Commentaire;
import com.example.demospringcours.modeles.Personne;
import com.example.demospringcours.metier.ServicePersonne;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.NoSuchElementException;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.isA;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ControlleurPersonne.class)
class ControlleurPersonneTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    ServicePersonne servicePersonne;

    @Test
    void ajouterPersonne() throws Exception {
        when(servicePersonne.ajouterPersonne(any(Personne.class))).thenReturn(new Personne(1L, "Test", "Jean", 30));

        mockMvc.perform(post("/api/personnes")
                        .contentType(MediaType.valueOf("application/json;charset=UTF-8"))
                        .content("{\"nom\" : \"Test\", \"prenom\":\"Jean\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", isA(Integer.class)))
                .andExpect(jsonPath("$.prenom", is("Jean")))
                .andExpect(jsonPath("$.nom", is("Test")));
    }

    @Test
    void recupererPersonne() throws Exception {
        when(servicePersonne.recupererPersonne(1L)).thenReturn(new Personne(1L, "Dupond", "Jean", 30));

        mockMvc.perform(get("/api/personnes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", isA(Integer.class)))
                .andExpect(jsonPath("$.prenom", is("Jean")))
                .andExpect(jsonPath("$.nom", is("Dupond")));

        when(servicePersonne.recupererPersonne(2L)).thenThrow(new NoSuchElementException());

        mockMvc.perform(get("/api/personnes/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void supprimerPersonne() throws Exception {
        mockMvc.perform(delete("/api/personnes/1"))
                .andExpect(status().isOk());

        verify(servicePersonne, times(1)).supprimerPersonne(1L);
    }

    @Test
    void modifierPersonne() throws Exception {
        when(servicePersonne.modifierPersonne(any(Personne.class))).thenReturn(new Personne(1L, "Test", "Jean", 30));

        mockMvc.perform(put("/api/personnes/1")
                        .contentType(MediaType.valueOf("application/json;charset=UTF-8"))
                        .content("{\"nom\" : \"Test\", \"prenom\":\"Jean\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", isA(Integer.class)))
                .andExpect(jsonPath("$.prenom", is("Jean")))
                .andExpect(jsonPath("$.nom", is("Test")));

        verify(servicePersonne, times(1)).modifierPersonne(any(Personne.class));
    }

    @Test
    void recupererPersonnes() throws Exception {
        when(servicePersonne.recupererPersonnes()).thenReturn(java.util.Arrays.asList(new Personne(1L, "Dupond", "Jean", 30)));

        mockMvc.perform(get("/api/personnes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasItems()))
                .andExpect(jsonPath("$[0].id", isA(Integer.class)))
                .andExpect(jsonPath("$[0].nom", isA(String.class)));
    }

    @Test
    void recupererCommentaires() throws Exception {
        when(servicePersonne.recupererPersonne(anyLong())).thenReturn(new Personne(1L, "Dupond", "Jean", 30));
        when(servicePersonne.recupererCommentaires(any(Personne.class))).thenReturn(java.util.Arrays.asList(new Commentaire("1", "Contenu 1", 1L)));

        mockMvc.perform(get("/api/personnes/1/commentaires"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasItems()))
                .andExpect(jsonPath("$[0].id", isA(String.class)))
                .andExpect(jsonPath("$[0].contenu", isA(String.class)))
                .andExpect(jsonPath("$[0].idAuteur", isA(Integer.class)));
    }

    @Test
    void ajouterCommentaire() throws Exception {
        when(servicePersonne.recupererPersonne(anyLong())).thenReturn(new Personne(1L, "Dupond", "Jean", 30));
        when(servicePersonne.ajouterCommentaire(any(Personne.class), any(Commentaire.class))).thenReturn(new Commentaire("1", "Contenu 1", 1L));

        mockMvc.perform(post("/api/personnes/1/commentaires")
                        .contentType(MediaType.valueOf("application/json;charset=UTF-8"))
                        .content("{\"contenu\" : \"Contenu 1\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", isA(String.class)))
                .andExpect(jsonPath("$.contenu", isA(String.class)))
                .andExpect(jsonPath("$.idAuteur", isA(Integer.class)));
    }
}