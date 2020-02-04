package br.com.dbserver.bancoapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@EqualsAndHashCode
public class Conta implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int agencia;
    private Long conta;
    private LocalDate dataCriacaoConta;
    private double saldo;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_cliente")
    @JsonBackReference
    private Cliente cliente;
    private Boolean bloqueio;


}
