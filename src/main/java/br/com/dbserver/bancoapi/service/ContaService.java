package br.com.dbserver.bancoapi.service;

import br.com.dbserver.bancoapi.controller.dto.ContaRequest;
import br.com.dbserver.bancoapi.controller.dto.ContaResponse;
import br.com.dbserver.bancoapi.controller.dto.SaldoContaDTO;
import br.com.dbserver.bancoapi.model.Conta;
import br.com.dbserver.bancoapi.repository.ClienteRepository;
import br.com.dbserver.bancoapi.repository.ContaRepository;
import br.com.dbserver.bancoapi.repository.LogTransacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContaService {

    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ContaRepository contaRepository;
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private LogTransacaoService logTransacaoService;
    @Autowired
    private LogTransacaoRepository logTransacaoRepository;

    private void validaCliente (ContaRequest novaContaDTO) {
        clienteService.buscarClientePeloCodigo(novaContaDTO.getCliente().getId());
    }

    public ContaResponse salvar(ContaRequest novaContaDTO) {
        validaCliente(novaContaDTO);
        Conta conta = novaContaDTO.converterConta();
        return new ContaResponse(contaRepository.save(conta));
    }

    public Optional<Conta> despositoPorConta(Long contaDep, double vlrDeposito){
        return contaRepository.findByNumero(contaDep)
                .filter(Conta::isNaoBloqueado)
                .map(conta -> {
                    conta.setSaldo(conta.getSaldo() + vlrDeposito);
                    contaRepository.save(conta);
                    logTransacaoService.salvaLogTransacao(conta, Conta.TIPO_CONTA_DEPOSITO);
                    return Optional.of(conta);
                })
                .orElse(Optional.empty());
    }

    public Optional<Conta> saquePorConta(Long contaSaq, double vlrSaque) {
        return contaRepository.findByNumero(contaSaq)
                .filter(Conta::isNaoBloqueado)
                .map(conta -> {
                    conta.setSaldo(conta.getSaldo() - vlrSaque);
                    contaRepository.save(conta);
                    logTransacaoService.salvaLogTransacao(conta, Conta.TIPO_CONTA_SAQUE);
                    return  Optional.of(conta);
                })
                .orElse(Optional.empty());
    }

    public Optional<Conta> atualizarBloqueio(Long contaBlq, Boolean bloqueio) {
        return  contaRepository.findByNumero(contaBlq)
                .map(conta -> {
                    conta.setBloqueio(bloqueio);
                    contaRepository.save(conta);
                    logTransacaoService.salvaLogTransacao(conta, Conta.TIPO_CONTA_BLOQUEIODESBLOQUEIO);
                    return Optional.of(conta);
                })
                .orElse(Optional.empty());
    }

    public Optional<Conta> buscaSaldoPorConta(Long conta) {
        Optional<Conta> contaSalva = contaRepository.findByNumero(conta);
        if (contaSalva.isEmpty()) {
                //TODO criar exception throw new EmptyResultDataAccessException(1);
        }
        return contaSalva;
    }

    public SaldoContaDTO somaTotal(){
        SaldoContaDTO somaSaldo = new SaldoContaDTO();
        List<Conta> streamContas = contaRepository.findAll();
        somaSaldo.setSaldo(streamContas
                                    .stream()
                                    .mapToDouble(Conta::getSaldo)
                                    .sum());
        return somaSaldo;
    }

    public Conta transferenciaEntreContas(Long contaDe, Long contaPara, double vlrTransferencia) {

        Conta contaOrigem = transferir(contaDe, vlrTransferencia, Conta.TIPO_CONTA_TRANSFERENCIADEBITO);
        transferir(contaPara, vlrTransferencia, Conta.TIPO_CONTA_TRANSFERENCIACREDITO);

        return contaOrigem;
    }

    public Conta transferir(Long contaDe, double vlrTransferencia, String tipoConta) {

            Conta contaTransfDe = contaRepository.findByNumero(contaDe)
                    .filter(Conta::isNaoBloqueado)
                    .orElseThrow();

            double debitoCreditoValor = 0;
            if(tipoConta == Conta.TIPO_CONTA_TRANSFERENCIADEBITO) {
                debitoCreditoValor = contaTransfDe.getSaldo() - vlrTransferencia;
            }
            else if(tipoConta == Conta.TIPO_CONTA_TRANSFERENCIACREDITO) {
                debitoCreditoValor = contaTransfDe.getSaldo() + vlrTransferencia;
            }

            contaTransfDe.setSaldo(debitoCreditoValor);

            contaRepository.save(contaTransfDe);

            logTransacaoService.salvaLogTransacao(contaTransfDe.getId(),contaTransfDe.getDataCriacaoConta(),
                    contaTransfDe.getNumero(),contaTransfDe.getSaldo(), tipoConta);

            return  contaTransfDe;

    }
}
