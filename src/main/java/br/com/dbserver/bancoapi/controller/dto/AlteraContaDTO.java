package br.com.dbserver.bancoapi.controller.dto;

import lombok.Data;
import lombok.Getter;

@Data
public class AlteraContaDTO {

    private int agencia;
    private double saldo;
}
