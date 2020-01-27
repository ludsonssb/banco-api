package br.com.dbserver.bancoapi.controller;

import br.com.dbserver.bancoapi.controller.dto.ClienteDTO;
import br.com.dbserver.bancoapi.controller.dto.ContaDTO;
import br.com.dbserver.bancoapi.controller.dto.NovaContaDTO;
import br.com.dbserver.bancoapi.controller.dto.NovoClienteDTO;
import br.com.dbserver.bancoapi.model.Cliente;
import br.com.dbserver.bancoapi.model.Conta;
import br.com.dbserver.bancoapi.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("contas")
public class ContaController {

    @Autowired
    private ContaRepository contaRepository;

    //POST
    @PostMapping
    public ContaDTO cadastrar(@RequestBody NovaContaDTO novaConta) {
        Conta conta = novaConta.converterConta();
        Conta contaSalva = contaRepository.save(conta);
        return new ContaDTO(contaSalva);
    }
    //PUT
    //GET
    //PUT
    //PUT
    //GET
    //PUT

}
