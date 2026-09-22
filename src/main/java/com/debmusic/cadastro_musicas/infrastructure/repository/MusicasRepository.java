package com.debmusic.cadastro_musicas.infrastructure.repository;

import com.debmusic.cadastro_musicas.infrastructure.entitys.Musicas;
import org.hibernate.internal.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicasRepository extends JpaRepository <Musicas, Integer> {
    Optional<Musicas> findByNome (String nome);
}
