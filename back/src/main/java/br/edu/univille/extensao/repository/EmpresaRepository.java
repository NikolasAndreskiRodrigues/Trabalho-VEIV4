package br.edu.univille.extensao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.univille.extensao.entity.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
}
