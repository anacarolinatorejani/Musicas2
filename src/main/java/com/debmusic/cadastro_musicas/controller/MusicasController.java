package com.debmusic.cadastro_musicas.controller;

import com.debmusic.cadastro_musicas.infrastructure.entitys.Musicas;
import com.debmusic.cadastro_musicas.business.MusicasService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/musicas")
public class MusicasController {

    private final MusicasService service;

    public MusicasController(MusicasService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Musicas>> listarMusicas() {
        return ResponseEntity.ok(service.listarMusicas());
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<Musicas> buscarPorNome(@PathVariable String nome) {
        return ResponseEntity.ok(service.buscarPorNome(nome));
    }

    @PostMapping
    public ResponseEntity<Musicas> salvarMusica(@RequestBody Musicas musica) {
        return ResponseEntity.ok(service.salvarMusica(musica));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Musicas> atualizarMusica(
            @PathVariable Integer id,
            @RequestBody Musicas musica) {

        return ResponseEntity.ok(
                service.atualizarMusicaPorId(id, musica)
        );
    }

    @DeleteMapping("/nome/{nome}")
    public ResponseEntity<Void> deletarPorNome(@PathVariable String nome) {
        service.deletarPorNome(nome);
        return ResponseEntity.noContent().build();
    }
}