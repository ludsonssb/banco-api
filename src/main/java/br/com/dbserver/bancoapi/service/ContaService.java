package br.com.dbserver.bancoapi.service;

import br.com.dbserver.bancoapi.controller.dto.ClienteDTO;
import br.com.dbserver.bancoapi.controller.dto.NovaContaDTO;
import br.com.dbserver.bancoapi.model.Cliente;
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
}
