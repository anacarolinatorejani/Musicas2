package com.debmusic.cadastro_musicas.business;

import com.debmusic.cadastro_musicas.infrastructure.entitys.Musicas;
import com.debmusic.cadastro_musicas.infrastructure.repository.MusicasRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MusicasService {

    private final MusicasRepository repository;

    public MusicasService(MusicasRepository repository) {
        this.repository = repository;
    }

    public List<Musicas> listarMusicas() {
        return repository.findAll();
    }

    public Musicas salvarMusica(Musicas musica) {
        return repository.save(musica);
    }

    public Musicas buscarPorNome(String nome) {
        return repository.findByNome(nome)
                .orElseThrow(() ->
                        new RuntimeException("Nome não encontrado"));
    }

    @Transactional
    public void deletarPorNome(String nome) {
        repository.deleteByNome(nome);
    }

    public Musicas atualizarMusicaPorId(Integer id, Musicas musica) {

        Musicas musicaEntity = repository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Música não encontrada"));

        Musicas musicaAtualizada = Musicas.builder()
                .id(musicaEntity.getId())
                .nome(musica.getNome() != null
                        ? musica.getNome()
                        : musicaEntity.getNome())
                .artista(musica.getArtista() != null
                        ? musica.getArtista()
                        : musicaEntity.getArtista())
                .genero(musica.getGenero() != null
                        ? musica.getGenero()
                        : musicaEntity.getGenero())
                .ano(musica.getAno() != null
                        ? musica.getAno()
                        : musicaEntity.getAno())
                .build();

        return repository.save(musicaAtualizada);
    }
}