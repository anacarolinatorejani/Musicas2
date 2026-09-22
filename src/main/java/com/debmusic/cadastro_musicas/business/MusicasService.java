package com.debmusic.cadastro_musicas.business;

import com.debmusic.cadastro_musicas.infrastructure.entitys.Musicas;
import com.debmusic.cadastro_musicas.infrastructure.repository.MusicasRepository;
import org.springframework.stereotype.Service;

@Service
public class MusicasService {
    private final MusicasRepository repository;

    public MusicasService(MusicasRepository repository){
        this.repository = repository;
    }

    public void salvarmusicas(Musicas musicas){
        repository.saveAndFlush(musicas);
    }
}
