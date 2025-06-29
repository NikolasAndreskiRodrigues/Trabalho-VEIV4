package br.edu.univille.extensao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaginaController {

    @GetMapping("/")
    public String raiz() {
        return "index"; // templates/index.html
    }   

    @GetMapping("/paginainicial")
    public String paginainicial() {
        return "paginainicial"; // templates/paginainicial.html
    }

    @GetMapping("/logout")
    public String logout() {
        return "index"; // templates/index.html
    }

    @GetMapping("/homeeventos")
    public String homeeventos() {
        return "homeeventos"; // templates/homeeventos.html
    }

    @GetMapping("/calendario")
    public String calendario() {
        return "calendario"; // templates/calendario.html
    }

    @GetMapping("/adicionarevento")
    public String adicionaEvento() {
        return "adicionarevento"; // templates/adicionarevento.html
    }

    @GetMapping("/perfiluser")
    public String perfiluser() {
        return "perfiluser"; // templates/perfiluser.html
    }

    @GetMapping("/perfilempresarial")
    public String perfilempresarial() {
        return "perfilempresarial"; // templates/perfilempresarial.html
    }

    @GetMapping("/cadastrousuario")
    public String cadastrousuario() {
        return "cadastrousuario"; // templates/cadastrousuario.html
    }

    @GetMapping("/cadastroempresa")
    public String cadastroempresa() {
        return "cadastroempresa"; // templates/cadastroempresa.html
    }

    @GetMapping("/conferencia")
    public String conferencia() {
        return "conferencia"; // templates/conferencia.html
    }

    @GetMapping("/loginusuario")
    public String loginusuario() {
        return "loginusuario"; // templates/loginusuario.html
    }

    // Adicione outros métodos para outras páginas
}
