package br.com.dbserver.bancoapi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
@EqualsAndHashCode
@NoArgsConstructor
public class Conta implements Serializable {

    public static final String TIPO_CONTA_DEPOSITO = "Deposito";
    public static final String TIPO_CONTA_SAQUE = "Saque";
    public static final String TIPO_CONTA_BLOQUEIODESBLOQUEIO = "Bloqueio/Desbloqueio";
    public static final String TIPO_CONTA_TRANSFERENCIACREDITO = "Transferencia Credito";
    public static final String TIPO_CONTA_TRANSFERENCIADEBITO= "Transferencia Debito";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int agencia;
    private Long numero;
    private LocalDate dataCriacaoConta;
    private double saldo;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codigo_cliente")
    @JsonBackReference
    private Cliente cliente;
    private Boolean bloqueio;

    public Conta(Conta conta) {

    }
}
