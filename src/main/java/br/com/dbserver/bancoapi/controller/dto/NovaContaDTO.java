package br.com.dbserver.bancoapi.controller.dto;

import br.com.dbserver.bancoapi.model.Cliente;
import br.com.dbserver.bancoapi.model.Conta;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class NovaContaDTO {

    private Long id;
    private int agencia;
    private Long conta;
    private double saldo;
    private boolean bloqueio;
    private LocalDate dataCriacaoConta;
    private Cliente cliente;

    public Conta converterConta() {
        Conta conta = new Conta();
        conta.setAgencia(this.agencia);
        conta.setConta(this.conta);
        conta.setDataCriacaoConta(this.dataCriacaoConta);
        conta.setSaldo(this.saldo);
        conta.setCliente(this.cliente);
        conta.setBloqueio(this.bloqueio);

        return conta;
    }
}
