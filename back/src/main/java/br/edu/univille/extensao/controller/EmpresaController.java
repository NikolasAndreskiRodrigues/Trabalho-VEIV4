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
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/login")
    public Empresa login(@RequestBody Empresa login) {
        return empresaRepository.findByEmailAndSenha(login.getEmail(), login.getSenha()).orElse(null);
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
}
