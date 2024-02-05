package com.example.demospringcours.metier;

import com.example.demospringcours.dao.CommentaireRepo;
import com.example.demospringcours.dao.PersonneRepo;
import com.example.demospringcours.modeles.Commentaire;
import com.example.demospringcours.modeles.Personne;
import org.springframework.stereotype.Service;

@Service
public class ServicePersonne {
    PersonneRepo personneRepo;
    CommentaireRepo commentaireRepo;

    public ServicePersonne(PersonneRepo personneRepo, CommentaireRepo commentaireRepo) {
        this.personneRepo = personneRepo;
        this.commentaireRepo = commentaireRepo;
    }
    public Personne ajouterPersonne(Personne personne) {
        return personneRepo.save(personne);
    }

    public Personne recupererPersonne(Long id) {
        return personneRepo.findById(id).orElseThrow();
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
    public Commentaire ajouterCommentaire(Personne personne, Commentaire commentaire) {
        commentaire.setIdAuteur(personne.getId());
        return commentaireRepo.save(commentaire);
    }
    public Iterable<Commentaire> recupererCommentaires(Personne personne) {
        return commentaireRepo.findByIdAuteur(personne.getId());
    }
}
