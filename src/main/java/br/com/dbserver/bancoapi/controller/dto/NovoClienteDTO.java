package br.com.dbserver.bancoapi.controller.dto;

import br.com.dbserver.bancoapi.model.Cliente;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class NovoClienteDTO {

    private String cpf;
    private String nome;

    public Cliente converter() {
        Cliente cliente = new Cliente();
        cliente.setCpf(this.cpf);
        cliente.setDataCadastro(LocalDate.now());
        cliente.setNome(this.nome);

        return cliente;

    }
}
