package br.com.ludsonbrandao.bancoapi.service;

import br.com.ludsonbrandao.bancoapi.controller.dto.*;
import br.com.ludsonbrandao.bancoapi.exceptions.ClienteNaoEncontradoException;
import br.com.ludsonbrandao.bancoapi.model.Cliente;
import br.com.ludsonbrandao.bancoapi.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public ClienteResponse buscarClientePeloCodigo(Long id) {
        return clienteRepository.findById(id)
                .map(ClienteResponse::new)
                .orElseThrow(ClienteNaoEncontradoException::new);
    }

    public ClienteResponse cadastroCliente(ClienteRequest novoCliente){
        Cliente cliente = novoCliente.converter();
        clienteRepository.save(cliente);
        return new ClienteResponse(cliente);
    }

    public Optional<Cliente> atualizaClientePorCpf(String cpf, AlteraClienteDTO alteraClienteDTO){
        return clienteRepository.findByCpf(cpf)
                .map(cliente -> {
                    cliente.setNome(alteraClienteDTO.getNome());
                    clienteRepository.save(cliente);
                    return Optional.of(cliente);
                })
                .orElse(Optional.empty());
    }

    public List<ClienteIdContaDTO> listaClienteIdContas() {

        List<Cliente> idContaCliente = clienteRepository.findAll();
        return idContaCliente.stream().map(ClienteIdContaDTO::new).collect(Collectors.toList());

        //TODO criar exception throw new EmptyResultDataAccessException(1);
    }

    public List<ClienteSaldoContaDTO> listaClienteSaldoContas() {

        List<Cliente> saldoContaCliente = clienteRepository.findAll();
        return saldoContaCliente.stream().map(ClienteSaldoContaDTO::new).collect(Collectors.toList());

        //TODO criar exception throw new EmptyResultDataAccessException(1);
    }

}
