package br.edu.univille.extensao.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

import br.edu.univille.extensao.entity.Empresa;
import br.edu.univille.extensao.repository.EmpresaRepository;

@RestController
@RequestMapping("/api/empresas")
@CrossOrigin
public class EmpresaController {

    @Autowired
    private EmpresaRepository empresaRepository;

    @PostMapping
    public Empresa cadastrar(@RequestBody Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    @GetMapping
    public List<Empresa> listar() {
        return empresaRepository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Empresa> getEmpresa(@PathVariable Long id) {
        return empresaRepository.findById(id);
    }

    @PutMapping("/{id}")
    public Empresa updateEmpresa(@PathVariable Long id, @RequestBody Empresa empresa) {
        empresa.setId(id);
        return empresaRepository.save(empresa);
    }

    @DeleteMapping("/{id}")
    public void deleteEmpresa(@PathVariable Long id) {
        empresaRepository.deleteById(id);
    }

    @GetMapping("/home")
    public Empresa home() {
        return new Empresa();
    }

    @PutMapping("/{id}/perfil")
    public Empresa atualizarPerfil(@PathVariable Long id, @RequestBody Empresa empresaAtualizada) {
        Empresa empresa = empresaRepository.findById(id).orElseThrow();
        empresa.setNome(empresaAtualizada.getNome());
        empresa.setEmail(empresaAtualizada.getEmail());
        empresa.setCnpj(empresaAtualizada.getCnpj());
        empresa.setTelefone(empresaAtualizada.getTelefone());
        empresa.setDescricao(empresaAtualizada.getDescricao());
        empresa.setEndereco(empresaAtualizada.getEndereco());
        empresa.setFotoLogo(empresaAtualizada.getFotoLogo());
        empresa.setLatitude(empresaAtualizada.getLatitude());
        empresa.setLongitude(empresaAtualizada.getLongitude());
        return empresaRepository.save(empresa);
    }

    @PostMapping("/{id}/upload-foto")
    public ResponseEntity<?> uploadFotoLogo(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            String pasta = "back/src/main/resources/static/foto/empresa/";
            Files.createDirectories(Paths.get(pasta));
            String nomeArquivo = id + "_" + file.getOriginalFilename();
            Path caminho = Paths.get(pasta + nomeArquivo);
            Files.write(caminho, file.getBytes());

            Empresa empresa = empresaRepository.findById(id).orElseThrow();
            empresa.setFotoLogo("/foto/empresa/" + nomeArquivo);
            empresaRepository.save(empresa);

            return ResponseEntity.ok().body("Foto enviada com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao fazer upload");
        }
    }
}
