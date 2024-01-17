package com.example.demospringcours.exposition;

import com.example.demospringcours.modeles.Commentaire;
import com.example.demospringcours.modeles.Personne;
import com.example.demospringcours.metier.ServicePersonne;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/personnes")
public class ControlleurPersonne {

    ServicePersonne servicePersonne;

    public ControlleurPersonne(ServicePersonne servicePersonne) {
        this.servicePersonne = servicePersonne;
    }

    @PostMapping
    public Personne ajouterPersonne(@RequestBody Personne personne) {
       return servicePersonne.ajouterPersonne(personne);
    }

    @GetMapping("/{id}")
    public Personne recupererPersonne(@PathVariable Long id) {
        return servicePersonne.recupererPersonne(id);
    }

    @DeleteMapping("/{id}")
    public void supprimerPersonne(@PathVariable Long id) {
        servicePersonne.supprimerPersonne(id);
    }

    @PutMapping("/{id}")
    public Personne modifierPersonne(@PathVariable Long id, @RequestBody Personne personne) {
        personne.setId(id);
        return servicePersonne.modifierPersonne(personne);
    }

    @GetMapping
    public Iterable<Personne> recupererPersonnes() {
        return servicePersonne.recupererPersonnes();
    }

    @GetMapping("/{id}/commentaires")
    public Iterable<Commentaire> recupererCommentaires(@PathVariable Long id) {
        Personne personne = servicePersonne.recupererPersonne(id);
        return servicePersonne.recupererCommentaires(personne);
    }

    @PostMapping("/{id}/commentaires")
    public Commentaire ajouterCommentaire(@PathVariable Long id, @RequestBody Commentaire commentaire) {
        Personne personne = servicePersonne.recupererPersonne(id);
        return servicePersonne.ajouterCommentaire(personne, commentaire);
    }

}
