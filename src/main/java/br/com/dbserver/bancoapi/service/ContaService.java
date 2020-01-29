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

    public Optional<Conta> despositoPorConta(Long contaDep, int vlrDeposito, AlteraContaDTO alteraContaDTO){
        Optional<Conta> contaDeposito = contaRepository.findByConta(contaDep);
        if(contaDeposito.isPresent()) {
            Conta conta = contaDeposito.get();
            conta.setSaldo(conta.getSaldo() + vlrDeposito);
            contaRepository.save(conta);
            return  contaDeposito;
        }
        //TODO criar exception throw new EmptyResultDataAccessException(1);
        return  null;
    }
}
