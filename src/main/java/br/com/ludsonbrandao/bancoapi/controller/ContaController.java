package br.com.ludsonbrandao.bancoapi.controller;

import br.com.ludsonbrandao.bancoapi.controller.dto.ContaRequest;
import br.com.ludsonbrandao.bancoapi.controller.dto.ContaResponse;
import br.com.ludsonbrandao.bancoapi.controller.dto.SaldoContaDTO;
import br.com.ludsonbrandao.bancoapi.model.Conta;
import br.com.ludsonbrandao.bancoapi.repository.ContaRepository;
import br.com.ludsonbrandao.bancoapi.service.ContaService;
import br.com.ludsonbrandao.bancoapi.service.LogTransacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("contas")
public class ContaController {

    @Autowired
    private ContaRepository contaRepository;
    @Autowired
    private ContaService contaService;
    @Autowired
    private LogTransacaoService logTransacaoService;

    //Implementar path que realiza a criação de uma conta
    //POST
    @PostMapping
    public ContaResponse cadastrar(@RequestBody ContaRequest novaConta) {
        ContaResponse contaSalva = contaService.salvar(novaConta);
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
    public ContaResponse saldoPorConta(@PathVariable Long conta) {
        Optional<Conta> saldoConta = contaService.buscaSaldoPorConta(conta);
        return saldoConta.map(ContaResponse::new).orElse(null);
    }

    //Implementar path que realiza operação de saque em uma conta
    //PUT
    @PutMapping("/saque")
    Optional<Conta> saqueEmConta(@RequestParam Long conta, @RequestParam double vlrSaque){
        return contaService.saquePorConta(conta,vlrSaque);
    }

    //Implementar path que realiza o bloqueio de uma conta
    //PUT
    @PutMapping("/{conta}/bloqueio")
    public void bloqueioConta(@PathVariable Long conta, @RequestBody Boolean bloqueio) {
        contaService.atualizarBloqueio(conta, bloqueio);
    }

    //Implementar path que recupera o extrato de transações de uma conta
    //GET
    @GetMapping
    public List<Conta> listar() {
        return contaRepository.findAll();
    }

    //Implementar path que realiza a transferencia entre contas
    //PUT
    @PutMapping("/transferencia")
    Conta saqueEmConta(@RequestParam Long contaDe, @RequestParam Long contaPara, @RequestParam double vlrTransferencia){
            return contaService.transferenciaEntreContas(contaDe,contaPara,vlrTransferencia);
    }

    @GetMapping("/somasaldos")
    public SaldoContaDTO soma(){
        return contaService.somaTotal();
    }




}
