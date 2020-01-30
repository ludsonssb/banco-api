package br.com.dbserver.bancoapi.service;

import br.com.dbserver.bancoapi.controller.dto.AlteraClienteDTO;
import br.com.dbserver.bancoapi.controller.dto.AlteraContaDTO;
import br.com.dbserver.bancoapi.controller.dto.ContaDTO;
import br.com.dbserver.bancoapi.controller.dto.NovaContaDTO;
import br.com.dbserver.bancoapi.model.Cliente;
import br.com.dbserver.bancoapi.model.Conta;
import br.com.dbserver.bancoapi.repository.ClienteRepository;
import br.com.dbserver.bancoapi.repository.ContaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ContaService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ContaRepository contaRepository;

    @Autowired
    private ClienteService clienteService;

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
            if(contaDeT.isPresent() && contaParaT.isPresent() && contaTransfDe.getBloqueio() != true && contaTransfPara.getBloqueio() != true) {
                contaTransfDe.setSaldo(contaTransfDe.getSaldo() - vlrTransferencia);
                contaTransfPara.setSaldo(contaTransfPara.getSaldo() + vlrTransferencia);

                contaRepository.save(contaTransfDe);
                contaRepository.save(contaTransfPara);

                return  contaDeT;
            }
            //TODO criar exception throw new EmptyResultDataAccessException(1);
            return  null;
    }
}
