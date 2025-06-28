package br.edu.univille.extensao.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.univille.extensao.entity.Evento;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
    Page<Evento> findByDestaqueTrue(Pageable pageable);
    Page<Evento> findByOrderByDataDesc(Pageable pageable);
    Page<Evento> findByCidadeContainingAndCategoriaContaining(
        String cidade, String categoria, Pageable pageable
    );
    Page<Evento> findByCidadeContainingAndCategoriaContainingAndData(
        String cidade, String categoria, LocalDate data, Pageable pageable
    );
    Page<Evento> findByData(LocalDate localDate, Pageable pageable);
}
