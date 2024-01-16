package com.example.demospringcours.metier;

import com.example.demospringcours.dao.PersonneRepo;
import com.example.demospringcours.entites.Personne;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class ServicePersonneTest {
    ServicePersonne servicePersonne;
    PersonneRepo personneRepo;
    Personne personne1;
    Personne personne2;
    @BeforeEach
    void setUp() {
        personneRepo = mock(PersonneRepo.class);
        assertNotNull(personneRepo);
        servicePersonne = new ServicePersonne(personneRepo);
        assertNotNull(servicePersonne);
        personne1 = new Personne(1L, "Dupond", "Jean", 30);
        personne2 = new Personne(2L, "Dupond", "Edouard", 35);
    }

    @Test
    void ajouterPersonne() {
        when(personneRepo.save(any(Personne.class))).thenReturn(personne1);
        Personne personne = servicePersonne.ajouterPersonne(personne1);
        assertEquals(personne1.getId(), personne.getId());
    }

    @Test
    void recupererPersonne() {
        when(personneRepo.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(personne1));
        Personne personne = servicePersonne.recupererPersonne(1L);
        assertEquals(personne1.getId(), personne.getId());
    }

    @Test
    void recupererPersonnes() {
        when(personneRepo.findAll()).thenReturn(java.util.Arrays.asList(personne1, personne2));
        Iterable<Personne> personnes = servicePersonne.recupererPersonnes();
        assertEquals(2, personnes.spliterator().getExactSizeIfKnown());
    }

    @Test
    void supprimerPersonne() {
        servicePersonne.supprimerPersonne(1L);
    }

    @Test
    void modifierPersonne() {
        when(personneRepo.save(any(Personne.class))).thenReturn(personne2);
        Personne personne = servicePersonne.modifierPersonne(personne1);
        assertEquals(personne2.getId(), personne.getId());
    }

    @Test
    void recupererPersonnesParNom() {
        when(personneRepo.findByNom(anyString())).thenReturn(java.util.Arrays.asList(personne1, personne2));
        Iterable<Personne> personnes = servicePersonne.recupererPersonnesParNom("Dupond");
        assertEquals(2, personnes.spliterator().getExactSizeIfKnown());
    }

    @Test
    void recupererPersonnesParAge() {
        when(personneRepo.findByAge(anyInt())).thenReturn(java.util.Arrays.asList(personne1));
        Iterable<Personne> personnes = servicePersonne.recupererPersonnesParAge(30);
        assertEquals(1, personnes.spliterator().getExactSizeIfKnown());
    }

    @Test
    void recupererPersonnesParNomEtPrenom() {
        when(personneRepo.findByNomAndPrenom(anyString(), anyString())).thenReturn(java.util.Arrays.asList(personne1));
        Iterable<Personne> personnes = servicePersonne.recupererPersonnesParNomEtPrenom("Dupond", "Jean");
        assertEquals(1, personnes.spliterator().getExactSizeIfKnown());
    }
}