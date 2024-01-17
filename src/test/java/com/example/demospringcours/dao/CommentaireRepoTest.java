package com.example.demospringcours.dao;

import com.example.demospringcours.modeles.Commentaire;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
class CommentaireRepoTest {

    @Autowired
    CommentaireRepo commentaireRepo;

    @BeforeEach
    void setUp() {
        assertNotNull(commentaireRepo);
        commentaireRepo.deleteAll();
        commentaireRepo.save(new Commentaire("1", "Contenu 1", 1L));
    }

    @Test
    void findByIdAuteur() {
        Collection<Commentaire> collection = commentaireRepo.findByIdAuteur(1L);
        assertEquals(1, collection.size());
        Commentaire commentaire = collection.iterator().next();
        assertEquals("Contenu 1", commentaire.getContenu());
        assertEquals(1L, commentaire.getIdAuteur());
    }

    @Test
    void testPourCouverture() {
        Commentaire commentaire = new Commentaire("1", "Contenu 1", 1L);
        commentaire.setId("2");
        assertEquals("2", commentaire.getId());
        commentaire.setContenu("Contenu 2");
        assertEquals("Contenu 2", commentaire.getContenu());
        commentaire.setIdAuteur(2L);
        assertEquals(2L, commentaire.getIdAuteur());
        System.out.println(commentaire);
        assertEquals("Commentaire(id=2, contenu=Contenu 2, idAuteur=2)", commentaire.toString());
    }
}