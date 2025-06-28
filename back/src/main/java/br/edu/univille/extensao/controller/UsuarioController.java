package br.edu.univille.extensao.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import br.edu.univille.extensao.entity.Evento;
import br.edu.univille.extensao.entity.Usuario;
import br.edu.univille.extensao.repository.UsuarioRepository;
import br.edu.univille.extensao.repository.EventoRepository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private EventoRepository eventoRepository;

    @PostMapping("/cadastro")
    public Usuario cadastrar(@RequestBody Usuario usuario) {
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new RuntimeException("E-mail já cadastrado");
        }
        return usuarioRepository.save(usuario);
    }

    @PostMapping("/login")
    public Usuario login(@RequestBody Usuario usuario) {
        Optional<Usuario> user = usuarioRepository.findByEmail(usuario.getEmail());
        if (user.isPresent() && user.get().getSenha().equals(usuario.getSenha())) {
            return user.get();
        }
        throw new RuntimeException("Usuário ou senha inválidos");
    }

    @GetMapping
        public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    @GetMapping("/{id}")
        public Usuario buscarPorId(@PathVariable Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    @PutMapping("/{id}")
        public Usuario atualizar(@PathVariable Long id, @RequestBody Usuario usuarioAtualizado) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario != null) {
            usuario.setNome(usuarioAtualizado.getNome());
            usuario.setEmail(usuarioAtualizado.getEmail());
            usuario.setSenha(usuarioAtualizado.getSenha());
        return usuarioRepository.save(usuario);
        }
        return null;
    }

    @PutMapping("/{id}/perfil")
    public Usuario atualizarPerfil(@PathVariable Long id, @RequestBody Usuario usuarioAtualizado) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow();
        usuario.setNome(usuarioAtualizado.getNome());
        usuario.setEmail(usuarioAtualizado.getEmail());
        usuario.setSenha(usuarioAtualizado.getSenha());
        usuario.setFotoPerfil(usuarioAtualizado.getFotoPerfil());
        return usuarioRepository.save(usuario);
    }

    @DeleteMapping("/{id}")
        public void deletar(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
    }

    @PostMapping("/{usuarioId}/participar/{eventoId}")
    public Usuario participarEvento(@PathVariable Long usuarioId, @PathVariable Long eventoId) {
        Usuario usuario = usuarioRepository.findById(usuarioId).orElseThrow();
        Evento evento = eventoRepository.findById(eventoId).orElseThrow();
        if (!usuario.getEventosParticipando().contains(evento)) {
            usuario.getEventosParticipando().add(evento);
            usuarioRepository.save(usuario);
        }
        return usuario;
    }

    @PostMapping("/{id}/upload-foto")
public ResponseEntity<?> uploadFotoPerfil(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
    try {
        String pasta = "uploads/usuarios/";
        Files.createDirectories(Paths.get(pasta));
        String nomeArquivo = id + "_" + file.getOriginalFilename();
        Path caminho = Paths.get(pasta + nomeArquivo);
        Files.write(caminho, file.getBytes());

        Usuario usuario = usuarioRepository.findById(id).orElseThrow();
        usuario.setFotoPerfil("/" + pasta + nomeArquivo);
        usuarioRepository.save(usuario);

        return ResponseEntity.ok().body("Foto enviada com sucesso!");
    } catch (Exception e) {
        return ResponseEntity.badRequest().body("Erro ao fazer upload");
    }
}

}
