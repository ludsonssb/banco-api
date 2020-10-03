package br.com.ludsonbrandao.bancoapi.controller.dto;

import br.com.ludsonbrandao.bancoapi.model.Cliente;
import br.com.ludsonbrandao.bancoapi.model.Conta;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ContaRequest {

    private Long id;
    private int agencia;
    private Long conta;
    private double saldo;
    private boolean bloqueio;
    private Cliente cliente;

    public Conta converterConta() {
        Conta conta = new Conta();
        conta.setAgencia(this.agencia);
        conta.setNumero(this.conta);
        conta.setDataCriacaoConta((LocalDate.now()));
        conta.setSaldo(this.saldo);
        conta.setCliente(this.cliente);
        conta.setBloqueio(this.bloqueio);

        return conta;
    }
}
