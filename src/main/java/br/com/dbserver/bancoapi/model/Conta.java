package br.com.dbserver.bancoapi.model;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.Getter;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Entity
@Data
public class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int agencia;
    private Long conta;
    private LocalDate dataCriacaoConta;
    private double saldo;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "codigo_cliente")
    private Cliente cliente;
    private Boolean bloqueio;


}
