package br.com.dbserver.bancoapi.controller.dto;

import br.com.dbserver.bancoapi.model.Cliente;
import br.com.dbserver.bancoapi.model.Conta;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;

@Getter
public class ContaDTO {

    private Long id;
    private int agencia;
    private Long conta;
    private LocalDate dataCriacaoConta;
    private double saldo;
    private boolean bloqueio;

    public ContaDTO(Conta contaSalva) {
        this.id = contaSalva.getId();
        this.agencia = contaSalva.getAgencia();
        this.conta = contaSalva.getConta();
        this.dataCriacaoConta = contaSalva.getDataCriacaoConta();
        this.saldo = contaSalva.getSaldo();
        this.bloqueio = contaSalva.getBloqueio();
    }
}
