package com.example.demospringcours.dao;

import com.example.demospringcours.modeles.Commentaire;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;

public interface CommentaireRepo extends CrudRepository<Commentaire, String> {
    Collection<Commentaire> findByIdAuteur(long idAuteur);
}
