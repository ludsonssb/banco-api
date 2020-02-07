package br.com.dbserver.bancoapi.controller.dto;

import br.com.dbserver.bancoapi.model.Cliente;
import br.com.dbserver.bancoapi.model.Conta;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ClienteSaldoContaDTO {

    private Long id;
    private String cpf;
    private String nome;
    private LocalDate dataCadastro;
    private List<Double> saldos;

    public ClienteSaldoContaDTO(Cliente clienteSalvo) {

        this.id = clienteSalvo.getId();
        this.cpf = clienteSalvo.getCpf();
        this.nome = clienteSalvo.getNome();
        this.dataCadastro = clienteSalvo.getDataCadastro();
        this.saldos = clienteSalvo.getContas().stream().map(Conta::getSaldo).collect(Collectors.toList());
    }
}
