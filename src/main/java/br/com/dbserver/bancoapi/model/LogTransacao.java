package br.com.dbserver.bancoapi.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
@Data
public class LogTransacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(columnDefinition = "DATE")
    private LocalDate dataAlteracao;
    private Long idConta;
    private LocalDate dataCriacaoConta;
    private Long conta;
    private double saldo;
    private String tipoMovimentacao;
}
