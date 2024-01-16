package com.example.demospringcours.metier;

import com.example.demospringcours.dao.PersonneRepo;
import com.example.demospringcours.entites.Personne;
import org.springframework.stereotype.Service;

@Service
public class ServicePersonne {
    PersonneRepo personneRepo;
    public ServicePersonne(PersonneRepo personneRepo) {
        this.personneRepo = personneRepo;
    }
    public Personne ajouterPersonne(Personne personne) {
        return personneRepo.save(personne);
    }

    public Personne recupererPersonne(Long id) {
        return personneRepo.findById(id).orElse(null);
    }

    public Iterable<Personne> recupererPersonnes() {
        return personneRepo.findAll();
    }

    public void supprimerPersonne(Long id) {
        personneRepo.deleteById(id);
    }

    public Personne modifierPersonne(Personne personne) {
        return personneRepo.save(personne);
    }

    public Iterable<Personne> recupererPersonnesParNom(String nom) {
        return personneRepo.findByNom(nom);
    }

    public Iterable<Personne> recupererPersonnesParAge(int age) {
        return personneRepo.findByAge(age);
    }

    public Iterable<Personne> recupererPersonnesParNomEtPrenom(String nom, String prenom) {
        return personneRepo.findByNomAndPrenom(nom, prenom);
    }

}
