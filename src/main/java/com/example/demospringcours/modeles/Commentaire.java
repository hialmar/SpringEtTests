package com.example.demospringcours.modeles;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Commentaire {
    @Id
    private String id;
    private String contenu;
    private long idAuteur;
}
