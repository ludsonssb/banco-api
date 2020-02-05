package br.com.dbserver.bancoapi.service;

import br.com.dbserver.bancoapi.controller.dto.SaldoContaDTO;
import br.com.dbserver.bancoapi.controller.dto.ContaDTO;
import br.com.dbserver.bancoapi.controller.dto.NovaContaDTO;
import br.com.dbserver.bancoapi.model.Cliente;
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

    private void validaCliente(NovaContaDTO novaContaDTO) {
        Optional<Cliente> clienteDTO = null;
        if(novaContaDTO.getCliente().getId() != null) {
            clienteDTO = clienteService.buscarClientePeloCodigo(novaContaDTO.getCliente().getId());
        }
        //TODO fazer uma exception
    }

    public ContaDTO salvarConta(NovaContaDTO novaContaDTO) {
        validaCliente(novaContaDTO);
        Conta conta = novaContaDTO.converterConta();
        Conta contaSalva = contaRepository.save(conta);
        return new ContaDTO(contaSalva);
    }

    public Optional<Conta> despositoPorConta(Long contaDep, double vlrDeposito){
        Optional<Conta> contaDeposito = contaRepository.findByConta(contaDep);
        Conta conta = contaDeposito.get();
        if(contaDeposito.isPresent() && conta.getBloqueio() != true) {
            conta.setSaldo(conta.getSaldo() + vlrDeposito);
            contaRepository.save(conta);
            logTransacaoService.salvaLogTransacao(conta.getId(),conta.getDataCriacaoConta(),
                    conta.getConta(),conta.getSaldo(), "Deposito");
            return  contaDeposito;
        }
        //TODO criar exception throw new EmptyResultDataAccessException(1);
        return  null;
    }

    public Optional<Conta> buscaSaldoPorConta(Long conta) {
        Optional<Conta> contaSalva = contaRepository.findByConta(conta);
        if (contaSalva == null) {
            //TODO criar exception throw new EmptyResultDataAccessException(1);
        }
        return contaSalva;
    }

    public Optional<Conta> saquePorConta(Long contaSaq, double vlrSaque) {
        Optional<Conta> contaSaque = contaRepository.findByConta(contaSaq);
        Conta conta = contaSaque.get();
        if(contaSaque.isPresent() && conta.getBloqueio() != true) {
            conta.setSaldo(conta.getSaldo() - vlrSaque);
            contaRepository.save(conta);
            logTransacaoService.salvaLogTransacao(conta.getId(),conta.getDataCriacaoConta(),
                    conta.getConta(),conta.getSaldo(), "Saque");
            return  contaSaque;
        }
        //TODO criar exception throw new EmptyResultDataAccessException(1);
        return  null;
    }

    public Optional<Conta> atualizarBloqueio(Long contaBlq, Boolean bloqueio) {
        Optional<Conta> contaBloqueio = contaRepository.findByConta(contaBlq);
        if(contaBloqueio.isPresent()) {
            Conta conta = contaBloqueio.get();
            conta.setBloqueio(bloqueio);
            contaRepository.save(conta);
            logTransacaoService.salvaLogTransacao(conta.getId(),conta.getDataCriacaoConta(),
                    conta.getConta(),conta.getSaldo(), "Bloqueio/Desbloqueio");
            return  contaBloqueio;
        }
        //TODO criar exception throw new EmptyResultDataAccessException(1);
        return  null;
    }

    public Optional<Conta> transferenciaEntreContas(Long contaDe, Long contaPara, double vlrTransferencia) {
            Optional<Conta> contaDeT = contaRepository.findByConta(contaDe);
            Optional<Conta> contaParaT = contaRepository.findByConta(contaPara);
            Conta contaTransfDe = contaDeT.get();
            Conta contaTransfPara = contaParaT.get();
            if((contaDeT.isPresent() && contaTransfDe.getBloqueio() != true) && (contaParaT.isPresent() && contaTransfPara.getBloqueio() != true)) {
                contaTransfDe.setSaldo(contaTransfDe.getSaldo() - vlrTransferencia);
                contaTransfPara.setSaldo(contaTransfPara.getSaldo() + vlrTransferencia);
                contaRepository.save(contaTransfDe);
                contaRepository.save(contaTransfPara);
                logTransacaoService.salvaLogTransacao(contaTransfDe.getId(),contaTransfDe.getDataCriacaoConta(),
                        contaTransfDe.getConta(),contaTransfDe.getSaldo(), "Transferencia Debito");
                logTransacaoService.salvaLogTransacao(contaTransfPara.getId(),contaTransfPara.getDataCriacaoConta(),
                        contaTransfPara.getConta(),contaTransfPara.getSaldo(), "Transferencia Credito");

                return  contaDeT;
            }
            //TODO criar exception throw new EmptyResultDataAccessException(1);
            return  null;
    }

    public SaldoContaDTO somaTotal(){
        SaldoContaDTO alteraContaDTO = new SaldoContaDTO();
        List<Conta> streamContas = contaRepository.findAll();

        Double somaSaldo = streamContas.stream().mapToDouble(c -> c.getSaldo()).sum();

        alteraContaDTO.setSaldo(somaSaldo);

        return alteraContaDTO;
    }

}
