package br.edu.univille.extensao.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Empresa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String cnpj;
    private String email;
    private String telefone;
    private String descricao;
    private String senha;   
    private String endereco;
    private String fotoLogo; // Caminho da imagem/logo da empresa
}
