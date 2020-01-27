package br.com.dbserver.bancoapi.controller.dto;

import br.com.dbserver.bancoapi.model.Cliente;
import br.com.dbserver.bancoapi.model.Conta;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ContaDTO {

    private Long id;
    private Long agencia;
    private Long conta;
    private LocalDate dataCriacaoConta;
    private double saldo;

    public ContaDTO(Conta contaSalva) {
        this.id = contaSalva.getId();
        this.agencia = contaSalva.getAgencia();
        this.conta = contaSalva.getConta();
        this.dataCriacaoConta = contaSalva.getDataCriacaoConta();
        this.saldo = contaSalva.getSaldo();
    }
}
