package br.edu.univille.extensao.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Transient;
import lombok.Data;

@Data
@Entity
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String descricao;
    private String endereco;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private Integer capacidade;
    private String organizacao;
    private String cidade;
    private String foto; 
    private String acesso;
    private Double latitude;
    private Double longitude;
    @ManyToOne
    @JoinColumn(name = "empresa_id")
    private Empresa empresa;

    // Adicione este campo auxiliar (não persistente) para receber o ID no JSON:
    @Transient
    private Long empresaId;

    @ManyToMany(mappedBy = "eventosParticipando")
    private List<Usuario> participantes;
}
