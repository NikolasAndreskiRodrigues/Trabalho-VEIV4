package br.edu.univille.extensao.dto;

import lombok.Data;

@Data
public class UsuarioUpdateDTO {
    private String nome;
    private String email;
    private String username;
    private String senha;
}
