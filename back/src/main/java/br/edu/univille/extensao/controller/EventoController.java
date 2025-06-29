package br.edu.univille.extensao.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import br.edu.univille.extensao.entity.Evento;
import br.edu.univille.extensao.repository.EventoRepository;
import br.edu.univille.extensao.repository.EmpresaRepository;
import br.edu.univille.extensao.entity.Empresa;

@RestController
@RequestMapping("/api/eventos")
@CrossOrigin
public class EventoController {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @PostMapping
    public Evento criar(@RequestBody Evento evento) {
        if (evento.getEmpresaId() != null) {
            Empresa empresa = empresaRepository.findById(evento.getEmpresaId()).orElse(null);
            evento.setEmpresa(empresa);
        }
        return eventoRepository.save(evento);
    }

    @GetMapping
    public List<Evento> listar() {
        return eventoRepository.findAll();
    }

    @GetMapping("/{id}")
    public Evento getEvento(@PathVariable Long id) {
        return eventoRepository.findById(id).orElse(null);
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
        // TODO: Implementar busca por eventos do usuário
        return Page.empty();
    }

    @GetMapping("/calendario/empresa/{id}")
    public Page<Evento> calendarioEmpresa(@PathVariable Long id, Pageable pageable) {
        // TODO: Implementar busca por eventos da empresa
        return Page.empty();
    }

    @PostMapping("/{id}/upload-foto")
    public ResponseEntity<?> uploadFotoEvento(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            String pasta = "back/src/main/resources/static/foto/evento/";
            Files.createDirectories(Paths.get(pasta));

            String nomeArquivo = id + "_" + file.getOriginalFilename();
            Path caminho = Paths.get(pasta + nomeArquivo);
            Files.write(caminho, file.getBytes());

            Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));

            evento.setFoto("/foto/evento/" + nomeArquivo); // Caminho para acessar via URL
            eventoRepository.save(evento);

            return ResponseEntity.ok().body("Foto enviada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao fazer upload: " + e.getMessage());
        }
    }

}
