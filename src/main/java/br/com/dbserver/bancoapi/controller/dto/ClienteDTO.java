package br.com.dbserver.bancoapi.controller.dto;

import br.com.dbserver.bancoapi.model.Cliente;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ClienteDTO {

    private Long id;
    private String cpf;
    private String nome;
    private LocalDate dataCadastro;

    public ClienteDTO(Cliente clienteSalvo) {

        this.id = clienteSalvo.getId();
        this.cpf = clienteSalvo.getCpf();
        this.nome = clienteSalvo.getNome();
        this.dataCadastro = clienteSalvo.getDataCadastro();

    }

}