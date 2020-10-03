package br.com.ludsonbrandao.bancoapi.controller.dto;

import br.com.ludsonbrandao.bancoapi.model.Cliente;
import br.com.ludsonbrandao.bancoapi.model.Conta;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ClienteIdContaDTO {

    private Long id;
    private String cpf;
    private String nome;
    private LocalDate dataCadastro;
    private List<Long> contas;

    public ClienteIdContaDTO(Cliente clienteSalvo) {

        this.id = clienteSalvo.getId();
        this.cpf = clienteSalvo.getCpf();
        this.nome = clienteSalvo.getNome();
        this.dataCadastro = clienteSalvo.getDataCadastro();
        this.contas = clienteSalvo.getContas().stream().map(Conta::getId).collect(Collectors.toList());
    }
}
