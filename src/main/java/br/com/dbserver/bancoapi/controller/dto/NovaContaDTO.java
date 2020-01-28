package br.com.dbserver.bancoapi.controller.dto;

import br.com.dbserver.bancoapi.model.Cliente;
import br.com.dbserver.bancoapi.model.Conta;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class NovaContaDTO {

    private Long id;
    private Long agencia;
    private Long conta;
    private double saldo;
    private Cliente cliente;

    public Conta converterConta() {
        Conta conta = new Conta();
        conta.setAgencia(this.agencia);
        conta.setConta(this.conta);
        conta.setDataCriacaoConta(LocalDate.now());
        conta.setSaldo(this.saldo);
        conta.setCliente(this.cliente);

        return conta;
    }
}