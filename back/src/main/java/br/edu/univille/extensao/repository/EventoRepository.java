package br.edu.univille.extensao.repository;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.univille.extensao.entity.Evento;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {
}
