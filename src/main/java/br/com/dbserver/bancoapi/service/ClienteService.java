package br.com.dbserver.bancoapi.service;

import br.com.dbserver.bancoapi.controller.dto.AlteraClienteDTO;
import br.com.dbserver.bancoapi.controller.dto.ClienteDTO;
import br.com.dbserver.bancoapi.controller.dto.ClienteIdContaDTO;
import br.com.dbserver.bancoapi.controller.dto.ClienteSaldoContaDTO;
import br.com.dbserver.bancoapi.exceptions.ClienteNaoEncontradoException;
import br.com.dbserver.bancoapi.model.Cliente;
import br.com.dbserver.bancoapi.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public ClienteDTO buscarClientePeloCodigo(Long id) {
        return clienteRepository.findById(id)
                .map(ClienteDTO::new)
                .orElseThrow(ClienteNaoEncontradoException::new);
    }

    /*public ClienteDTO criaCliente(NovoClienteDTO novoClienteDTO){
        Cliente cliente = novoClienteDTO.converter();
        Cliente clienteSalvo = clienteRepository.save(cliente);
        return new ClienteDTO(clienteSalvo);
    }*/

    public Optional<Cliente> atualizaClientePorCpf(String cpf, AlteraClienteDTO alteraClienteDTO){
        Optional<Cliente> clienteAtualiza = clienteRepository.findByCpf(cpf);
        if(clienteAtualiza.isPresent()) {
            Cliente cliente = clienteAtualiza.get();
            cliente.setNome(alteraClienteDTO.getNome());
            clienteRepository.save(cliente);
            return  clienteAtualiza;
        }
        //TODO criar exception throw new EmptyResultDataAccessException(1);
        return  null;
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
