package br.com.dbserver.bancoapi.model;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cpf;
    private LocalDate dataCadastro;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Conta> conta;

    public Cliente() {
    }
}
