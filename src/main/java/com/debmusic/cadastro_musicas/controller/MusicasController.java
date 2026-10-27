package com.debmusic.cadastro_musicas.controller;
import com.debmusic.cadastro_musicas.business.MusicasService;
import com.debmusic.cadastro_musicas.infrastructure.entitys.Musicas;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/musicas")
public class MusicasController {

    private final MusicasService service;

    public MusicasController(MusicasService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody Musicas musica) {
        service.salvarMusica(musica);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{nome}")
    public ResponseEntity<Musicas> buscarPorNome(@PathVariable String nome) {
        return ResponseEntity.ok(service.buscarPorNome(nome));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> atualizarPorId(
            @PathVariable Integer id,
            @RequestBody Musicas musica) {

        service.atualizarMusicaPorId(id, musica);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{nome}")
    public ResponseEntity<Void> deletarPorNome(@PathVariable String nome) {
        service.deletarPorNome(nome);
        return ResponseEntity.noContent().build();
    }
}