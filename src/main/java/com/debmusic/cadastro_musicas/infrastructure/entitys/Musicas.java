package com.debmusic.cadastro_musicas.infrastructure.entitys;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "musicas")
@Entity

public class Musicas {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "nome", unique = true)
    private String nome;
    @Column(name = "artista", unique = true)
    private String artista;
    @Column(name = "genero", unique = true)
    private String genero;
    @Column(name = "ano", unique = true)
    private String ano;
}
