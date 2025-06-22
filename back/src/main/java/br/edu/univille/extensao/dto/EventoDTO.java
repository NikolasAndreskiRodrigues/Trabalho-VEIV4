package br.edu.univille.extensao.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class EventoDTO {
    private Long id;
    private String nome;
    private String cidade;
    private String categoria;
    private LocalDate data;
    private boolean destaque;
    private Long empresaId;
}