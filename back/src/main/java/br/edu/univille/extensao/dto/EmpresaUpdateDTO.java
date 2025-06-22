package br.edu.univille.extensao.dto;

import lombok.Data;

@Data
public class EmpresaUpdateDTO {
    private String nome;
    private String email;
    private String cnpj;
    private String senha;
}
