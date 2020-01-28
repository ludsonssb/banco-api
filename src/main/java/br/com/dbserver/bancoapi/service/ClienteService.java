package br.com.dbserver.bancoapi.service;

import br.com.dbserver.bancoapi.controller.dto.AlteraClienteDTO;
import br.com.dbserver.bancoapi.controller.dto.ClienteDTO;
import br.com.dbserver.bancoapi.controller.dto.NovoClienteDTO;
import br.com.dbserver.bancoapi.model.Cliente;
import br.com.dbserver.bancoapi.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Optional<Cliente> buscarClientePeloCodigo(Long id) {
        Optional<Cliente> clienteSalvo = clienteRepository.findById(id);
        if (clienteSalvo == null) {
            //TODO criar exception throw new EmptyResultDataAccessException(1);
        }
        return clienteSalvo;
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
}
