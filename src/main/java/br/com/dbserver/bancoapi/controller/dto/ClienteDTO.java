package br.com.dbserver.bancoapi.controller.dto;

import br.com.dbserver.bancoapi.model.Cliente;
import br.com.dbserver.bancoapi.model.Conta;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
public class ClienteDTO {

    private Long id;
    private String cpf;
    private String nome;
    private LocalDate dataCadastro;
    private List<Conta> conta;

    public ClienteDTO(Cliente clienteSalvo) {

        this.id = clienteSalvo.getId();
        this.cpf = clienteSalvo.getCpf();
        this.nome = clienteSalvo.getNome();
        this.dataCadastro = clienteSalvo.getDataCadastro();
        this.conta = clienteSalvo.getContas();

    }

}