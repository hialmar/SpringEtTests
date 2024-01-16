package com.example.demospringcours.dao;

import com.example.demospringcours.entites.Personne;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface PersonneRepo extends CrudRepository<Personne, Long> {
    Collection<Personne> findByNomAndPrenom(String nom, String prenom);

    Collection<Personne> findByNom(String nom);

    Collection<Personne> findByAge(int age);
}
