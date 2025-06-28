package br.edu.univille.extensao.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import br.edu.univille.extensao.entity.Evento;
import br.edu.univille.extensao.repository.EventoRepository;

@RestController
@RequestMapping("/api/eventos")
@CrossOrigin
public class EventoController {

    @Autowired
    private EventoRepository eventoRepository;

    @PostMapping
    public Evento criar(@RequestBody Evento evento) {
        return eventoRepository.save(evento);
    }

    @GetMapping
    public List<Evento> listar() {
        return eventoRepository.findAll();
    }

    @GetMapping("/destaques")
    public Page<Evento> destaques(Pageable pageable) {
        return eventoRepository.findByDestaqueTrue(pageable);
    }

    @GetMapping("/recentes")
    public Page<Evento> recentes(Pageable pageable) {
        return eventoRepository.findByOrderByDataDesc(pageable);
    }

    @GetMapping("/por-data")
    public Page<Evento> buscarPorData(@RequestParam String data) {
        return eventoRepository.findByData(LocalDate.parse(data));
    }

    @GetMapping("/filtrar")
    public Page<Evento> filtrar(
        @RequestParam(required = false, defaultValue = "") String cidade,
        @RequestParam(required = false, defaultValue = "") String categoria,
        @RequestParam(required = false) LocalDate data,
        Pageable pageable
    ) {
        if (data != null) {
            return eventoRepository.findByCidadeContainingAndCategoriaContainingAndData(
                cidade, categoria, data, pageable
            );
        } else {
            return eventoRepository.findByCidadeContainingAndCategoriaContaining(
                cidade, categoria, pageable
            );
        }
    }

    @GetMapping("/{id}")
    public Optional<Evento> getEvento(@PathVariable Long id) {
        return eventoRepository.findById(id);
    }


    @PostMapping
    public Evento criarEvento(@RequestBody Evento evento) {
        // Validações aqui
        return eventoRepository.save(evento);
    }

    @PutMapping("/{id}")
    public Evento editarEvento(@PathVariable Long id, @RequestBody Evento evento) {
        evento.setId(id);
        return eventoRepository.save(evento);
    }

    @DeleteMapping("/{id}")
    public void deletarEvento(@PathVariable Long id) {
        eventoRepository.deleteById(id);
    }

    @GetMapping("/calendario/usuario/{id}")
    public Page<Evento> calendarioUsuario(@PathVariable Long id, Pageable pageable) {
        // Implemente busca por eventos do usuário
        return Page.empty();
    }

    @GetMapping("/calendario/empresa/{id}")
    public Page<Evento> calendarioEmpresa(@PathVariable Long id, Pageable pageable) {
        // Implemente busca por eventos da empresa
        return Page.empty();
    }

    @PostMapping("/{id}/upload-foto")
    public ResponseEntity<?> uploadFotoEvento(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            String pasta = "uploads/eventos/";
            Files.createDirectories(Paths.get(pasta));
            String nomeArquivo = id + "_" + file.getOriginalFilename();
            Path caminho = Paths.get(pasta + nomeArquivo);
            Files.write(caminho, file.getBytes());

            Evento evento = eventoRepository.findById(id).orElseThrow();
            evento.setFoto("/" + pasta + nomeArquivo);
            eventoRepository.save(evento);

            return ResponseEntity.ok().body("Foto enviada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao fazer upload");
        }
    }

}
