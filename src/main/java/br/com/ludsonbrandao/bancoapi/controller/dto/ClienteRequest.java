package br.com.ludsonbrandao.bancoapi.controller.dto;

import br.com.ludsonbrandao.bancoapi.model.Cliente;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ClienteRequest {

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
