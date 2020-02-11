package br.com.dbserver.bancoapi.service;

import br.com.dbserver.bancoapi.controller.dto.ContaDTO;
import br.com.dbserver.bancoapi.controller.dto.NovaContaDTO;
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

    private void validaCliente (NovaContaDTO novaContaDTO) {
        clienteService.buscarClientePeloCodigo(novaContaDTO.getCliente().getId());
    }

    public ContaDTO salvar(NovaContaDTO novaContaDTO) {
        validaCliente(novaContaDTO);
        Conta conta = novaContaDTO.converterConta();
        return new ContaDTO(contaRepository.save(conta));
    }

    public Optional<Conta> despositoPorConta(Long contaDep, double vlrDeposito){
        return contaRepository.findByNumero(contaDep)
                .filter(conta -> conta.getBloqueio() != true)
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
                .filter(conta -> conta.getBloqueio() != true)
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
        SaldoContaDTO alteraContaDTO = new SaldoContaDTO();
        List<Conta> streamContas = contaRepository.findAll();
        alteraContaDTO.setSaldo(streamContas.stream().mapToDouble(conta -> conta.getSaldo()).sum());
        return alteraContaDTO;
    }

    public Optional<Conta> transferenciaEntreContas(Long contaDe, Long contaPara, double vlrTransferencia) {
            Optional<Conta> contaDeT = contaRepository.findByNumero(contaDe);
            Optional<Conta> contaParaT = contaRepository.findByNumero(contaPara);
            Conta contaTransfDe = contaDeT.get();
            Conta contaTransfPara = contaParaT.get();
            if((contaDeT.isPresent() && contaTransfDe.getBloqueio() != true) && (contaParaT.isPresent() && contaTransfPara.getBloqueio() != true)) {
                contaTransfDe.setSaldo(contaTransfDe.getSaldo() - vlrTransferencia);
                contaTransfPara.setSaldo(contaTransfPara.getSaldo() + vlrTransferencia);
                contaRepository.save(contaTransfDe);
                contaRepository.save(contaTransfPara);
                logTransacaoService.salvaLogTransacao(contaTransfDe.getId(),contaTransfDe.getDataCriacaoConta(),
                        contaTransfDe.getNumero(),contaTransfDe.getSaldo(), "Transferencia Debito");
                logTransacaoService.salvaLogTransacao(contaTransfPara.getId(),contaTransfPara.getDataCriacaoConta(),
                        contaTransfPara.getNumero(),contaTransfPara.getSaldo(), "Transferencia Credito");

                return  contaDeT;
            }
            //TODO criar exception throw new EmptyResultDataAccessException(1);
            return  null;
    }
}
