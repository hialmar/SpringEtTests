package com.example.demospringcours.dao;

import com.example.demospringcours.modeles.Personne;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PersonneRepoTest {

    @Autowired
    PersonneRepo personneRepo;

    @BeforeEach
    void setUp() {
        assertNotNull(personneRepo);
        personneRepo.save(new Personne(1L, "Dupond", "Jean", 30));
        personneRepo.save(new Personne(2L, "Dupond", "Eric", 35));
        personneRepo.save(new Personne(3L, "Durand", "Jean", 30));
        personneRepo.save(new Personne(4L, "Dupond", "Jeanne", 35));
    }

    @Test
    void findByNomAndPrenom() {
        Collection<Personne> collection = personneRepo.findByNomAndPrenom("Dupond", "Jean");
        assertEquals(1, collection.size());
        Personne personne = collection.iterator().next();
        assertEquals("Dupond", personne.getNom());
        assertEquals("Jean", personne.getPrenom());
        assertEquals(30, personne.getAge());
    }

    @Test
    void findByNom() {
        Collection<Personne> collection = personneRepo.findByNom("Dupond");
        assertEquals(3, collection.size());
        assertEquals("Dupond", collection.iterator().next().getNom());
    }

    @Test
    void findByAge() {
        Collection<Personne> collection = personneRepo.findByAge(35);
        assertEquals(2, collection.size());
        assertEquals(35, collection.iterator().next().getAge());
    }

    @Test
    void testsPourCouverture() {
        Personne personne = new Personne();
        personne.setNom("Dupond");
        personne.setPrenom("Jean");
        personne.setAge(30);
        assertEquals("Dupond", personne.getNom());
        assertEquals("Jean", personne.getPrenom());
        assertEquals(30, personne.getAge());
        assertEquals("Personne(id=null, nom=Dupond, prenom=Jean, age=30)", personne.toString());
        Personne personne1 = new Personne(1L, "Dupond", "Jean", 30);
        Personne personne2 = new Personne(1L, "Dupond", "Jean", 30);
        Personne personne3 = new Personne(2L, "Dupond", "Jean", 30);
        assertEquals(personne1, personne2);
        assertNotEquals(personne1, personne3);
        assertEquals(personne1.hashCode(), personne2.hashCode());
    }
}