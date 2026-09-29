package com.debmusic.cadastro_musicas.infrastructure.repository;

import com.debmusic.cadastro_musicas.infrastructure.entitys.Musicas;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MusicasRepository extends JpaRepository<Musicas, Integer> {

    Optional<Musicas> findByNome(String nome);

    void deleteByNome(String nome);
}