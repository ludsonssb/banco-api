package br.com.dbserver.bancoapi.controller;

import br.com.dbserver.bancoapi.controller.dto.*;
import br.com.dbserver.bancoapi.model.Cliente;
import br.com.dbserver.bancoapi.model.Conta;
import br.com.dbserver.bancoapi.repository.ContaRepository;
import br.com.dbserver.bancoapi.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("contas")
public class ContaController {

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private ContaService contaService;

    //Implementar path que realiza a criação de uma conta
    //POST
    @PostMapping
    public ContaDTO cadastrar(@RequestBody NovaContaDTO novaConta) {
        ContaDTO contaSalva = contaService.salvarConta(novaConta);
        return contaSalva;
    }

    //Implementar path que realiza operação de deposito em uma conta
    //PUT
    @PutMapping("/deposito")
    Optional<Conta> depositoEmConta(@RequestParam Long conta, @RequestParam double vlrDeposito){
        return contaService.despositoPorConta(conta,vlrDeposito);
    }

    //Implementar path que realiza operação de consulta de saldo em uma determinada conta
    //GET
    @GetMapping("{conta}")
    public ContaDTO saldoPorConta(@PathVariable Long conta) {
        Optional<Conta> saldoConta = contaService.buscaSaldoPorConta(conta);
        return saldoConta.map(ContaDTO::new).orElse(null);
    }

    //Implementar path que realiza operação de saque em uma conta
    //PUT
    @PutMapping("/saque")
    Optional<Conta> saqueEmConta(@RequestParam Long conta, @RequestParam double vlrSaque){
        return contaService.saquePorConta(conta,vlrSaque);
    }

    //Implementar path que realiza o bloqueio de uma conta
    //PUT

    //Implementar path que recupera o extrato de transações de uma conta
    //GET

    //Implementar path que realiza a transferencia entre contas
    //PUT






}
